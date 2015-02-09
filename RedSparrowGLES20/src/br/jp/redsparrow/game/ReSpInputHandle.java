package br.jp.redsparrow.game;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import br.jp.redsparrow.engine.core.Game;
import br.jp.redsparrow.engine.core.InputHandler;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.physics.Collision;
import br.jp.redsparrow.game.components.PlayerPhysicsComponent;

public class ReSpInputHandle extends InputHandler {

	//Ativa e desativa controles por acelerometro
	private boolean accelControls = true;
	private Vibrator mVibrator;
	

	public Vector2f playerMoveVel = new Vector2f(0, 0);
	public Vector2f projMoveVel = new Vector2f(0.6f, 0.6f);

	public boolean move = false;

	public ReSpInputHandle(Game game) {
		super(game);
		mVibrator = (Vibrator) game.getContext().getSystemService(Context.VIBRATOR_SERVICE);

	}

	@Override
	public void handleTouchPress(float normalizedX, float normalizedY) {
		try {

			Log.i("Input", " Touch em: (" + normalizedX + ", " + normalizedY + ")");

			if (!Collision.isInside(new Vector2f(normalizedX, normalizedY).add(game.getWorld().getPlayer().getPosition()),
					game.getHUD().getItem(0).getBounds())) {
				mVibrator.vibrate(100);
				projMoveVel.setX(normalizedX);
				projMoveVel.setY(normalizedY);
				((SoundComponent) game.getWorld().getPlayer().getUpdatableComponent(1))
				.setSoundVolume(0, 0.05f, 0.05f);
				((SoundComponent) game.getWorld().getPlayer().getUpdatableComponent(1))
				.startSound(0, false);
				((GunComponent) game.getWorld().getPlayer().getUpdatableComponent(2))
				.shoot(projMoveVel);
			}

		} catch (Exception e) {
		}
	}

	@Override
	public void handleTouchRelease(float normalizedX, float normalizedY) {
		try {
			//			((SoundComponent) World.getPlayer().getComponent("Sound")).pauseSound(0);
		} catch (Exception e) {

		}
	}

	@Override
	public void handleTouchDrag(float normalizedX, float normalizedY) {
		if (!accelControls) {
			//TODO Movimentacao correta
			playerMoveVel.setX(normalizedX/100);
			playerMoveVel.setY(normalizedY/100);

			try {
				((PlayerPhysicsComponent) game.getWorld().getPlayer().getUpdatableComponent(0)).move(playerMoveVel);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	}

	@Override
	public void handleSensorChange(float[] values) {
		if (accelControls) {

			playerMoveVel.setX(-values[0]/500);
			playerMoveVel.setY(-values[1]/500);
			//			playerMoveVel = playerMoveVel.normalize();
			if(playerMoveVel.length() > 0.001f) {
				move = true;
				//				Log.i("Physics", "(" + values[0] + "," + values[1] + ")");
			}

		}
	}

	@Override
	public void loop(Game game, float[] projectionMatrix) {
		// TODO Auto-generated method stub

	}

}
