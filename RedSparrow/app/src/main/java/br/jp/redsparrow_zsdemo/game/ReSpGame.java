package br.jp.redsparrow_zsdemo.game;

import java.util.Random;

import android.app.Activity;
import android.opengl.Matrix;
import br.jp.redsparrow_zsdemo.engine.GameObject;
import br.jp.redsparrow_zsdemo.engine.game.Game;
import br.jp.redsparrow_zsdemo.engine.game.ScoreSystem;
import br.jp.redsparrow_zsdemo.engine.game.World;
import br.jp.redsparrow_zsdemo.engine.missions.MissionSequence;
import br.jp.redsparrow_zsdemo.engine.missions.MissionSystem;
import br.jp.redsparrow_zsdemo.game.missions.TestMission;

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
		mScoreSystem = new ScoreSystem(this, "RedSparrow");

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

		mInputHandler.loop(this, viewProjMatrix);
		
		mWorld.loop(this, viewProjMatrix);

		mCamera.loop(this, viewProjMatrix);

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
