package br.jp.redsparrow.game;

import static android.opengl.GLES20.glViewport;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.Vibrator;
import android.util.Log;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Message;
import br.jp.redsparrow.engine.core.Tilemap;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.missions.MissionSystem;
import br.jp.redsparrow.engine.core.missions.TestMission;
import br.jp.redsparrow.engine.core.util.FPSCounter;
import br.jp.redsparrow.engine.core.util.LogConfig;
import br.jp.redsparrow.engine.core.util.MatrixUtil;
import br.jp.redsparrow.game.ObjectFactory.OBJ_TYPE;
import br.jp.redsparrow.R;

@SuppressWarnings("unused")
public class GameRenderer implements Renderer {

	private static Context mContext;

	private Vibrator mVibrator;

	private int mScreenWidth;	
	private int mScreenHeight;

	private GameObject mDbgBackground;
	private TestMission mTestMission;
	
	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];

	private ArrayList<Message> mCurMessage;

	private final FPSCounter fps = new FPSCounter();

	public GameRenderer(Context context) {

		mContext = context;

		mCurMessage = new ArrayList<Message>();

		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		Tilemap tilemap = new Tilemap(context, R.raw.tilemap_test);
		
		MissionSystem.init();
		mTestMission = new TestMission(5, 5);
		new Thread(mTestMission).start();

	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		//		GLES20.glClearColor(0.0f, 0.749f, 1.0f, 0.0f);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		//TODO: Ativar teste p terceira dim
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		//		GLES20.glClearDepthf(1.0f); 

		//ativando e definindo alpha blending
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc( GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA );
		
		mDbgBackground = ObjectFactory.createObject(mContext, OBJ_TYPE.DBG_BG, 0, 0, 100, 100);
		World.init(mContext);
		World.setPlayer(ObjectFactory.createObject(mContext, OBJ_TYPE.PLAYER, 0f, 0f, 2f, 2f));
		for (int i = 0; i < 30; i++) {
			World.addObject(ObjectFactory.createObject(mContext, OBJ_TYPE.B_ENEMY, 0 + (i/100), 0 + (i/10), 1f, 1f));
		}


	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		mScreenWidth = width;
		mScreenHeight = height;

		glViewport(0, 0, width, height);
		//criando e ajustando matriz de projecao
		MatrixUtil.perspectiveM(projectionMatrix, 45, (float) width
				/ (float) height, 1f, 100f);

		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f);

		final float[] temp = new float[16];
		Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
		System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);

		Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f);
		Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
	}

	//------------TESTE
	int times = 0;
	int objects = 0;
	int dir = 1;
	//-----------------

	@Override
	public void onDrawFrame(GL10 gl) {

		//criando e ajustando matriz de projecao
		MatrixUtil.perspectiveM(projectionMatrix, 45, (float) mScreenWidth
				/ (float) mScreenHeight, 1f, 100f);

		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, -World.getPlayer().getPosition().getX(), -World.getPlayer().getPosition().getY(), -20f);

		final float[] temp = new float[16];
		Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
		System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);

		Matrix.translateM(modelMatrix, 0, 0 ,0 , -2.5f);
		Matrix.rotateM(modelMatrix, 0, -60f + rot, 1f, 0f, 0f);


		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		mDbgBackground.render(projectionMatrix);
		World.loop(projectionMatrix);

		//------------TESTE
		if(times < 50) times++;
		else {
			times = 0;
			try {

				float[] moveO = {0.0f, ((objects + 0.00001f)/30)*dir };
				ArrayList<Message> messages = new ArrayList<Message>();
				messages.add(new Message(objects, "MOVE", moveO));
				World.sendMessages(objects, messages);

			} catch (Exception e) {
				objects = 0;
				dir *= -1;
			}
			objects++;
		}
		//-------------------------------

		World.getPlayer().recieveMessages(mCurMessage);

		if(LogConfig.ON) fps.logFrame();
	}

	public static Context getContext() {
		return mContext;
	}

	float[] move = {0.0f,0.0f};
	float rot  = 0.0f;

	public void handleTouchPress(float normalizedX, float normalizedY) {
		mVibrator.vibrate(100);
		try {
			((SoundComponent) World.getPlayer().getComponent("Sound")).startSound(0, true);
		} catch (Exception e) {
		}
	}
	public void handleTouchRelease(float normalizedX, float normalizedY) {
		try {
			((SoundComponent) World.getPlayer().getComponent("Sound")).pauseSound(0);
		} catch (Exception e) {

		}
	}

	public void handleTouchDrag(float normalizedX, float normalizedY) {
		//TODO Movimentacao correta
		move[0] = normalizedX/10;
		move[1] = normalizedY/10;
		mCurMessage.add(new Message(0, "MOVE",  move));

	}

	public void handleSensorChange(float[] values) {
		//TODO: Calculo correto da rotacao
		//TODO: Rotacao a partir do centro do objeto 

		if(values[0] < -1.0f) {
			move[0] = 0.1f;
		}
		else if(values[0] > 1.0f) {
			move[0] = -0.1f;
		}else {
			move[0] = 0.0f;
		}

		if(values[1] < -1.0f) {
			move[1] = 0.1f;	
		}
		else if(values[1] > 1.0f) {
			move[1] = -0.1f;
		}else {
			move[1] = 0.0f;
		}

		//		try {
		//			if(!((SoundComponent) World.getPlayer().getComponent("Sound")).getSounds().get(0).isPlaying() &&
		//					values[0]!=0 || values[1]!=0) {
		//					((SoundComponent) World.getPlayer().getComponent("Sound")).startSound(0, true);
		//			}
		//		} catch (Exception e) {	}

		mCurMessage.add(new Message(0, "MOVE",  move));

		//		if((values[0] > 1.5f || values[0] < -1.5f) &&
		//				(values[1] > 1.5f || values[1] < -1.5f) ){			
		//
		//			rot = (float) Math.toDegrees(Math.asin((values[0])/
		//					Math.sqrt(Math.pow(values[0], 2)+Math.pow(values[1], 2))));
		//			if(rot<0) rot = (rot * -1) + 180.0f;
		//
		//		}

//		float azimuth = Math.round(values[0]) % 0.2f;
//		float pitch = Math.round(values[1]);
//		float roll = Math.round(values[2]);
//
//		String out = "AZ: " + azimuth;
//		Log.i("ROT", out);


		//		mCurMessage.add(new Message(0, "ROT",  rot));

		World.getPlayer().recieveMessages(mCurMessage);

		//		mCurMessage.clear();

	}


}
