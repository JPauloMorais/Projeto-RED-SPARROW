package br.jp.engine.core;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GameView extends GLSurfaceView {

	public GameView(Context context) {
		super(context);
		setEGLContextClientVersion(1);
		setRenderer(new GameRenderer(context));
	}

	public void resume() {
		// TODO Auto-generated method stub
		requestRender();
	}

	public void pause() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
