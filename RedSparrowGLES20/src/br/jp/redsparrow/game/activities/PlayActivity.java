package br.jp.redsparrow.game.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Toast;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.game.GameView;
import br.jp.redsparrow.game.ReSpGame;

public class PlayActivity extends Activity implements OnTouchListener, SensorEventListener {

	private GameView mGameView;
	private ReSpGame game;

	Sensor mSensorAccelerometer;
	SensorManager mSensorManager;

	//	private boolean rendererSet;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGameView = new GameView(this);
		game = new ReSpGame(this);

		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo confInfo = activityManager.getDeviceConfigurationInfo();
		final boolean supES2 = confInfo.reqGlEsVersion >= 0x20000;

		if(supES2){

			mGameView.setEGLContextClientVersion(2);

			mGameView.setOnTouchListener(this);
			mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
			mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);

			
			mGameView.setRenderer(game.getRenderer());
			mGameView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
			//			rendererSet = true;

		}else{
			Toast.makeText(this, " Aparelho n�o suporta OpenGL ES 2.0 ", Toast.LENGTH_LONG).show();
			return;
		}

		setContentView(mGameView);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	
	}



	@Override
	protected void onResume() {
		super.onResume();
		mGameView.onResume();
		game.resume();
		mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGameView.onPause();
		game.pause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		game.stop();
		mSensorManager.unregisterListener(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ogl, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		final SensorEvent se = event;

		if(event.values!=null) mGameView.queueEvent(new Runnable() {
			@Override
			public void run() {
				if(game!=null) game.getInputHandler().handleSensorChange(se.values);
			}
		});
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.performClick();
		if(event!=null){

			final float normalizedX = (event.getX()/(float)v.getWidth())*2-1;
			final float normalizedY = -((event.getY()/(float)v.getHeight())*2-1);

			if(event.getAction()==MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_POINTER_DOWN){
				mGameView.queueEvent(new Runnable() {
					@Override
					public void run() {
						game.getInputHandler().handleTouchPress(normalizedX,normalizedY);
					}
				});
			}else if(event.getAction()==MotionEvent.ACTION_UP){
				mGameView.queueEvent(new Runnable() {
					@Override
					public void run() {
						game.getInputHandler().handleTouchRelease(normalizedX,normalizedY);
					}
				});
			}else if(event.getAction()==MotionEvent.ACTION_MOVE){
				mGameView.queueEvent(new Runnable() {
					@Override
					public void run() {
						game.getInputHandler().handleTouchDrag(normalizedX,normalizedY);
					}
				});
			}
			return true;
		}else return false;
	}
}
