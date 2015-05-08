package br.jp.redsparrow.engine;

import android.content.Context;
import br.jp.redsparrow.engine.game.Game;
import br.jp.redsparrow.engine.game.World;
import br.jp.redsparrow.engine.physics.AABB;


public class HUDitem extends GameObject {
	
	private Vector2f mRelPos;
	
	public HUDitem(Context context, World world, float x, float y, float width, float height) {
		super(new AABB(new Vector2f(world.getPlayer().getX()+x, world.getPlayer().getY()+y), width, height));

		mRelPos = new Vector2f(x, y);
				
	}
	
	@Override
	public void update(Game game) {
		super.update(game);

		setPosition(game.getWorld().getPlayer().getPosition().add(mRelPos));
		
	}

}


