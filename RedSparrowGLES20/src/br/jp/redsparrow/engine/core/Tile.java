package br.jp.redsparrow.engine.core;

import android.content.Context;
import br.jp.redsparrow.engine.core.components.SpriteComponent;

public class Tile implements Renderable {

	@SuppressWarnings("unused")
	private final int mTxtr;
	private SpriteComponent sprite;
	
	public Tile(Context context, int textureSrc){
		mTxtr = textureSrc;
		sprite = new SpriteComponent(context, textureSrc);
	}
	
	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {
		sprite.render(vertexArray, projectionMatrix);
	}
	
}
