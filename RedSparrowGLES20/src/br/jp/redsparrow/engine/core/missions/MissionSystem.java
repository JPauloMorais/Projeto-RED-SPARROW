package br.jp.redsparrow.engine.core.missions;

import java.util.ArrayList;

<<<<<<< HEAD
import br.jp.redsparrow.engine.core.messages.Message;
=======
import br.jp.redsparrow.engine.core.Message;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

public class MissionSystem {

	private static volatile ArrayList<Mission> mMissions;
	private static volatile ArrayList<Message> mEventMessages;
	
	public static void init(){
		mMissions = new ArrayList<Mission>();
		mEventMessages = new ArrayList<Message>();
	}
	
	//adiciona a missao e retorna o id
	public static int registerMission(Mission mission){
		mMissions.add(mission);
		
		return mMissions.indexOf(mission);
	}
	
	public static void removeMission(int id){
		mMissions.remove(id);
	}
	
	public static void sendEventMessage(Message eventMessage){
		mEventMessages.add(eventMessage);
	}
	
	public static ArrayList<Message> getMessages(int missionId){
		ArrayList<Message> toReturn = new ArrayList<Message>();
		//adiciona as msg a lista de retorno e as remove da lista do sistema de missoes
		for (Message message : mEventMessages) {
			if( message.getObjectId() == missionId ) {
<<<<<<< HEAD
				toReturn.add(message);
				 mEventMessages.remove( mEventMessages.indexOf(message) );
=======
				mEventMessages.remove( mEventMessages.indexOf(message) );
				toReturn.add(message);
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
			}
		}
		
		return toReturn;
	}
	
}
