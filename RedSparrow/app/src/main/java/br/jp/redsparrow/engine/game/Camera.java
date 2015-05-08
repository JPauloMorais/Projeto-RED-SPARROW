package br.jp.redsparrow.engine.game;

public abstract class Camera extends GameSystem {

	protected float mDistY;
	protected float mFollowX;
	protected float mFollowY;
	
	public Camera(Game game,float followX, float followY, float distY) {
		super(game);
	
		setDistanceY(distY);
		setFollowX(followX);
		setFollowY(followY);
		
	}

	public float getDistanceY() {
		return mDistY;
	}

	public void setDistanceY(float distance) {
		this.mDistY = distance;
	}

	public float getFollowX() {
		return mFollowX;
	}

	public void setFollowX(float followX) {
		this.mFollowX = followX;
	}

	public float getFollowY() {
		return mFollowY;
	}

	public void setFollowY(float mFollowY) {
		this.mFollowY = mFollowY;
	}

}
