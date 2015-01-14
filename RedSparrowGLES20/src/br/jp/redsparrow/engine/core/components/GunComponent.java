package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.game.GameRenderer;
import br.jp.redsparrow.game.ObjectFactory;
import br.jp.redsparrow.game.ObjectFactory.OBJ_TYPE;

public class GunComponent extends Component implements Updatable {

	private boolean toShoot = false;
	private Vector2f mMoveVel;
	
	public GunComponent() {
		super("Gun");
	}

	@Override
	public void update(GameObject parent) {

		if(toShoot){
			
			GameObject proj = ObjectFactory.createObject(GameRenderer.getContext(), OBJ_TYPE.PROJECTL, parent.getPosition().getX(), parent.getPosition().getY() + 0.55f,
					0.2f, 0.4f);
			((ProjectilePhysicsComponent) proj.getUpdatableComponent(0)).setShooterType(parent.getType());
			((ProjectilePhysicsComponent) proj.getUpdatableComponent(0)).shoot(mMoveVel);
			
			World.addObject(proj, 1);

			
			toShoot = false;
		}
		
	}

	public void shoot(Vector2f moveVel){
		mMoveVel = moveVel;
		toShoot = true;
	}
}
