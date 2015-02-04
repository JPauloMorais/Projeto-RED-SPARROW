package br.jp.redsparrow.engine.shaders;

import android.content.Context;
import android.opengl.GLES20;
import br.jp.redsparrow.R;

public class ParticleShaderProg extends  ShaderProg {

	private final int uMatrixLocation;
	private final int uTimeLocation;

	private final int aPositionLocation;
	private final int aColorLocation;
	private final int aDirectionVectorLocation;
	private final int aParticleStartTimeLocation;

	public ParticleShaderProg(Context context) {
		super(context, R.raw.particle_vertex,
				R.raw.particle_fragment);

		uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
		uTimeLocation = GLES20.glGetUniformLocation(program, U_TIME);

		aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
		aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
		aDirectionVectorLocation = GLES20.glGetAttribLocation(program, A_DIRECTION_VECTOR);
		aParticleStartTimeLocation = GLES20.glGetAttribLocation(program, A_PARTICLE_START_TIME);

	}

	public void setUniforms(float[] matrix, float elapsedTime) {

		GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		GLES20.glUniform1f(uTimeLocation, elapsedTime);

	}

	public int getPositionAttributeLocation() {
		return aPositionLocation;
	}
	
	public int getColorAttributeLocation() {
		return aColorLocation;
	}
	
	public int getDirectionVectorAttributeLocation() {
		return aDirectionVectorLocation;
	}
	
	public int getParticleStartTimeAttributeLocation() {
		return aParticleStartTimeLocation;
	}
}
