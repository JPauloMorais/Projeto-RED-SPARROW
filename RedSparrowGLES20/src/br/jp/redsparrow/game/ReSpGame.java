package br.jp.redsparrow.game;

import java.util.Random;

import android.content.Context;
import android.opengl.Matrix;
import br.jp.redsparrow.engine.core.Game;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.HUD;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.missions.MissionSequence;
import br.jp.redsparrow.engine.core.missions.MissionSystem;
import br.jp.redsparrow.game.ObjectFactory.HUDITEM_TYPE;
import br.jp.redsparrow.game.ObjectFactory.OBJECT_TYPE;
import br.jp.redsparrow.game.components.PlayerPhysicsComponent;
import br.jp.redsparrow.game.missions.TestMission;

public class ReSpGame extends Game {

	private Random random;
	private GameObject mDbgBackground;
	private GameObject mDbgBackground1;

	public ReSpGame(Context context) {
		super(context);
		
		random = new Random();
		mRenderer = new ReSpRenderer(mContext, this);
		mInputHandler = new ReSpInputHandle(this);	
		mMissionSystem = new MissionSystem(this, new MissionSequence(new TestMission()));
		
	}

	@Override
	public void create() {
		
		mObjFactory = new ObjectFactory(this);
		
		mDbgBackground = mObjFactory.createObject(mContext, OBJECT_TYPE.DBG_BG, 0, 0);
		mDbgBackground1 =mObjFactory.createObject(mContext, OBJECT_TYPE.DBG_BG1, 0, 0);

		mWorld = new World(mContext, this);
		mWorld.setPlayer(mObjFactory.createObject(mContext, OBJECT_TYPE.PLAYER, 0f, 0f));
		
		int qd = 1; int qd2 = 1;
		for (int i = 0; i < 15; i++) {
			mWorld.addObject(mObjFactory.createObject(mContext, OBJECT_TYPE.BASIC_ENEMY, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2));
			if(i%2==0) qd *= -1;
			else qd2 *= -1;
		}		

		qd = 1; qd2 = 2;
		for (int i = 0; i < 15; i++) {
			mWorld.addObject(mObjFactory.createObject(mContext, OBJECT_TYPE.BASIC_ENEMY_2, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2));
			if(i%2==0) qd *= -1;
			else qd2 *= -1;
		}
		
		mHUD = new HUD(this);
		mHUD.addItem(mObjFactory.createHUDitem(mContext, HUDITEM_TYPE.AMMO_DISP));
		mHUD.addItem(mObjFactory.createHUDitem(mContext, HUDITEM_TYPE.LIFEBAR));
		
		mMissionSystem.start();
	}

	//------------TESTE
	int times = 0;
	int objIds = -1;
	int dir = 1;
	//-----------------

	@Override
	public void loop(float[] viewMatrix, float[] projMatrix, float[] viewProjMatrix) {

		//Setando o ponto central da perspectiva como a posicao do player
		Matrix.setLookAtM(viewMatrix, 0,
				mWorld.getPlayer().getX(), mWorld.getPlayer().getY(), 45f,
				mWorld.getPlayer().getX(), mWorld.getPlayer().getY(), 0f,
				0f, 1f, 0f);
		
		mDbgBackground.render(viewProjMatrix);
		Matrix.translateM(viewProjMatrix, 0, 0, 0, 25);
		mDbgBackground1.render(viewProjMatrix);
		Matrix.translateM(viewProjMatrix, 0, 0, 0, 10);
		
		mWorld.loop(this, viewProjMatrix);
		
		Matrix.multiplyMM(viewProjMatrix, 0, projMatrix, 0, viewMatrix, 0);
		
		mHUD.loop(this, viewProjMatrix);

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

		if (((ReSpInputHandle)mInputHandler).move) {
			try {
				((PlayerPhysicsComponent) mWorld.getPlayer()
						.getUpdatableComponent(0)).move(((ReSpInputHandle)mInputHandler).playerMoveVel);
				
				((ReSpInputHandle) mInputHandler).playerMoveVel.setX(0);
				((ReSpInputHandle)mInputHandler).playerMoveVel.setY(0);
				((ReSpInputHandle)mInputHandler).move = false;
			} catch (Exception e) {
				//				e.printStackTrace();
			}
		}

	}

	@Override
	public void pause() {
		if(mWorld != null) mWorld.pause();
		mMissionSystem.stop();
		try {
			mMissionSystem.getThread().join();
			System.out.println(mMissionSystem.getThread().getState().toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resume() {
		if(mWorld != null) mWorld.resume();;
	}

	@Override
	public void stop() {
		if(mWorld != null) mWorld.stop();
		mMissionSystem.stop();
	}

}
