package br.jp.redsparrow.engine.core.missions;

import java.util.ArrayList;

import br.jp.redsparrow.engine.core.messages.Message;

public class MissionSystem {

	private static ArrayList<Mission> mMissions;
	private static ArrayList<Message> mEventMessages;
	
	private static ThreadGroup mMissionTG;
	private static ArrayList<Thread> mMissionThreads;

	public static void init(){
		mMissions = new ArrayList<Mission>();
		mEventMessages = new ArrayList<Message>();
		
		mMissionThreads = new ArrayList<Thread>();
		mMissionTG = new ThreadGroup("MissionTG");
	
	}

	//adiciona a missao e retorna o id
	public static int registerMission(Mission mission){
		
		mMissions.add(mission);

		return mMissions.indexOf(mission);
	}
	
	public static void update(){
		
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
				toReturn.add(message);
				mEventMessages.remove( mEventMessages.indexOf(message) );
			}
		}

		return toReturn;
	}

}
