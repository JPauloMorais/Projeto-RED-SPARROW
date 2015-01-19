package br.jp.redsparrow.engine.core.physics;

import br.jp.redsparrow.engine.core.Vector2f;

public interface Bounds {
	
	public Vector2f getCenter();
	public void setCenter(Vector2f center);
	
	public float getWidth();
	public void setWidth(float w);
	
	public float getHeight();
	public void setHeight(float h);
	
}
