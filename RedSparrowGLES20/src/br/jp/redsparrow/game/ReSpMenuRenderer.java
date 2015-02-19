
package br.jp.redsparrow.game;

import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import br.jp.redsparrow.engine.core.particles.ParticleEmitter;
import br.jp.redsparrow.engine.core.particles.ParticleSystem;
import br.jp.redsparrow.engine.shaders.ParticleShaderProg;

public class ReSpMenuRenderer implements GLSurfaceView.Renderer {

	private Context mContext;

	private final float[] viewMatrix = new float[16];
	private final float[] projectionMatrix = new float[16];
	private final float[] viewProjectionMatrix = new float[16];

	private ParticleShaderProg mPartShader;
	private ParticleSystem mParticleSystem;
	private ParticleEmitter mEmitterWhite;

	private int times = 0;

	public ReSpMenuRenderer(Context context) {
		mContext = context;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		mPartShader = new ParticleShaderProg(mContext);

		GLES20.glClearColor(0, 0, 0, 1);

		GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc( GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA );

		mParticleSystem = new ParticleSystem(100000, mContext);
		mEmitterWhite = new ParticleEmitter(new float[]{0,0,0}, new float[]{0,0,5f}, Color.WHITE, 360, 5);

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		glViewport(0, 0, width, height);

		//criando e ajustando matriz de projecao em perspectiva
		Matrix.perspectiveM(projectionMatrix, 0, 100, (float) width
				/ (float) height, 1, 100);
		Matrix.setLookAtM(viewMatrix, 0,
				0f, 0f, 45f,
				0f, 0f, 0f,
				0f, 1f, 1f);	
	}

	@Override
	public void onDrawFrame(GL10 gl) {

		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

		mEmitterWhite.addParticles(mParticleSystem, mParticleSystem.getCurTime(), 3);

		mParticleSystem.render(mPartShader,viewProjectionMatrix);

		Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
	}

}
