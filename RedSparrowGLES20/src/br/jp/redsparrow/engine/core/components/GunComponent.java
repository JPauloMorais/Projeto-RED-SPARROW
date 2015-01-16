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
	OBJ_TYPE mTargetType;
	
	public GunComponent(OBJ_TYPE targetType) {
		super("Gun");
		
		mTargetType = targetType;
		
	}

	@Override
	public void update(GameObject parent) {

		if(toShoot){
			
			GameObject proj = ObjectFactory.createObject(GameRenderer.getContext(), OBJ_TYPE.PROJECTL, parent.getPosition().getX(), parent.getPosition().getY(),
					0.2f, 0.4f);
			
			((ProjectilePhysicsComponent) proj.getUpdatableComponent(0)).setTargetType(mTargetType);
			((ProjectilePhysicsComponent) proj.getUpdatableComponent(0)).shoot(mMoveVel.copy());
			
			World.addObject(proj);

			toShoot = false;
		}
		
	}

	public void shoot(Vector2f moveVel){
		mMoveVel = moveVel;
		toShoot = true;
	}
}
