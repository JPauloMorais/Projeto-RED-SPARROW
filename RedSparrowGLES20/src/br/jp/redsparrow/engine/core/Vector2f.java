package br.jp.redsparrow.engine.core;

public class Vector2f {

	private float mX;
	private float mY;
	
	public Vector2f(float x, float y){
		setX(x);
		setY(y);
	}
	
	public Vector2f copy(){
		return new Vector2f(mX, mY);
	}
	
	public float length(){
		return (float) Math.sqrt((mX*mX) + (mY*mY));
	}
	
	public float dot(Vector2f v){
		return mX * v.getX() + mY * v.getY();
	}
	
	public Vector2f normalize(){
		float length = length();
		
		mX /= length;
		mY /= length;
		
		return this;
	}
	
	public Vector2f rotate(float angle){
		
		double aRad = Math.toRadians(angle);
		double sen = Math.sin(aRad);
		double cos = Math.cos(aRad);
		
		return new Vector2f((float)(mX * cos - mY * sen), (float)(mX * sen + mY * cos));
	}
	
	public Vector2f add(Vector2f v){
		return new Vector2f(mX + v.getX(), mY + v.getY());
	}
	
	public Vector2f add(float n){
		return new Vector2f(mX + n, mY + n);
	}
	
	public Vector2f sub(Vector2f v){
		return new Vector2f(mX - v.getX(), mY - v.getY());
	}
	
	public Vector2f sub(float n){
		return new Vector2f(mX - n, mY - n);
	}
	
	public Vector2f mult(Vector2f v){
		return new Vector2f(mX * v.getX(), mY * v.getY());
	}
	
	public Vector2f mult(float n){
		return new Vector2f(mX * n, mY * n);
	}
	
	public Vector2f div(Vector2f v){
		return new Vector2f(mX / v.getX(), mY / v.getY());
	}
	
	public Vector2f div(float n){
		return new Vector2f(mX / n, mY / n);
	}
	
	public String toString(){
		return "(" + mX + "," + mY + ")";
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
