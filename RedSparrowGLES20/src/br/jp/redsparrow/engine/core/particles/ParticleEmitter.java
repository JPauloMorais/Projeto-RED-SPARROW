package br.jp.redsparrow.engine.core.particles;

import java.util.Random;

import android.opengl.Matrix;


public class ParticleEmitter {

	private float[] mPosition;
//	private final float[] mDirection;
	private final int mColor;

	private final float mAngleVar;
	private final float mSpeedVar;
	private final Random mRandom = new Random();
	private float[] mRotMatrix = new float[16];
	private float[] mDirVector = new float[4];
	private float[] mResVector = new float[4];

	public ParticleEmitter(float[] position, float[] direction, int color, float angleVar, float speedVar) {

		mPosition = position;
//		mDirection = direction;
		mColor = color;

		mAngleVar = angleVar;
		mSpeedVar = speedVar;

		mDirVector[0] = direction[0];
		mDirVector[1] = direction[1];
		mDirVector[2] = direction[2];

	}

	public void addParticles(ParticleSystem particleSystem, float curTime, int count) {
		for (int i = 0; i < count; i++) {

			Matrix.setRotateEulerM(mRotMatrix, 0,
					(mRandom.nextFloat() - 0.5f) * mAngleVar,
					(mRandom.nextFloat() - 0.5f) * mAngleVar,
					(mRandom.nextFloat() - 0.5f) * mAngleVar);
			
			Matrix.multiplyMV(
					mResVector, 0,
					mRotMatrix, 0,
					mDirVector, 0);
			
			float speedAdj = 1f + mRandom.nextFloat() * mSpeedVar;
			
			float[] curDir = {
					mResVector[0] * speedAdj,
					mResVector[1] * speedAdj,
					mResVector[2] * speedAdj
					};

			particleSystem.addParticle(mPosition, mColor, curDir, curTime);

		}
	}
	
	public void setPosition(float[] pos) {
		mPosition = pos;
	}

}
