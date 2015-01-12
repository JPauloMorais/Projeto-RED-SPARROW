package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;

public class PlayerPhysicsComponent extends Component implements Updatable {

	private final float[] MAX_VELS;

	private float[] newVel = { 0.0f, 0.0f};
	private float[] curVel = { 0.0f, 0.0f};

	public PlayerPhysicsComponent(GameObject parent) {
		super("Physics");

		float maxVels[] = { parent.getWidth()+2, parent.getHeight()+2 };
		MAX_VELS = maxVels;
		
	}

	@Override
	public void update(GameObject parent) {			

		try {

			newVel = (float[]) parent.getMessage("MOVE").getMessage();

			//clamp
			if(newVel[0]>MAX_VELS[0]) newVel[0] = MAX_VELS[0];
			if(newVel[1]>MAX_VELS[1]) newVel[1] = MAX_VELS[1];
			
			curVel = newVel;

		} catch (Exception e) {

		}

		if(parent.getMessage("Collision") != null) {
			newVel[0] = 0;
			newVel[1] = 0;
		}
		
		float curX = parent.getPosition().getX() + curVel[0];
		float curY = parent.getPosition().getY() + curVel[1];

		parent.setPosition(new Vector2f( curX, curY));

	}

	public float getVelX() {
		return curVel[0];
	}

	public void setVelX(float velX) {
		this.curVel[0] = velX;
	}

	public float getVelY() {
		return curVel[1];
	}

	public void setVelY(float velY) {
		this.curVel[1] = velY;
	}

}
