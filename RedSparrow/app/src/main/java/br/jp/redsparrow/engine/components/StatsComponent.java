package br.jp.redsparrow.engine.components;

import br.jp.redsparrow.engine.GameObject;
import br.jp.redsparrow.engine.Updatable;
import br.jp.redsparrow.engine.game.Game;

public abstract class StatsComponent extends Component implements Updatable {
	
	protected boolean isDying;
	protected int mHealth;
	protected int mCurHealth;
	
	public StatsComponent(GameObject parent, int health) {
		super(parent);
		
		mHealth = health;
		mCurHealth = health;
		isDying = false;
		
	}
	
	@Override
	public void update(Game game, GameObject object) {
		if(mCurHealth <= 0) die(game);
	}

	protected abstract void die(Game game);

	public int getCurHealth() {
		return mCurHealth;
	}

	public void setCurHealth(int mCurHealth) {
		this.mCurHealth = mCurHealth;
	}
	
	public void takeDamage(int damage) {
		mCurHealth -= damage;
	}

	public boolean isDying() {
		return isDying;
	}

	public void setIsDying(boolean isDying) {
		this.isDying = isDying;
	}

	public int getHealth() {
		return mHealth;
	}

	public void setHealth(int mHealth) {
		this.mHealth = mHealth;
	}

}
