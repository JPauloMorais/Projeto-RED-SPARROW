package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;

public class LifeComponent extends Component implements Updatable {
	
	public LifeComponent(GameObject parent) {
		super("Life", parent);
		
	}
	
	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
	}
	
	public void die() {
		mParent.die();
	}

}
