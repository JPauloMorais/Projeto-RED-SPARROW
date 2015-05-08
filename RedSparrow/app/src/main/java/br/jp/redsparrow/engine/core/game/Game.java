package br.jp.redsparrow.engine.core.game;

import android.app.Activity;
import android.content.Context;
import br.jp.redsparrow.engine.core.HUD;
import br.jp.redsparrow.engine.core.missions.MissionSystem;
import br.jp.redsparrow.engine.core.tilemaps.Tilemap;

public abstract class Game {
	
	protected Context mContext;
	protected Activity mActivity;
	protected World mWorld;
	protected Tilemap mTilemap;
	protected ObjectFactory mObjFactory;
	protected HUD mHUD;
	protected GameRenderer mRenderer;
	protected InputHandler mInputHandler;
	protected MissionSystem mMissionSystem;
	protected Camera mCamera;
	protected ScoreSystem mScoreSystem;
	
	public Game(Activity activity) {
		mContext = activity;
		mActivity = activity;
	}
	
	public abstract void create();

	public abstract void loop(float[] viewMatrix, float[] projMatrix, float[] viewProjMatrix);
	public abstract void pause();
	public abstract void resume();
	public abstract void stop();

	public Camera getmCamera() {
		return mCamera;
	}

	public void setmCamera(Camera mCamera) {
		this.mCamera = mCamera;
	}

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

	public Activity getActivity() {
		return mActivity;
	}

	public void setActivity(Activity activity) {
		this.mActivity = activity;
	}

	public ScoreSystem getScoreSystem() {
		return mScoreSystem;
	}

	public void setScoreSystem(ScoreSystem mScoreSystem) {
		this.mScoreSystem = mScoreSystem;
	}


}
