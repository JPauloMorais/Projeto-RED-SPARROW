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
	private float mRotation;

	public PhysicsComponent() {
		super("PhysicsComponent");
		velX = 0.001f;
		velY = 0.001f;
		mRotation = 0.0f;
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

		gl.glTranslatef(object.getX(), object.getY(), object.getLayer());	

		switch (object.getCurMessage().getOperation()) {
		case "ROT":
			mRotation = (float) object.getCurMessage().getMessage();
			break;

		default:
			
			break;
		} 	

		gl.glRotatef(mRotation, 0.0f, 0.0f, 0.1f);

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
