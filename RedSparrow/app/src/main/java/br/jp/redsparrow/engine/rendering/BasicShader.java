package br.jp.redsparrow.engine.rendering;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class BasicShader
{
	private static final String TAG = "Shader";

	protected static final String U_MVPMATRIX = "u_MVPMatrix";
	protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
	protected static final String A_POSITION  = "a_Position";
	protected static final String A_TEXCOORDS = "a_TextureCoords";

	private static final String VERTEX_SOURCE = "\n" +
	                                            "uniform mat4 " + U_MVPMATRIX + ";\n" +
	                                            "attribute vec4 " + A_POSITION + ";\n" +
	                                            "attribute vec2 " + A_TEXCOORDS + ";\n\n" +
	                                            "varying vec2 v_TextureCoords;\n" +
	                                            "void main()\n" +
	                                            "{\n" +
	                                            "\tv_TextureCoords = " + A_TEXCOORDS + ";\n" +
	                                            "\tgl_Position = " + U_MVPMATRIX + " * " + A_POSITION + ";\n" +
	                                            "\n}";

	private static final String FRAGMENT_SOURCE = "\n" +
	                                              "precision mediump float;\n" +
	                                              "uniform sampler2D " + U_TEXTURE_UNIT + ";\n" +
	                                              "varying vec2 v_TextureCoords;\n" +
	                                              "\n" +
	                                              "void main()\n" +
	                                              "{\n" +
	                                              "\tgl_FragColor = texture2D(" + U_TEXTURE_UNIT + ", v_TextureCoords);\n" +
	                                              "}\n";

	public int programHandle;

	public int mvpMatrixUniform;
	public int textureUnitUniform;
	public int positionAttribute;
	public int texcoordAttribute;

	public BasicShader ()
	{
		programHandle = createAndLinkProgram(compileShader(GLES20.GL_VERTEX_SHADER, VERTEX_SOURCE),
		                                     compileShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SOURCE),
		                                     A_POSITION, A_TEXCOORDS);
	}

	public void use()
	{
		GLES20.glUseProgram(programHandle);
	}

	public void getLocations()
	{
		mvpMatrixUniform = GLES20.glGetUniformLocation(programHandle, U_MVPMATRIX);
		textureUnitUniform = GLES20.glGetUniformLocation(programHandle, U_TEXTURE_UNIT);
		positionAttribute = GLES20.glGetAttribLocation(programHandle, A_POSITION);
		texcoordAttribute = GLES20.glGetAttribLocation(programHandle, A_TEXCOORDS);
	}

	protected int compileShader (final int type, String src)
	{
		int shaderHandle = GLES20.glCreateShader(type);

		if(shaderHandle != 0)
		{
			GLES20.glShaderSource(shaderHandle, src);

			GLES20.glCompileShader(shaderHandle);

			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			if(compileStatus[0] == 0)
			{
				Log.w(TAG, "Erro na compilacao do shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle = 0;
			}
		}

		if(shaderHandle == 0)
			throw new RuntimeException("Erro na criacao do shader.");

		return shaderHandle;
	}

	protected int createAndLinkProgram (int vertexShaderHandle, int fragmentShaderHandle, String ... attributes)
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
				Log.w(TAG, "Erro na compilacao do shader prog.: " + GLES20.glGetProgramInfoLog(programHandle));
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
			}
		}

		if (programHandle == 0)
			throw new RuntimeException("Erro na criacao do shader prog.");

		return programHandle;
	}
}
