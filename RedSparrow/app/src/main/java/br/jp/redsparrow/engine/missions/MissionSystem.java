package br.jp.redsparrow.engine.missions;

import java.util.ArrayList;

import br.jp.redsparrow.engine.game.Game;
import br.jp.redsparrow.engine.game.GameSystem;
import br.jp.redsparrow.engine.physics.Collision;

public class MissionSystem extends GameSystem implements Runnable {

	public static final String TAG = "MissionSystem";

	private boolean isRunning;
	private static Thread mThread;

	private static MissionSequence mainSequence;
	private static ArrayList<MissionSequence> sideSequences;

	public MissionSystem(Game game, MissionSequence mainMissionSequence) {
		super(game);
		
		mThread = new Thread( this, TAG );
		mThread.setPriority(Thread.MIN_PRIORITY);

		mainSequence = mainMissionSequence;
		sideSequences = new ArrayList<MissionSequence>();

	}

	public void start() {
		isRunning = true;
		mThread.start();
	}

	@Override
	public void loop(Game game, float[] projectionMatrix) {

		while(isRunning) {

			//------MAIN--------------
			if (!mainSequence.isComplete()) {
				if (!mainSequence.getCurMission().wasTriggered()) {

					if (Collision.areIntersecting(game.getWorld().getPlayer()
							.getBounds(), mainSequence.getCurMission().mBounds)) {

						mainSequence.getCurMission().trigger();
						mainSequence.getCurMission().onTrigger(game);
						
					}
				} else {

					mainSequence.update(game);

				}
			}
			//-------SIDE-------------
			for (MissionSequence sideSequence : sideSequences) {
				if(!sideSequence.getCurMission().wasTriggered()){
					if(Collision.areIntersecting(game.getWorld().getPlayer().getBounds(), sideSequence.getCurMission().mBounds)){

						sideSequence.getCurMission().trigger();

					}
				}else {

					sideSequence.update(game);

				}
			}

			removeCompletedSequences();

		}		
	}

	@Override
	public void run() {

		loop(game, game.getRenderer().projectionMatrix);

	}

	public void stop() {
		isRunning = false;
	}
	
	public Thread getThread() {
		return mThread;
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
