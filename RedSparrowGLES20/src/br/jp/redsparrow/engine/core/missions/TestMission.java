package br.jp.redsparrow.engine.core.missions;

import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.components.SpriteComponent;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.physics.AABB;
import br.jp.redsparrow.engine.core.physics.Collision;
import br.jp.redsparrow.game.GameRenderer;

public class TestMission extends Mission {

	private SoundComponent mSoundComp;
	private SpriteComponent mSpriteComponent;

	public TestMission(float x, float y) {
		super("Test Mission",x, y, 5f);

		mSoundComp = new SoundComponent(GameRenderer.getContext(), this);
		mSoundComp.addSound(R.raw.test_missionaccomplished);
		this.addComponent(mSoundComp);
		
		this.addComponent(mSpriteComponent);

		this.addStage(new MissionStage("Do Nothing For 10 Seconds"));
		this.addStage(new MissionStage("Do Nothing For 10 More Seconds"));

	}

	@Override
	public void update() {
		super.update();
		if (Collision.areIntersecting((AABB)World.getPlayer().getBounds(),
				(AABB)this.getBounds())) {


			mSoundComp.startSound(0, false);
//				Thread.sleep(10000);

			MissionSystem.sendEventMessage(new Message(this.getId(),
					"Completed", ""));

		}

	}
	
	@Override
	public void render(float[] projectionMatrix) {
		// TODO Auto-generated method stub
		super.render(projectionMatrix);
	}

}
