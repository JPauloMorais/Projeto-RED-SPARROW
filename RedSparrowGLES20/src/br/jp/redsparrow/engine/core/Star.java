package br.jp.redsparrow.engine.core;

import android.opengl.GLES20;
import br.jp.redsparrow.engine.shaders.ColorShaderProg;
import br.jp.redsparrow.game.GameRenderer;

public class Star {

	private static final String A_COLOR = "a_Color";
	private static final int POSITION_COMPONENT_COUNT = 3;
	private static final int COLOR_COMPONENT_COUNT = 3;
	private static final int STRIDE =
	(POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * 4;
	
	private int aColorLocation;
	private float[] mVertData;
	private VertexArray mVertArray;
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
		
		mVertArray = new VertexArray(mVertData);
	
		mColShader = new ColorShaderProg(GameRenderer.getContext());
		
	}
	
	public void render(float[] projMatrix) {
		
		mVertArray.setVertexAttribPointer(0, mColShader.getPositionAttributeLocation(), 3, 0);
		mVertArray.setVertexAttribPointer(3, mColShader.getColorAttributeLocation(), 3, 0);
		mColShader.useProgram();
		
		GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 3);
	}

	public float[] getPosition() {
		return mVertData;
	}

	public void setPosition(float[] mPosition) {
		this.mVertData = mPosition;
	}

}
