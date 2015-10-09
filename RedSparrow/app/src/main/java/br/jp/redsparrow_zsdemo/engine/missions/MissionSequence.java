package br.jp.redsparrow_zsdemo.engine.missions;

import br.jp.redsparrow_zsdemo.engine.game.Game;

public class MissionSequence {

	private Mission[] mMissions;
	private int mCurMission;
	private boolean mComplete;
	
	public MissionSequence(Mission ... missions) {
		
		mMissions = missions;
		mCurMission = 0;
		mComplete = false;
		
	}
	
	public void update(Game game) {
		if(mMissions[mCurMission] != null) {
			
			mMissions[mCurMission].update(game);
			
			if(mMissions[mCurMission].isComplete()) {
				mCurMission++;
				if(mCurMission > mMissions.length - 1) mComplete = true;
			}
		}
	}
	
	public Mission getCurMission() {
		return mMissions[mCurMission];
	}
	
	public int getCompletion() {
		//TODO
		return 0;
	}
	
	public boolean isComplete() {
		return mComplete;
	}
	
	public void reset() {
		mComplete = false;
		mCurMission = 0;
		for (Mission mission : mMissions) {
			mission.reset();
		}
	}

}
