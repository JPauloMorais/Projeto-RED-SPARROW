package br.jp.redsparrow.engine.core;

import android.content.Context;
import br.jp.redsparrow.engine.core.physics.AABB;


public class HUDitem extends GameObject {
	
	private Vector2f mRelPos;
	
	public HUDitem(Context context, float x, float y, float width, float height) {
		super(new AABB(new Vector2f(World.getPlayer().getX()+x, World.getPlayer().getY()+y), width, height));

		mRelPos = new Vector2f(x, y);
				
	}
	
	@Override
	public void update() {
		super.update();

		setPosition(World.getPlayer().getPosition().add(mRelPos));
		
	}

}


