package br.jp.spaceshooter;

import br.jp.engine.components.SpriteComponent;
import br.jp.engine.components.TestComponent;
import br.jp.engine.components.TestComponent2;
import br.jp.engine.core.Component;
import br.jp.engine.core.GameObject;

public class ObjectFactory {

	public static enum ObjectType{
		DEFAULT
	}

	public static GameObject createObject(ObjectType type, float x, float y){

		switch (type) {
		case DEFAULT:

			Component[] components = new Component[2];
			components[0] = new TestComponent();
			components[1] = new SpriteComponent();

			GameObject object = new GameObject(x, y, 130, 130, components);

			return object;

		default:
			break;
		}

		return null;

	}

}
