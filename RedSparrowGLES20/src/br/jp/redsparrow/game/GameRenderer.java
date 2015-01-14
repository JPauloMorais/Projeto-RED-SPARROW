package br.jp.redsparrow.game;

import static android.opengl.GLES20.glViewport;

import java.util.ArrayList;
import java.util.Random;











import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.Vibrator;
import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.Collision;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Tilemap;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.ProjectilePhysicsComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.messages.MessagingSystem;
import br.jp.redsparrow.engine.core.missions.MissionSystem;
import br.jp.redsparrow.engine.core.missions.TestMission;
import br.jp.redsparrow.engine.core.util.FPSCounter;
import br.jp.redsparrow.engine.core.util.LogConfig;
import br.jp.redsparrow.engine.core.util.MatrixUtil;
import br.jp.redsparrow.game.ObjectFactory.OBJ_TYPE;

@SuppressWarnings("unused")
public class GameRenderer implements Renderer {

	//Ativa e desativa controles por acelerometro
	private boolean accelControls = true;

	Vector2f playerMoveVel = new Vector2f(0, 0);
	//	float rot  = 0.0f;
	Vector2f projMoveVel = new Vector2f(0.5f, 0.5f);

	private static Context mContext;

	private Vibrator mVibrator;

	private int mScreenWidth;	
	private int mScreenHeight;

	private GameObject mDbgBackground;
	private TestMission mTestMission;

	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];

	private final FPSCounter fps = new FPSCounter();

	public GameRenderer(Context context) {

		mContext = context;

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
		//----TESTE----
		int qd = 1; int qd2 = 1;
//				float size = (random.nextFloat() + random.nextFloat()) * 2;
		
		//		World.addObject(ObjectFactory.createObject(mContext, OBJ_TYPE.B_ENEMY, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2,
		//				size, size));
		//		World.addObject(ObjectFactory.createObject(mContext, OBJ_TYPE.B_ENEMY, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2,
		//				size, size));
		for (int i = 0; i < 30; i++) {
			World.addObject(ObjectFactory.createObject(mContext, OBJ_TYPE.B_ENEMY, (qd * random.nextFloat() * random.nextInt(10)) + 2*qd, (qd2 * random.nextFloat() * random.nextInt(10)) + 2*qd2,
					2f, 2f), 
					1);
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

		//criando e ajustando matriz de projecao
		MatrixUtil.perspectiveM(projectionMatrix, 45, (float) mScreenWidth
				/ (float) mScreenHeight, 1f, 100f);

		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, -World.getPlayer().getPosition().getX(), -World.getPlayer().getPosition().getY(), -20f);

		final float[] temp = new float[16];
		Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
		System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);

		Matrix.translateM(modelMatrix, 0, 0 ,0 , -2.5f);
		Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
	}

	//------------TESTE
	Random random = new Random();
	int times = 0;
	int objIds = -1;
	int dir = 1;
	//-----------------

	@Override
	public void onDrawFrame(GL10 gl) {

//		Matrix.rotateM(modelMatrix, 0, -45f, 1f, 0f, 0f);


		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, -World.getPlayer().getPosition().getX(), -World.getPlayer().getPosition().getY(), -35f);

		mTestMission.render(projectionMatrix);
		mDbgBackground.render(projectionMatrix);
		World.loop(projectionMatrix);

		//criando e ajustando matriz de projecao
		MatrixUtil.perspectiveM(projectionMatrix, 45, (float) mScreenWidth
				/ (float) mScreenHeight, 1f, 100f);
		
		
		final float[] temp = new float[16];
		Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
		System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
		
		Matrix.translateM(modelMatrix, 0, 0 ,0 , -2.5f);
		//------------TESTE
		if(times < 50) times++;
		else {
			times = 0;

			if ( objIds < World.getObjectCount() ) {

				try {
					if( World.getObjectById(objIds).getType().equals(OBJ_TYPE.B_ENEMY) ){
						
						Vector2f moveO = new Vector2f(0f,
								((random.nextFloat()) / 10) * dir);
						MessagingSystem.sendMessagesToObject(objIds, new Message(
								objIds, "MOVE", moveO));
						((SoundComponent) World.getObjectById(objIds)
								.getUpdatableComponent(1)).startSound(0, false);
						((GunComponent) World.getObjectById(objIds)
								.getUpdatableComponent(2)).shoot(new Vector2f(0f, -0.5f));

						objIds++;
					}
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
				}

			}else{
				objIds = -1;
				dir *= -1;
			}




		}

		//-------------------------------
		World.getPlayer().recieveMessage(new Message(0, "MOVE",  playerMoveVel));

		if(LogConfig.ON) fps.logFrame();
	}

	public static Context getContext() {
		return mContext;
	}

	public void handleTouchPress(float normalizedX, float normalizedY) {
		mVibrator.vibrate(100);
		try {

			Log.i("Input", " Touch em: (" + normalizedX + ", " + normalizedY + ")");

			projMoveVel.setX(normalizedX);
			projMoveVel.setY(normalizedY);
//			if(normalizedX > 0.1f){
//				projMoveVel.setX(0.5f);
//			}else if (normalizedX < -0.1f){
//				projMoveVel.setX(-0.5f);
//			}else {
//				projMoveVel.setX(0f);
//			}
//
//			if(normalizedY > 0.1f){
//				projMoveVel.setY(0.5f);
//			}else if (normalizedY < -0.1f){
//				projMoveVel.setY(-0.5f);
//			}else {
//				projMoveVel.setY(0f);
//			}

			((SoundComponent) World.getPlayer().getUpdatableComponent(1)).startSound(0, false);
			((GunComponent) World.getPlayer().getUpdatableComponent(2)).shoot(projMoveVel);

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
		//TODO: Calculo correto da rotacao
		//TODO: Rotacao a partir do centro do objeto 

		if (accelControls) {

			playerMoveVel.setX(-values[0]/101f);
			playerMoveVel.setY(-values[1]/101f);
//			if (values[0] < -1.0f) {
//				playerMoveVel.setX(0.1f);
//			} else if (values[0] > 1.0f) {
//				playerMoveVel.setX(-0.1f);
//			} else {
//				playerMoveVel.setX(0f);
//			}
//			if (values[1] < -1.0f) {
//				playerMoveVel.setY(0.1f);
//			} else if (values[1] > 1.0f) {
//				playerMoveVel.setY(-0.1f);
//			} else {
//				playerMoveVel.setY(0f);
//			}

		}

		//		try {
		//			if(!((SoundComponent) World.getPlayer().getComponent("Sound")).getSounds().get(0).isPlaying() &&
		//					values[0]!=0 || values[1]!=0) {
		//					((SoundComponent) World.getPlayer().getComponent("Sound")).startSound(0, true);
		//			}
		//		} catch (Exception e) {	}


	}


}
