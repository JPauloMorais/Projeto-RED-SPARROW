package br.jp.redsparrow.engine.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import br.jp.redsparrow.engine.core.util.FPSCounter;
import br.jp.redsparrow.engine.core.util.LogConfig;

public class GameRenderer extends GameSystem implements Renderer {



	private static boolean isRunning = false; 

	protected static int mScreenWidth;	
	protected static int mScreenHeight;

	public final float[] viewMatrix = new float[16];
	public final float[] projectionMatrix = new float[16];
	public final float[] viewProjectionMatrix = new float[16];
	//	private final float[] modelViewProjectionMatrix = new float[16];

	//	private final float[] modelMatrix = new float[16];

	private final FPSCounter fps = new FPSCounter();

	public GameRenderer(Game game) {
		super(game);
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {

		setRunning(true);
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

	}

	float angle;
	@Override
	public void onDrawFrame(GL10 gl) {	

		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		if(LogConfig.ON) fps.logFrame();

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
