package br.jp.redsparrow_zsdemo.engine;

public class Point2f {

	private float mX;
	private float mY;
	
	public Point2f(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public String toString(){
		return "(" + mX + "," + mY + ")";
	}
	
	public Point2f copy() {
		return new Point2f(mX, mY);
	}
	
	public void set(Point2f p) {
		setX(p.getX());
		setY(p.getY());
	}
	
	public void set(float x, float y) {
		setX(x);
		setY(y);
	}

	public float getX() {
		return mX;
	}

	public void setX(float x) {
		this.mX = x;
	}

	public float getY() {
		return mY;
	}

	public void setY(float y) {
		this.mY = y;
	}

}
