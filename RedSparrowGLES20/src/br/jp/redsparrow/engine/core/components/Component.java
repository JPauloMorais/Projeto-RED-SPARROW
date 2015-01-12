package br.jp.redsparrow.engine.core.components;

public class Component {
	
	private final String mNAME;
	private int mId;
	
	public Component(String name){
		mNAME = name;
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
