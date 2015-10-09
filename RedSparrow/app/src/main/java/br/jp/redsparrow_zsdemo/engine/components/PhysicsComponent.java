package br.jp.redsparrow_zsdemo.engine.components;

import br.jp.redsparrow_zsdemo.engine.GameObject;
import br.jp.redsparrow_zsdemo.engine.Updatable;
import br.jp.redsparrow_zsdemo.engine.Vector2f;
import br.jp.redsparrow_zsdemo.engine.game.Game;

public abstract class PhysicsComponent extends Component implements Updatable {
	
	protected float mMaxVel;

	protected Vector2f mVelocity = new Vector2f(0f, 0f);
	protected Vector2f mFric = new Vector2f(0f, 0f);

	protected float mMass;

	protected boolean mMoved;
	protected boolean mCollided;

	public PhysicsComponent(GameObject parent) {
		super(parent);

		mMoved = false;
		mCollided = false;

	}

	public void addVel(GameObject parent){

		parent.setPosition( mParent.getPosition().copy().add(mVelocity) );
//		mVelocity = mVelocity.mult(0);

	}
	
	public void clampToMaxVel() {
		if(mVelocity.length()>mMaxVel) 
			mVelocity.setLength(mMaxVel);
	}
	
	public void setupFric(int framesToStop) {
		mFric = mVelocity.div(framesToStop);
	}
	
	public void applyFric() {
		applyForce(mFric);
	}
	
	public void pointForwards(GameObject parent) {
		//Calcula arco tangente do vetor de velocidade - 90 graus para determinar a rotacao do objeto
		parent.setRotation(Math.atan2(mVelocity.getY(), mVelocity.getX())-1.5707963268d);
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

	}
		
	public abstract void collide(Game game, GameObject other);

	public Vector2f getVelocity(){
		return mVelocity;
	}

	public float getMaxVel() {
		return mMaxVel;
	}

	public void setMaxVel(float mMaxVel) {
		this.mMaxVel = mMaxVel;
	}

}
