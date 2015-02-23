package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.game.objecttypes.ProjectilePhysicsComponent;

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
