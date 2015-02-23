package br.jp.redsparrow.game.objecttypes.basicplayer;

import java.util.ArrayList;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.game.objecttypes.ProjectilePhysicsComponent;

public class PlayerGunComponent extends GunComponent {

	private ArrayList<String> mAvailableBulletTypes;
	private int mCurBulletTypeIndex;
	
	public PlayerGunComponent(GameObject parent) {
		super(parent, 0, 0);
		
		mAvailableBulletTypes = new ArrayList<String>();
		mAvailableBulletTypes.add("BasicProjectile");
		mCurBulletTypeIndex = 0;
	}
	
	@Override
	public void update(Game game, GameObject parent) {
	
		if(toShoot){
						
			GameObject proj = game.getObjFactory().create(mAvailableBulletTypes.get(mCurBulletTypeIndex),
					mParent.getX(), mParent.getY());
						
			((ProjectilePhysicsComponent) proj.getUpdatableComponent("Physics")).setShooterSuperType(mParent.getType().getSuperType());
			((ProjectilePhysicsComponent) proj.getUpdatableComponent("Physics")).shoot(mMoveVel.copy());
			
			game.getWorld().addObject(proj);

			toShoot = false;
		}
		
	}
	
	public int getBulletTypeCount() {
		return mAvailableBulletTypes.size();
	}

	public void addBulletType(String bulletType) {
		mAvailableBulletTypes.add(bulletType);
		switchNextBulletType();
	}
	
	public void removeBulletType(String bulletType) {
		mAvailableBulletTypes.remove(bulletType);
	}
	
	public void switchNextBulletType() {
		mCurBulletTypeIndex++;
		if(mCurBulletTypeIndex > mAvailableBulletTypes.size()-1) mCurBulletTypeIndex = 0;
	}

}
