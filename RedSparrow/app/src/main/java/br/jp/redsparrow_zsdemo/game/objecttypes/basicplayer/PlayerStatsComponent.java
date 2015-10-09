package br.jp.redsparrow_zsdemo.game.objecttypes.basicplayer;

import android.graphics.Color;

import br.jp.redsparrow_zsdemo.engine.GameObject;
import br.jp.redsparrow_zsdemo.engine.Vector3f;
import br.jp.redsparrow_zsdemo.engine.components.AnimatedSpriteComponent;
import br.jp.redsparrow_zsdemo.engine.components.PhysicsComponent;
import br.jp.redsparrow_zsdemo.engine.components.SoundComponent;
import br.jp.redsparrow_zsdemo.engine.components.StatsComponent;
import br.jp.redsparrow_zsdemo.engine.game.Game;
import br.jp.redsparrow_zsdemo.engine.particles.ParticleEmitter;
import br.jp.redsparrow_zsdemo.game.ReSpInputHandler;
import br.jp.redsparrow_zsdemo.game.activities.PlayActivity;

public class PlayerStatsComponent extends StatsComponent {

	private int killPoints;

	public PlayerStatsComponent(GameObject parent, int health) {
		super(parent, health);
		killPoints = 0;
	}

	@Override
	public void update(Game game, GameObject object) {
		super.update(game, object);
	}

	@Override
	protected void die(final Game game) {
		if (!isDying) {

			game.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					((ReSpInputHandler) game.getInputHandler()).getVibrator().vibrate(1000);
				}
			});

			((SoundComponent) game.getWorld().getPlayer().getUpdatableComponent("Sound"))
					.setSoundVolume(1, 0.5f, 0.5f);
			((SoundComponent) game.getWorld().getPlayer().getUpdatableComponent("Sound"))
					.startSound(1, false);

			((AnimatedSpriteComponent) mParent.getRenderableComponent("AnimatedSprite")).setCurAnim(1);

			ParticleEmitter emittr = new ParticleEmitter(new Vector3f(mParent.getX(), mParent.getY(),0),
					new Vector3f(((PhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity().getX()*10,
							((PhysicsComponent) mParent.getUpdatableComponent("Physics")).getVelocity().getY()*10, 1f),
					Color.YELLOW, 180, 2);
			emittr.addParticles(game.getWorld().getTopParticleSystem(), game.getWorld().getTopParticleSystem().getCurTime(), 10);
			emittr.setColor(Color.RED);
			emittr.addParticles(game.getWorld().getBottomParticleSystem(), game.getWorld().getTopParticleSystem().getCurTime(), 80);

			isDying = true;
			((PlayActivity) game.getActivity()).setPoints(0);
		}
		else if(((AnimatedSpriteComponent) mParent.getRenderableComponent("AnimatedSprite")).getAnimation(1).hasLoopedOnce()) {
			mParent.die();
		}
		else
			game.getmCamera().setDistanceY(game.getmCamera().getDistanceY() + .09f);

	}

	public void addKillPoints(Game game, int ammount) {
		setKillPoints(game, getKillPoints() + ammount);
	}

	public void removeKillPoints(Game game, int ammount) {
		setKillPoints(game, getKillPoints() - ammount);
	}

	public int getKillPoints() {
		return killPoints;
	}

	public void setKillPoints(Game game, int killPoints) {
		this.killPoints = killPoints;
		((PlayActivity) game.getActivity()).setPoints(this.killPoints);
		if(killPoints%100==0 && killPoints < 410) ((PlayActivity) game.getActivity()).showUpgrade();
	}



}