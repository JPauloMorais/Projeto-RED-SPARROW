package br.jp.redsparrow.game.components;

import android.util.Log;
import br.jp.redsparrow.engine.core.GameObject;
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
			damage(damage);
			Log.i("MessagingSystem", "" + mHealth);

		} catch (Exception e) {
		}
	}

	@Override
	protected void die() {
		mParent.die();
	}

}
