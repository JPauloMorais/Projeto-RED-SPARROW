package br.jp.redsparrow.game.objecttypes.basicplayer;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.AnimatedSpriteComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.game.activities.PlayActivity;

public class PlayerStatsComponent extends StatsComponent {

	private int killPoints;

	public PlayerStatsComponent(GameObject parent, int health) {
		super(parent, health);
		killPoints = 0;
	}

	@Override
	public void update(Game game, GameObject object) {
		super.update(game, object);
	}

	@Override
	protected void die(Game game) {
		if (!isDying) {
			//			mParent.removeUpdatableComponent("Physics");
			((AnimatedSpriteComponent) mParent.getRenderableComponent("AnimatedSprite")).setCurAnim(1);
			isDying = true;
			((PlayActivity) game.getActivity()).setPoints(0);
		}
		else if(((AnimatedSpriteComponent) mParent.getRenderableComponent("AnimatedSprite")).getAnimation(1).hasLoopedOnce()) 
			mParent.die();
	}

	public void addKillPoints(Game game, int ammount) {
		setKillPoints(game, getKillPoints() + ammount);
	}

	public void removeKillPoints(Game game, int ammount) {
		setKillPoints(game, getKillPoints() - ammount);
	}

	public int getKillPoints() {
		return killPoints;
	}

	public void setKillPoints(Game game, int killPoints) {
		this.killPoints = killPoints;
		((PlayActivity) game.getActivity()).setPoints(this.killPoints);
		if(killPoints%100==0) ((PlayActivity) game.getActivity()).showUpgrade();
	}

}
