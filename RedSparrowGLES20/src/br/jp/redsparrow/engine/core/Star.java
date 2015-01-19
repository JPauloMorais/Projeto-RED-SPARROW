package br.jp.redsparrow.engine.core;

import br.jp.redsparrow.engine.shaders.ColorShaderProg;
import br.jp.redsparrow.game.GameRenderer;
import android.opengl.GLES20;

public class Star {

	private float[] mVertData;
	private ColorShaderProg mColShader;
	
	public Star(float x, float y, float z, float r, float g, float b) {
		
		mVertData = new float[6];
		//posicao da estrela
		mVertData[0] = x;
		mVertData[1] = y;
		mVertData[2] = z;
		//cor da estrela
		mVertData[3] = r;
		mVertData[4] = g;
		mVertData[5] = b;
		
		mColShader = new ColorShaderProg(GameRenderer.getContext());
		
	}
	
	public void render(float[] projMatrix) {
		
		GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
	}

	public float[] getPosition() {
		return mVertData;
	}

	public void setPosition(float[] mPosition) {
		this.mVertData = mPosition;
	}

}
