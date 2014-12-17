package br.jp.engine.core;


public abstract class Component {
	
	public static String mName;
	
	public Component(String name){
		setName(name);
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}

}
