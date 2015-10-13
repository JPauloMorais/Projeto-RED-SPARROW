package br.jp.redsparrow.engine.rendering;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by JoaoPaulo on 09/10/2015.
 */
public abstract class Shader
{
	private static final String TAG = "Shader";

	protected static final String U_MVPMATRIX      = "u_MVPMatrix";
	protected static final String U_MVMATRIX       = "u_MVMatrix";
	protected static final String U_TEXTURE        = "u_Texture";
	protected static final String U_LIGHT_POSITION = "u_LightPos";

	protected static final String A_POSITION  = "a_Position";
	protected static final String A_NORMAL    = "a_Normal";
	protected static final String A_COLOR     = "a_Color";
	protected static final String A_TEXCOORDS = "a_TexCoord";

	protected int programHandle;

	public Shader (String vertexSrc, String fragmentSrc, String ... attribs)
	{
		programHandle = createAndLinkProgram(compileShader(GLES20.GL_VERTEX_SHADER, vertexSrc),
		                                     compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentSrc),
		                                     attribs);
	}

	public void use() {GLES20.glUseProgram(programHandle);}

	public abstract void getLocations();

	protected int compileShader (final int shaderType, final String shaderSource)
	{
		int shaderHandle = GLES20.glCreateShader(shaderType);

		if(shaderHandle != 0)
		{
			GLES20.glShaderSource(shaderHandle, shaderSource);
			GLES20.glCompileShader(shaderHandle);
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
			if (compileStatus[0] == 0)
			{
				Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle = 0;
			}
		}

		if (shaderHandle == 0)
			Log.e(TAG, "Error creating shader.");

		return shaderHandle;
	}

	protected int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String ... attributes)
	{
		int programHandle = GLES20.glCreateProgram();

		if (programHandle != 0)
		{
			GLES20.glAttachShader(programHandle, vertexShaderHandle);
			GLES20.glAttachShader(programHandle, fragmentShaderHandle);
			if (attributes != null)
			{
				final int size = attributes.length;
				for (int i = 0; i < size; i++)
					GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
			}

			GLES20.glLinkProgram(programHandle);
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] == 0)
			{
				Log.e(TAG, "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
			}
		}

		if (programHandle == 0)
			Log.e(TAG, "Error creating program.");

		return programHandle;
	}
}
