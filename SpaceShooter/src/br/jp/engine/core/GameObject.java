package br.jp.engine.core;

import android.graphics.Canvas;



public class GameObject {
	
	private int id;
	private float mX, mY;
	private float mWidth, mHeight;
	private Component[] mComponents;
	
	public GameObject(float x, float y, float size){ this(x, y, size, size); }
	
	public GameObject(float x, float y, float width, float height){ this(x, y, width, height, null); }
	
	public GameObject(float x, float y, float width, float height, Component[] components) {
		super();
		id = 0;
		mX = x;
		mY = y;
		mWidth = width;
		mHeight = height;
		mComponents = components;
	}
	
	public void update(Canvas canvas) {
		for (Component component : mComponents) {
			component.update(canvas);
		}
	}
	public void render() {
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

	public float getWidth() {
		return mWidth;
	}

	public void setWidth(float mWidth) {
		this.mWidth = mWidth;
	}

	public float getHeight() {
		return mHeight;
	}

	public void setHeight(float mHeight) {
		this.mHeight = mHeight;
	}

	public Component[] getComponents() {
		return mComponents;
	}
	public Component getComponent(String name) {
		for (Component component : mComponents) {
			if (component.getName().equals(name)) return component;
		}
		return null;
	}

	public void setComponents(Component[] mComponents) {
		this.mComponents = mComponents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
