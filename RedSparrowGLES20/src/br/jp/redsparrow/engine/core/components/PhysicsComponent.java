package br.jp.redsparrow.engine.core.components;

import android.util.Log;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;

public abstract class PhysicsComponent extends Component implements Updatable {

	protected Vector2f mMaxVels;

	protected Vector2f mPosition; 
	protected Vector2f mVelocity = new Vector2f(0f, 0f);
	protected Vector2f mFric = new Vector2f(0f, 0f);

	protected float mMass;
	
	protected boolean mMoved;
	protected boolean mCollided;

	public PhysicsComponent(GameObject parent) {
		super("Physics", parent);

		mPosition = parent.getPosition();
		mMoved = false;
		mCollided = false;
		
	}

	@Override
	public void update(GameObject parent) {			
			
			//Friccao

			if(mVelocity.getX() > 0.0000001f){
				mVelocity.setX(mVelocity.getX() - mFric.getX());
			}else if (mVelocity.getX() < -0.0000001f){
				mVelocity.setX(mVelocity.getX() + mFric.getX());
			}

			if(mVelocity.getY() > 0.0000001f){
				mVelocity.setY(mVelocity.getY() - mFric.getY());
			}else if (mVelocity.getY() < -0.00000001f){
				mVelocity.setY(mVelocity.getY() + mFric.getY());
			}
			

		//Clamp de vel
		if( mVelocity.getX() > mMaxVels.getX() ) mVelocity.setX(mMaxVels.getX());
		else if(mVelocity.getX() < -mMaxVels.getX()) mVelocity.setX(-mMaxVels.getX());

		if( mVelocity.getY() > mMaxVels.getY() ) mVelocity.setY(mMaxVels.getY());
		else if(mVelocity.getY() < -mMaxVels.getY()) mVelocity.setY(-mMaxVels.getY());


	}
	
	public void addVel(GameObject parent){
		mPosition = mPosition.add(mVelocity);
		parent.setPosition( mPosition );
		
		parent.setRotation((Math.atan2(mVelocity.getY(), mVelocity.getX()))-1.5707963268d);
		
	}

	public void applyForce(float force){

		force /= mMass;
		mVelocity = mVelocity.add(force);

	}

	public void applyForce(Vector2f force){

		force = force.div(mMass);
		mVelocity = mVelocity.add(force);

	}
	
	public void collide(Vector2f otherVel){

		applyForce(otherVel);
		mCollided = true;

		mFric.setX(0.01f);
		mFric.setY(0.01f);

	}

	public Vector2f getVelocity(){
		return mVelocity;
	}

}
