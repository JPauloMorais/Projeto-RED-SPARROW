package br.jp.redsparrow.engine.components;

import br.jp.redsparrow.engine.GameObject;


public class Component {
	
	protected GameObject mParent;
	
	public Component(GameObject parent){
		mParent = parent;
	}
	
	public GameObject getParent(){
		return mParent;
	}

}
