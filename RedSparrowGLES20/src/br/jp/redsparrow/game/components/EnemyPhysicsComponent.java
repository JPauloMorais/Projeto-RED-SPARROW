package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.physics.Collision;

public class EnemyPhysicsComponent extends PhysicsComponent {

	public EnemyPhysicsComponent(GameObject parent) {
		super(parent);

		mMaxVel = parent.getWidth()/15;
		mMass = (parent.getWidth()+parent.getHeight())*10;

	}

	@Override
	public void update(Game game, GameObject parent) {			

		applyFric();
		clampToMaxVel();
		addVel(parent);
		pointForwards(parent);

		mCollided = false;

	}

	public void move(Vector2f velocity){

		applyForce( ((velocity.length() > 0.01f ? velocity : new Vector2f(0, 0))));

		pointForwards(mParent);

		//		mFric = mVelocity.div(60);

	}

	public void collide(Vector2f otherVel){

		applyForce(otherVel);
		mCollided = true;

		//		mFric.setX(0);
		//		mFric.setY(0);

	}

	@Override
	public void collide(GameObject other) {

		if(!other.getType().getSuperType().getName().equals("Projectile")) {

			applyForce(Collision.getColVector(mParent.getBounds(), other.getBounds()));
			setupFric(60);

		}else if(!((ProjectilePhysicsComponent) other.getUpdatableComponent("Physics"))
				.getShooterSuperType().getName().equals("Enemy")) {

			((StatsComponent) mParent.getUpdatableComponent("Stats")).takeDamage(
					((ProjectilePhysicsComponent) other.getUpdatableComponent("Physics")).getDamage());

		}

	}

}
