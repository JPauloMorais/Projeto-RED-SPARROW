package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;

public class EnemyPhysicsComponent extends PhysicsComponent {

	public EnemyPhysicsComponent(GameObject parent) {
		super(parent);

		mPosition = parent.getPosition();

		mMaxVels = new Vector2f(parent.getWidth()/10, parent.getHeight()/10);

		mMass = (parent.getWidth()+parent.getHeight())*10;

	}

	@Override
	public void update(GameObject parent) {			

		//Input de Movimentacao
		//		try { 
		//
		//			applyForce((Vector2f) (((Vector2f) parent.getMessage("MOVE").getMessage()).length() > 0.01f ? ((Vector2f) parent.getMessage("MOVE").getMessage()) : 0));
		//
		//			fric.setX(0);
		//			fric.setY(0);

		//		} catch (Exception e) {
		//		}

		//Colisao
		//		try {
		//
		//			//			mVelocity = mVelocity.add((Vector2f) parent.getMessage("Collision").getMessage());
		//			applyForce((Vector2f) parent.getMessage("Collision").getMessage());
		//			//			mPosition.setX(mPosition.getX() + mVelocity.getX()/10);
		//			//			mPosition.setY( mPosition.getY() + mVelocity.getY()/10);
		//
		//			fric.setX(0.009f);
		//			fric.setY(0.009f);

		//		} catch (Exception e) {

		//Friccao

		if (!mCollided) {

			if (mVelocity.getX() > 0.0000001f) {
				mVelocity.setX(mVelocity.getX() - mFric.getX());
			} else if (mVelocity.getX() < -0.0000001f) {
				mVelocity.setX(mVelocity.getX() + mFric.getX());
			}
			if (mVelocity.getY() > 0.0000001f) {
				mVelocity.setY(mVelocity.getY() - mFric.getY());
			} else if (mVelocity.getY() < -0.00000001f) {
				mVelocity.setY(mVelocity.getY() + mFric.getY());
			}

		}



		//		}


		if (!mCollided) {
//			Clamp de vel
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

	}

	public void move(Vector2f velocity){

		applyForce(((velocity.length() > 0.01f ? velocity : new Vector2f(0, 0))));

		mFric.setX(0.009f);
		mFric.setY(0.009f);

	}

	public void collide(Vector2f otherVel){

		applyForce(otherVel);
		mCollided = true;

		mFric.setX(0f);
		mFric.setY(0f);

	}

//	public void applyForce(float force){
//
//		force /= mMass;
//		mVelocity = mVelocity.add(force);
//
//	}
//
//	public void applyForce(Vector2f force){
//
//		force = force.div(mMass);
//		mVelocity = mVelocity.add(force);
//
//	}
//
//	public Vector2f getVelocity(){
//		return mVelocity;
//	}

}
