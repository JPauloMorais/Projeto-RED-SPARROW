package br.jp.redsparrow_zsdemo.engine.shaders;

import android.content.Context;
import android.opengl.GLES20;

import br.jp.redsparrow_zsdemo.R;

public class ColorShaderProg extends ShaderProg {

	private final int uMatrixLocation;

	private final int aPositionLocation;
	private final int aColorLocation;

	public ColorShaderProg(Context context) {
		super(context, R.raw.simple_vertex,
				R.raw.simple_fragment);

		uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

		aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
		aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
	}

	public void setUniforms(float[] matrix) {
		GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
	}
	
	public int getPositionAttributeLocation() {
		return aPositionLocation;
	}
	
	public int getColorAttributeLocation() {
		return aColorLocation;
	}

}
