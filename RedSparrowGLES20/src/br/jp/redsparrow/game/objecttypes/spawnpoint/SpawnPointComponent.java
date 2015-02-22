package br.jp.redsparrow.game.objecttypes.spawnpoint;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.components.Component;
import br.jp.redsparrow.engine.core.game.Game;

public class SpawnPointComponent extends Component implements Updatable {

	private String[] mSpawnTypeNames;

	private int mSpawnInterval;
	private int mSpawnAmmount;
	private int counter = 0;

	public SpawnPointComponent(GameObject parent, int spawnInterval, int spawnAmmount, String ... spawnTypeNames) {
		super(parent);

		mSpawnTypeNames = spawnTypeNames;
		mSpawnInterval = spawnInterval;
		mSpawnAmmount = spawnAmmount;

	}

	@Override
	public void update(Game game, GameObject parent) {

		parent.setPosition(game.getWorld().getPlayer().getPosition().add(20));
		
		if(counter == mSpawnInterval) {
			
			for (int i = 0; i < mSpawnAmmount; i++) {
				game.getWorld().addObject(game.getObjFactory().create(mSpawnTypeNames[0], parent.getX(), parent.getY()));
			}
			
			counter = 0;
			
		}else counter++;

	}

}
