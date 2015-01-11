package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;

public class PhysicsComponent extends Component implements Updatable {

	private float[] newVel = { 0.0f, 0.0f};
	private float[] curVel = { 0.0f, 0.0f};

	public PhysicsComponent(GameObject parent) {
		super("Physics");

	}

	@Override
	public void update(GameObject parent) {			

		try {
			
			newVel = (float[]) parent.getMessage("MOVE").getMessage();
			
			curVel = newVel;

		} catch (Exception e) {
			
		}
		
		float curX = parent.getPosition().getX() + curVel[0];
		float curY = parent.getPosition().getY() + curVel[1];
		
		parent.setPosition(new Vector2f( curX, curY));
		
		parent.updateVertsData(curX, curY);


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
