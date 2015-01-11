package br.jp.redsparrow.engine.core.missions;

import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.Collision;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.messages.Message;

import br.jp.redsparrow.game.GameRenderer;

public class TestMission extends Mission {

	private SoundComponent mSoundComp;
	
	public TestMission(float x, float y) {
		super("Test Mission",x, y, 5f);
		
		mSoundComp = new SoundComponent(GameRenderer.getContext());
		mSoundComp.addSound(R.raw.test_shot);
		this.addComponent(mSoundComp);
		
		this.addStage(new MissionStage("Do Nothing For 10 Seconds"));
		this.addStage(new MissionStage("Do Nothing For 10 More Seconds"));

	}
	
	@Override
	public void update() {
		super.update();
		
		if (this.getCompletionPercentage()==0) {
			
			if (Collision.areIntersecting(World.getPlayer().getBounds(),
					this.getBounds())) {

				mSoundComp.startSound(0, false);
				Log.i("MissionSystem",  "Triggered!");
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}


				MissionSystem.sendEventMessage(new Message(this.getId(),
						"Completed", ""));

			}

		}
		
	}

}
