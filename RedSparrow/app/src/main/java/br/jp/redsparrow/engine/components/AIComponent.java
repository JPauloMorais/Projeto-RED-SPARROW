package br.jp.redsparrow.engine.components;

import br.jp.redsparrow.engine.GameObject;
import br.jp.redsparrow.engine.Updatable;
import br.jp.redsparrow.engine.Vector2f;
import br.jp.redsparrow.game.objecttypes.basicenemy.EnemyPhysicsComponent;

public abstract class AIComponent extends Component implements Updatable {

	protected Vector2f mSteerForce;
	protected float mMaxSteer;

	public AIComponent(GameObject parent) {
		super(parent);
		mSteerForce = new Vector2f(0, 0);
	}

	public void seek(Vector2f target) {

		Vector2f desVel = target.sub(mParent.getPosition());
		desVel =  desVel.normalize();
		desVel = desVel.mult(((EnemyPhysicsComponent) mParent.getUpdatableComponent("Physics")).getMaxVel());

		mSteerForce = desVel.sub(((EnemyPhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity());

		//		mSteerForce.setLength(mMaxSteer);
	}

	public void flee(Vector2f target) {

		Vector2f desVel = mParent.getPosition().sub(target);
		desVel =  desVel.normalize();
		desVel = desVel.mult(((EnemyPhysicsComponent) mParent.getUpdatableComponent("Physics")).getMaxVel());

		mSteerForce = desVel.sub(((EnemyPhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity());

		//		mSteerForce.setLength(mMaxSteer);
	}

	public void seekTillDistance(Vector2f target, float distance) {

		Vector2f desVel = target.sub(mParent.getPosition());
			desVel =  desVel.normalize();
			desVel = desVel.mult(((EnemyPhysicsComponent) mParent.getUpdatableComponent("Physics")).getMaxVel());
			
			mSteerForce = desVel.sub(((EnemyPhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity());		

	}

}
