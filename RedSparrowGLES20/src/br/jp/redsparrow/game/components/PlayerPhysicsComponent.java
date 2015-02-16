package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.physics.Collision;

public class PlayerPhysicsComponent extends PhysicsComponent {

	public PlayerPhysicsComponent(GameObject parent) {
		super(parent);

		mMaxVel = parent.getWidth()/10;
		mMass = parent.getWidth()+parent.getHeight();

	}

	@Override
	public void update(Game game, GameObject parent) {			

		applyFric();
		clampToMaxVel();
		addVel(parent);
//		pointForwards(parent);

		mCollided = false;
		mMoved = false;

	}

	public void move(Vector2f velocity){

//		applyForce( ((velocity.length() > 0.01f ? velocity : new Vector2f(0, 0))));

		applyForce(velocity);
		
		//		mVelocity = velocity;
		//				(Vector2f) ((velocity.length() > 0.01f ? velocity : new Vector2f(0, 0))));


		setupFric(60);
		pointForwards(mParent);

		mMoved = true;
	}

	@Override
	public void collide(GameObject other) {

		if(!other.getType().getSuperType().getName().equals("Projectile")) {

			applyForce(Collision.getColVector(mParent.getBounds(), other.getBounds()));

		}else if(!((ProjectilePhysicsComponent) other.getUpdatableComponent("Physics"))
				.getShooterSuperType().getName().equals("Player")) {

			((StatsComponent) mParent.getUpdatableComponent("Stats")).takeDamage(
					((ProjectilePhysicsComponent) other.getUpdatableComponent("Physics")).getDamage());

		}

	}

	public void applyForce(float force){

		force /= mMass;
		mVelocity = mVelocity.add(force);

	}

	public void applyForce(Vector2f force){

		force = force.div(mMass);
		mVelocity = mVelocity.add(force);

	}

	public Vector2f getVelocity(){
		return mVelocity;
	}

}
