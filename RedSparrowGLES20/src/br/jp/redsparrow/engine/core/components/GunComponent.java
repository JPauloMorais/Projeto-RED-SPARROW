package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.game.components.ProjectilePhysicsComponent;

public class GunComponent extends Component implements Updatable {
	
	private boolean toShoot = false;
	private Vector2f mMoveVel;
	
	public GunComponent(GameObject parent) {
		super(parent);		
	}

	@Override
	public void update(Game game, GameObject parent) {

		if(toShoot){
						
			GameObject proj = game.getObjFactory().create("BasicProjectile", parent.getX(), parent.getY());
			
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
