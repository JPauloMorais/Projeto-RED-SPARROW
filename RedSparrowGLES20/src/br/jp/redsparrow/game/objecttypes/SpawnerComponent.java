package br.jp.redsparrow.game.objecttypes;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.Component;
import br.jp.redsparrow.engine.core.game.Game;

public class SpawnerComponent extends Component implements Updatable {

	public static final String NAME = "Spawner";

	public SpawnerComponent(GameObject parent) {
		super(parent);
	}

	@Override
	public void update(Game game, GameObject object) {

	}

	public void spawn(Game game, String typeName, int ammount, Vector2f location) {

		for (int i = 0; i < ammount; i++) {
			GameObject obj = game.getObjFactory().create( typeName, location.getX(), location.getY() );
			game.getWorld().addObject(obj);
		}

	}

	public void spawn(Game game, String typeName, int ammount, Vector2f ... locations) {

				for (int i = 0; i < ammount; i++) {
					GameObject obj = game.getObjFactory().create(typeName , locations[(i <= locations.length ? i : 0)].getX(), locations[(i <= locations.length ? i : 0)].getY());
					game.getWorld().addObject(obj);
				}

	}

}
