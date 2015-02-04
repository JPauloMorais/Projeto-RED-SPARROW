package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;

public abstract class StatsComponent extends Component implements Updatable {

	protected int mHealth;
	protected int mCurHealth;
	protected int mSpeed;
	protected boolean isDiyng;
	
	public StatsComponent(GameObject parent, int health, int speed) {
		super("Stats", parent);
		
		mHealth = health;
		mCurHealth = health;
		mSpeed = speed;
		isDiyng = false;
		
	}
	
	@Override
	public void update(GameObject object) {
		if(mHealth <= 0) die();
	}

	public synchronized int getCurHealth() {
		return mCurHealth;
	}

	public synchronized void setCurHealth(int mCurHealth) {
		this.mCurHealth = mCurHealth;
	}
	
	public void takeDamage(int damage) {
		mCurHealth -= damage;
	}

	protected abstract void die();

	public int getSpeed() {
		return mSpeed;
	}

	public void setSpeed(int mSpeed) {
		this.mSpeed = mSpeed;
	}

	public int getHealth() {
		return mHealth;
	}

	public void setHealth(int mHealth) {
		this.mHealth = mHealth;
	}

}
