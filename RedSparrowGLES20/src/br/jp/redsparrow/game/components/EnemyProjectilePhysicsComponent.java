package br.jp.redsparrow.game.components;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.game.Game;

public class EnemyProjectilePhysicsComponent extends ProjectilePhysicsComponent {

	public EnemyProjectilePhysicsComponent(GameObject parent, int damage) {
		super(parent, damage);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void collide(Game game, GameObject other) {
		if(!other.getType().getSuperType().getName().equals("Projectile") &&
				!other.getType().getSuperType().getName().equals(mShooterSuperType.getName())){
			
			mParent.die();
			
			if(other.getType().getSuperType().getName().equals("Player")){
				game.getWorld().addObject(game.getObjFactory().create("BasicEnemy1", mParent.getX(), mParent.getY()));
			}
			
		}
	}

}
