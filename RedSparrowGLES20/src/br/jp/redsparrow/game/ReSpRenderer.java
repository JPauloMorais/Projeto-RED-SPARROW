package br.jp.redsparrow.game;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import br.jp.redsparrow.engine.core.Game;
import br.jp.redsparrow.engine.core.GameRenderer;
import br.jp.redsparrow.game.ObjectFactory.OBJECT_TYPE;

public class ReSpRenderer extends GameRenderer {

	public ReSpRenderer(Context context, Game game) {
		super(context, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		super.onSurfaceCreated(glUnused, config);
		//		GLES20.glClearColor(0.0f, 0.749f, 1.0f, 0.0f);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		//TODO: Ativar teste p terceira dim
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glClearDepthf(100.0f); 

		GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		//ativando e definindo alpha blending
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc( GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA );
		
		mDbgBackground = game.getObjFactory().createObject(mContext, OBJECT_TYPE.DBG_BG, 0, 0);
		mDbgBackground1 = game.getObjFactory().createObject(mContext, OBJECT_TYPE.DBG_BG1, 0, 0);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		super.onSurfaceChanged(gl, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		super.onDrawFrame(gl);
	}

	@Override
	public void loop(Game game, float[] projectionMatrix) {
		super.loop(game, projectionMatrix);
	}

}
