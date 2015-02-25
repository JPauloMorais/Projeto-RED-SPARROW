package br.jp.redsparrow.game;

import java.util.Random;

import android.app.Activity;
import android.opengl.Matrix;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.game.ScoreSystem;
import br.jp.redsparrow.engine.core.game.World;
import br.jp.redsparrow.engine.core.missions.MissionSequence;
import br.jp.redsparrow.engine.core.missions.MissionSystem;
import br.jp.redsparrow.game.missions.TestMission;
import br.jp.redsparrow.game.objecttypes.basicplayer.PlayerPhysicsComponent;

public class ReSpGame extends Game {

	private Random random;
	private GameObject mDbgBackground;
	
	public ReSpGame(Activity activity) {
		super(activity);

		random = new Random();
		mRenderer = new ReSpGameRenderer(mContext, this);
		mInputHandler = new ReSpInputHandler(this);	
		mMissionSystem = new MissionSystem(this, new MissionSequence(new TestMission()));
		mObjFactory = new ReSpObjectFactory(this);
		mScoreSystem = new ScoreSystem(this);

	}

	@Override
	public void create() {

		mWorld = new World(mContext, this);
		mWorld.setPlayer(mObjFactory.create("BasicPlayer", 1f, -1f));

		//		mTilemap = new Tilemap(R.raw.tilemap_test, this ,
		//				R.drawable.tile_blue, R.drawable.tile_green, R.drawable.tile_red, R.drawable.tile_white);

		mDbgBackground = mObjFactory.create("BG1", 0, 0);

		mCamera = new ReSpCamera(this);

		int qd = 1; int qd2 = 1;
		for (int i = 0; i < 10; i++) {
			mWorld.addObject(mObjFactory
					.create("BasicEnemy1", (qd * random.nextFloat() * random.nextInt(10)) + 10*qd, 
							(qd2 * random.nextFloat() * random.nextInt(10)) + 10*qd2));
			if(i%2==0) qd *= -1;
			else qd2 *= -1;
		}	

		mWorld.addObject(mObjFactory.create("Spawnpoint", 10, 10));

		//				qd = 1; qd2 = 1;
		//				for (int i = 0; i < 35; i++) {
		//					mWorld.addObject(mObjFactory.create("BasicEnemy2", (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2));
		//					if(i%2==0) qd *= -1;
		//					else qd2 *= -1;
		//				}
		//
		//		qd = 1; qd2 = 2;
		//		for (int i = 0; i < 15; i++) {
		//			mWorld.addObject(mObjFactory.create("BasicEnemy3", (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2));
		//			if(i%2==0) qd *= -1;
		//			else qd2 *= -1;
		//		}

		//		mHUD = new HUD(this);
		//		mHUD.addItem(mObjFactory.createHUDitem(mContext, HUDITEM_TYPE.AMMO_DISP));
		//		mHUD.addItem(mObjFactory.createHUDitem(mContext, HUDITEM_TYPE.LIFEBAR));

		//		mMissionSystem.start();
	}

	//------------TESTE
	int times = 0;
	int objIds = -1;
	int dir = 1;
	//-----------------

	@Override
	public void loop(float[] viewMatrix, float[] projMatrix, float[] viewProjMatrix) {

		
		mDbgBackground.setX(0);
		mDbgBackground.setY(0);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(100);
		mDbgBackground.setY(0);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(-100);
		mDbgBackground.setY(0);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(0);
		mDbgBackground.setY(-100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(-100);
		mDbgBackground.setY(-100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(100);
		mDbgBackground.setY(-100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(0);
		mDbgBackground.setY(100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(-100);
		mDbgBackground.setY(100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(100);
		mDbgBackground.setY(100);
		mDbgBackground.render(viewProjMatrix);
		Matrix.translateM(viewProjMatrix, 0, 0, 0, 25);
		
		mDbgBackground.setX(0);
		mDbgBackground.setY(0);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(100);
		mDbgBackground.setY(0);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(-100);
		mDbgBackground.setY(0);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(0);
		mDbgBackground.setY(-100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(-100);
		mDbgBackground.setY(-100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(100);
		mDbgBackground.setY(-100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(0);
		mDbgBackground.setY(100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(-100);
		mDbgBackground.setY(100);
		mDbgBackground.render(viewProjMatrix);
		
		mDbgBackground.setX(100);
		mDbgBackground.setY(100);
		mDbgBackground.render(viewProjMatrix);		
		
		Matrix.translateM(viewProjMatrix, 0, 0, 0, 20);

		//		mTilemap.loop(this, viewProjMatrix);

		mWorld.loop(this, viewProjMatrix);

		mCamera.loop(this, viewProjMatrix);

		//		mHUD.loop(this, viewProjMatrix);

		//------------TESTE

		//		if(times < 30) times++;
		//		else {
		//			times = 0;
		//
		//			if ( objIds < mWorld.getObjectCount() ) {
		//
		//				try {
		//					if( mWorld.getObject(objIds).getType().getSuperType().getName().equals("Enemy")){
		//
		//						//						World.getPlayer().recieveMessage(new Message(-2, "Damage", 1));
		//
		//						//						Vector2f moveO = World.getPlayer().getPosition().add(World.getObjectById(objIds).getPosition());
		//
		//						//						((EnemyPhysicsComponent) World.getObjectById(objIds).getUpdatableComponent(0)).move(moveO);
		//
		//
		//						((SoundComponent) mWorld.getObject(objIds)
		//								.getUpdatableComponent("Sound")).setSoundVolume(0, 0.05f, 0.05f);
		//						((SoundComponent) mWorld.getObject(objIds)
		//								.getUpdatableComponent("Sound")).startSound(0, false);
		//						((GunComponent) mWorld.getObject(objIds)
		//								.getUpdatableComponent("Gun")).shoot(new Vector2f(0, 0.2f));
		//						
		//						objIds++;
		//					}
		//				} catch (Exception e) {
		//					objIds = 0;
		//					dir *= -1;
		//				}
		//
		//			}else{
		//				objIds = 0;
		//				dir *= -1;
		//			}
		//
		//		}

		//-------------------------------

		//		if (((ReSpInputHandler)mInputHandler).move) {
		try {
			((PlayerPhysicsComponent) mWorld.getPlayer()
					.getUpdatableComponent("Physics")).move(((ReSpInputHandler)mInputHandler).playerMoveVel);

			((ReSpInputHandler) mInputHandler).playerMoveVel.setX(0);
			((ReSpInputHandler)mInputHandler).playerMoveVel.setY(0);
			((ReSpInputHandler)mInputHandler).move = false;
		} catch (Exception e) {
			//				e.printStackTrace();
		}
		//		}

	}

	@Override
	public void pause() {
		if(mWorld != null) mWorld.pause();
		mMissionSystem.stop();
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
