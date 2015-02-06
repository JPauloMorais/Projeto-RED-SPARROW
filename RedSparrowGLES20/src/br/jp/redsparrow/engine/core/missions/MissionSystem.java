package br.jp.redsparrow.engine.core.missions;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.physics.Collision;
import br.jp.redsparrow.game.GameRenderer;

public class MissionSystem extends IntentService {

	public static final String TAG = "MissionSystem";
	
	private static MissionSequence mainSequence;
	private static ArrayList<MissionSequence> sideSequences;
	
	public MissionSystem() {
		super(TAG);				
	}
	
	public static void setup(MissionSequence mainMissionSequence) {
		mainSequence = mainMissionSequence;
		sideSequences = new ArrayList<MissionSequence>();
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.d(TAG, "-------------UPD_______________");

		while(GameRenderer.isRunning()) {
			
			//------MAIN--------------
			if(!mainSequence.getCurMission().wasTriggered()) {
				if(Collision.areIntersecting(World.getPlayer().getBounds(), mainSequence.getCurMission().mBounds)) {
					
					mainSequence.getCurMission().trigger();
				
				}
			}else {
			
				mainSequence.update();
			
			}
			
			//-------SIDE-------------
			for (MissionSequence sideSequence : sideSequences) {
				if(!sideSequence.getCurMission().wasTriggered()){
					if(Collision.areIntersecting(World.getPlayer().getBounds(), sideSequence.getCurMission().mBounds)){
						
						sideSequence.getCurMission().trigger();
						
					}
				}else {
					
					sideSequence.update();
					
				}
			}
			
			removeCompletedSequences();
			
		}
	
	}
	
	private void removeCompletedSequences() {

		ArrayList<MissionSequence> aux = sideSequences;
		
		for (MissionSequence sideSequence : aux) {
			if(sideSequence.isComplete()) sideSequences.remove(sideSequence);
		}
		
	}

	public void addSideMissionSequence(MissionSequence sequence) {
		sideSequences.add(sequence);
	}

}
