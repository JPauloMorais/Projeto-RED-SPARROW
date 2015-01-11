package br.jp.redsparrow.engine.core.missions;

import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;

public class MissionStage implements Updatable{
	
	private String mExplanation;
	private boolean completed = false;
	
	public MissionStage(String explanation) {
		setExplanation(explanation);
	}

	@Override
	public void update(GameObject object) {
		
	}

	public String getExplanation() {
		return mExplanation;
	}

	public void setExplanation(String mExplanation) {
		this.mExplanation = mExplanation;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
}
