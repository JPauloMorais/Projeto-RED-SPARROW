package br.jp.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;
import br.jp.engine.core.GameController;
import br.jp.engine.core.World;
import br.jp.spaceshooter.ObjectFactory.ObjectType;

public class SpaceShooterController extends GameController {
	
	private static World world;
	
	public SpaceShooterController(Context context) {
		super(context);
			
		world = new World();	 
		world.addObject(ObjectFactory.createObject(ObjectType.DEFAULT,10,10));
		world.addObject(ObjectFactory.createObject(ObjectType.DEFAULT,1000,1000));
		world.addObject(ObjectFactory.createObject(ObjectType.DEFAULT,20,20));
		
	}

	public static World getWorld() {
		return world;
	}

	public static void setWorld(World world) {
		SpaceShooterController.world = world;
	}

	@Override
	public void update(Canvas canvas) {
		world.update(canvas);
	}

	@Override
	public void render(Canvas canvas) {
		world.render(canvas);
	}

}
