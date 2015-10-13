package br.jp.redsparrow.engine.rendering;

import android.opengl.GLES20;

import br.jp.redsparrow.engine.Assets;

/**
 * Created by JoaoPaulo on 09/10/2015.
 */
public class LightPointShader extends Shader
{
	private int MVPMatrixUniformHandle;

	public LightPointShader ()
	{
		super(Assets.getShaderText("LightPointVertex"), Assets.getShaderText("LightPointFragment"));
	}

	public void getLocations()
	{
		MVPMatrixUniformHandle = GLES20.glGetUniformLocation(programHandle, U_MVPMATRIX);
	}

	public void setMVPMatrixUniform(float[] MVPMatrix)
	{
		GLES20.glUniformMatrix4fv(MVPMatrixUniformHandle, 1, false, MVPMatrix, 0);
	}
}
