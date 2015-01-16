package br.jp.redsparrow.game;

import static android.opengl.GLES20.glViewport;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.Vibrator;
import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Tile;
import br.jp.redsparrow.engine.core.TiledBackground;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.EnemyPhysicsComponent;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.PlayerPhysicsComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.messages.MessagingSystem;
import br.jp.redsparrow.engine.core.missions.MissionSystem;
import br.jp.redsparrow.engine.core.missions.TestMission;
import br.jp.redsparrow.engine.core.particles.Particle;
import br.jp.redsparrow.engine.core.util.FPSCounter;
import br.jp.redsparrow.engine.core.util.LogConfig;
import br.jp.redsparrow.game.ObjectFactory.OBJ_TYPE;

@SuppressWarnings("unused")
public class GameRenderer implements Renderer {

	//Ativa e desativa controles por acelerometro
	private boolean accelControls = true;

	Vector2f playerMoveVel = new Vector2f(0, 0);
	Vector2f projMoveVel = new Vector2f(0.6f, 0.6f);

	private static Context mContext;

	private Vibrator mVibrator;

	private static int mScreenWidth;	
	private static int mScreenHeight;

	private GameObject mDbgBackground;
	private GameObject mDbgBackground1;
	private TestMission mTestMission;

	private final float[] viewMatrix = new float[16];
	private final float[] viewProjectionMatrix = new float[16];
	private final float[] modelViewProjectionMatrix = new float[16];

	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];

	private final FPSCounter fps = new FPSCounter();

	Tile tile;

	public GameRenderer(Context context) {

		mContext = context;

		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		MissionSystem.init();
		mTestMission = new TestMission(5, 5);
		new Thread(mTestMission).start();

	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
//				GLES20.glClearColor(0.0f, 0.749f, 1.0f, 0.0f);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		//TODO: Ativar teste p terceira dim
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glClearDepthf(100.0f); 

		GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		//ativando e definindo alpha blending
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc( GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA );

		TiledBackground.init(mContext, 10, 10, 40, R.drawable.points_test_1, R.drawable.points_test_2, R.drawable.points_test_3, R.drawable.points_test_4);

		World.init(mContext);
		World.setPlayer(ObjectFactory.createObject(mContext, OBJ_TYPE.PLAYER, 0f, 0f, 2f, 2f));
		//----TESTE----
		int qd = 1; int qd2 = 1;
		for (int i = 0; i < 30; i++) {
			World.addObject(ObjectFactory.createObject(mContext, OBJ_TYPE.B_ENEMY, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2,
					2f, 2f));
			if(i%2==0) qd *= -1;
			else qd2 *= -1;
		}
		//--------------

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		mScreenWidth = width;
		mScreenHeight = height;

		glViewport(0, 0, width, height);

		//criando e ajustando matriz de projecao em perspectiva
		Matrix.perspectiveM(projectionMatrix, 0, 90, (float) width
				/ (float) height, 1, 100);
		Matrix.setLookAtM(viewMatrix, 0,
				0f, 0f, 10f,
				0f, 0f, 0f,
				0f, 0f, 1f);

	}

	//------------TESTE
	Random random = new Random();
	int times = 0;
	int objIds = -1;
	int dir = 1;
	//-----------------

	@Override
	public void onDrawFrame(GL10 gl) {	

		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		//Setando o ponto central da perspectiva como a posicao do player
		Matrix.setLookAtM(viewMatrix, 0,
				World.getPlayer().getPosition().getX(), World.getPlayer().getPosition().getY(), 30f,
				World.getPlayer().getPosition().getX(), World.getPlayer().getPosition().getY(), 0f,
				0f, 1f, 0f);
		Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);


		//Renderizando 
		//		mTestMission.render(viewProjectionMatrix);
		TiledBackground.render(viewProjectionMatrix);
		World.loop(viewProjectionMatrix);



		//------------TESTE
		if(times < 50) times++;
		else {
			times = 0;

			if ( objIds < World.getObjectCount() ) {

				try {
					if( World.getObjectById(objIds).getType().equals(OBJ_TYPE.B_ENEMY) ){

						Vector2f moveO = new Vector2f(0f,
								((random.nextFloat()) / 10) * dir);

						((EnemyPhysicsComponent) World.getObjectById(objIds).getUpdatableComponent(0)).move(moveO);


						((SoundComponent) World.getObjectById(objIds)
								.getUpdatableComponent(1)).startSound(0, false);
						((GunComponent) World.getObjectById(objIds)
								.getUpdatableComponent(2)).shoot(new Vector2f(0f, -0.5f));

						objIds++;
					}
				} catch (NullPointerException e) {
					objIds = 0;
					dir *= -1;
				}

			}else{
				objIds = 0;
				dir *= -1;
			}

		}

		//-------------------------------

		if(LogConfig.ON) fps.logFrame();

	}

	public static Context getContext() {
		return mContext;
	}

	public void handleTouchPress(float normalizedX, float normalizedY) {
		mVibrator.vibrate(100);
		try {

			Log.i("Input", " Touch em: (" + normalizedX + ", " + normalizedY + ")");

			try {
				projMoveVel.setX(normalizedX);
				projMoveVel.setY(normalizedY);
				((SoundComponent) World.getPlayer().getUpdatableComponent(1))
				.startSound(0, false);
				((GunComponent) World.getPlayer().getUpdatableComponent(2))
				.shoot(projMoveVel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
		}
	}
	public void handleTouchRelease(float normalizedX, float normalizedY) {
		try {
			//			((SoundComponent) World.getPlayer().getComponent("Sound")).pauseSound(0);
		} catch (Exception e) {

		}
	}

	public void handleTouchDrag(float normalizedX, float normalizedY) {
		if (!accelControls) {
			//TODO Movimentacao correta
			playerMoveVel.setX(normalizedX/10);
			playerMoveVel.setY(normalizedY/10);
		}

	}

	public void handleSensorChange(float[] values) {
		//TODO: Rotacao a partir do centro do objeto 

		if (accelControls) {

			playerMoveVel.setX(-values[0]/110f);
			playerMoveVel.setY(-values[1]/110f);

			try {
				((PlayerPhysicsComponent) World.getPlayer().getUpdatableComponent(0)).move(playerMoveVel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static int getScreenWidth() {
		return mScreenWidth;
	}

	public static int getScreenHeight() {
		return mScreenHeight;
	}

}
