package br.jp.redsparrow.engine.core.tilemaps;

import android.content.Context;
import android.opengl.GLES20;

import br.jp.redsparrow.engine.core.VertexArray;
import br.jp.redsparrow.engine.core.Consts;
import br.jp.redsparrow.engine.core.game.Game;

import br.jp.redsparrow.engine.core.game.GameRenderer;
import br.jp.redsparrow.engine.core.util.TextureUtil;


public class Tile {

	private float mX;
	private float mY;
	private int mTexture;
	private VertexArray mVertsArray;

	public Tile(Tilemap map, float x, float y, int texture) {
		setX(x);
		setY(y);
		setTexture(map.getGame().getContext(), texture);

		float[] vertsData =  new float[30];
		//X Y Z                                                                                           
		vertsData[0] = mX + map.getTileSize() / 2;  //right 
		vertsData[1] = mY + map.getTileSize() / 2; //top
		vertsData[2] = 0f;
		//U V
		vertsData[3] = 0;//right
		vertsData[4] = 0;//top

		//X Y Z
		vertsData[5] = mX - map.getTileSize() / 2;  //left
		vertsData[6] = vertsData[1];                                         //top		
		vertsData[7] = vertsData[2];
		//U V
		vertsData[8] = 1;//left
		vertsData[9] = 0;//top

		//X Y Z
		vertsData[10] = vertsData[5];                                         //left
		vertsData[11] = mY - map.getTileSize() / 2; //bottom
		vertsData[12] = vertsData[2];
		//U V
		vertsData[13] = 1;//left
		vertsData[14] = 1;//bottom

		//X Y Z
		vertsData[15] = vertsData[0];                                          //right
		vertsData[16] = vertsData[11];                                         //bottom
		vertsData[17] = vertsData[2];
		//U V
		vertsData[18] = 0;//right
		vertsData[19] = 1;//bottom

		//X Y Z
		vertsData[20] = vertsData[0];                                         //right
		vertsData[21] = vertsData[1];                                         //top
		vertsData[22] = vertsData[2];
		//U V
		vertsData[23] = vertsData[3];//right
		vertsData[24] = vertsData[4];//top

		//X Y Z
		vertsData[25] = vertsData[5];                                         //left
		vertsData[26] = vertsData[11];                                        //bottom
		vertsData[27] = vertsData[2];
		//U V
		vertsData[28] = 1;//left
		vertsData[29] = 1;//bottom

		mVertsArray = new VertexArray(vertsData);

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

	public void render(Game game, float[] projectionMatrix) {

		GameRenderer.textureProgram.useProgram();
		GameRenderer.textureProgram.setUniforms(projectionMatrix, mTexture);
		bindData();
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

	}

	public float getX() {
		return mX;
	}

	public void setX(float mX) {
		this.mX = mX;
	}

	public float getY() {
		return mY;
	}

	public void setY(float mY) {
		this.mY = mY;
	}

	public int getTexture() {
		return mTexture;
	}

	public void setTexture(Context context, int texture) {
		this.mTexture = TextureUtil.loadTexture(context, texture);
	}

}
