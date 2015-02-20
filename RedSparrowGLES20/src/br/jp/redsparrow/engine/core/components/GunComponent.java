package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.game.components.ProjectilePhysicsComponent;

public class GunComponent extends Component implements Updatable {
	
	//TODO: shootPoint
	
	protected boolean toShoot = false;
	protected Vector2f mMoveVel;
//	private Vector2f mShootPoint;
	
	public GunComponent(GameObject parent, float shootPointRelX, float shootPointRelY) {
		super(parent);
//		mShootPoint = new Vector2f(parent.getX() + shootPointRelX, parent.getY() + shootPointRelY);
	}

	@Override
	public void update(Game game, GameObject parent) {
		
//		mShootPoint.set(parent.getPosition().add(mShootPoint));
//		
//		float cos = (float) Math.cos(mParent.getRotation());
//		float sen = (float) Math.sin(mParent.getRotation());
//		
//		mShootPoint.setX(cos * (mShootPoint.getX() - mParent.getX()) - sen * (mShootPoint.getY() - mParent.getY()) + mParent.getX());
//		mShootPoint.setY(sen * (mShootPoint.getX() - mParent.getX()) + cos * (mShootPoint.getY() - mParent.getY()) + mParent.getY());
//		
		if(toShoot){
						
			GameObject proj = game.getObjFactory().create("BasicProjectile", mParent.getX(), mParent.getY());
			
//			((PhysicsComponent) parent.getUpdatableComponent(0)).collide(Collision.getColVector(parent.getBounds(), proj.getBounds()));
			
			((ProjectilePhysicsComponent) proj.getUpdatableComponent("Physics")).setShooterSuperType(mParent.getType().getSuperType());
			((ProjectilePhysicsComponent) proj.getUpdatableComponent("Physics")).shoot(mMoveVel.copy());
			
			game.getWorld().addObject(proj);

			toShoot = false;
		}
		
	}

	public void shoot(Vector2f moveVel){
		mMoveVel = moveVel;
//		mParent.setRotation(Math.atan2(moveVel.getY(), moveVel.getX()) - 1.5707963268d);
		toShoot = true;
	}
}
