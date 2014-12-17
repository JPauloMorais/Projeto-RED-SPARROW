package br.jp.engine.core;


public class Message {
	private final int mOBJ_ID;
	private final String mOP_TAG;
	private final Object mMESSAGE;
	
	public Message(int ObjectID, String operationTag, Object message){
		mOBJ_ID = ObjectID;
		mOP_TAG = operationTag;
		mMESSAGE = message;
	}
	public int getObjectId() {
		return mOBJ_ID;
	}
	public Object getMessage() {
		return mMESSAGE;
	}
	public String getOperation() {
		return mOP_TAG;
	}
}
