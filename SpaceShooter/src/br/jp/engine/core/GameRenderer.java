package br.jp.engine.core;

import static javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT;
import static javax.microedition.khronos.opengles.GL10.GL_DEPTH_TEST;
import static javax.microedition.khronos.opengles.GL10.GL_MODELVIEW;
import static javax.microedition.khronos.opengles.GL10.GL_PROJECTION;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import br.jp.engine.util.FPSCounter;
import br.jp.spaceshooter.ObjectFactory;
import br.jp.spaceshooter.ObjectFactory.ObjectType;

public class GameRenderer implements Renderer {

	private float mWidth, mHeight;
	//	private Context mContext;

	private GameObject mPlayer;
	private World mWorld;

	private FPSCounter log;

	public GameRenderer(Context context) {

		//		mContext = context;
		mWidth = 180;
		mHeight = 320;

		log = new FPSCounter();

		mPlayer = ObjectFactory.createObject(context, ObjectType.PLAYER, -0.5f, -0.5f, 1,0,0,1);
		mWorld = new World();

		mWorld.addObject(mPlayer);

//		for (int i = 0; i < 30; i++) {			
//			mWorld.addObject(ObjectFactory.createObject(context, ObjectType.DEFAULT, -0.5f + i/10, -0.5f + i/10,
//					1,0,0,1));
//		}

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//obtendo matriz de projecao
		gl.glMatrixMode(GL_PROJECTION);
		//obtendo matriz identidade
		gl.glLoadIdentity();
		//setando projecao para o tamanho da tela
		gl.glOrthof(0, mWidth, 0, mHeight, -1, 1);
		//obtendo matriz de modelview
		gl.glMatrixMode(GL_MODELVIEW);
		//setando cor de limpeza da tela
		gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
		//destivando teste para 3a dimensao
//		gl.glDisable(GL_DEPTH_TEST); 	
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						
			height = 1; 						
		}

		//Obtendo novas dimensoes da tela
		this.mWidth  = width;
		this.mHeight = height;

		gl.glViewport(0, 0, width, height); 	
		gl.glMatrixMode(GL_PROJECTION); 	
		gl.glLoadIdentity(); 					

		//Calculando Aspect Ratio
		GLU.gluPerspective(gl, 70.0f, (float)width / (float)height, 1f, 1000.0f);
		gl.glMatrixMode(GL_MODELVIEW); 	
		gl.glLoadIdentity(); 					
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		//Limpando tela com a cor no Color Buffer
		gl.glClear(GL_COLOR_BUFFER_BIT);	
		gl.glLoadIdentity();					

		mWorld.loop(gl);

		log.logFrame();

	}

	public void handleTouchPress(float normalizedX, float normalizedY) {
		// TODO Auto-generated method stub		
		if(LogConfig.IS_DEBUGGING) Log.i("INPUT", "TouchPress");
	}

	public void handleTouchDrag(float normalizedX, float normalizedY) {
		// TODO Auto-generated method stub
		if(LogConfig.IS_DEBUGGING) Log.i("INPUT", "TouchDrag");
	}

	float[] move = {0.0f,0.0f};
	float rot  = 0.0f;
	public void handleSensorChange(float[] values) {
		//TODO Calculo correto da rotacao
		//TODO Rotacao a partir do centro do objeto 

		
		if(values[0] < -2.0f) {
			move[0] = 0.55f;
		}
		else if(values[0] > 2.0f) {
			move[0] = -0.55f;
		}else {
			move[0] = 0.0f;
		}
		
		if(values[1] < -2.0f) {
			move[1] = 0.55f;	
		}
		else if(values[1] > 2.0f) {
			move[1] = -0.55f;
		}else {
			move[1] = 0.0f;
		}
		
		mWorld.sendMessage(new Message(0, "MOVE",  move));
		
//		if((values[0] > 1.5f || values[0] < -1.5f) &&
//				(values[1] > 1.5f || values[1] < -1.5f) ){			
//			
//			rot = (float) Math.toDegrees(Math.asin((values[0])/
//					Math.sqrt(Math.pow(values[0], 2)+Math.pow(values[1], 2))));
//			if(rot<0) rot = (rot * -1) + 180.0f;
//			
//		}
//		
//		Log.i("ANGLE", "A: " + rot);		
//		
//		mWorld.sendMessage(new Message(0, "ROT",  rot));

	}

	public float getWidth() {
		return mWidth;
	}

	public void setWidth(float width) {
		mWidth = width;
	}

	public float getHeight() {
		return mHeight;
	}

	public void setHeight(float height) {
		mHeight = height;
	}



}
