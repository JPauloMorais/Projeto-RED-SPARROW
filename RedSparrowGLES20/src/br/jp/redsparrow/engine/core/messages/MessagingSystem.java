package br.jp.redsparrow.engine.core.messages;

import java.util.ArrayList;

import android.util.Log;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.util.LogConfig;

public class MessagingSystem {

	private static final String TAG = "MessagingSystem";

	private static Thread mMessagingSystemThread;
	private static ArrayList<Message> mMessages;

	public static void init(){
		mMessages = new ArrayList<Message>();
		mMessagingSystemThread = new Thread();
	}

	public static ArrayList<Message> getMessages(int id){
		try{

			ArrayList<Message> toReturn = new ArrayList<Message>();

			for (Message message : mMessages) {
				if(message.getObjectId()==id) {
					toReturn.add(message);
					mMessages.remove(mMessages.indexOf(message));
				}
			}

			return toReturn;

		}catch (Exception e){

			return null;

		}
	}

	public static void sendMessages(Message ... message) throws NullPointerException{
		for (int i = 0; i < message.length; i++) {			
			mMessages.add(message[i]);
		}
	}

	public static void sendMessagesToObject(final int objectId, final Message ... messages) throws NullPointerException {
		for (int i = 0; i < messages.length; i++) {
			World.getObjectById(objectId).recieveMessage(messages[i]);
			if(LogConfig.ON) Log.i(TAG, " MSG entegue a obj de id: " + objectId);
		}
	}

}
