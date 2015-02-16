package br.jp.redsparrow.game;

import java.util.Random;

import android.content.Context;
import android.opengl.Matrix;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.game.World;
import br.jp.redsparrow.engine.core.missions.MissionSequence;
import br.jp.redsparrow.engine.core.missions.MissionSystem;
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
		mInputHandler = new ReSpInputHandler(this);	
		mMissionSystem = new MissionSystem(this, new MissionSequence(new TestMission()));
		mObjFactory = new ReSpObjectFactory(this);
		
	}

	@Override
	public void create() {
		
		mDbgBackground = mObjFactory.create("BG1", 0, 0);
		mDbgBackground1 =mObjFactory.create("BG2", 0, 0);

		mWorld = new World(mContext, this);
		mWorld.setPlayer(mObjFactory.create("BasicPlayer", 0f, 0f));
		
		int qd = 1; int qd2 = 1;
		for (int i = 0; i < 35; i++) {
			mWorld.addObject(mObjFactory.create("BasicEnemy1", (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2));
			if(i%2==0) qd *= -1;
			else qd2 *= -1;
		}		

		qd = 1; qd2 = 1;
		for (int i = 0; i < 35; i++) {
			mWorld.addObject(mObjFactory.create("BasicEnemy2", (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2));
			if(i%2==0) qd *= -1;
			else qd2 *= -1;
		}
		
//		mHUD = new HUD(this);
//		mHUD.addItem(mObjFactory.createHUDitem(mContext, HUDITEM_TYPE.AMMO_DISP));
//		mHUD.addItem(mObjFactory.createHUDitem(mContext, HUDITEM_TYPE.LIFEBAR));
		
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
				mWorld.getPlayer().getX(), mWorld.getPlayer().getY(), 65f,
				mWorld.getPlayer().getX(), mWorld.getPlayer().getY(), 0f,
				0f, 1f, 0f);
		
		mDbgBackground.render(viewProjMatrix);
		Matrix.translateM(viewProjMatrix, 0, 0, 0, 25);
		mDbgBackground1.render(viewProjMatrix);
		Matrix.translateM(viewProjMatrix, 0, 0, 0, 10);
		
		mWorld.loop(this, viewProjMatrix);
		
		Matrix.multiplyMM(viewProjMatrix, 0, projMatrix, 0, viewMatrix, 0);
		
//		mHUD.loop(this, viewProjMatrix);

		//------------TESTE

		if(times < 10) times++;
		else {
			times = 0;

			if ( objIds < mWorld.getObjectCount() ) {

				try {
					if( mWorld.getObject(objIds).getType().getSuperType().getName().equals("Enemy")){

						//						World.getPlayer().recieveMessage(new Message(-2, "Damage", 1));

						//						Vector2f moveO = World.getPlayer().getPosition().add(World.getObjectById(objIds).getPosition());

						//						((EnemyPhysicsComponent) World.getObjectById(objIds).getUpdatableComponent(0)).move(moveO);


						((SoundComponent) mWorld.getObject(objIds)
								.getUpdatableComponent("Sound")).setSoundVolume(0, 0.05f, 0.05f);
						((SoundComponent) mWorld.getObject(objIds)
								.getUpdatableComponent("Sound")).startSound(0, false);
						((GunComponent) mWorld.getObject(objIds)
								.getUpdatableComponent("Gun")).shoot(new Vector2f(0, 0.2f));

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

		if (((ReSpInputHandler)mInputHandler).move) {
			try {
				((PlayerPhysicsComponent) mWorld.getPlayer()
						.getUpdatableComponent("Physics")).move(((ReSpInputHandler)mInputHandler).playerMoveVel);
				
				((ReSpInputHandler) mInputHandler).playerMoveVel.setX(0);
				((ReSpInputHandler)mInputHandler).playerMoveVel.setY(0);
				((ReSpInputHandler)mInputHandler).move = false;
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
