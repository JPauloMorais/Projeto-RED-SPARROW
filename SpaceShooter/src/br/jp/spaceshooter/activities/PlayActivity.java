package br.jp.spaceshooter.activities;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import br.jp.engine.core.GameRenderer;

public class PlayActivity extends Activity {

	//	private GameController controller;
	//	private GameView gv;
	private GLSurfaceView gv;
	private GameRenderer mGameRenderer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//		controller = new SpaceShooterController(this);
		//		gv = new GameView(this);
		gv = new GLSurfaceView(this);
		mGameRenderer = new GameRenderer(this);

		gv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event!=null){
					
					final float normalizedX = (event.getX()/(float)v.getWidth())*2-1;
					final float normalizedY = -((event.getY()/(float)v.getHeight())*2-1);

					if(event.getAction()==MotionEvent.ACTION_DOWN){
						gv.queueEvent(new Runnable() {

							@Override
							public void run() {
								mGameRenderer.handleTouchPress(normalizedX,normalizedY);
							}
						});
					}else if(event.getAction()==MotionEvent.ACTION_MOVE){
						gv.queueEvent(new Runnable() {

							@Override
							public void run() {
								mGameRenderer.handleTouchDrag(normalizedX,normalizedY);
							}
						});
					}
					return true;
				}else return false;
			}});

		gv.setRenderer(mGameRenderer);
		setContentView(gv);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		//		gv.resume();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//		gv.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		//		gv.pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		//		gv.stop();
	}
}
