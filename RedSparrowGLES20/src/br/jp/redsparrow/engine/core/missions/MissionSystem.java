package br.jp.redsparrow.engine.core.missions;

import java.util.ArrayList;

import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.physics.Collision;

public class MissionSystem implements Runnable {

	public static final String TAG = "MissionSystem";

	private boolean isRunning;
	private static Thread mThread;

	private static MissionSequence mainSequence;
	private static ArrayList<MissionSequence> sideSequences;

	public MissionSystem(MissionSequence mainMissionSequence) {

		mThread = new Thread( this, "MissionSystem" );
		
		mainSequence = mainMissionSequence;
		sideSequences = new ArrayList<MissionSequence>();

	}
	
	public void start() {
		isRunning = true;
		mThread.start();
	}

	@Override
	public void run() {
		
		while(isRunning) {

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
	
	public void stop() {
		isRunning = false;
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
