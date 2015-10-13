package br.jp.redsparrow.engine.rendering;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.FloatBuffer;

import br.jp.redsparrow.engine.Assets;
import br.jp.redsparrow.engine.Consts;
import br.jp.redsparrow.engine.math.Vec3;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class SpriteShader extends Shader
{
	private static final String TAG = "Shader";

	private int MVMatrixUniformHandle;
	private int MVPMatrixUniformHandle;
	private int textureUniformHandle;
	private int lightPosUniformHandle;

	private int positionAttributeHandle;
	private int normalAttributeHandle;
	private int colorAttributeHandle;
	private int texcoordAttributeHandle;

	public SpriteShader ()
	{
		super(Assets.getShaderText("SpriteVertex"), Assets.getShaderText("SpriteFragment"),
		      A_POSITION, A_NORMAL, A_COLOR, A_TEXCOORDS);
	}

	@Override
	public void getLocations()
	{
		MVMatrixUniformHandle = GLES20.glGetUniformLocation(programHandle, U_MVMATRIX);
		MVPMatrixUniformHandle = GLES20.glGetUniformLocation(programHandle, U_MVPMATRIX);
		textureUniformHandle = GLES20.glGetUniformLocation(programHandle, U_TEXTURE);
		lightPosUniformHandle = GLES20.glGetUniformLocation(programHandle, U_LIGHT_POSITION);

		positionAttributeHandle = GLES20.glGetAttribLocation(programHandle, A_POSITION);
		normalAttributeHandle = GLES20.glGetAttribLocation(programHandle, A_NORMAL);
		colorAttributeHandle = GLES20.glGetAttribLocation(programHandle, A_COLOR);
		texcoordAttributeHandle = GLES20.glGetAttribLocation(programHandle, A_TEXCOORDS);
	}

	public void setMVMatrixUniform (float[] MVMatrix)
	{
		GLES20.glUniformMatrix4fv(MVMatrixUniformHandle, 1, false, MVMatrix, 0);
	}

	public void setMVPMatrixUniform(float[] MVPMatrix)
	{
		GLES20.glUniformMatrix4fv(MVPMatrixUniformHandle, 1, false, MVPMatrix, 0);
	}

	public void setTextureUniform(int textureId)
	{
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glUniform1i(textureUniformHandle, 0);
	}

	public void setLightPosEyeSpaceUniform(float x, float y, float z)
	{
		GLES20.glUniform3f(lightPosUniformHandle, x, y, z);
	}

	public void setPositionAttribute(FloatBuffer positionDataBuffer)
	{
		positionDataBuffer.position(0);
		GLES20.glVertexAttribPointer(positionAttributeHandle, Consts.SPRITE_POSITION_DATA_SIZE, GLES20.GL_FLOAT, false, 0, positionDataBuffer);
		GLES20.glEnableVertexAttribArray(positionAttributeHandle);
	}

	public void setNormalAttribute(FloatBuffer normalDataBuffer)
	{
		normalDataBuffer.position(0);
		GLES20.glVertexAttribPointer(normalAttributeHandle, Consts.NORMAL_DATA_SIZE, GLES20.GL_FLOAT, false, 0, normalDataBuffer);
		GLES20.glEnableVertexAttribArray(normalAttributeHandle);
	}

	public void setColorAttribute(FloatBuffer colorDataBuffer)
	{
		colorDataBuffer.position(0);
		GLES20.glVertexAttribPointer(colorAttributeHandle, Consts.SPRITE_COLOR_DATA_SIZE, GLES20.GL_FLOAT, false, 0, colorDataBuffer);
		GLES20.glEnableVertexAttribArray(colorAttributeHandle);
	}

	public void setTexCoordAttribute(FloatBuffer texCoordDataBuffer)
	{
		texCoordDataBuffer.position(0);
		GLES20.glVertexAttribPointer(texcoordAttributeHandle, Consts.TEXCOORD_DATA_SIZE, GLES20.GL_FLOAT, false, 0, texCoordDataBuffer);
		GLES20.glEnableVertexAttribArray(texcoordAttributeHandle);
	}
}
