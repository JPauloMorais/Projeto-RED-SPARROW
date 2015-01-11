package br.jp.redsparrow.engine.core.missions;

import java.util.ArrayList;

import android.util.Log;
import br.jp.redsparrow.engine.core.GameObject;

public abstract class Mission extends GameObject implements Runnable {

	private String mName;
	
	private ArrayList<MissionStage> mStages;
	
	private boolean completed = false;

	public Mission(String name, float x, float y, float range) {
		super( x, y, range, range);
		
		mName = name;
		
		setStages(new ArrayList<MissionStage>());
		
		this.setId(MissionSystem.registerMission(this));

	}

	@Override
	public void run() {
		while (!completed) {
			try {
				
				update();
				
				if(MissionSystem.getMessages(this.getId()).get(0).getOperation()=="Completed") {
					Log.i("MissionSystem", mName + " Completed!");
					completed = true;
				}

			} catch (Exception e) {

			}
		}
	}
	
	@Override
	public void update() {
		super.update();
		for (MissionStage missionStage : mStages) {
			missionStage.update(this);
		}
	}

	public ArrayList<MissionStage> getStages() {
		return mStages;
	}
	
	public void addStage(MissionStage stage){
		mStages.add(stage);
	}

	public void setStages(ArrayList<MissionStage> mStages) {
		this.mStages = mStages;
	}
	
	public int getCompletionPercentage(){
		int stagesCompleted = 0;
		
		for (MissionStage missionStage : mStages) {
			if(missionStage.isCompleted()) stagesCompleted++;
		}
		
		return ((stagesCompleted * 100)/mStages.size());
	}

}
