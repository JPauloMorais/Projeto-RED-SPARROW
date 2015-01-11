package br.jp.redsparrow.engine.core.missions;

import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.Collision;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.components.SpriteComponent;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.game.GameRenderer;

public class TestMission extends Mission {

	private SoundComponent mSoundComp;
	private SpriteComponent mSpriteComponent;

	public TestMission(float x, float y) {
		super("Test Mission",x, y, 5f);

		mSoundComp = new SoundComponent(GameRenderer.getContext());
		mSoundComp.addSound(R.raw.test_shot);
		this.addComponent(mSoundComp);
		
		mSpriteComponent = new SpriteComponent(GameRenderer.getContext(), R.drawable.player_ship_test);
		this.addComponent(mSpriteComponent);

		this.addStage(new MissionStage("Do Nothing For 10 Seconds"));
		this.addStage(new MissionStage("Do Nothing For 10 More Seconds"));

	}

	@Override
	public void update() {
		super.update();
		if (Collision.areIntersecting(World.getPlayer().getBounds(),
				this.getBounds())) {


			try {
				mSoundComp.startSound(0, false);
				Thread.sleep(10000);
				
			} catch (InterruptedException e) {
			}


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
