package br.jp.redsparrow_zsdemo.game;

import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.Matrix;
import br.jp.redsparrow_zsdemo.engine.game.Game;
import br.jp.redsparrow_zsdemo.engine.game.GameRenderer;

public class ReSpGameRenderer extends GameRenderer {
	
	public ReSpGameRenderer(Context context, Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		super.onSurfaceCreated(glUnused, config);
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

}
