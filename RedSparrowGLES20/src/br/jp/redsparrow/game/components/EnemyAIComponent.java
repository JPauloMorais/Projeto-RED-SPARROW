package br.jp.redsparrow.game.components;

import java.util.Random;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.AIComponent;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.game.Game;

public class EnemyAIComponent extends AIComponent {

	private int frames = 0;
	private int shootInterval;
	
	public EnemyAIComponent(GameObject parent) {
		super(parent);
		mMaxSteer = parent.getWidth();
		shootInterval = 80+new Random().nextInt(100);
	}

	@Override
	public void update(Game game, GameObject parent) {

		seek(game.getWorld().getPlayer().getPosition());
//				flee(game.getWorld().getPlayer().getPosition());
		//		seekTillDistance(game.getWorld().getPlayer().getPosition(), 1);
		((EnemyPhysicsComponent) parent.getUpdatableComponent("Physics")).applyForce(mSteerForce);
		mSteerForce.setX(0);
		mSteerForce.setY(0);

		if (frames==shootInterval) {
			((SoundComponent) parent.getUpdatableComponent("Sound"))
					.setSoundVolume(0, 0.05f, 0.05f);
			((SoundComponent) parent.getUpdatableComponent("Sound"))
					.startSound(0, false);
			((GunComponent) parent.getUpdatableComponent("Gun")).shoot(
					game.getWorld().getPlayer().getPosition().sub(parent.getPosition()));
			
			frames=0;
		}else frames++;

	}

}
