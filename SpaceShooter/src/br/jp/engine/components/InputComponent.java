package br.jp.engine.components;

import javax.microedition.khronos.opengles.GL10;

import br.jp.engine.core.Component;
import br.jp.engine.core.GameObject;

public abstract class InputComponent extends Component implements Updatable {

	public InputComponent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract void update(GL10 gl, GameObject object);
	

}
