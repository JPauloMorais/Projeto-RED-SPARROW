package br.jp.redsparrow.game.missions;

import android.media.MediaPlayer;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.GameRenderer;
import br.jp.redsparrow.engine.core.missions.Mission;

public class TestMission extends Mission {

	MediaPlayer mp;
	
	public TestMission() {
		super("TestMission", "Go somewhere near 10,10 and do absolutely nothing",
				10, 10, 5);
	}

	@Override
	public void update() {
		
		mp = MediaPlayer.create(GameRenderer.getContext(), R.raw.test_missionaccomplished);
		mp.start();
		this.mComplete = true;
		
	}

}
