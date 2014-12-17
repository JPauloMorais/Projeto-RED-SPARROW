package br.jp.engine.components;

import javax.microedition.khronos.opengles.GL10;

import br.jp.engine.core.GameObject;

public interface Updatable {	
//	public void update(); 
//	public void update(GL10 gl); 
	public void update(GL10 gl, GameObject object); 
}
