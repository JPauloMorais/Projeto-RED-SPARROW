package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;

public class PlayerPhysicsComponent extends Component implements Updatable {

	private final Vector2f MAX_VELS;

	private Vector2f mPosition; 
	private Vector2f mVelocity = new Vector2f(0f, 0f);
	private Vector2f fric = new Vector2f(0f, 0f);

	private float mMass;

	public PlayerPhysicsComponent(GameObject parent) {
		super("Physics");

		mPosition = parent.getPosition();

		MAX_VELS = new Vector2f(parent.getWidth()/10, parent.getHeight()/10);

		mMass = parent.getWidth()+parent.getHeight();

	}

	@Override
	public void update(GameObject parent) {			

		//Input de Movimentacao
		try { 

//			mVelocity = mVelocity.add((Vector2f) (((Vector2f) parent.getMessage("MOVE").getMessage()).length() > 0.01f ? ((Vector2f) parent.getMessage("MOVE").getMessage()) : 0));
			applyForce((Vector2f) (((Vector2f) parent.getMessage("MOVE").getMessage()).length() > 0.01f ? ((Vector2f) parent.getMessage("MOVE").getMessage()) : 0));

			//			mPosition.add(mVelocity);
			fric.setX(0);
			fric.setY(0);
			
		} catch (Exception e) {
		}
		
		//Colisao
		try {

			//			mVelocity = mVelocity.add((Vector2f) parent.getMessage("Collision").getMessage());
			applyForce((Vector2f) parent.getMessage("Collision").getMessage());
			//			mPosition.setX(mPosition.getX() + mVelocity.getX()/10);
			//			mPosition.setY( mPosition.getY() + mVelocity.getY()/10);

			fric.setX(0.005f);
			fric.setY(0.005f);

		} catch (Exception e) {
			
			//Friccao

			if(mVelocity.getX() > 0.0000000001f){
				mVelocity.setX(mVelocity.getX() - fric.getX());
			}else if (mVelocity.getX() < -0.0000000001f){
				mVelocity.setX(mVelocity.getX() + fric.getX());
			}

			if(mVelocity.getY() > 0.0000000001f){
				mVelocity.setY(mVelocity.getY() - fric.getY());
			}else if (mVelocity.getY() < -0.00000000001f){
				mVelocity.setY(mVelocity.getY() + fric.getY());
			}
			
			
		}

		//Clamp de vel
		if( mVelocity.getX() > MAX_VELS.getX() ) mVelocity.setX(MAX_VELS.getX());
		else if(mVelocity.getX() < -MAX_VELS.getX()) mVelocity.setX(-MAX_VELS.getX());

		if( mVelocity.getY() > MAX_VELS.getY() ) mVelocity.setY(MAX_VELS.getY());
		else if(mVelocity.getY() < -MAX_VELS.getY()) mVelocity.setY(-MAX_VELS.getY());

		mPosition = mPosition.add(mVelocity);
		
		parent.setPosition( mPosition );

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
