package br.jp.spaceshooter.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import br.jp.engine.core.GameRenderer;

public class PlayActivity extends Activity implements OnTouchListener, SensorEventListener {

	//	private GameController controller;
	//	private GameView gv;
	private GLSurfaceView mGlSView;
	private GameRenderer mGameRenderer;

	Sensor mSensorAccelerometer;
	SensorManager mSensorManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//		controller = new SpaceShooterController(this);
		//		gv = new GameView(this);
		mGlSView = new GLSurfaceView(this);
		mGameRenderer = new GameRenderer(this);

		mGlSView.setOnTouchListener(this);
		mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);

		mGlSView.setRenderer(mGameRenderer);
		setContentView(mGlSView);

	}

	@Override
	protected void onStart() {
		super.onStart();
		mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		//		gv.resume();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		//		gv.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
		//		gv.pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mSensorManager.unregisterListener(this);
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.performClick();
		if(event!=null){

			final float normalizedX = (event.getX()/(float)v.getWidth())*2-1;
			final float normalizedY = -((event.getY()/(float)v.getHeight())*2-1);

			if(event.getAction()==MotionEvent.ACTION_DOWN){
				mGlSView.queueEvent(new Runnable() {
					@Override
					public void run() {
						mGameRenderer.handleTouchPress(normalizedX,normalizedY);
					}
				});
			}else if(event.getAction()==MotionEvent.ACTION_MOVE){
				mGlSView.queueEvent(new Runnable() {
					@Override
					public void run() {
						mGameRenderer.handleTouchDrag(normalizedX,normalizedY);
					}
				});
			}
			return true;
		}else return false;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		final SensorEvent se = event;
		
		if(event.values!=null) mGlSView.queueEvent(new Runnable() {
			@Override
			public void run() {
				mGameRenderer.handleSensorChange(se.values);
			}
		});
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}
}
