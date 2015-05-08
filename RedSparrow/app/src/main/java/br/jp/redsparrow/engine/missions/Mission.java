package br.jp.redsparrow.engine.missions;

import br.jp.redsparrow.engine.Vector2f;
import br.jp.redsparrow.engine.game.Game;
import br.jp.redsparrow.engine.physics.BCircle;

public abstract class Mission {

	protected BCircle mBounds; 
	
	protected final String mName;
	protected final String mDescription;
	
	protected boolean mComplete;
	protected boolean mTriggered;
	
	public Mission( String name, String description, float x, float y, float range) {
		
		mName = name;
		mDescription = description;
		
		mBounds = new BCircle( new Vector2f(x, y), range );
		
		mComplete = false;
		mTriggered = false;
		
	}
	
	public abstract void update(Game game);
	public abstract void onTrigger(Game game);
	
	public boolean isComplete() {
		return mComplete;
	}
	
	public void complete() {
		mComplete = true;
	}
	
	public boolean wasTriggered() {
		return mTriggered;
	}
	
	public void trigger() {
		mTriggered = true;
	}
	
	public void reset() {
		mComplete = false;
		mTriggered = false;
	}

}
