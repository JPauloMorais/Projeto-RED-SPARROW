package br.jp.redsparrow.engine.core.messages;


public class Message {
	
	private final int mOBJ_ID;
	private final String mOP_TAG;
	private final Object mMESSAGE;
	private boolean mRecieved;
	
	public Message(int ObjectID, String operationTag, Object message){
		mOBJ_ID = ObjectID;
		mOP_TAG = operationTag;
		mMESSAGE = message;
		mRecieved = false;
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
	public boolean hasBeenRecieved() {
		return mRecieved;
	}
	public void recieve() {
		this.mRecieved = true;
	}
}
