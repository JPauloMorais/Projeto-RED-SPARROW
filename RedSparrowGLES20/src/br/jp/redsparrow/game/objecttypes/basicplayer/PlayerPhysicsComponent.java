package br.jp.redsparrow.game.objecttypes.basicplayer;

import android.graphics.Color;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.particles.ParticleEmitter;
import br.jp.redsparrow.engine.core.physics.Collision;
import br.jp.redsparrow.game.objecttypes.ProjectilePhysicsComponent;

public class PlayerPhysicsComponent extends PhysicsComponent {

	private ParticleEmitter mEmitter;
	
	public PlayerPhysicsComponent(GameObject parent) {
		super(parent);

		mMaxVel = parent.getWidth()/10;
		mMass = parent.getWidth()+parent.getHeight();
		
		mEmitter = new ParticleEmitter(new float[]{parent.getX(),parent.getY(),0}, new float[]{0,0,0}, Color.RED, 90, 2);

	}

	@Override
	public void update(Game game, GameObject parent) {			

//		applyFric();
		clampToMaxVel();
		if(mCollided || mMoved) addVel(parent);
		pointForwards(parent);
		
		mEmitter.setDirection(mVelocity.getX()*-1, mVelocity.getY()*-1, .7f);
		mEmitter.setPosition(parent.getX(), parent.getY(), 0);
		mEmitter.addParticles(game.getWorld().getBottomParticleSystem(), 
				game.getWorld().getBottomParticleSystem().getCurTime(), 10);
		
		mEmitter.setColor(Color.YELLOW);
		mEmitter.setDispAngle(20);
		mEmitter.addParticles(game.getWorld().getBottomParticleSystem(), 
				game.getWorld().getBottomParticleSystem().getCurTime(), 3);
		
		mEmitter.setColor(Color.RED);
		mEmitter.setDispAngle(90);

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
	public void collide(Game game, GameObject other) {

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
