package br.jp.redsparrow_zsdemo.game;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.Matrix;

import br.jp.redsparrow_zsdemo.engine.game.Camera;
import br.jp.redsparrow_zsdemo.engine.game.Game;

public class ReSpCamera extends Camera {

	private float distInterpor = 0;
	private float lookDirX = 0;
	private float lookDirY = 0;


	public ReSpCamera(Game game) {
		super(game, game.getWorld().getPlayer().getX(), game.getWorld().getPlayer().getY(), 57);

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
	}

	@Override
	public void loop(Game game, float[] projectionMatrix) {

//		distInterpor += 0.00f;

		mFollowX = game.getWorld().getPlayer().getX();
		mFollowY  = game.getWorld().getPlayer().getY();
//		lookDirX = ((PhysicsComponent)game.getWorld().getPlayer().getUpdatableComponent("Physics")).getVelocity().getX();
//		lookDirY = ((PhysicsComponent)game.getWorld().getPlayer().getUpdatableComponent("Physics")).getVelocity().getY();

		Matrix.setLookAtM(game.getRenderer().viewMatrix, 0,
				mFollowX, mFollowY, mDistY + distInterpor,
				mFollowX, mFollowY, 0f,
				0,1,0);
//				lookDirX , lookDirY, 0f);

		Matrix.multiplyMM(game.getRenderer().viewProjectionMatrix, 0, game.getRenderer().projectionMatrix, 0, game.getRenderer().viewMatrix, 0);
	}



}
