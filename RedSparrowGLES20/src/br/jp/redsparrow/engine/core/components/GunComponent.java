package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.game.GameRenderer;
import br.jp.redsparrow.game.ObjectFactory;
import br.jp.redsparrow.game.ObjectFactory.OBJECT_TYPE;
import br.jp.redsparrow.game.components.ProjectilePhysicsComponent;

public class GunComponent extends Component implements Updatable {

	private boolean toShoot = false;
	private Vector2f mMoveVel;
	
	public GunComponent(GameObject parent) {
		super("Gun", parent);		
	}

	@Override
	public void update(GameObject parent) {

		if(toShoot){
			
			GameObject proj = ObjectFactory.createObject(GameRenderer.getContext(), OBJECT_TYPE.PROJECTILE, parent.getX(), parent.getY());
			
			((ProjectilePhysicsComponent) proj.getUpdatableComponent(0)).setShooterType(mParent.getType());
			((ProjectilePhysicsComponent) proj.getUpdatableComponent(0)).shoot(mMoveVel.copy());
			
			World.addObject(proj);

			toShoot = false;
		}
		
	}

	public void shoot(Vector2f moveVel){
		mMoveVel = moveVel;
		mParent.setRotation(Math.atan2(moveVel.getY(), moveVel.getX()) - 1.5707963268d);
		toShoot = true;
	}
}
