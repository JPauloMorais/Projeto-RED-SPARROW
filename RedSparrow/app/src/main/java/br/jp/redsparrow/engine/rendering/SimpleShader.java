package br.jp.redsparrow.engine.rendering;

import android.opengl.GLES20;

import java.nio.FloatBuffer;

import br.jp.redsparrow.engine.Assets;

/**
 * Created by JoaoPaulo on 11/10/2015.
 */
public class SimpleShader extends Shader
{
	private int MVPMatrixUniformHandle;
	private int positionAttributeHandle;
	private int colorAttributeHandle;

	public SimpleShader ()
	{
		super(Assets.getShaderText("SimpleVertex"), Assets.getShaderText("SimpleFragment"),
		      A_POSITION, A_COLOR);
	}

	@Override
	public void getLocations ()
	{
		MVPMatrixUniformHandle = GLES20.glGetUniformLocation(programHandle, U_MVPMATRIX);
		positionAttributeHandle = GLES20.glGetAttribLocation(programHandle, A_POSITION);
		colorAttributeHandle = GLES20.glGetAttribLocation(programHandle, A_COLOR);
	}

	public void setMVPMatrixUniform(float[] MVPMatrix)
	{
		GLES20.glUniformMatrix4fv(MVPMatrixUniformHandle, 1, false, MVPMatrix, 0);
	}

	public void setPositionAttribute(FloatBuffer positionDataBuffer)
	{
		positionDataBuffer.position(0);
		GLES20.glVertexAttribPointer(positionAttributeHandle, 2, GLES20.GL_FLOAT, false, 0, positionDataBuffer);
		GLES20.glEnableVertexAttribArray(positionAttributeHandle);
	}

	public void setColorAttribute(FloatBuffer colorDataBuffer)
	{
		colorDataBuffer.position(0);
		GLES20.glVertexAttribPointer(colorAttributeHandle, 4, GLES20.GL_FLOAT, false, 0, colorDataBuffer);
		GLES20.glEnableVertexAttribArray(colorAttributeHandle);
	}
}
