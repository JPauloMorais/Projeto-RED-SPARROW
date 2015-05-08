package br.jp.redsparrow.game.missions;

import android.media.MediaPlayer;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.game.Game;
import br.jp.redsparrow.engine.missions.Mission;

public class TestMission extends Mission {

	MediaPlayer mp;
	
	public TestMission() {
		super("TestMission", "Go somewhere near 10,10 and do absolutely nothing",
				10, 10, 5);
	}

	@Override
	public void update(Game game) {
		
		mp = MediaPlayer.create(game.getContext(), R.raw.test_missionaccomplished);
		mp.start();
		this.mComplete = true;
		
	}

	@Override
	public void onTrigger(Game game) {
//		Toast.makeText(game.getContext(), this.mDescription, Toast.LENGTH_LONG).show();
	}

}
