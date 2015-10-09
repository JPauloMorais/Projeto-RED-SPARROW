package br.jp.redsparrow_zsdemo.engine.particles;

import java.util.Random;

import android.opengl.Matrix;

import br.jp.redsparrow_zsdemo.engine.Vector3f;


public class ParticleEmitter {

	private Vector3f mPosition;
	private Vector3f mDirection;
	private int mColor;

	private float mAngleVar;
	private float mSpeedVar;
	private final Random mRandom = new Random();
	private float[] mRotMatrix = new float[16];
	private float[] mDirVector = new float[4];
	private float[] mResVector = new float[4];

	public ParticleEmitter(Vector3f position, Vector3f direction, int color, float dispAngle, float speedVar) {

		mPosition = position;
		mColor = color;

		mAngleVar = dispAngle;
		mSpeedVar = speedVar;

		mDirection = direction;
		mDirVector[0] = direction.x;
		mDirVector[1] = direction.y;
		mDirVector[2] = direction.z;

	}

	public void addParticles(ParticleSystem system, float curTime, int count) {
		for (int i = 0; i < count; i++) {

			Matrix.setRotateEulerM(mRotMatrix, 0,
					(mRandom.nextFloat() - 0.5f) * mAngleVar,
					(mRandom.nextFloat() - 0.5f) * mAngleVar,
					(mRandom.nextFloat() - 0.5f) * mAngleVar);
			Matrix.multiplyMV(mResVector, 0, mRotMatrix, 0, mDirVector, 0);
			float speedAdj = 1f + mRandom.nextFloat() * mSpeedVar;
			mDirection.x = mResVector[0] * speedAdj;
			mDirection.y = mResVector[1] * speedAdj;
			mDirection.z = mResVector[2] * speedAdj;
			system.addParticle(mPosition, mColor, mDirection, curTime);

		}
	}

	public void setPosition(float x, float y, float z) {
		mPosition.x = x;
		mPosition.y = y;
		mPosition.z = z;
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
