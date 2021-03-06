package br.jp.redsparrow_zsdemo.game.objecttypes.spawnpoint;

import br.jp.redsparrow_zsdemo.engine.GameObject;
import br.jp.redsparrow_zsdemo.engine.Updatable;
import br.jp.redsparrow_zsdemo.engine.components.Component;
import br.jp.redsparrow_zsdemo.engine.game.Game;
import br.jp.redsparrow_zsdemo.game.objecttypes.basicenemy.EnemyStatsComponent;

public class SpawnPointComponent extends Component implements Updatable {

	private String[] mSpawnTypeNames;

	private int mCurType = 0;

	private int mSpawnInterval;
	private int mSpawnAmmount;
	private int counter = 0;
	private int counter2 = 0;

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
			
			mSpawnAmmount+=counter2/2;
			if(mSpawnAmmount >= 6) {
				mSpawnAmmount=2;
				mCurType++;
				if(mCurType>mSpawnTypeNames.length-1) mCurType = mSpawnTypeNames.length-1;
			}
			
			for (int i = 0; i < mSpawnAmmount; i++) {
				GameObject go = game.getObjFactory().create(mSpawnTypeNames[mCurType], parent.getX(), parent.getY());
				((EnemyStatsComponent)go.getUpdatableComponent("Stats")).setHealth(((EnemyStatsComponent)go.getUpdatableComponent("Stats")).getHealth()+counter2);
				game.getWorld().addObject(go);
			}
			
			counter = 0;
			counter2++;
			
		}else counter++;

	}

}
