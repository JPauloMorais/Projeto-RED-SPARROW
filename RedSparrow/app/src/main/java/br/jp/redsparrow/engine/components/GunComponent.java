package br.jp.redsparrow.engine.components;

import br.jp.redsparrow.engine.GameObject;
import br.jp.redsparrow.engine.Updatable;
import br.jp.redsparrow.engine.Vector2f;

public abstract class GunComponent extends Component implements Updatable {
	
	//TODO: shootPoint
	
	protected boolean toShoot = false;
	protected Vector2f mMoveVel;
//	private Vector2f mShootPoint;
	
	public GunComponent(GameObject parent, float shootPointRelX, float shootPointRelY) {
		super(parent);
//		mShootPoint = new Vector2f(parent.getX() + shootPointRelX, parent.getY() + shootPointRelY);
	}

	public void shoot(Vector2f moveVel){
		mMoveVel = moveVel;
//		mParent.setRotation(Math.atan2(moveVel.getY(), moveVel.getX()) - 1.5707963268d);
		toShoot = true;
	}
}
