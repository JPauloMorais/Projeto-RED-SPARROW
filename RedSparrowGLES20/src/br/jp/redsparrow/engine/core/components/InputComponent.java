package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;

public class InputComponent extends Component implements Updatable {

	public InputComponent() {
		super("Input");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameObject object) {
		
	}
	
	public void doClick(GameObject object, Vector2f clickLocation){
		
	}
	
	public void doClickHold(GameObject object, Vector2f clickLocation){
		
	}
	
	public void doRotation(GameObject object){
		
	}

}
