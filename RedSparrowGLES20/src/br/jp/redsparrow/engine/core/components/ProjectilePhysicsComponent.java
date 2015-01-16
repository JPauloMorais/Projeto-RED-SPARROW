package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.game.ObjectFactory.OBJ_TYPE;

public class ProjectilePhysicsComponent extends Component implements Updatable {

	private OBJ_TYPE mTargetType;
	private boolean shot = false;

	private Vector2f mVelocity = new Vector2f(0f, 0f);
	//	private Vector2f location = new Vector2f(0f, 0f); 

	private boolean hitTarget;

	public ProjectilePhysicsComponent() {
		super("Physics");

		hitTarget = false;

	}

	@Override
	public void update(GameObject parent) {			

		if (!hitTarget) {

			if (!shot) {
				//Input de Movimentacao
				try {

					mVelocity = (Vector2f) parent.getMessage("MOVE").getMessage();
					shot = true;

				} catch (Exception e) {
				}

			}

			parent.setPosition(	parent.getPosition().add(mVelocity) );

		}else parent.die();
		//			((LifeComponent) parent.getUpdatableComponent(1)).die();
	}

	public boolean wasShot() {
		return shot;
	}

	public void shoot(Vector2f velocity) {
		mVelocity = velocity;
		shot = true;
	}

	public boolean hasHitTarget() {
		return hitTarget;
	}

	public void hitTarget() {
		this.hitTarget = true;
	}

	public OBJ_TYPE getTargetType() {
		return mTargetType;
	}

	public void setTargetType(OBJ_TYPE shooterType) {
		this.mTargetType = shooterType;
	}

}
