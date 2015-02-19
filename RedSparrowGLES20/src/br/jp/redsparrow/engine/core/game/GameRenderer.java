package br.jp.redsparrow.engine.core.game;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import br.jp.redsparrow.engine.core.util.FPSCounter;
import br.jp.redsparrow.engine.core.util.LogConfig;
import br.jp.redsparrow.engine.shaders.ParticleShaderProg;
import br.jp.redsparrow.engine.shaders.TextureShaderProg;

public class GameRenderer extends GameSystem implements Renderer {

	private static boolean isRunning = false; 

	protected static int mScreenWidth;	
	protected static int mScreenHeight;

	public final float[] viewMatrix = new float[16];
	public final float[] projectionMatrix = new float[16];
	public final float[] viewProjectionMatrix = new float[16];
	
	public static TextureShaderProg textureProgram;
	public ParticleShaderProg particleProgram;

	private final FPSCounter fps = new FPSCounter();

	public GameRenderer(Game game) {
		super(game);
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {

		setRunning(true);
		textureProgram = new TextureShaderProg(game.getContext());
		particleProgram = new ParticleShaderProg(game.getContext());
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

	}

	float angle;
	@Override
	public void onDrawFrame(GL10 gl) {	

		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

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
