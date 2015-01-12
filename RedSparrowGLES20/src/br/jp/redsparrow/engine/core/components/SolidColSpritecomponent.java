package br.jp.redsparrow.engine.core.components;

import android.content.Context;
import android.opengl.GLES20;
import br.jp.redsparrow.engine.core.Constants;
import br.jp.redsparrow.engine.core.Renderable;
import br.jp.redsparrow.engine.core.VertexArray;
import br.jp.redsparrow.engine.shaders.ColorShaderProg;

public class SolidColSpritecomponent extends Component implements Renderable {

	private static final int POSITION_COUNT = 2;
	private static final int TEXTURE_COORDS_COUNT = 2;
	private static final int STRIDE = (POSITION_COUNT
			+ TEXTURE_COORDS_COUNT) * Constants.BYTES_PER_FLOAT;

	private ColorShaderProg colorShader;

	public SolidColSpritecomponent(String name, Context context) {
		super(name);
		
		colorShader = new ColorShaderProg(context);

	}
	
	public SolidColSpritecomponent(Context context) {
		super("Sprite");

		colorShader = new ColorShaderProg(context);
	}


	public void bindData(VertexArray vertexArray, ColorShaderProg colorShader) {

		vertexArray.setVertexAttribPointer(
				0,
				colorShader.getPositionAttributeLocation(),
				POSITION_COUNT,
				STRIDE);

	}

	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {
		
		colorShader.useProgram();
		bindData(vertexArray, colorShader);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
		
	}

}
