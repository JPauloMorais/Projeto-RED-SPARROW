package br.jp.redsparrow.engine.core;

import br.jp.redsparrow.game.ObjectFactory;
import android.content.Context;

public abstract class Game {
	
	protected World mWorld;
	protected ObjectFactory mObjFactory;
	protected HUD mHUD;
	protected GameRenderer mRenderer;
	protected InputHandler mInputHandler;
	
	public void create(Context context) {
		mWorld = new World(context, this);
		mHUD = new HUD(this);
		mObjFactory = new ObjectFactory(this);
		mRenderer = new GameRenderer(context, this);
		mInputHandler = new InputHandler(this);
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

}
