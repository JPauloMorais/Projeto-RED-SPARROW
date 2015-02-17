package br.jp.redsparrow.engine.core.tilemaps;

import br.jp.redsparrow.engine.core.Renderable;
import br.jp.redsparrow.engine.core.VertexArray;


public class Tile implements Renderable {

	private float mX;
	private float mY;
	
	public Tile(float x, float y) {
		setX(x);
		setY(y);
	}
	
	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {
		// TODO Auto-generated method stub
		
	}

	public float getX() {
		return mX;
	}

	public void setX(float mX) {
		this.mX = mX;
	}

	public float getY() {
		return mY;
	}

	public void setY(float mY) {
		this.mY = mY;
	}
	
}
