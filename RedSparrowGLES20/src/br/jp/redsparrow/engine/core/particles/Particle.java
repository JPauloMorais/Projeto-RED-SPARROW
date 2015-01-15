package br.jp.redsparrow.engine.core.particles;

import android.content.Context;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Renderable;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.VertexArray;
import br.jp.redsparrow.engine.core.components.SpriteComponent;

public class Particle implements Updatable, Renderable {

	private Vector2f mPosition;
	private Vector2f mVelocity;
	private Vector2f mAcceleration;
	private float mSize;
	
	private float[] mVertsData;
	private VertexArray mVertsArray;	
	private SpriteComponent sComp;
	
	public Particle(Context context, Vector2f position, float size) {
		
		mPosition = position;
		mVelocity = new Vector2f(0, 0);
		mAcceleration = new Vector2f(0, 0);
		mSize = size;
		
		sComp = new SpriteComponent(context, R.drawable.particula_1);

		mVertsData[0] = mPosition.getX();  mVertsData[1] =  mPosition.getY()             ;     mVertsData[2] = 0.5f; mVertsData[3] = 0.5f; //centro
		mVertsData[4] = mPosition.getX()+(size/2);   mVertsData[5] = mPosition.getY()-(size/2);     mVertsData[6] = 0f  ; mVertsData[7] = 1f  ; //inf. esq.
		mVertsData[8] = mPosition.getX()-((size/2));  mVertsData[9] = mVertsData[5];     mVertsData[10] = 1f ; mVertsData[11] = 1f ; //inf. dir.
		mVertsData[12] = mVertsData[8]; mVertsData[13] = mPosition.getY()+((size/2));     mVertsData[14] = 1f ; mVertsData[15] =  0f; //sup. dir.
		mVertsData[16] = mVertsData[4] ; mVertsData[17] = mVertsData[13];     mVertsData[18] = 0f ; mVertsData[19] = 0f ; //sup. esq.
		mVertsData[20] = mVertsData[4] ; mVertsData[21] = mVertsData[5];     mVertsData[22] = 0f ; mVertsData[23] = 1f;    //inf. esq.
		
		mVertsArray = new VertexArray(mVertsData);
	}
	
	@Override
	public void update(GameObject object) {

		mVelocity = mVelocity.add(mAcceleration);
		mPosition = mPosition.add(mVelocity);
		
		mVertsData[0] = mPosition.getX();              mVertsData[1] =  mPosition.getY()             ;     mVertsData[2] = 0.5f; mVertsData[3] = 0.5f; //centro
		mVertsData[4] = mPosition.getX()+(mSize/2);   mVertsData[5] = mPosition.getY()-(mSize/2);     mVertsData[6] = 0f  ; mVertsData[7] = 1f  ; //inf. esq.
		mVertsData[8] = mPosition.getX()-((mSize/2));  mVertsData[9] = mVertsData[5];     mVertsData[10] = 1f ; mVertsData[11] = 1f ; //inf. dir.
		mVertsData[12] = mVertsData[8]; mVertsData[13] = mPosition.getY()+((mSize/2));     mVertsData[14] = 1f ; mVertsData[15] =  0f; //sup. dir.
		mVertsData[16] = mVertsData[4] ; mVertsData[17] = mVertsData[13];     mVertsData[18] = 0f ; mVertsData[19] = 0f ; //sup. esq.
		mVertsData[20] = mVertsData[4] ; mVertsData[21] = mVertsData[5];     mVertsData[22] = 0f ; mVertsData[23] = 1f;    //inf. esq.
		
	}
	
	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {
		sComp.render(mVertsArray, projectionMatrix);		
	}

}
