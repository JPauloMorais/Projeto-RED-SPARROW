package br.jp.redsparrow.engine.physics;

import br.jp.redsparrow.engine.Vector2f;

public class BCircle implements Bounds {

	private Vector2f mCenter;
	private float mRad;
	
	public BCircle(Vector2f center, float r) {
		
		mCenter = center;
		mRad = r;
	
	}
	
	@Override
	public Vector2f getCenter() {
		return mCenter;
	}

	@Override
	public void setCenter(Vector2f center) {
		this.mCenter = center;
	}

	@Override
	public float getWidth() {
		return mRad;
	}

	@Override
	public void setWidth(float w) {
		mRad = w;
	}

	@Override
	public float getHeight() {
		return mRad;
	}

	@Override
	public void setHeight(float h) {
		mRad = h;
	}

}
