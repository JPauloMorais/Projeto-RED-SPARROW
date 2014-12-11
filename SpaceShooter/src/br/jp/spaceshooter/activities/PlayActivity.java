package br.jp.spaceshooter.activities;

import android.app.Activity;
import android.os.Bundle;
import br.jp.engine.core.GameController;
import br.jp.spaceshooter.SpaceShooterController;

public class PlayActivity extends Activity {

	private GameController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		controller = new SpaceShooterController(this);
		setContentView(controller);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		controller.resume();
	}

	@Override
	protected void onResume() {
		super.onResume();
		controller.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		controller.pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		controller.stop();
	}
}
