package br.jp.engine.physics;

import br.jp.engine.core.GameObject;

public class Physics {

	public static boolean isColliding(GameObject object, GameObject object2) {
		//TODO: implement
		return !(object.getX()+object.getWidth()<object2.getX() ||
				object2.getX()+object2.getWidth()<object.getX() ||
				object.getY()+object.getHeight()<object2.getY() ||
				object2.getY()+object2.getHeight()<object.getY());
	}

}
