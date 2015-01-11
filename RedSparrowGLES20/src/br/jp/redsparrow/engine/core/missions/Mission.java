package br.jp.redsparrow.engine.core.missions;

<<<<<<< HEAD
import java.util.ArrayList;

=======
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
import android.util.Log;
import br.jp.redsparrow.engine.core.GameObject;

public abstract class Mission extends GameObject implements Runnable {

	private String mName;
<<<<<<< HEAD
	
	private ArrayList<MissionStage> mStages;
	
=======
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	private boolean completed = false;

	public Mission(String name, float x, float y, float range) {
		super( x, y, range, range);
<<<<<<< HEAD
		
		mName = name;
		
		setStages(new ArrayList<MissionStage>());
		
=======
		mName = name;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
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
<<<<<<< HEAD
	
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
=======
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

}
