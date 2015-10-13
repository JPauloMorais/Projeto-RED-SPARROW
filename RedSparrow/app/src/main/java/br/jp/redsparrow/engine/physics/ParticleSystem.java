package br.jp.redsparrow.engine.physics;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import br.jp.redsparrow.engine.Assets;
import br.jp.redsparrow.engine.Consts;
import br.jp.redsparrow.engine.math.Vec2;
import br.jp.redsparrow.engine.rendering.Renderer;

/**
 * Created by JoaoPaulo on 12/10/2015.
 */
public class ParticleSystem
{
	private final int POSITION_DATA_SIZE  = 2;
	private final int DIRECTION_DATA_SIZE = 2;
	private final int TOTAL_DATA_SIZE     = POSITION_DATA_SIZE + DIRECTION_DATA_SIZE;
	private final int STRIDE              = TOTAL_DATA_SIZE * Consts.BYTES_PER_FLOAT;

	private final int     textureId;
	private final float[] particleData;

	private long startTime;
	private long curTime;

	private final int maxParticleCount;
	private       int currentParticleCount;
	private       int nextParticle;

	private FloatBuffer particleDataBuffer;

	public ParticleSystem (String imgName, int maxParticleCount)
	{
		Bitmap b = Assets.getBitmap(imgName);
		textureId = Renderer.loadBitmap(b);
		particleData = new float[maxParticleCount * TOTAL_DATA_SIZE];
		particleDataBuffer = ByteBuffer
				.allocateDirect(particleData.length * Consts.BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(particleData);
		particleDataBuffer.position(0);
		this.maxParticleCount = maxParticleCount;
	}

	public void addParticle(Vec2 position, Vec2 direction)
	{
		final int particleOffset = nextParticle * TOTAL_DATA_SIZE;

		int currentOffset = particleOffset;
		nextParticle++;

		if (currentParticleCount < maxParticleCount) currentParticleCount++;

		if (nextParticle == maxParticleCount) nextParticle = 0;

		particleData[currentOffset++] = position.x;
		particleData[currentOffset++] = position.y;

		particleData[currentOffset++] = direction.x;
		particleData[currentOffset++] = direction.y;

		particleDataBuffer.position(particleOffset);
		particleDataBuffer.put(particleData, particleOffset, TOTAL_DATA_SIZE);
		particleDataBuffer.position(0);
	}

}
