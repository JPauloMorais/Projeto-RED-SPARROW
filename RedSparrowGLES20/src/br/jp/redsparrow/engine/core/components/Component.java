package br.jp.redsparrow.engine.core.components;

public class Component {
	private final String mNAME;
	
	public Component(String name){
		mNAME = name;
	}

	public String getName() {
		return mNAME;
	}
	
}
