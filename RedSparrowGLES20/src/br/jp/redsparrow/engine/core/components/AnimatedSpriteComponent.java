package br.jp.redsparrow.engine.core.components;

import java.util.ArrayList;

import android.content.Context;
import android.opengl.GLES20;
import br.jp.redsparrow.engine.core.Animation;
import br.jp.redsparrow.engine.core.Consts;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Renderable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.VertexArray;
import br.jp.redsparrow.engine.core.util.TextureUtil;
import br.jp.redsparrow.engine.shaders.TextureShaderProg;

public class AnimatedSpriteComponent extends Component implements Renderable {

	private VertexArray mVertsArray;

	private float[] mOffset;

	private TextureShaderProg mTextureProgram;
	private ArrayList<Integer> mTextures;
//	private int mTexture;

	private float[] mVertsData;

	private int curAnim;
	private ArrayList<Animation> mAnimations;

	private boolean isPaused;

	public AnimatedSpriteComponent(Context context, int imgId, GameObject parent, Animation anim, float offsetX, float offsetY) {
		super("Sprite", parent);

		mTextureProgram = new TextureShaderProg(context);
		mTextures = new ArrayList<Integer>();
		mTextures.add(TextureUtil.loadTexture(context, imgId));

		mVertsData = new float[30];

		curAnim = 0;
		mAnimations = new ArrayList<Animation>();
		mAnimations.add(anim);

		mOffset = new float[2];
		mOffset[0] = offsetX;
		mOffset[1] = offsetY;

		mVertsArray = new VertexArray(mVertsData);
		updateVertsData();

	}

	private void bindData() {

		mVertsArray.setVertexAttribPointer(
				0,
				mTextureProgram.getPositionAttributeLocation(),
				Consts.POSITION_COUNT,
				Consts.STRIDE);
		mVertsArray.setVertexAttribPointer(
				Consts.POSITION_COUNT,
				mTextureProgram.getTextureCoordinatesAttributeLocation(),
				Consts.TEXTURE_COORDS_COUNT,
				Consts.STRIDE);

	}

	private void updateVertsData(){

		//TODO: setting do z
		//TODO: Suporte para eixos de rotacao arbitrarios

		if(!isPaused) mAnimations.get(curAnim).update();

		//X Y Z                                                                                           
		mVertsData[0] = mParent.getX() + mParent.getWidth() / 2 + mOffset[0];  //right 
		mVertsData[1] = mParent.getY() + mParent.getHeight() / 2 + mOffset[1]; //top
		mVertsData[2] = 1f;
		//U V
		mVertsData[3] = mAnimations.get(curAnim).getUVs()[2];
		mVertsData[4] = mAnimations.get(curAnim).getUVs()[1];

		//X Y Z
		mVertsData[5] = mParent.getX() - mParent.getWidth() / 2 - mOffset[0];  //left
		mVertsData[6] = mVertsData[1];                                         //top		
		mVertsData[7] = 1f;
		//U V
		mVertsData[8] = mAnimations.get(curAnim).getUVs()[0];
		mVertsData[9] = mAnimations.get(curAnim).getUVs()[1];

		//X Y Z
		mVertsData[10] = mVertsData[5];                                         //left
		mVertsData[11] = mParent.getY() - mParent.getHeight() / 2 - mOffset[1]; //bottom
		mVertsData[12] = 1f;
		//U V
		mVertsData[13] = mAnimations.get(curAnim).getUVs()[0];
		mVertsData[14] = mAnimations.get(curAnim).getUVs()[3];

		//X Y Z
		mVertsData[15] = mVertsData[0];                                          //right
		mVertsData[16] = mVertsData[11];                                         //bottom
		mVertsData[17] = 1f;
		//U V
		mVertsData[18] = mAnimations.get(curAnim).getUVs()[2];
		mVertsData[19] = mAnimations.get(curAnim).getUVs()[3];

		//X Y Z
		mVertsData[20] = mVertsData[0];                                         //right
		mVertsData[21] = mVertsData[1];                                         //top
		mVertsData[22] = 1f;
		//U V
		mVertsData[23] = mAnimations.get(curAnim).getUVs()[2];
		mVertsData[24] = mAnimations.get(curAnim).getUVs()[1];

		//X Y Z
		mVertsData[25] = mVertsData[5];                                         //left
		mVertsData[26] = mVertsData[11];                                        //bottom
		mVertsData[27] = 1f;
		//U V
		mVertsData[28] = mAnimations.get(curAnim).getUVs()[0];
		mVertsData[29] = mAnimations.get(curAnim).getUVs()[3];

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

		mVertsArray.updateBuffer(mVertsData, 0, mVertsData.length);
//		mVertsArray = new VertexArray(mVertsData);

	}

	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {

		updateVertsData();
		mTextureProgram.useProgram();
		mTextureProgram.setUniforms(projectionMatrix, mTextures.get(curAnim));
		bindData();
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

	}

	public void setCurAnim(int curAnim) {
		this.curAnim = curAnim;
	}

	public Animation getAnimation(int indx) {
		return mAnimations.get(indx);
	}

	public void addAnimation(Context context, int srcSheet, Animation anim){
		mTextures.add(TextureUtil.loadTexture(context, srcSheet));
		mAnimations.add(anim);
	}

	public void pause() {
		this.isPaused = true;
	}

	public void resume() {
		this.isPaused = false;
	}

}
