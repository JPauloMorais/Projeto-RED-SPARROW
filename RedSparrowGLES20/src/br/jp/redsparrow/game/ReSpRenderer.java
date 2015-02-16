package br.jp.redsparrow.game;

import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.game.GameRenderer;

public class ReSpRenderer extends GameRenderer {
	
	public ReSpRenderer(Context context, Game game) {
		super(game);
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
//		GLES20.glClearDepthf(100.0f); 

		GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		//ativando e definindo alpha blending
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc( GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA );
				
		game.create();
	}

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

	@Override
	public void onDrawFrame(GL10 gl) {
		super.onDrawFrame(gl);
		
		//Renderizando 
		game.loop(viewMatrix, projectionMatrix, viewProjectionMatrix);
		
	}

	@Override
	public void loop(Game game, float[] projectionMatrix) {
		super.loop(game, projectionMatrix);
	}

}
