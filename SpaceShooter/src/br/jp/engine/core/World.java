package br.jp.engine.core;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import br.jp.engine.components.SpriteComponent;
import br.jp.engine.physics.Physics;

public class World {

	private List<GameObject> mObjects;
	private Grid grid;
	private List<GameObject> toCheck;

	public World(){

		mObjects = new ArrayList<GameObject>();
		grid = new Grid(64000, 64000, 64);
		toCheck = new ArrayList<GameObject>();

	}

	public synchronized void update(Canvas canvas){
		try {			
			//checando colisões
			if(!mObjects.isEmpty()){ 
				for (GameObject object : mObjects) {
					toCheck = grid.getLikelyToInteract(object);
					for (GameObject object2 : toCheck) {
						if(object.getId()!=object2.getId()){						
							if(Physics.isColliding(object, object2)) ;
							//								Log.i("COLLISION", "Collision!");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			update(canvas);
		}
	}

	public synchronized void render(Canvas canvas){
		for (GameObject object : mObjects) {
			Component sComp = object.getComponent(SpriteComponent.mName);
			if(sComp!=null) sComp.render(canvas);
		}
	}

	public void addObject(GameObject object){
		if(!mObjects.isEmpty())
			object.setId(mObjects.size()-1);
		else object.setId(0);
		mObjects.add(object);
		grid.addObject(object);
	}

}
