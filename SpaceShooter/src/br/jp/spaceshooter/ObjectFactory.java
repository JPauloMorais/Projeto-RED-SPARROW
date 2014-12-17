package br.jp.spaceshooter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import br.jp.engine.components.PhysicsComponent;
import br.jp.engine.components.SpriteComponent;
import br.jp.engine.core.Component;
import br.jp.engine.core.GameObject;

public class ObjectFactory {

	private static GameObject object;

	public static enum ObjectType{
		DEFAULT, PLAYER
	}

	public static GameObject createObject(Context context, ObjectType type, float x, float y, 
			float r, float g, float b, float a){

		List<Component> components = new ArrayList<Component>();

		switch (type) {
		case DEFAULT:

			object = new GameObject(context,x, y, 1, 1, components);
			object.setLayer(2);

			object.addComponent(new SpriteComponent(object, r, g, b, a));
			object.addComponent(new PhysicsComponent());

			return object;
		case PLAYER:

			object = new GameObject(context,x, y, 1, 1, components);
			object.setLayer(0);

			object.addComponent(new SpriteComponent(object, .5f, .5f, 1f, 1));
			object.addComponent(new PhysicsComponent());
			//			object.addComponent(new PlayerInputComponent());

			return object;
		default:
			break;
		}

		return null;

	}

}
