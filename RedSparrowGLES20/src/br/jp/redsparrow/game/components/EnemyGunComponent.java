package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.game.Game;

public class EnemyGunComponent extends GunComponent {

	public EnemyGunComponent(GameObject parent, float shootPointRelX,
			float shootPointRelY) {
		super(parent, shootPointRelX, shootPointRelY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Game game, GameObject parent) {

		if(toShoot){
			GameObject proj = game.getObjFactory().create("BasicEnemyProjectile", mParent.getX(), mParent.getY());

			//			((PhysicsComponent) parent.getUpdatableComponent(0)).collide(Collision.getColVector(parent.getBounds(), proj.getBounds()));

			((EnemyProjectilePhysicsComponent) proj.getUpdatableComponent("Physics")).setShooterSuperType(mParent.getType().getSuperType());
			((EnemyProjectilePhysicsComponent) proj.getUpdatableComponent("Physics")).shoot(mMoveVel.copy());

			game.getWorld().addObject(proj);

			toShoot = false;
		}

	}

}
