package br.jp.redsparrow.engine.core.tilemaps;

import br.jp.redsparrow.engine.core.Renderable;
import br.jp.redsparrow.engine.core.VertexArray;


public class Tile implements Renderable {

	private float mX;
	private float mY;
	private char t;
	
	public Tile(float x, float y, char t) {
		setX(x);
		setY(y);
		this.setT(t);
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

	public char getT() {
		return t;
	}

	public void setT(char t) {
		this.t = t;
	}
	
}
