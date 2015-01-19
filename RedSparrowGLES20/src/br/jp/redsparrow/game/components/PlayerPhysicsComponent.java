package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.Component;

public class PlayerPhysicsComponent extends Component implements Updatable {

	private final Vector2f MAX_VELS;

	private Vector2f mPosition; 
	private Vector2f mVelocity = new Vector2f(0f, 0f);
	private Vector2f fric = new Vector2f(0f, 0f);

	private boolean mCollided;
	private boolean mMoved;

	private float mMass;


	public PlayerPhysicsComponent(GameObject parent) {
		super("Physics", parent);

		mPosition = parent.getPosition();

		MAX_VELS = new Vector2f(parent.getWidth()/10, parent.getHeight()/10);

		mMass = parent.getWidth()+parent.getHeight();

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
		//
		//		} catch (Exception e) {

		if (!mMoved) {
			fric.setX(0.01f);
			fric.setY(0.01f);
		}

		//		}

		//Colisao
		//		try {
		//
		//			//			mVelocity = mVelocity.add((Vector2f) parent.getMessage("Collision").getMessage());
		//			applyForce((Vector2f) parent.getMessage("Collision").getMessage());
		//
		//			fric.setX(0.01f);
		//			fric.setY(0.01f);
		//
		//		} catch (Exception e) {

		//Friccao

		if (!mCollided) {

			if (mVelocity.getX() > 0.00000000001f) {
				mVelocity.setX(mVelocity.getX() - fric.getX());
			} else if (mVelocity.getX() < -0.00000000001f) {
				mVelocity.setX(mVelocity.getX() + fric.getX());
			}
			if (mVelocity.getY() > 0.00000000001f) {
				mVelocity.setY(mVelocity.getY() - fric.getY());
			} else if (mVelocity.getY() < -0.000000000001f) {
				mVelocity.setY(mVelocity.getY() + fric.getY());
			}

		}

		//		}

		if (mMoved || mCollided) {
			
			//Clamp de vel
			if (mVelocity.getX() > MAX_VELS.getX())
				mVelocity.setX(MAX_VELS.getX());
			else if (mVelocity.getX() < -MAX_VELS.getX())
				mVelocity.setX(-MAX_VELS.getX());
			if (mVelocity.getY() > MAX_VELS.getY())
				mVelocity.setY(MAX_VELS.getY());
			else if (mVelocity.getY() < -MAX_VELS.getY())
				mVelocity.setY(-MAX_VELS.getY());
			
		}
		
		mPosition = mPosition.add(mVelocity);

		parent.setPosition( mPosition );

		mCollided = false;
		mMoved = false;

	}

	public void move(Vector2f velocity){

		applyForce((Vector2f) ((velocity.length() > 0.01f ? velocity : new Vector2f(0, 0))));

		fric.setX(0);
		fric.setY(0);

		mMoved = true;
	}

	public void collide(Vector2f otherVel){

		applyForce(otherVel);
		mCollided = true;

		fric.setX(0.01f);
		fric.setY(0.01f);

	}

	@SuppressWarnings("unused")
	private void applyForce(float force){

		force /= mMass;
		mVelocity = mVelocity.add(force);

	}

	private void applyForce(Vector2f force){

		force = force.div(mMass);
		mVelocity = mVelocity.add(force);

	}

	public Vector2f getVelocity(){
		return mVelocity;
	}

}
