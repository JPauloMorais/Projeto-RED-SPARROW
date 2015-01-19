package br.jp.redsparrow.engine.core.physics;

import br.jp.redsparrow.engine.core.Vector2f;

public class AABB implements Bounds {

	private Vector2f mCenter;
	private float mWidth;
	private float mHeight;
	
	public AABB(Vector2f pos, float w, float h) {
		setCenter(pos);
		mWidth = w;
		mHeight = h;
	}
	
	@Override
	public float getWidth() {
		return mWidth;
	}
	
	@Override
	public float getHeight() {
		return mHeight;
	}

	@Override
	public void setWidth(float w) {
		this.mWidth = w;
	}
	
	@Override
	public void setHeight(float h) {
		this.mHeight = h;
	}

	@Override
	public Vector2f getCenter() {
		return mCenter;
	}

	@Override
	public void setCenter(Vector2f mCenter) {
		this.mCenter = mCenter;
	}
	
}
