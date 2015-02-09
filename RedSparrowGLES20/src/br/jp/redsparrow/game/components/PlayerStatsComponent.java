package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.Game;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.AnimatedSpriteComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;

public class PlayerStatsComponent extends StatsComponent {
	
	public PlayerStatsComponent(GameObject parent, int health) {
		super(parent, health);

	}

	@Override
	public void update(Game game, GameObject object) {
		super.update(game, object);
	}

	@Override
	protected void die() {
		if (!isDying) {
			((AnimatedSpriteComponent) mParent.getRenderableComponent(0)).setCurAnim(1);
			isDying = true;
		}
		else if(((AnimatedSpriteComponent) mParent.getRenderableComponent(0)).getAnimation(1).hasLoopedOnce()) 
			mParent.die();
	}

}
