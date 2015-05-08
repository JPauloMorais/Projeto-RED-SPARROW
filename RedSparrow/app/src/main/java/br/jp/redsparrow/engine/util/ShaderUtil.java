package br.jp.redsparrow.engine.util;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderUtil {

	public static int compileVertexShader(String shaderCode) {
		return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
	}
	public static int compileFragmentShader(String shaderCode) {
		return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
	}
	private static int compileShader(int type, String shaderCode) {

		int shaderObjId = GLES20.glCreateShader(type);

		if (shaderObjId==0) {
			if(LogConfig.ON) Log.w("ShaderUtil", "Nao foi possivel criar shader");
			return 0;
		}

		GLES20.glShaderSource(shaderObjId, shaderCode);
		GLES20.glCompileShader(shaderObjId);
		final int[] compileStatus = new int[1];
		GLES20.glGetShaderiv(shaderObjId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

		if (LogConfig.ON) {
			Log.v("ShaderUtil", "Compilacao do shader: \n" + shaderCode + "\n:"
					+ GLES20.glGetShaderInfoLog(shaderObjId));
		}

		if (compileStatus[0] == 0) {
			GLES20.glDeleteShader(shaderObjId);
			if (LogConfig.ON) {
				Log.w("ShaderUtil", "Falha na compilacao do shader.");
			}
			return 0;
		}

		return shaderObjId;

	}

	public static int linkProgram(int vertexShaderId, int fragmentShaderId) {

		final int progObjId = GLES20.glCreateProgram();
		if (progObjId == 0) {
			if (LogConfig.ON) {
				Log.w("ShaderUtil", "Erro ao criar shader program");
			}
			return 0;
		}

		GLES20.glAttachShader(progObjId, vertexShaderId);
		GLES20.glAttachShader(progObjId, fragmentShaderId);

		GLES20.glLinkProgram(progObjId);
		final int[] linkStatus = new int[1];
		GLES20.glGetProgramiv(progObjId, GLES20.GL_LINK_STATUS, linkStatus, 0);
		if (LogConfig.ON) {
			Log.v("ShaderUtil", "Link do shader program: \n"
					+ GLES20.glGetProgramInfoLog(progObjId));
		}
		if (linkStatus[0] == 0) {
			GLES20.glDeleteProgram(progObjId);
			if (LogConfig.ON) {
				Log.w("ShaderUtil", "Erro ao fazer link do shader program.");
			}
			return 0;
		}

		return progObjId;

	}

	public static boolean validateProgram(int progObjId) {
		GLES20.glValidateProgram(progObjId);
		final int[] validateStatus = new int[1];
		GLES20.glGetProgramiv(progObjId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
		Log.v("ShaderUtil", "Validacao do shader program: " + validateStatus[0]
				+ "\nLog:" + GLES20.glGetProgramInfoLog(progObjId));
		return validateStatus[0] != 0;
	}

	public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
		int program;

		int vertexShader = compileVertexShader(vertexShaderSource);
		int fragmentShader = compileFragmentShader(fragmentShaderSource);

		program = linkProgram(vertexShader, fragmentShader);
		if (LogConfig.ON) {
			validateProgram(program);
		}
		return program;
	}

}
