package br.jp.engine.core;

public class Message {
	private final int mOBJ_ID;
	private final Object mMESSAGE;
	public Message(int ObjectID, Object message){
		mOBJ_ID = ObjectID;
		mMESSAGE = message;
	}
	public int getObjectId() {
		return mOBJ_ID;
	}
	public Object getMessage() {
		return mMESSAGE;
	}
}
