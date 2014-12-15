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
		DEFAULT
	}

	public static GameObject createObject(Context context, ObjectType type, float x, float y, 
			float r, float g, float b, float a){

		switch (type) {
		case DEFAULT:

			List<Component> components = new ArrayList<Component>();

			object = new GameObject(context,x, y, 2, 2, components);
			object.setLayer(2);

			object.addComponent(new SpriteComponent(object, r, g, b, a));
			object.addComponent(new PhysicsComponent(object));

			return object;
		default:
			break;
		}

		return null;

	}

}
