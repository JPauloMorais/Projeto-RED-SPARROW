package br.jp.redsparrow.game.components;

import android.graphics.Color;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.AnimatedSpriteComponent;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.particles.ParticleEmitter;

public class EnemyStatsComponent extends StatsComponent {

	public EnemyStatsComponent(GameObject parent, int health) {
		super(parent, health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Game game, GameObject object) {
		super.update(game, object);
	}

	@Override
	protected void die(Game game) {
		if (!isDying) {
			((AnimatedSpriteComponent) mParent.getRenderableComponent("AnimatedSprite")).setCurAnim(1);
			isDying = true;
		
			ParticleEmitter emittr = 	new ParticleEmitter(new float[]{mParent.getX(), mParent.getY(),0}, 
					new float[]{((PhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity().getX()*2,
					((PhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity().getY()*2,0.2f}, Color.rgb(0, 200, 0), 360, 2);
			emittr.addParticles(game.getWorld().getTopParticleSystem(), game.getWorld().getTopParticleSystem().getCurTime(), 10);
			emittr.addParticles(game.getWorld().getBottomParticleSystem(), game.getWorld().getTopParticleSystem().getCurTime(), 80);
		}
		else if(((AnimatedSpriteComponent) mParent.getRenderableComponent("AnimatedSprite")).getAnimation(1).hasLoopedOnce()) 
			mParent.die();
	}


}
