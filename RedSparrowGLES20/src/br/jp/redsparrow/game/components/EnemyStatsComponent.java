package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.AnimatedSpriteComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;
import br.jp.redsparrow.engine.core.game.Game;

public class EnemyStatsComponent extends StatsComponent {

	public EnemyStatsComponent(GameObject parent, int health) {
		super(parent, health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Game game, GameObject object) {
		super.update(game, object);
	}

	@Override
	protected void die() {
		if (!isDying) {
			((AnimatedSpriteComponent) mParent.getRenderableComponent("AnimatedSprite")).setCurAnim(1);
			isDying = true;
		}
		else if(((AnimatedSpriteComponent) mParent.getRenderableComponent("AnimatedSprite")).getAnimation(1).hasLoopedOnce()) 
			mParent.die();
	}


}
