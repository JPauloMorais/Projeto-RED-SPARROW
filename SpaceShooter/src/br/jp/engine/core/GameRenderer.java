package br.jp.engine.core;

import static javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT;
import static javax.microedition.khronos.opengles.GL10.GL_DEPTH_BUFFER_BIT;
import static javax.microedition.khronos.opengles.GL10.GL_DEPTH_TEST;
import static javax.microedition.khronos.opengles.GL10.GL_MODELVIEW;
import static javax.microedition.khronos.opengles.GL10.GL_PROJECTION;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import br.jp.engine.components.SpriteComponent;
import br.jp.engine.util.FPSCounter;
import br.jp.spaceshooter.ObjectFactory;
import br.jp.spaceshooter.ObjectFactory.ObjectType;

public class GameRenderer implements Renderer {

	private float mWidth, mHeight;
//	private Context mContext;

	private GameObject testObj;
	private World world;
	
	private FPSCounter log;

	public GameRenderer(Context context) {

//		mContext = context;
		mWidth = 180;
		mHeight = 320;
		
		log = new FPSCounter();
		world = new World();
				
		for (int i = 0; i < 300; i++) {			
			world.addObject(ObjectFactory.createObject(context, ObjectType.DEFAULT, -1f, -1f,
					1,0,0,1));
			
			world.addObject(ObjectFactory.createObject(context, ObjectType.DEFAULT, 0f, 0f,
					1,0,0,1));
			
			world.addObject(ObjectFactory.createObject(context, ObjectType.DEFAULT, -1f, -1f,
					1,0,0,1));
			
		}
		
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
		gl.glDisable(GL_DEPTH_TEST); 	
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						
			height = 1; 						
		}
		
		this.mWidth  = width;
		this.mHeight = height;
		
		gl.glViewport(0, 0, width, height); 	
		gl.glMatrixMode(GL_PROJECTION); 	
		gl.glLoadIdentity(); 					

		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 1f, 1000.0f);
		gl.glMatrixMode(GL_MODELVIEW); 	
		gl.glLoadIdentity(); 					
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		//Clear Screen And Depth Buffer
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
		gl.glLoadIdentity();					

		world.loop(gl);
		
		log.logFrame();
		
	}

	public void handleTouchPress(float normalizedX, float normalizedY) {
		// TODO Auto-generated method stub		
		Log.i("TouchPress", "TouchPress");
	}

	public void handleTouchDrag(float normalizedX, float normalizedY) {
		// TODO Auto-generated method stub
		Log.i("TouchDrag", "TouchDrag");
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
