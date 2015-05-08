package br.jp.redsparrow.engine.core.components;

import android.content.Context;
import android.opengl.GLES20;
import br.jp.redsparrow.engine.core.Consts;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Renderable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.VertexArray;
import br.jp.redsparrow.engine.core.game.GameRenderer;
import br.jp.redsparrow.engine.core.util.TextureUtil;

public class SpriteComponent extends Component implements Renderable {
	
	/*
	 * Sprite component simples que se posiciona no centro do objeto ao qual pertence.
	 * */
	
	private VertexArray mVertsArray;

	private float[] mSizeOffset;

	private int mTexture;

	private float[] mVertsData;

	public SpriteComponent(Context context, int imgId, GameObject parent, 
			float sizeOffsetX, float sizeOffsetY, 
			int spritesInX, int spritesInY, int row, int col) {
	
		super(parent);

		mTexture = TextureUtil.loadTexture(context, imgId);

		mVertsData = new float[30];

		mSizeOffset = new float[2];
		mSizeOffset[0] = sizeOffsetX;
		mSizeOffset[1] = sizeOffsetY;

		setUVs(spritesInX, spritesInY, row, col);
		updateVertsData();
		
	}
	
	public SpriteComponent(Context context, int imgId, GameObject parent, float sizeOffsetX, float sizeOffsetY) {
		super(parent);

		mTexture = TextureUtil.loadTexture(context, imgId);

		mVertsData = new float[30];

		mSizeOffset = new float[2];
		mSizeOffset[0] = sizeOffsetX;
		mSizeOffset[1] = sizeOffsetY;

		setUVs();
		updateVertsData();

	}

	private void updateVertsData(){

		//X Y Z                                                                                           
		mVertsData[0] = mParent.getX() + mParent.getWidth() / 2 + mSizeOffset[0];  //right 
		mVertsData[1] = mParent.getY() + mParent.getHeight() / 2 + mSizeOffset[1]; //top
		mVertsData[2] = 1f;

		//X Y Z
		mVertsData[5] = mParent.getX() - mParent.getWidth() / 2 - mSizeOffset[0];  //left
		mVertsData[6] = mVertsData[1];                                         //top		
		mVertsData[7] = mVertsData[2];

		//X Y Z
		mVertsData[10] = mVertsData[5];                                         //left
		mVertsData[11] = mParent.getY() - mParent.getHeight() / 2 - mSizeOffset[1]; //bottom
		mVertsData[12] = mVertsData[2];

		//X Y Z
		mVertsData[15] = mVertsData[0];                                          //right
		mVertsData[16] = mVertsData[11];                                         //bottom
		mVertsData[17] = mVertsData[2];

		//X Y Z
		mVertsData[20] = mVertsData[0];                                         //right
		mVertsData[21] = mVertsData[1];                                         //top
		mVertsData[22] = mVertsData[2];

		//X Y Z
		mVertsData[25] = mVertsData[5];                                         //left
		mVertsData[26] = mVertsData[11];                                        //bottom
		mVertsData[27] = mVertsData[2];

		if(mParent.getRotation() != 0) {			

			float cos = (float) Math.cos(mParent.getRotation());
			float sen = (float) Math.sin(mParent.getRotation());

			Vector2f a = new Vector2f(mVertsData[0], mVertsData[1]);
			Vector2f b = new Vector2f(mVertsData[5], mVertsData[6]);
			Vector2f c = new Vector2f(mVertsData[10], mVertsData[11]);
			Vector2f d = new Vector2f(mVertsData[15], mVertsData[16]);

			mVertsData[0] = cos * (a.getX() - mParent.getX()) - sen * (a.getY() - mParent.getY()) + mParent.getX();
			mVertsData[1] = sen * (a.getX() - mParent.getX()) + cos * (a.getY() - mParent.getY()) + mParent.getY();

			mVertsData[5] = cos * (b.getX() - mParent.getX()) - sen * (b.getY() - mParent.getY()) + mParent.getX();
			mVertsData[6] = sen * (b.getX() - mParent.getX()) + cos * (b.getY() - mParent.getY()) + mParent.getY();

			mVertsData[10] = cos * (c.getX() - mParent.getX()) - sen * (c.getY() - mParent.getY()) + mParent.getX();
			mVertsData[11] = sen * (c.getX() - mParent.getX()) + cos * (c.getY() - mParent.getY()) + mParent.getY();

			mVertsData[15] = cos * (d.getX() - mParent.getX()) - sen * (d.getY() - mParent.getY()) + mParent.getX();
			mVertsData[16] = sen * (d.getX() - mParent.getX()) + cos * (d.getY() - mParent.getY()) + mParent.getY();

			mVertsData[20] = cos * (a.getX() - mParent.getX()) - sen * (a.getY() - mParent.getY()) + mParent.getX();
			mVertsData[21] = sen * (a.getX() - mParent.getX()) + cos * (a.getY() - mParent.getY()) + mParent.getY();

			mVertsData[25] = cos * (b.getX() - mParent.getX()) - sen * (c.getY() - mParent.getY()) + mParent.getX();
			mVertsData[26] = sen * (b.getX() - mParent.getX()) + cos * (c.getY() - mParent.getY()) + mParent.getY();

		}

		mVertsArray = new VertexArray(mVertsData);

	}

	private void setUVs() {
		//U V
		mVertsData[3] = 0;//right
		mVertsData[4] = 0;//top
		//U V
		mVertsData[8] = 1;//left
		mVertsData[9] = 0;//top
		//U V
		mVertsData[13] = 1;//left
		mVertsData[14] = 1;//bottom
		//U V
		mVertsData[18] = 0;//right
		mVertsData[19] = 1;//bottom
		//U V
		mVertsData[23] = 0;//right
		mVertsData[24] = 0;//top
		//U V
		mVertsData[28] = 1;//left
		mVertsData[29] = 1;//bottom
	}

	public void setUVs(int spritesInX, int spritesInY, int row, int col){

		float w = (float) 1/spritesInX;
		float h = (float) 1/spritesInY;

		//U V
		mVertsData[3] = col * w + w - 0.01f;//right
		mVertsData[4] = row * h - 0.01f;//top
		//U V
		mVertsData[8] = col * w + 0.01f;//left
		mVertsData[9] = mVertsData[4];//top
		//U V
		mVertsData[13] = mVertsData[8];//left
		mVertsData[14] = mVertsData[4] + h + 0.01f;//bottom
		//U V
		mVertsData[18] = mVertsData[3];//right
		mVertsData[19] = mVertsData[14];//bottom
		//U V
		mVertsData[23] = mVertsData[3];//right
		mVertsData[24] = mVertsData[4];//top
		//U V
		mVertsData[28] = mVertsData[8];//left
		mVertsData[29] = mVertsData[14];//bottom
	}

	private void bindData() {

		mVertsArray.setVertexAttribPointer(
				0,
				GameRenderer.textureProgram.getPositionAttributeLocation(),
				Consts.POSITION_COUNT,
				Consts.STRIDE);
		mVertsArray.setVertexAttribPointer(
				Consts.POSITION_COUNT,
				GameRenderer.textureProgram.getTextureCoordinatesAttributeLocation(),
				Consts.TEXTURE_COORDS_COUNT,
				Consts.STRIDE);

	}

	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {

		updateVertsData();
		GameRenderer.textureProgram.useProgram();
		GameRenderer.textureProgram.setUniforms(projectionMatrix, mTexture);
		bindData();
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

	}

}
