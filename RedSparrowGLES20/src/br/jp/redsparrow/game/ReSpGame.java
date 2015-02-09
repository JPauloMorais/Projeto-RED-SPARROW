package br.jp.redsparrow.game;

import java.util.Random;

import android.content.Context;
import android.opengl.Matrix;
import br.jp.redsparrow.engine.core.Game;
import br.jp.redsparrow.engine.core.GameRenderer;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.game.ObjectFactory.HUDITEM_TYPE;
import br.jp.redsparrow.game.ObjectFactory.OBJECT_TYPE;
import br.jp.redsparrow.game.components.PlayerPhysicsComponent;

public class ReSpGame extends Game {

	private Random random;
	
	public ReSpGame() {
		random = new Random();
	}

	@Override
	public void create(Context context) {
		super.create(context);

		mHUD.addItem(mObjFactory.createHUDitem(context, HUDITEM_TYPE.AMMO_DISP));
		mHUD.addItem(mObjFactory.createHUDitem(context, HUDITEM_TYPE.LIFEBAR));

		mWorld.setPlayer(mObjFactory.createObject(context, OBJECT_TYPE.PLAYER, 0f, 0f));
		int qd = 1; int qd2 = 1;
		for (int i = 0; i < 15; i++) {
			mWorld.addObject(mObjFactory.createObject(context, OBJECT_TYPE.BASIC_ENEMY, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2));
			if(i%2==0) qd *= -1;
			else qd2 *= -1;
		}		

		qd = 1; qd2 = 2;
		for (int i = 0; i < 15; i++) {
			mWorld.addObject(mObjFactory.createObject(context, OBJECT_TYPE.BASIC_ENEMY_2, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2));
			if(i%2==0) qd *= -1;
			else qd2 *= -1;
		}
	}

	//------------TESTE
		int times = 0;
		int objIds = -1;
		int dir = 1;
		//-----------------
	
	@Override
	public void loop(float[] viewMatrix, float[] projMatrix, float[] viewProjMatrix) {
		
		mWorld.loop(this, projMatrix);
		Matrix.multiplyMM(viewProjMatrix, 0, projMatrix, 0, viewMatrix, 0);
		mHUD.loop(this, projMatrix);
		
		//------------TESTE

		if(times < 50) times++;
		else {
			times = 0;

			if ( objIds < mWorld.getObjectCount() ) {

				try {
					if( mWorld.getObject(objIds).getType().equals(OBJECT_TYPE.BASIC_ENEMY) || 
							mWorld.getObject(objIds).getType().equals(OBJECT_TYPE.BASIC_ENEMY_2)){

						//						World.getPlayer().recieveMessage(new Message(-2, "Damage", 1));

						//						Vector2f moveO = World.getPlayer().getPosition().add(World.getObjectById(objIds).getPosition());

						//						((EnemyPhysicsComponent) World.getObjectById(objIds).getUpdatableComponent(0)).move(moveO);


						((SoundComponent) mWorld.getObject(objIds)
								.getUpdatableComponent(1)).setSoundVolume(0, 0.05f, 0.05f);
						((SoundComponent) mWorld.getObject(objIds)
								.getUpdatableComponent(1)).startSound(0, false);
						((GunComponent) mWorld.getObject(objIds)
								.getUpdatableComponent(2)).shoot(new Vector2f(0, 0.2f));

						objIds++;
					}
				} catch (Exception e) {
					objIds = 0;
					dir *= -1;
				}

			}else{
				objIds = 0;
				dir *= -1;
			}

		}

		//-------------------------------

		if (GameRenderer.move) {
			try {
				((PlayerPhysicsComponent) mWorld.getPlayer()
						.getUpdatableComponent(0)).move(GameRenderer.playerMoveVel);
				GameRenderer.playerMoveVel.setX(0);
				GameRenderer.playerMoveVel.setY(0);
				GameRenderer.move = false;
			} catch (Exception e) {
				//				e.printStackTrace();
			}
		}

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void stop() {

	}

}
