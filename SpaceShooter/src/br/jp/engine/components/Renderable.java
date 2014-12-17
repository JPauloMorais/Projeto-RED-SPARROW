package br.jp.engine.components;

import javax.microedition.khronos.opengles.GL10;

import br.jp.engine.core.GameObject;

public interface Renderable {
//	public void render();
//	public void render(GL10 gl);
	public void render(GL10 gl, GameObject object);
}
