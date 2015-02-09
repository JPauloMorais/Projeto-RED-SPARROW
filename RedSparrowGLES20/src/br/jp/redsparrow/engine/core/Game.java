package br.jp.redsparrow.engine.core;

import android.content.Context;

public abstract class Game {
	
	protected World mWorld;
	protected HUD mHUD;
	
	public void create(Context context) {
		mWorld = new World(context);
		mHUD = new HUD();
	}
	
	public abstract void loop(float[] viewMatrix, float[] projMatrix, float[] viewProjMatrix);
	public abstract void pause();
	public abstract void resume();
	public abstract void stop();

	public World getWorld() {
		return mWorld;
	}

	public void setWorld(World mWorld) {
		this.mWorld = mWorld;
	}

	public HUD getHUD() {
		return mHUD;
	}

	public void setHUD(HUD mHUD) {
		this.mHUD = mHUD;
	}

}
