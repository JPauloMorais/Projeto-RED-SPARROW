package br.jp.redsparrow.game.components;

import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.Animation;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.AnimatedSpriteComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;

public class PlayerStatsComponent extends StatsComponent {
	
	public PlayerStatsComponent(GameObject parent, int health, int speed) {
		super(parent, health, speed);

	}

	@Override
	public void update(GameObject object) {
		super.update(object);

		try {
			
			int damage = Integer.valueOf(mParent.getMessage("Damage").getMessage().toString());
			takeDamage(damage);
			Log.i("MessagingSystem", "" + mHealth);

		} catch (Exception e) {
		}
		
		
	}

	@Override
	protected void die() {
		if(!isDiyng) {
			((AnimatedSpriteComponent) mParent.getUpdatableComponent(1)).addAnimation( R.drawable.explosion_test,new Animation(5, 4));
			isDiyng = true;
		}
		else if(((AnimatedSpriteComponent) mParent.getUpdatableComponent(1)).getAnimation(1).hasLoopedOnce()) mParent.die();
	}

}
