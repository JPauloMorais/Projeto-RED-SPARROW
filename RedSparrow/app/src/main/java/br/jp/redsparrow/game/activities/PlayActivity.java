package br.jp.redsparrow.game.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.game.GameView;
import br.jp.redsparrow.game.ReSpGame;
import br.jp.redsparrow.game.objecttypes.basicplayer.PlayerGunComponent;

public class PlayActivity extends Activity implements OnTouchListener, SensorEventListener {

	private RelativeLayout mRelLayout;
	private GameView mGameView;
	private ReSpGame game;

	Sensor mSensorAccelerometer;
	SensorManager mSensorManager;

	//	private ImageView ammoDisplay;
	//	private ImageView ammo;
	private TextView gameOver;
	
	private TextView upgradeButton;
	
	private Button pauseButton;
	private TextView killPoints;

	private int upgrades = 2;
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
		mRelLayout = new RelativeLayout(this);

		mRelLayout.addView(mGameView);

		//		ammoDisplay = new ImageView(this);
		//
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//		layoutParams.addRule(RelativeLayout.ALIGN_TOP);
		//		ammoDisplay.setLayoutParams(layoutParams);
		//		ammoDisplay.setBackgroundResource(R.drawable.ammo_display_test);
		//		ammoDisplay.setAlpha(0.5f);
		//		mRelLayout.addView(ammoDisplay);
		//
		//		ammo = new ImageView(this);
		//
		//		layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		////		layoutParams.addRule(RelativeLayout.tra);
		////		ammo.setLayoutParams(layoutParams);
		//		ammo.setScaleX(10);
		//		ammo.setScaleY(10);
		//		ammo.setFadingEdgeLength(1);
		//		ammo.setX(ammoDisplay.getWidth()/2);
		//		ammo.setBackgroundResource(R.drawable.player_projectile_1);
		//		ammo.setAlpha(0.5f);
		//		ammo.setOnClickListener(new OnClickListener() {
		//			@Override
		//			public void onClick(View v) {
		//				if(((PlayerGunComponent)game.getWorld().getPlayer().getUpdatableComponent("Gun")).getBulletTypeCount()>1)
		//				{
		//					((PlayerGunComponent)game.getWorld().getPlayer().getUpdatableComponent("Gun")).switchNextBulletType();
		//					ammo.setBackgroundResource(R.drawable.player_projectile_2);
		//				}
		//			}
		//		});
		//		mRelLayout.addView(ammo);


		pauseButton = new Button(this);

		layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		pauseButton.setLayoutParams(layoutParams);
		pauseButton.setBackgroundResource(R.drawable.pause_buton);
		pauseButton.setAlpha(0.5f);
		pauseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( !game.getWorld().isPaused() ) {
					game.getWorld().pause();
					pauseButton.setBackgroundResource(R.drawable.play_button_v1);
					killPoints.setVisibility(View.GONE);
					upgradeButton.setVisibility(View.GONE);

					//					ammo.setVisibility(View.GONE);
					//					ammoDisplay.setVisibility(View.GONE);
					//					pauseButton.setX();

				}
				else {
					game.getWorld().resume();
					pauseButton.setBackgroundResource(R.drawable.pause_buton);
					pauseButton.setAlpha(0.5f);
					killPoints.setVisibility(View.VISIBLE);
					//					ammo.setVisibility(View.VISIBLE);
					//					ammoDisplay.setVisibility(View.VISIBLE);

				}
			}
		});
		mRelLayout.addView(pauseButton);

		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/DisposableDroidBB.otf"); 
		
		killPoints = new TextView(this);

		layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		killPoints.setTypeface(type);
		killPoints.setLayoutParams(layoutParams);
		killPoints.setBackgroundResource(R.drawable.kill_points);
		killPoints.setText("     "+0);
		killPoints.setAlpha(0.7f);
		//		killPoints.setOnClickListener(new OnClickListener() {
		//			@Override
		//			public void onClick(View v) {
		//				
		//			}
		//		});
		mRelLayout.addView(killPoints);

		gameOver = new TextView(this);

		layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		gameOver.setLayoutParams(layoutParams);
		gameOver.setTextColor(Color.RED);
		gameOver.setGravity(Gravity.CENTER);
		gameOver.setTypeface(type);
		gameOver.setTextSize(50);
//		final Intent i = new Intent(this, MenuActivity.class);
		gameOver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				finish();
			}
		});
		gameOver.setVisibility(View.GONE);
		mRelLayout.addView(gameOver);
		
		upgradeButton = new TextView(this);

		layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		upgradeButton.setLayoutParams(layoutParams);
		upgradeButton.setTextColor(Color.YELLOW);
		upgradeButton.setTypeface(type);
		upgradeButton.setText("UPGRADE");
		upgradeButton.setTextSize(30);
		upgradeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((PlayerGunComponent)game.getWorld().getPlayer().getUpdatableComponent("Gun"))
				.addBulletType("BasicProjectile"+upgrades);
				
				upgrades++;
				
				upgradeButton.setVisibility(View.GONE);

			}
		});
		upgradeButton.setVisibility(View.GONE);
		mRelLayout.addView(upgradeButton);

		setContentView(mRelLayout);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	}

	public void showUpgrade() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				upgradeButton.setText("UPGRADE " + (upgrades-1));
				upgradeButton.setVisibility(View.VISIBLE);
			}
		});
	}
	
	public void setPoints(final int points) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				killPoints.setText("     "+points);
			}
		});
	}

	private String playerName;
	private EditText nameIn;
	private long killsAmmountOut;
	private TextView killsOut;
	public void gameOver(final int points) {
		

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				game.getWorld().stop();

				setContentView(R.layout.game_over_screen);

				Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/DisposableDroidBB.otf");
				TextView go0 = (TextView) findViewById(R.id.gameOver0);
				go0.setTypeface(tf);
				TextView go1 = (TextView) findViewById(R.id.gameOver1);
				go1.setTypeface(tf);
				TextView go2 = (TextView) findViewById(R.id.gameOver2);
				go2.setTypeface(tf);
				TextView go3 = (TextView) findViewById(R.id.gameOver3);
				go3.setTypeface(tf);
				TextView go4 = (TextView) findViewById(R.id.gameOver4);
				go4.setTypeface(tf);

				nameIn = (EditText) findViewById(R.id.nomeEditText);
				killsOut = (TextView) findViewById(R.id.gameOverKillPoints);
				killsAmmountOut = points/10;
				killsOut.setText("" + killsAmmountOut);
			}
		});
	}

	@Override
	public void onBackPressed() {
		if(!game.getWorld().isRunning()){
			playerName = (nameIn.getText().toString() != null ? nameIn.getText().toString() : "Anonimo");
			game.getScoreSystem().addScore(playerName, killsAmmountOut);

			try {
				finalize();
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
			startActivity(new Intent(this, MenuActivity.class));
		}
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
		if(event!=null && !game.getWorld().isPaused()){

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

	public boolean isExStorageWritable() {
		String state = Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}
}
