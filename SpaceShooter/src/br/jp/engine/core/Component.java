package br.jp.engine.core;

import javax.microedition.khronos.opengles.GL10;

public abstract class Component {
	
	public static String mName;
	protected GameObject mParent;
	
	public Component(String name){
		setName(name);
		mParent = null;
	}
	
	public void update(GL10 gl, float x, float y) {}
	public void render (GL10 gl){}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}

	public GameObject getmParent() {
		return mParent;
	}

	public void setmParent(GameObject mParent) {
		this.mParent = mParent;
	}

	public void update(GL10 gl) {		
	}
}
