package br.jp.redsparrow.engine.core.particles;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import br.jp.redsparrow.engine.core.Consts;
import br.jp.redsparrow.engine.core.GameRenderer;
import br.jp.redsparrow.engine.core.VertexArray;

public class ParticleSystem{

	private final int POSITION_COMPONENT_COUNT = 3;
	private final int COLOR_COMPONENT_COUNT = 3;
	private final int VECTOR_COMPONENT_COUNT = 3;
	private final int PARTICLE_START_TIME_COMPONENT_COUNT = 1;
	private final int TOTAL_COMPONENT_COUNT =
			POSITION_COMPONENT_COUNT
			+ COLOR_COMPONENT_COUNT
			+ VECTOR_COMPONENT_COUNT
			+ PARTICLE_START_TIME_COMPONENT_COUNT;
	private final int STRIDE = TOTAL_COMPONENT_COUNT * Consts.BYTES_PER_FLOAT;

	private float[] particles;

	private VertexArray vertexArray;
	
	private long startTime;
	private float curTime;
	
	private int mMaxParticleCount;
	private int currentParticleCount;
	private int nextParticle;

	public ParticleSystem(int maxParticleCount, Context context) {

		particles = new float[maxParticleCount * TOTAL_COMPONENT_COUNT];
		vertexArray = new VertexArray(particles);
		mMaxParticleCount = maxParticleCount;
				
		startTime = System.nanoTime();

	}

	public void addParticle(float[] position, int color, float[] direction,
			float particleStartTime) {

		final int particleOffset = nextParticle * TOTAL_COMPONENT_COUNT;

		int currentOffset = particleOffset;
		nextParticle++;

		if (currentParticleCount < mMaxParticleCount) {
			currentParticleCount++;
		}

		if (nextParticle == mMaxParticleCount) {
			nextParticle = 0;
		}

		particles[currentOffset++] = position[0];
		particles[currentOffset++] = position[1];
		particles[currentOffset++] = position[2];

		particles[currentOffset++] = Color.red(color) / 255f;
		particles[currentOffset++] = Color.green(color) / 255f;
		particles[currentOffset++] = Color.blue(color) / 255f;

		particles[currentOffset++] = direction[0];
		particles[currentOffset++] = direction[1];
		particles[currentOffset++] = direction[2];
		particles[currentOffset++] = particleStartTime;

		vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);

	}

	public void bindData() {
		int dataOffset = 0;
		vertexArray.setVertexAttribPointer(dataOffset,
				GameRenderer.particleProgram.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT, STRIDE);
		dataOffset += POSITION_COMPONENT_COUNT;
		vertexArray.setVertexAttribPointer(dataOffset,
				GameRenderer.particleProgram.getColorAttributeLocation(),
				COLOR_COMPONENT_COUNT, STRIDE);
		dataOffset += COLOR_COMPONENT_COUNT;
		vertexArray.setVertexAttribPointer(dataOffset,
				GameRenderer.particleProgram.getDirectionVectorAttributeLocation(),
				VECTOR_COMPONENT_COUNT, STRIDE);
		dataOffset += VECTOR_COMPONENT_COUNT;
		vertexArray.setVertexAttribPointer(dataOffset,
				GameRenderer.particleProgram.getParticleStartTimeAttributeLocation(),
				PARTICLE_START_TIME_COMPONENT_COUNT, STRIDE);
	}

	public void render(float[] projectionMatrix) {
		
		curTime = (System.nanoTime() - startTime) / 1000000000f;
		
		GameRenderer.particleProgram.useProgram();
		GameRenderer.particleProgram.setUniforms(projectionMatrix, curTime);
		bindData();
		
		GLES20.glDrawArrays(GLES20.GL_POINTS, 0, currentParticleCount);
		
	}

	public float getCurTime() {
		return curTime;
	}
}
