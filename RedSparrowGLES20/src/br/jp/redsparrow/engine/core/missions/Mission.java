package br.jp.redsparrow.engine.core.missions;

import android.util.Log;
import br.jp.redsparrow.engine.core.GameObject;

public abstract class Mission extends GameObject implements Runnable {

	private String mName;
	private boolean completed = false;

	public Mission(String name, float x, float y, float range) {
		super( x, y, range, range);
		mName = name;
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

}
