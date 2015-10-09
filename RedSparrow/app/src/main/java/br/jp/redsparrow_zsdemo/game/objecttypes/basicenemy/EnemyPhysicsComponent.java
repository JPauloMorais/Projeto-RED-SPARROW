package br.jp.redsparrow_zsdemo.game.objecttypes.basicenemy;

import android.graphics.Color;
import br.jp.redsparrow_zsdemo.engine.GameObject;
import br.jp.redsparrow_zsdemo.engine.Vector2f;
import br.jp.redsparrow_zsdemo.engine.Vector3f;
import br.jp.redsparrow_zsdemo.engine.components.PhysicsComponent;
import br.jp.redsparrow_zsdemo.engine.components.StatsComponent;
import br.jp.redsparrow_zsdemo.engine.game.Game;
import br.jp.redsparrow_zsdemo.engine.particles.ParticleEmitter;
import br.jp.redsparrow_zsdemo.engine.physics.Collision;
import br.jp.redsparrow_zsdemo.game.objecttypes.ProjectilePhysicsComponent;

public class EnemyPhysicsComponent extends PhysicsComponent {

	private ParticleEmitter mEmitter;

	public EnemyPhysicsComponent(GameObject parent) {
		super(parent);

		mMaxVel = parent.getWidth()/15;
		mMass = (parent.getWidth()+parent.getHeight())*10;
		mEmitter = new ParticleEmitter(new Vector3f(parent.getX(),parent.getY(),0), new Vector3f(0,0,1),
				Color.BLUE, 90, 1);

	}

	@Override
	public void update(Game game, GameObject parent) {			

		//		applyFric();
		clampToMaxVel();
		addVel(parent);
		pointForwards(parent);

		mEmitter.setDirection(mVelocity.getX()*-1, mVelocity.getY()*-1, 0.5f);
		mEmitter.setPosition(parent.getX(), parent.getY(), 0);
		mEmitter.addParticles(game.getWorld().getBottomParticleSystem(), 
				game.getWorld().getBottomParticleSystem().getCurTime(), 10);
		
		mEmitter.setColor(Color.rgb(230, 230, 255));
		mEmitter.setDispAngle(20);
		mEmitter.addParticles(game.getWorld().getBottomParticleSystem(),
				game.getWorld().getBottomParticleSystem().getCurTime(), 3);
		
		mEmitter.setColor(Color.BLUE);
		mEmitter.setDispAngle(90);
		
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
	public void collide(Game game, GameObject other) {

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
