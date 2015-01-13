package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.game.ObjectFactory.OBJ_TYPE;

public class ProjectilePhysicsComponent extends Component implements Updatable {

	private OBJ_TYPE mShooterType;

	private float[] newVel = { 0.0f, 0.0f};
	private float[] curVel = { 0.0f, 0.0f};

	private boolean hitTarget;

	public ProjectilePhysicsComponent() {
		super("Physics");

		hitTarget = false;

	}

	@Override
	public void update(GameObject parent) {			

		if (!hitTarget) {
			//Input de Movimentacao
			try {
				newVel = (float[]) parent.getMessage("MOVE").getMessage();
				curVel = newVel;
			} catch (Exception e) {
			}
			parent.setPosition(new Vector2f( parent.getPosition().getX() + curVel[0], parent.getPosition().getY() + curVel[1]));

		}else ((LifeComponent) parent.getUpdatableComponent(1)).die();
	}

	public boolean hasHitTarget() {
		return hitTarget;
	}

	public void hitTarget() {
		this.hitTarget = true;
	}

	public OBJ_TYPE getShooterType() {
		return mShooterType;
	}

	public void setShooterType(OBJ_TYPE shooterType) {
		this.mShooterType = shooterType;
	}

}
