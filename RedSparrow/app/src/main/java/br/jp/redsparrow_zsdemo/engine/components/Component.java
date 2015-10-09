package br.jp.redsparrow_zsdemo.engine.components;

import br.jp.redsparrow_zsdemo.engine.GameObject;


public class Component {
	
	protected GameObject mParent;
	
	public Component(GameObject parent){
		mParent = parent;
	}
	
	public GameObject getParent(){
		return mParent;
	}

}
