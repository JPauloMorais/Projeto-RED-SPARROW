package br.jp.redsparrow.engine.core;

import android.content.Context;
import br.jp.redsparrow.engine.core.components.SpriteComponent;
import br.jp.redsparrow.engine.core.physics.AABB;
import br.jp.redsparrow.game.components.PlayerPhysicsComponent;


public class HUDitem extends GameObject {

	private boolean toMove;
	
	public HUDitem(Context context, float x, float y, float width, float height, int imgId) {
		super(new AABB(new Vector2f(World.getPlayer().getX()+x, World.getPlayer().getY()+y), width, height));

		this.addComponent(new SpriteComponent(context, imgId, this, 0, 0));
		
	}
	
	@Override
	public void update() {
		super.update();
		//TODO: posicao baseada na posicao do player
		if(toMove){
			setPosition(getPosition().add(((PlayerPhysicsComponent) World.getPlayer().getUpdatableComponent(0)).getVelocity()));
			toMove = false;
		}
	}

	public void move() {
		this.toMove = true;
	}

}


