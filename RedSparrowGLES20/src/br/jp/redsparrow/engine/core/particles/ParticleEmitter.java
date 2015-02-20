package br.jp.redsparrow.engine.core.particles;

import java.util.Random;

import android.opengl.Matrix;


public class ParticleEmitter {

	private float[] mPosition;
	private int mColor;

	private float mAngleVar;
	private float mSpeedVar;
	private final Random mRandom = new Random();
	private float[] mRotMatrix = new float[16];
	private float[] mDirVector = new float[4];
	private float[] mResVector = new float[4];

	public ParticleEmitter(float[] position, float[] direction, int color, float dispAngle, float speedVar) {

		mPosition = position;
		mColor = color;

		mAngleVar = dispAngle;
		mSpeedVar = speedVar;

		mDirVector[0] = direction[0];
		mDirVector[1] = direction[1];
		mDirVector[2] = direction[2];

	}

	public void addParticles(ParticleSystem system, float curTime, int count) {
		for (int i = 0; i < count; i++) {
				
				Matrix.setRotateEulerM(mRotMatrix, 0,
						(mRandom.nextFloat() - 0.5f) * mAngleVar,
						(mRandom.nextFloat() - 0.5f) * mAngleVar,
						(mRandom.nextFloat() - 0.5f) * mAngleVar);
				Matrix.multiplyMV(mResVector, 0, mRotMatrix, 0, mDirVector, 0);
				float speedAdj = 1f + mRandom.nextFloat() * mSpeedVar;
				float[] curDir = { mResVector[0] * speedAdj,
						mResVector[1] * speedAdj, mResVector[2] * speedAdj };
				system.addParticle(mPosition, mColor, curDir, curTime);
			
		}
	}

	public void setPosition(float x, float y, float z) {
		mPosition[0] = x;
		mPosition[1] = y;
		mPosition[2] = z;
	}

	public void setDirection(float x, float y, float z) {
		mDirVector[0] = x;
		mDirVector[1] = y;
		mDirVector[2] = z;
	}

	public void setColor(int color) {
		this.mColor = color;
	}

	public void setDispAngle(float dispAngle) {
		this.mAngleVar = dispAngle;
	}

	public void setSpeed(float speedVar) {
		this.mSpeedVar = speedVar;
	}

}
