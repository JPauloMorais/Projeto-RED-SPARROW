package br.jp.engine.core;

import android.graphics.Canvas;

public abstract class Component {
	
	public static String mName;
	
	public Component(String name){
		setName(name);
	}
	
	public abstract void update(Canvas canvas);
	public void render (Canvas canvas){	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
}
