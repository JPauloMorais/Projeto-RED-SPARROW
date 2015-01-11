package br.jp.redsparrow.engine.core.missions;

import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.Collision;
import br.jp.redsparrow.engine.core.Message;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.game.GameRenderer;

public class TestMission extends Mission {

	private SoundComponent mSoundComp;
	
	public TestMission(float x, float y) {
		super("Test Mission",x, y, 0.5f);
		
		mSoundComp = new SoundComponent(GameRenderer.getContext());
		mSoundComp.addSound(R.raw.test_shot);
		
		this.addComponent(mSoundComp);
	}
	
	@Override
	public void update() {
		
		if(Collision.areIntersecting(World.getPlayer().getVertices(), this.getVertices())) {
			
			try { Thread.sleep(5000); } catch (InterruptedException e) { }
			
			mSoundComp.startSound(0, false);
			
			MissionSystem.sendEventMessage(new Message(this.getId(), "Completed", ""));
			
		}
		
	}

}
