package br.jp.redsparrow_zsdemo.engine.components;


import br.jp.redsparrow_zsdemo.engine.GameObject;
import br.jp.redsparrow_zsdemo.engine.Vector2f;
import br.jp.redsparrow_zsdemo.engine.game.Game;
import br.jp.redsparrow_zsdemo.engine.game.ObjectType;

public abstract class ProjectilePhysicsComponent extends PhysicsComponent {

	protected ObjectType mShooterSuperType;
	protected boolean shot = false;

	//	private Vector2f location = new Vector2f(0f, 0f); 

	private int mDamage;

	public ProjectilePhysicsComponent(GameObject parent, int damage) {
		super(parent);

		mMaxVel = 0.2f;
		mDamage = damage;

	}

	@Override
	public void update(Game game, GameObject parent) {			
		addVel(parent);
	}

	public boolean wasShot() {
		return shot;
	}

	public void shoot(Vector2f velocity) {
		mVelocity = velocity;
		mVelocity.setLength(mMaxVel);
		pointForwards(mParent);
		shot = true;
	}

	@Override
	public void collide(Game game, GameObject other) {
		if(!other.getType().getSuperType().getName().equals("Projectile") &&
				!other.getType().getSuperType().getName().equals(mShooterSuperType.getName())){
			mParent.die();
		}
	}

	public ObjectType getShooterSuperType() {
		return mShooterSuperType;
	}

	public void setShooterSuperType(ObjectType shooterType) {
		this.mShooterSuperType = shooterType;
	}

	public int getDamage() {
		return mDamage;
	}

}
