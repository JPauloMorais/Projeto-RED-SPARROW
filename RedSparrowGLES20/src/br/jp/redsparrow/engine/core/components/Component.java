package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;


public class Component {
	
	private final String mNAME;
	protected GameObject mParent;
	private int mId;
	
	public Component(String name, GameObject parent){
		mNAME = name;
		mParent = parent;
	}
	
	public GameObject getParent(){
		return mParent;
	}

	public String getName() {
		return mNAME;
	}
	
	public int getId(){
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}
	
}
