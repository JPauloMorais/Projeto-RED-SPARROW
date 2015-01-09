package br.jp.redsparrow.engine.core.components;

import android.content.Context;
import android.opengl.GLES20;
import br.jp.redsparrow.engine.core.Constants;
import br.jp.redsparrow.engine.core.Renderable;
import br.jp.redsparrow.engine.core.VertexArray;
import br.jp.redsparrow.engine.core.util.TextureUtil;
import br.jp.redsparrow.engine.shaders.TextureShaderProg;

public class SpriteComponent extends Component implements Renderable {

	private static final int POSITION_COUNT = 2;
	private static final int TEXTURE_COORDS_COUNT = 2;
	private static final int STRIDE = (POSITION_COUNT
			+ TEXTURE_COORDS_COUNT) * Constants.BYTES_PER_FLOAT;

	private TextureShaderProg textureProgram;

	private int texture;

	public SpriteComponent(String name, Context context, int imgId) {
		super(name);
		
		textureProgram = new TextureShaderProg(context);
		texture = TextureUtil.loadTexture(context, imgId);
	}
	
	public SpriteComponent(Context context, int imgId) {
		super("Sprite");

		textureProgram = new TextureShaderProg(context);
		texture = TextureUtil.loadTexture(context, imgId);
	}


	public void bindData(VertexArray vertexArray, TextureShaderProg textureProgram) {

		vertexArray.setVertexAttribPointer(
				0,
				textureProgram.getPositionAttributeLocation(),
				POSITION_COUNT,
				STRIDE);
		vertexArray.setVertexAttribPointer(
				POSITION_COUNT,
				textureProgram.getTextureCoordinatesAttributeLocation(),
				TEXTURE_COORDS_COUNT,
				STRIDE);

	}

	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {
		textureProgram.useProgram();
		textureProgram.setUniforms(projectionMatrix, texture);
		bindData(vertexArray, textureProgram);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
	}

}
