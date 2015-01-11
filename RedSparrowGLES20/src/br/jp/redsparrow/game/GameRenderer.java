package br.jp.redsparrow.game;

import static android.opengl.GLES20.glViewport;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Random;
=======
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
<<<<<<< HEAD
=======
import android.media.MediaPlayer;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.Vibrator;
<<<<<<< HEAD
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Tilemap;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.messages.MessagingSystem;
=======
import android.util.Log;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Message;
import br.jp.redsparrow.engine.core.Tilemap;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
import br.jp.redsparrow.engine.core.missions.MissionSystem;
import br.jp.redsparrow.engine.core.missions.TestMission;
import br.jp.redsparrow.engine.core.util.FPSCounter;
import br.jp.redsparrow.engine.core.util.LogConfig;
import br.jp.redsparrow.engine.core.util.MatrixUtil;
import br.jp.redsparrow.game.ObjectFactory.OBJ_TYPE;
<<<<<<< HEAD
=======
import br.jp.redsparrow.R;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

@SuppressWarnings("unused")
public class GameRenderer implements Renderer {

<<<<<<< HEAD
	//Ativa e desativa controles por acelerometro
	private boolean accelControls = false;
	
=======
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	private static Context mContext;

	private Vibrator mVibrator;

	private int mScreenWidth;	
	private int mScreenHeight;

	private GameObject mDbgBackground;
	private TestMission mTestMission;
<<<<<<< HEAD

	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];

=======
	
	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];

	private ArrayList<Message> mCurMessage;

>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	private final FPSCounter fps = new FPSCounter();

	public GameRenderer(Context context) {

		mContext = context;

<<<<<<< HEAD
		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		Tilemap tilemap = new Tilemap(context, R.raw.tilemap_test);

=======
		mCurMessage = new ArrayList<Message>();

		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		Tilemap tilemap = new Tilemap(context, R.raw.tilemap_test);
		
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
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
<<<<<<< HEAD

		mDbgBackground = ObjectFactory.createObject(mContext, OBJ_TYPE.DBG_BG, 0, 0, 100, 100);
		World.init(mContext);
		World.setPlayer(ObjectFactory.createObject(mContext, OBJ_TYPE.PLAYER, 0f, 0f, 2f, 2f));
		//----TESTE----

		int qd = 1; int qd2 = 1;
		for (int i = 0; i < 30; i++) {
			float size = (random.nextFloat() + random.nextFloat()) * 2;
			World.addObject(ObjectFactory.createObject(mContext, OBJ_TYPE.B_ENEMY, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2,
					size, size));
			if(i%2==0) qd *= -1;
			else qd2 *= -1;
		}
		//--------------
=======
		
		mDbgBackground = ObjectFactory.createObject(mContext, OBJ_TYPE.DBG_BG, 0, 0, 100, 100);
		World.init(mContext);
		World.setPlayer(ObjectFactory.createObject(mContext, OBJ_TYPE.PLAYER, 0f, 0f, 2f, 2f));
		for (int i = 0; i < 30; i++) {
			World.addObject(ObjectFactory.createObject(mContext, OBJ_TYPE.B_ENEMY, 0 + (i/100), 0 + (i/10), 1f, 1f));
		}
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f


	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		mScreenWidth = width;
		mScreenHeight = height;

		glViewport(0, 0, width, height);
<<<<<<< HEAD

		//criando e ajustando matriz de projecao
		MatrixUtil.perspectiveM(projectionMatrix, 45, (float) mScreenWidth
				/ (float) mScreenHeight, 1f, 100f);

		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, -World.getPlayer().getPosition().getX(), -World.getPlayer().getPosition().getY(), -20f);
=======
		//criando e ajustando matriz de projecao
		MatrixUtil.perspectiveM(projectionMatrix, 45, (float) width
				/ (float) height, 1f, 100f);

		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f);
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

		final float[] temp = new float[16];
		Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
		System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);

<<<<<<< HEAD
		Matrix.translateM(modelMatrix, 0, 0 ,0 , -2.5f);
		Matrix.rotateM(modelMatrix, 0, -60f + rot, 1f, 0f, 0f);
	}

	//------------TESTE
	Random random = new Random();
	int times = 0;
	int objIds = -1;
=======
		Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f);
		Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
	}

	//------------TESTE
	int times = 0;
	int objects = 0;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
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


<<<<<<< HEAD
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
=======
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

		mDbgBackground.render(projectionMatrix);
		World.loop(projectionMatrix);

		//------------TESTE
<<<<<<< HEAD
		if(times < 30) times++;
=======
		if(times < 50) times++;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
		else {
			times = 0;
			try {

<<<<<<< HEAD
				float[] moveO = { 0.0f, ((random.nextFloat())/50)*dir };

				MessagingSystem.sendMessagesToObject(objIds, new Message(objIds, "MOVE", moveO));

			} catch (Exception e) {
				objIds = 0;
				dir *= -1;
			}
			objIds++;
		}
		//-------------------------------
		
		World.getPlayer().recieveMessage(new Message(0, "MOVE",  move));
=======
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
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

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
<<<<<<< HEAD
		if (!accelControls) {
			//TODO Movimentacao correta
			move[0] = normalizedX/10;
			move[1] = normalizedY/10;
		}
=======
		//TODO Movimentacao correta
		move[0] = normalizedX/10;
		move[1] = normalizedY/10;
		mCurMessage.add(new Message(0, "MOVE",  move));
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

	}

	public void handleSensorChange(float[] values) {
		//TODO: Calculo correto da rotacao
		//TODO: Rotacao a partir do centro do objeto 

<<<<<<< HEAD
		if (accelControls) {
			if (values[0] < -1.0f) {
				move[0] = 0.1f;
			} else if (values[0] > 1.0f) {
				move[0] = -0.1f;
			} else {
				move[0] = 0.0f;
			}
			if (values[1] < -1.0f) {
				move[1] = 0.1f;
			} else if (values[1] > 1.0f) {
				move[1] = -0.1f;
			} else {
				move[1] = 0.0f;
			}
=======
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
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
		}

		//		try {
		//			if(!((SoundComponent) World.getPlayer().getComponent("Sound")).getSounds().get(0).isPlaying() &&
		//					values[0]!=0 || values[1]!=0) {
		//					((SoundComponent) World.getPlayer().getComponent("Sound")).startSound(0, true);
		//			}
		//		} catch (Exception e) {	}

<<<<<<< HEAD
=======
		mCurMessage.add(new Message(0, "MOVE",  move));

>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
		//		if((values[0] > 1.5f || values[0] < -1.5f) &&
		//				(values[1] > 1.5f || values[1] < -1.5f) ){			
		//
		//			rot = (float) Math.toDegrees(Math.asin((values[0])/
		//					Math.sqrt(Math.pow(values[0], 2)+Math.pow(values[1], 2))));
		//			if(rot<0) rot = (rot * -1) + 180.0f;
		//
		//		}

<<<<<<< HEAD
		//		float azimuth = Math.round(values[0]) % 0.2f;
		//		float pitch = Math.round(values[1]);
		//		float roll = Math.round(values[2]);
		//
		//		String out = "AZ: " + azimuth;
		//		Log.i("ROT", out);

=======
//		float azimuth = Math.round(values[0]) % 0.2f;
//		float pitch = Math.round(values[1]);
//		float roll = Math.round(values[2]);
//
//		String out = "AZ: " + azimuth;
//		Log.i("ROT", out);


		//		mCurMessage.add(new Message(0, "ROT",  rot));

		World.getPlayer().recieveMessages(mCurMessage);

		//		mCurMessage.clear();
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

	}


}
