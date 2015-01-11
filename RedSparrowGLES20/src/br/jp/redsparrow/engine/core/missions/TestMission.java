package br.jp.redsparrow.engine.core.missions;

<<<<<<< HEAD
import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.Collision;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.messages.Message;
=======
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.Collision;
import br.jp.redsparrow.engine.core.Message;
import br.jp.redsparrow.engine.core.World;
import br.jp.redsparrow.engine.core.components.SoundComponent;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
import br.jp.redsparrow.game.GameRenderer;

public class TestMission extends Mission {

	private SoundComponent mSoundComp;
	
	public TestMission(float x, float y) {
<<<<<<< HEAD
		super("Test Mission",x, y, 5f);
		
		mSoundComp = new SoundComponent(GameRenderer.getContext());
		mSoundComp.addSound(R.raw.test_shot);
		this.addComponent(mSoundComp);
		
		this.addStage(new MissionStage("Do Nothing For 10 Seconds"));
		this.addStage(new MissionStage("Do Nothing For 10 More Seconds"));
		
=======
		super("Test Mission",x, y, 0.5f);
		
		mSoundComp = new SoundComponent(GameRenderer.getContext());
		mSoundComp.addSound(R.raw.test_shot);
		
		this.addComponent(mSoundComp);
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}
	
	@Override
	public void update() {
<<<<<<< HEAD
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
=======
		
		if(Collision.areIntersecting(World.getPlayer().getVertices(), this.getVertices())) {
			
			try { Thread.sleep(5000); } catch (InterruptedException e) { }
			
			mSoundComp.startSound(0, false);
			
			MissionSystem.sendEventMessage(new Message(this.getId(), "Completed", ""));
			
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
		}
		
	}

}
