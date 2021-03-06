package br.jp.redsparrow_zsdemo.game.objecttypes.basicenemy;

import android.graphics.Color;
import android.util.Log;
import br.jp.redsparrow_zsdemo.engine.GameObject;
import br.jp.redsparrow_zsdemo.engine.Vector3f;
import br.jp.redsparrow_zsdemo.engine.components.PhysicsComponent;
import br.jp.redsparrow_zsdemo.engine.components.SoundComponent;
import br.jp.redsparrow_zsdemo.engine.components.StatsComponent;
import br.jp.redsparrow_zsdemo.engine.game.Game;
import br.jp.redsparrow_zsdemo.engine.particles.ParticleEmitter;
import br.jp.redsparrow_zsdemo.game.ReSpObjectFactory;
import br.jp.redsparrow_zsdemo.game.objecttypes.basicplayer.PlayerStatsComponent;

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
		
		//Efeito de explosao
		ParticleEmitter emittr = new ParticleEmitter(new Vector3f(mParent.getX(), mParent.getY(),0),
				new Vector3f(((PhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity().getX()*10,
				((PhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity().getY()*10, 1f),
				Color.YELLOW, 360, 2);
		emittr.addParticles(game.getWorld().getTopParticleSystem(), game.getWorld().getTopParticleSystem().getCurTime(), 10);
		emittr.setColor(Color.RED);
		emittr.addParticles(game.getWorld().getBottomParticleSystem(), game.getWorld().getTopParticleSystem().getCurTime(), 80);
		if (!isDying) {

			((SoundComponent) mParent.getUpdatableComponent("Sound"))
					.setSoundVolume(0, 0.1f, 0.1f);
			((SoundComponent) mParent.getUpdatableComponent("Sound"))
					.startSound(0, false);

			mParent.removeUpdatableComponent("AnimatedSprite");
			isDying = true;
			
			((ReSpObjectFactory) game.getObjFactory()).getTypeCounts().set(1, ((ReSpObjectFactory) game.getObjFactory()).getTypeCounts().get(1) -1);
			Log.i("PlayerGame", "Enemies: "+((ReSpObjectFactory) game.getObjFactory()).getTypeCounts().get(1));
			
			//Adicionando os pontos pelo kill
			((PlayerStatsComponent) game.getWorld().getPlayer().getUpdatableComponent("Stats")).addKillPoints(game, 10);
		}
		else if(deathCounter<=0) 
		{
			mParent.die();
		}else deathCounter--;
	}


}
