package br.jp.engine.core;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.jp.engine.components.SpriteComponent;
import br.jp.engine.components.PhysicsComponent;
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

	public synchronized void loop(GL10 gl){
		if(!mObjects.isEmpty()){ 
			for (GameObject object : mObjects) {
				object.update(gl);
				toCheck = grid.getLikelyToInteract(object);
				for (GameObject object2 : toCheck) {
					if(object.getId()!=object2.getId()){						
						if(Physics.isColliding(object, object2)) 
						{
							if(object.getComponentByName("PhysicsComponent")!=null){
								grid.clear();
								grid.addObject(object);

							}
						}
					}
				}
				object.render(gl);
				gl.glLoadIdentity();
			}
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
