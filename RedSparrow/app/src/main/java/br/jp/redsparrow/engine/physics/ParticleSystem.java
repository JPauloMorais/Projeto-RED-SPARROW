package br.jp.redsparrow.engine.physics;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import br.jp.redsparrow.engine.Assets;
import br.jp.redsparrow.engine.Consts;
import br.jp.redsparrow.engine.math.Vec2;
import br.jp.redsparrow.engine.misc.AutoArray;
import br.jp.redsparrow.engine.rendering.GPU;

/**
 * Created by JoaoPaulo on 12/10/2015.
 */
public class ParticleSystem
{
	private final int POSITION_DATA_SIZE  = 2;
	private final int TOTAL_DATA_SIZE     = POSITION_DATA_SIZE + 1;
	private final int STRIDE              = TOTAL_DATA_SIZE * Consts.BYTES_PER_FLOAT;

	private final int textureId;

	private long startTime;
	private long curTime;

	private final int maxParticleCount;
	private       int currentParticleCount;
	private       int nextParticle;

	private AutoArray<Particle> particles;
	private FloatBuffer renderDataBuffer;

	public ParticleSystem (String imgName, int maxParticleCount)
	{
		Bitmap b = Assets.getBitmap(imgName);
		textureId = GPU.loadBitmap(b);
		renderDataBuffer = ByteBuffer
				.allocateDirect(maxParticleCount * TOTAL_DATA_SIZE * Consts.BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		renderDataBuffer.position(0);
		this.maxParticleCount = maxParticleCount;
		particles = new AutoArray<Particle>(maxParticleCount);
	}

	public void addParticle (Vec2 position, Vec2 direction)
	{
		nextParticle++;
		if (currentParticleCount < maxParticleCount) currentParticleCount++;
		if (nextParticle == maxParticleCount) nextParticle = 0;


//		particleData[currentOffset++] = position.x;
//		particleData[currentOffset++] = position.y;
//
//		particleData[currentOffset++] = direction.x;
//		particleData[currentOffset++] = direction.y;
//
//		renderDataBuffer.position(particleOffset);
//		renderDataBuffer.put(0,0.0f);
//		renderDataBuffer.put(particleData, particleOffset, TOTAL_DATA_SIZE);
//		renderDataBuffer.position(0);
	}

	public void integrate (final float delta)
	{
		int curOffset = 0;
		for (int i = 0; i < particles.size; i++)
		{
			final Particle p = particles.get(i);

			p.lifetime -= delta;
			if(p.lifetime > 0.0f)
			{
				Vec2.add(p.loc, p.vel, delta, p.loc);

				renderDataBuffer.position(curOffset);
				renderDataBuffer.put(new float[] { p.loc.x, p.loc.y, p.lifetime });
				curOffset += TOTAL_DATA_SIZE;
			}
			else
			{
				particles.remove(i);
			}
		}
	}
}
