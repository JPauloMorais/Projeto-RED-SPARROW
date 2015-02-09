package br.jp.redsparrow.engine.core.components;

import android.util.Log;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;

public abstract class StatsComponent extends Component implements Updatable {

	protected boolean isDying;
	protected int mHealth;
	protected int mCurHealth;
	
	public StatsComponent(GameObject parent, int health) {
		super("Stats", parent);
		
		mHealth = health;
		mCurHealth = health;
		isDying = false;
		
	}
	
	@Override
	public void update(GameObject object) {
		if(mCurHealth <= 0) die();
	}

	public int getCurHealth() {
		return mCurHealth;
	}

	public void setCurHealth(int mCurHealth) {
		this.mCurHealth = mCurHealth;
	}
	
	public void takeDamage(int damage) {
		mCurHealth -= damage;
		Log.i("MessagingSystem", "!!!!!!!!!DMG!!!!!!!!!!!!!!!!");
	}

	protected abstract void die();

	public int getHealth() {
		return mHealth;
	}

	public void setHealth(int mHealth) {
		this.mHealth = mHealth;
	}

}
