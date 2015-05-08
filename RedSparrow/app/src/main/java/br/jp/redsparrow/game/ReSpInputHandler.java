package br.jp.redsparrow.game;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import br.jp.redsparrow.engine.Vector2f;
import br.jp.redsparrow.engine.components.GunComponent;
import br.jp.redsparrow.engine.game.Game;
import br.jp.redsparrow.engine.game.InputHandler;
import br.jp.redsparrow.game.objecttypes.basicplayer.PlayerPhysicsComponent;

public class ReSpInputHandler extends InputHandler {

	//Ativa e desativa controles por acelerometro
	private boolean accelControls = true;
	private Vibrator mVibrator;


	public Vector2f playerMoveVel = new Vector2f(0, 0);
	public Vector2f projMoveVel = new Vector2f(0, 0);

	public boolean toMove = false;

	public ReSpInputHandler(Game game) {
		super(game);
		mVibrator = (Vibrator) game.getContext().getSystemService(Context.VIBRATOR_SERVICE);

	}

	@Override
	public void loop(Game game, float[] projectionMatrix) {

//		if (toMove) {
			
			((PlayerPhysicsComponent) game.getWorld().getPlayer()
					.getUpdatableComponent("Physics")).move(playerMoveVel);
			
			playerMoveVel.mult(0);

			toMove = false;
			
//		}

	}

	@Override
	public void handleTouchPress(float normalizedX, float normalizedY) {
		try {

			Log.i(TAG, " Touch em: (" + normalizedX + ", " + normalizedY + ")");


			//			if (!Collision.isInside(new Vector2f(normalizedX, normalizedY).add(game.getWorld().getPlayer().getPosition()),
			//			game.getHUD().getItem(0).getBounds())) {
			mVibrator.vibrate(90);
			projMoveVel.setX(normalizedX);
			projMoveVel.setY(normalizedY);
//			((SoundComponent) game.getWorld().getPlayer().getUpdatableComponent("Sound"))
//			.setSoundVolume(0, 0.05f, 0.05f);
//			((SoundComponent) game.getWorld().getPlayer().getUpdatableComponent("Sound"))
//			.startSound(0, false);
			((GunComponent) game.getWorld().getPlayer().getUpdatableComponent("Gun"))
			.shoot(projMoveVel);
			//			}

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
				((PlayerPhysicsComponent) game.getWorld().getPlayer().getUpdatableComponent("Physics")).move(playerMoveVel);
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
//			if(playerMoveVel.length() < 0.005f) {
//				playerMoveVel.mult(0);
//				toMove = false;
//			}else toMove = true;

		}
	}

	public Vibrator getVibrator() {
		return mVibrator;
	}
}
