package br.jp.redsparrow_zsdemo.engine.game;

import br.jp.redsparrow_zsdemo.engine.GameObject;


public abstract class ObjectType {

	private String mName;
	private ObjectType mSuperType;
	
	public ObjectType(String name, ObjectType superType) {
		setName(name);
		setSuperType(superType);
	}
	
	public abstract GameObject create(Game game, float positionX, float positionY);

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public ObjectType getSuperType() {
		return mSuperType;
	}

	public void setSuperType(ObjectType mSuperType) {
		this.mSuperType = mSuperType;
	}

}
