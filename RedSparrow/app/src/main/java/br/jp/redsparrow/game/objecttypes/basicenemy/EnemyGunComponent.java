package br.jp.redsparrow.game.objecttypes.basicenemy;

import br.jp.redsparrow.engine.GameObject;
import br.jp.redsparrow.engine.components.GunComponent;
import br.jp.redsparrow.engine.game.Game;

public class EnemyGunComponent extends GunComponent {

	public EnemyGunComponent(GameObject parent, float shootPointRelX,
			float shootPointRelY) {
		super(parent, shootPointRelX, shootPointRelY);
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
