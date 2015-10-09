package br.jp.redsparrow_zsdemo.engine.game;

import java.util.ArrayList;
import java.util.HashMap;

import br.jp.redsparrow_zsdemo.engine.GameObject;

public abstract class ObjectFactory extends GameSystem {

	protected ArrayList<ObjectType> mSupertypes;
	protected HashMap<String, ObjectType> mTypes;
	
	public ObjectFactory(Game game) {
		super(game);
		mSupertypes = new ArrayList<ObjectType>();
		mTypes = new HashMap<String, ObjectType>();
	}
	
	public abstract GameObject create(String typeName, float x, float y);

}
