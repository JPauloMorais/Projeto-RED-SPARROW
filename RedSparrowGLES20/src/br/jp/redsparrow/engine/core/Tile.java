package br.jp.redsparrow.engine.core;

import android.content.Context;
import br.jp.redsparrow.engine.core.components.SpriteComponent;

public class Tile implements Renderable {

	private SpriteComponent sprite;
	private VertexArray mVertsArray;
	private Vector2f mPosition;
	
	public Tile(Context context, int textureSrc, float x, float y, float layer, float size){
		
		mPosition = new Vector2f(x, y);
		sprite = new SpriteComponent(context, textureSrc);
		
		float vertsData[] = new float[30];
		vertsData[0] = x;  vertsData[1] =  y ; vertsData[2] = layer;                                  vertsData[3] = 0.5f; vertsData[4] = 0.5f; //centro
		vertsData[5] = x+(size/2);   vertsData[6] = y-(size/2) ; vertsData[7] = layer;            vertsData[8] = 0f  ; vertsData[9] = 1f  ; //inf. esq.
		vertsData[10] = x-((size/2));  vertsData[11] = y-(size/2) ; vertsData[12] = layer;           vertsData[13] = 1f ; vertsData[14] = 1f ; //inf. dir.
		vertsData[15] = x-((size/2)); vertsData[16] = y+((size/2))   ; vertsData[17] = layer;       vertsData[18] = 1f ; vertsData[19] =  0f; //sup. dir.
		vertsData[20] = x+(size/2) ; vertsData[21] = y+((size/2))   ; vertsData[22] = layer;     vertsData[23] = 0f ; vertsData[24] = 0f ; //sup. esq.
		vertsData[25] = x+(size/2) ; vertsData[26] = y-(size/2); vertsData[27] = layer;           vertsData[28] = 0f ; vertsData[29] = 1f;    //inf. esq.

		mVertsArray = new VertexArray(vertsData);
		
	}
	
	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {
		sprite.render( mVertsArray, projectionMatrix );
	}

	public Vector2f getPosition() {
		return mPosition;
	}
	
}
