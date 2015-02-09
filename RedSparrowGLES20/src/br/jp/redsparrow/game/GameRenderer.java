package br.jp.redsparrow.game;

import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.Vibrator;
import android.util.Log;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.physics.Collision;
import br.jp.redsparrow.engine.core.util.FPSCounter;
import br.jp.redsparrow.engine.core.util.LogConfig;
import br.jp.redsparrow.game.ObjectFactory.OBJECT_TYPE;
import br.jp.redsparrow.game.components.PlayerPhysicsComponent;

public class GameRenderer implements Renderer {

	//Ativa e desativa controles por acelerometro
	private boolean accelControls = true;

	private static boolean isRunning = false; 



	private static Context mContext;

	private Vibrator mVibrator;

	private static int mScreenWidth;	
	private static int mScreenHeight;

	
	private RedSparrow mGame;
	private GameObject mDbgBackground;
	private GameObject mDbgBackground1;

	private final float[] viewMatrix = new float[16];
	private final float[] viewProjectionMatrix = new float[16];
	//	private final float[] modelViewProjectionMatrix = new float[16];

	private final float[] projectionMatrix = new float[16];
	//	private final float[] modelMatrix = new float[16];

	private final FPSCounter fps = new FPSCounter();

	public GameRenderer(Context context) {

		mContext = context;

		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		mGame = new RedSparrow();
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {

		setRunning(true);

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

//		HUD.init();
//		HUD.addItem(ObjectFactory.createHUDitem(mContext, HUDITEM_TYPE.AMMO_DISP));
//		HUD.addItem(ObjectFactory.createHUDitem(mContext, HUDITEM_TYPE.LIFEBAR));
//
//		World.init(mContext);
//		World.setPlayer(ObjectFactory.createObject(mContext, OBJECT_TYPE.PLAYER, 0f, 0f));
		mGame.create(mContext);

		//----TESTE----

		mDbgBackground = ObjectFactory.createObject(mContext, OBJECT_TYPE.DBG_BG, 0, 0);
		mDbgBackground1 = ObjectFactory.createObject(mContext, OBJECT_TYPE.DBG_BG1, 0, 0);

//		int qd = 1; int qd2 = 1;
//		for (int i = 0; i < 15; i++) {
//			World.addObject(ObjectFactory.createObject(mContext, OBJECT_TYPE.BASIC_ENEMY, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2));
//			if(i%2==0) qd *= -1;
//			else qd2 *= -1;
//		}		
//
//		int qd5 = 1; int qd6 = 1;
//		for (int i = 0; i < 15; i++) {
//			World.addObject(ObjectFactory.createObject(mContext, OBJECT_TYPE.BASIC_ENEMY_2, (qd5 * random.nextFloat() * random.nextInt(10)) + 2*qd5, (qd6 * random.nextFloat() * random.nextInt(10)) + 2*qd6));
//			if(i%2==0) qd5 *= -1;
//			else qd6 *= -1;
//		}

		//--------------


	}

	GameObject obj;

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

	float angle;
	@Override
	public void onDrawFrame(GL10 gl) {	

		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		//Setando o ponto central da perspectiva como a posicao do player
		Matrix.setLookAtM(viewMatrix, 0,
				mGame.getWorld().getPlayer().getX(), mGame.getWorld().getPlayer().getY(), 45f,
				mGame.getWorld().getPlayer().getX(), mGame.getWorld().getPlayer().getY(), 0f,
				0f, 1f, 0f);

		//Renderizando 
		mDbgBackground.render(viewProjectionMatrix);
		Matrix.translateM(viewProjectionMatrix, 0, 0, 0, 25);
		mDbgBackground1.render(viewProjectionMatrix);
		Matrix.translateM(viewProjectionMatrix, 0, 0, 0, 10);
//		World.loop(viewProjectionMatrix);
//		Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
//		HUD.loop(viewProjectionMatrix);
		mGame.loop(viewMatrix, projectionMatrix, viewProjectionMatrix);

		if(LogConfig.ON) fps.logFrame();

	}

	public static Context getContext() {
		return mContext;
	}

	public static Vector2f playerMoveVel = new Vector2f(0, 0);
	public static Vector2f projMoveVel = new Vector2f(0.6f, 0.6f);
	public void handleTouchPress(float normalizedX, float normalizedY) {
		try {

			Log.i("Input", " Touch em: (" + normalizedX + ", " + normalizedY + ")");

			if (!Collision.isInside(new Vector2f(normalizedX, normalizedY).add(mGame.getWorld().getPlayer().getPosition()),
					mGame.getHUD().getItem(0).getBounds())) {
				mVibrator.vibrate(100);
				projMoveVel.setX(normalizedX);
				projMoveVel.setY(normalizedY);
				((SoundComponent) mGame.getWorld().getPlayer().getUpdatableComponent(1))
				.setSoundVolume(0, 0.05f, 0.05f);
				((SoundComponent) mGame.getWorld().getPlayer().getUpdatableComponent(1))
				.startSound(0, false);
				((GunComponent) mGame.getWorld().getPlayer().getUpdatableComponent(2))
				.shoot(projMoveVel);
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
			playerMoveVel.setX(normalizedX/100);
			playerMoveVel.setY(normalizedY/100);

			try {
				((PlayerPhysicsComponent) mGame.getWorld().getPlayer().getUpdatableComponent(0)).move(playerMoveVel);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}

	}

	public static boolean move = false;
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

	public static int getScreenWidth() {
		return mScreenWidth;
	}

	public static int getScreenHeight() {
		return mScreenHeight;
	}

	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		GameRenderer.isRunning = isRunning;
	}

}
