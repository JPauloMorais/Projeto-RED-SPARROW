package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.Matrix;
import br.jp.redsparrow.engine.core.components.Component;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.util.TextureUtil;
import br.jp.redsparrow.engine.shaders.TextureShaderProg;

public class HUDitem implements Updatable, Renderable {

	private Vector2f mPosition;
	private float mWidth;
	private float mHeight;
	private RectF mBounds;
	private Vector2f mColOffset;

	private float[] mVertsData;

	private boolean isDead = false;

	//	private Vector2f mTexturePosition;

	private VertexArray mVertexArray;

	private ArrayList<Component> mUpdatableComponents;
	private ArrayList<Component> mRenderableComponents;

	private ArrayList<Message> mCurMessages;
	private ArrayList<Message> mMessagesToRemove;

	private static final int POSITION_COUNT = 2;
	private static final int TEXTURE_COORDS_COUNT = 2;
	private static final int STRIDE = (POSITION_COUNT
			+ TEXTURE_COORDS_COUNT) * Constants.BYTES_PER_FLOAT;

	private TextureShaderProg textureProgram;

	private int texture;

	public HUDitem(Context context, int imgId, float x, float y, float width, float height) {

		mPosition = new Vector2f(x, y);
		mWidth = width;
		mHeight = height;

		textureProgram = new TextureShaderProg(context);
		texture = TextureUtil.loadTexture(context, imgId);
		
		updateVertsData(x, y);
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
	public void update(GameObject object) {

		
		
	}

	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {
		textureProgram.useProgram();
		textureProgram.setUniforms(projectionMatrix, texture);
		bindData(mVertexArray, textureProgram);
		Matrix.rotateM(projectionMatrix, 0, 10, 1f, 0, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
	}

	//Redefine vertices
	private void updateVertsData(float x, float y){
		if (mBounds.centerX() != x || mBounds.centerY() != y) {
			//                     left                 top            right         bottom  
			mBounds.set( x-((mWidth/2)) , y-(mHeight/2), x+(mWidth/2), y+((mHeight/2)) );

			//   X   ,  Y   ,     S   , T
			mVertsData[0] = x;              mVertsData[1] =  y             ;     mVertsData[2] = 0.5f; mVertsData[3] = 0.5f; //centro
			mVertsData[4] = mBounds.right;   mVertsData[5] = mBounds.top ;     mVertsData[6] = 0f  ; mVertsData[7] = 1f  ; //inf. esq.
			mVertsData[8] = mBounds.left;  mVertsData[9] = mBounds.top ;     mVertsData[10] = 1f ; mVertsData[11] = 1f ; //inf. dir.
			mVertsData[12] = mBounds.left; mVertsData[13] = mBounds.bottom   ;     mVertsData[14] = 1f ; mVertsData[15] =  0f; //sup. dir.
			mVertsData[16] = mBounds.right ; mVertsData[17] = mBounds.bottom   ;     mVertsData[18] = 0f ; mVertsData[19] = 0f ; //sup. esq.
			mVertsData[20] = mBounds.right ; mVertsData[21] = mBounds.top;     mVertsData[22] = 0f ; mVertsData[23] = 1f;    //inf. esq.

			mVertexArray = new VertexArray(mVertsData);
		}

	}

}
