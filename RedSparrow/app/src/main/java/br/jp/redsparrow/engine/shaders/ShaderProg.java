package br.jp.redsparrow.engine.shaders;

import android.content.Context;
import android.opengl.GLES20;
import br.jp.redsparrow.engine.util.ShaderUtil;
import br.jp.redsparrow.engine.util.TextFileReader;

public class ShaderProg {
	
	protected static final String U_TIME = "u_Time";
	protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
	protected static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";

	protected static final String U_MATRIX = "u_Matrix";
	protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

	protected static final String A_POSITION = "a_Position";
	protected static final String A_COLOR = "a_Color";
	protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

	protected final int program;

	protected ShaderProg(Context context, int vertexShaderResourceId,
			int fragmentShaderResourceId) {
		// Compile the shaders and link the program.
		program = ShaderUtil.buildProgram(
				TextFileReader.readTextFromFile(
						context, vertexShaderResourceId),
						TextFileReader.readTextFromFile(
								context, fragmentShaderResourceId));
	}

	public void useProgram() {
		GLES20.glUseProgram(program);
	}

}
