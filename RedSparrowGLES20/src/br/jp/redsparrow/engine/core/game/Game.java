package br.jp.redsparrow.engine.core.game;

import android.content.Context;
import br.jp.redsparrow.engine.core.HUD;
import br.jp.redsparrow.engine.core.missions.MissionSystem;

public abstract class Game {
	
	protected Context mContext;
	protected World mWorld;
	protected ObjectFactory mObjFactory;
	protected HUD mHUD;
	protected GameRenderer mRenderer;
	protected InputHandler mInputHandler;
	protected MissionSystem mMissionSystem;
	
	public Game(Context context) {
		mContext = context;
	}
	
	public abstract void create();

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
	
	public ObjectFactory getObjFactory() {
		return mObjFactory;
	}
	
	public void setObjFactory(ObjectFactory mObjFactory) {
		this.mObjFactory = mObjFactory;
	}

	public HUD getHUD() {
		return mHUD;
	}

	public void setHUD(HUD mHUD) {
		this.mHUD = mHUD;
	}

	public GameRenderer getRenderer() {
		return mRenderer;
	}

	public void setRenderer(GameRenderer mRenderer) {
		this.mRenderer = mRenderer;
	}

	public InputHandler getInputHandler() {
		return mInputHandler;
	}

	public void setInputHandler(InputHandler mInputHandler) {
		this.mInputHandler = mInputHandler;
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context context) {
		this.mContext = context;
	}

}
