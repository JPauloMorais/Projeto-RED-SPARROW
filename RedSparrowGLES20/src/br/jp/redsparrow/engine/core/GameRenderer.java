package br.jp.redsparrow.engine.core;

import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.Vibrator;
import android.util.Log;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.physics.Collision;
import br.jp.redsparrow.engine.core.util.FPSCounter;
import br.jp.redsparrow.engine.core.util.LogConfig;
import br.jp.redsparrow.game.ObjectFactory.OBJECT_TYPE;
import br.jp.redsparrow.game.components.PlayerPhysicsComponent;

public class GameRenderer extends GameSystem implements Renderer {

	//Ativa e desativa controles por acelerometro
	private boolean accelControls = true;

	private static boolean isRunning = false; 



	private static Context mContext;

	private Vibrator mVibrator;

	private static int mScreenWidth;	
	private static int mScreenHeight;

	private GameObject mDbgBackground;
	private GameObject mDbgBackground1;

	public final float[] viewMatrix = new float[16];
	public final float[] projectionMatrix = new float[16];
	public final float[] viewProjectionMatrix = new float[16];
	//	private final float[] modelViewProjectionMatrix = new float[16];

	//	private final float[] modelMatrix = new float[16];

	private final FPSCounter fps = new FPSCounter();

	public GameRenderer(Context context, Game game) {
		super(game);
		
		mContext = context;
		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {

		setRunning(true);

		

//		HUD.init();
//		HUD.addItem(ObjectFactory.createHUDitem(mContext, HUDITEM_TYPE.AMMO_DISP));
//		HUD.addItem(ObjectFactory.createHUDitem(mContext, HUDITEM_TYPE.LIFEBAR));
//
//		World.init(mContext);
//		World.setPlayer(ObjectFactory.createObject(mContext, OBJECT_TYPE.PLAYER, 0f, 0f));
		game.create(mContext);

		//----TESTE----

		

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
				game.getWorld().getPlayer().getX(), game.getWorld().getPlayer().getY(), 45f,
				game.getWorld().getPlayer().getX(), game.getWorld().getPlayer().getY(), 0f,
				0f, 1f, 0f);

		//Renderizando 
		mDbgBackground.render(viewProjectionMatrix);
		Matrix.translateM(viewProjectionMatrix, 0, 0, 0, 25);
		mDbgBackground1.render(viewProjectionMatrix);
		Matrix.translateM(viewProjectionMatrix, 0, 0, 0, 10);
//		World.loop(viewProjectionMatrix);
//		Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
//		HUD.loop(viewProjectionMatrix);
		game.loop(viewMatrix, projectionMatrix, viewProjectionMatrix);

		if(LogConfig.ON) fps.logFrame();

	}

	public static Context getContext() {
		return mContext;
	}

	public static Vector2f playerMoveVel = new Vector2f(0, 0);
	public static Vector2f projMoveVel = new Vector2f(0.6f, 0.6f);
	public void {
		
	}
	public void {
		
	}

	public void {
		

	}

	public static boolean move = false;
	public void handleSensorChange(float[] values) {

		

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

	@Override
	public void loop(Game game, float[] projectionMatrix) {
		// TODO Auto-generated method stub
		
	}

}
