package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;

public class PlayerPhysicsComponent extends PhysicsComponent {

	public PlayerPhysicsComponent(GameObject parent) {
		super(parent);
		
		mMaxVels = new Vector2f(parent.getWidth()/10, parent.getHeight()/10);
		mMass = parent.getWidth()+parent.getHeight();

	}

	@Override
	public void update(GameObject parent) {			

		if (!mMoved) {
			mFric.setX(0.01f);
			mFric.setY(0.01f);
		}

		if (!mCollided) {

			if (mVelocity.getX() > 0.00000000001f) {
				mVelocity.setX(mVelocity.getX() - mFric.getX());
			} else if (mVelocity.getX() < -0.00000000001f) {
				mVelocity.setX(mVelocity.getX() + mFric.getX());
			}
			if (mVelocity.getY() > 0.00000000001f) {
				mVelocity.setY(mVelocity.getY() - mFric.getY());
			} else if (mVelocity.getY() < -0.000000000001f) {
				mVelocity.setY(mVelocity.getY() + mFric.getY());
			}

		}

		if (mMoved || mCollided) {
			
			//Clamp de vel
			if (mVelocity.getX() > mMaxVels.getX())
				mVelocity.setX(mMaxVels.getX());
			else if (mVelocity.getX() < -mMaxVels.getX())
				mVelocity.setX(-mMaxVels.getX());
			if (mVelocity.getY() > mMaxVels.getY())
				mVelocity.setY(mMaxVels.getY());
			else if (mVelocity.getY() < -mMaxVels.getY())
				mVelocity.setY(-mMaxVels.getY());
			
		}
		
		addVel(parent);

		mCollided = false;
		mMoved = false;

	}

	public void move(Vector2f velocity){

		applyForce((Vector2f) ((velocity.length() > 0.01f ? velocity : new Vector2f(0, 0))));

		mFric.setX(0);
		mFric.setY(0);

		mMoved = true;
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
