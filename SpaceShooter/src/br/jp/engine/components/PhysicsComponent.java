package br.jp.engine.components;

import javax.microedition.khronos.opengles.GL10;

import br.jp.engine.core.Component;
import br.jp.engine.core.GameObject;

public class PhysicsComponent extends Component implements Updatable{

	//	private final float MIN_VEL_Y = 0.001f;
	//	private final float MIN_VEL_X = 0.001f;
	//	private final float MAX_VEL_X = 0.05f;
	//	private final float MAX_VEL_Y = 0.05f;
	private float velX, velY;
	private float mTargetRotation, mCurRotation;

	public PhysicsComponent() {
		super("PhysicsComponent");
		velX = 0.0f;
		velY = 0.0f;
		mTargetRotation = 0.0f;
		mCurRotation = 0.0f;
		}

	@Override
	public void update(GL10 gl, GameObject object) {

		//		mParent.setX(mParent.getX() + velX);
		//		mParent.setY(mParent.getY() + velY);

		//		if(velX>MAX_VEL_X) velX -= 0.005f;
		//		else if(velX<MIN_VEL_X) velX += 0.005f;
		//		else velX += 0.005f;
		//		if(velY>MAX_VEL_Y) velY -= 0.005f;
		//		else if(velY<MIN_VEL_Y) velY += 0.005f;
		//		else velY += 0.005f;

		switch (object.getCurMessage().getOperation()) {
		case "ROT":
			mTargetRotation = (float) object.getCurMessage().getMessage();
			break;
		case "MOVE":
			float[] move = (float[]) object.getCurMessage().getMessage(); 
			object.setX(object.getX() + move[0]);
			object.setY(object.getY() + move[1]);
			break;

		default:

			break;
		} 	

		gl.glTranslatef(object.getCenterX(), object.getCenterY(), object.getLayer());	

		gl.glRotatef(mCurRotation, 0.0f, 0.0f, 1.0f);
		if(mCurRotation < mTargetRotation) mCurRotation += mTargetRotation/20f;
		else mCurRotation = mTargetRotation;

	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}
}
