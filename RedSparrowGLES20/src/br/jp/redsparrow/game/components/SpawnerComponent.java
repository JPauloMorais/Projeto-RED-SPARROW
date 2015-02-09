package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.Game;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.Component;
import br.jp.redsparrow.game.ObjectFactory.OBJECT_TYPE;

public class SpawnerComponent extends Component implements Updatable {

	public SpawnerComponent(String name, GameObject parent) {
		super(name, parent);
	}
	
	@Override
	public void update(Game game, GameObject object) {
		
	}
	
	public void spawn(OBJECT_TYPE type, int ammount, Vector2f location) {
		
		for (int i = 0; i < ammount; i++) {
//			GameObject obj = ObjectFactory.createObject(GameRenderer.getContext(), type, location.getX(), location.getY());
//			World.addObject(obj);
		}
		
	}
	
	public void spawn(OBJECT_TYPE type, int ammount, Vector2f ... locations) {
		
//		for (int i = 0; i < ammount; i++) {
//			GameObject obj = ObjectFactory.createObject(GameRenderer.getContext(), type, locations[(i <= locations.length ? i : 0)].getX(), locations[(i <= locations.length ? i : 0)].getY());
//			World.addObject(obj);
//		}
		
	}

}
