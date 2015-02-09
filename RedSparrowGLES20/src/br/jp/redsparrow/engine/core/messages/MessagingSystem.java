package br.jp.redsparrow.engine.core.messages;

import java.util.ArrayList;

import android.util.Log;
import br.jp.redsparrow.engine.core.Game;
import br.jp.redsparrow.engine.core.GameSystem;
import br.jp.redsparrow.engine.core.util.LogConfig;

public class MessagingSystem extends GameSystem{

	private static final String TAG = "MessagingSystem";

	private static ArrayList<Message> mMessages;

	public MessagingSystem(Game game){
		super(game);
		mMessages = new ArrayList<Message>();
	}
	
	@Override
	public void loop(Game game, float[] projectionMatrix) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Message> getMessages(int id){
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

	public void sendMessages(Message ... message) throws NullPointerException{
		for (int i = 0; i < message.length; i++) {			
			mMessages.add(message[i]);
		}
	}

	public void sendMessagesToObject(final int objectId, final Message ... messages) throws NullPointerException {
		for (int i = 0; i < messages.length; i++) {
			game.getWorld().getObjectById(objectId).recieveMessage(messages[i]);
			if(LogConfig.ON) Log.i(TAG, " MSG entegue a obj de id: " + objectId);
		}
	}

}
