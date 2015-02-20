package br.jp.redsparrow.game.objecttypes.basicenemy;

import android.graphics.Color;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.particles.ParticleEmitter;

public class EnemyStatsComponent extends StatsComponent {

	private int deathCounter;
	
	public EnemyStatsComponent(GameObject parent, int health) {
		super(parent, health);
		deathCounter = 5;
	}

	@Override
	public void update(Game game, GameObject object) {
		super.update(game, object);
	}

	@Override
	protected void die(Game game) {
		ParticleEmitter emittr = 	new ParticleEmitter(new float[]{mParent.getX(), mParent.getY(),0}, 
				new float[]{((PhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity().getX()*10,
				((PhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity().getY()*10,1f}, Color.YELLOW, 360, 2);
		emittr.addParticles(game.getWorld().getTopParticleSystem(), game.getWorld().getTopParticleSystem().getCurTime(), 10);
		emittr.setColor(Color.RED);
		emittr.addParticles(game.getWorld().getBottomParticleSystem(), game.getWorld().getTopParticleSystem().getCurTime(), 80);
		if (!isDying) {
//			((AnimatedSpriteComponent) mParent.getRenderableComponent("AnimatedSprite")).setCurAnim(1);
			mParent.removeUpdatableComponent("AnimatedSprite");
			isDying = true;
		}
		else if(deathCounter<=0) 
		{
			mParent.die();
		}else deathCounter--;
	}


}
