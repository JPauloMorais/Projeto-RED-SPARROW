package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.AnimatedSpriteComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;

public class EnemyStatsComponent extends StatsComponent {

	public EnemyStatsComponent(GameObject parent, int health) {
		super(parent, health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameObject object) {
		super.update(object);
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
