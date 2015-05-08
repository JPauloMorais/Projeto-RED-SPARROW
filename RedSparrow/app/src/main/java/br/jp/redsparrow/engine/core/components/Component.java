package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.GameObject;


public class Component {
	
	protected GameObject mParent;
	
	public Component(GameObject parent){
		mParent = parent;
	}
	
	public GameObject getParent(){
		return mParent;
	}

}
