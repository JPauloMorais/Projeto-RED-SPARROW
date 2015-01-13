package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;

public class PhysicsComponent extends Component implements Updatable {

	private final float[] MAX_VELS;

	private float[] newVel = { 0.0f, 0.0f};
	private float[] curVel = { 0.0f, 0.0f};

	public PhysicsComponent(GameObject parent) {
		super("Physics");

		float maxVels[] = { parent.getWidth()/2, parent.getHeight()/2 };
		MAX_VELS = maxVels;

	}

	@Override
	public void update(GameObject parent) {			

		//Input de Movimentacao
		try { 
			newVel = (float[]) parent.getMessage("MOVE").getMessage();
			curVel = newVel; 
			} catch (Exception e) {
			}

		//Colisao
		try {
			newVel = (float[]) parent.getMessage("Collision").getMessage();

			curVel[0] += (newVel[0]/10);
			curVel[1] += (newVel[1]/10); 
			
		} catch (Exception e) {

			//Friccao
			
			if(curVel[0] > 0.000000001f){
				curVel[0] -= 0.005f;
			}else if (curVel[0] < -0.000000001f){
				curVel[0] += 0.005f;
			}
			
			if(curVel[1] > 0.000000001f){
				curVel[1] -= 0.005f;
			}else if (curVel[1] < -0.0000000001f){
				curVel[1] += 0.005f;
			}

		}


		//Clamp de vel
		if(curVel[0]>MAX_VELS[0]) curVel[0] = MAX_VELS[0];
		if(curVel[1]>MAX_VELS[1]) curVel[1] = MAX_VELS[1];

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
