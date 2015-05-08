package br.jp.redsparrow.game.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.drawable.AnimationDrawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.game.GameView;
import br.jp.redsparrow.game.ReSpMenuRenderer;

public class MenuActivity extends Activity implements OnTouchListener {

	private GameView mView;
	private AnimationDrawable mSplashAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		setContentView(R.layout.activity_menu);

		mView = new GameView(this);

		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo confInfo = activityManager.getDeviceConfigurationInfo();
		final boolean supES2 = confInfo.reqGlEsVersion >= 0x20000;

		if(supES2){
			mView.setEGLContextClientVersion(2);
			mView.setOnTouchListener(this);
			mView.setRenderer(new ReSpMenuRenderer(this));
			mView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

		}else{
			Toast.makeText(this, " Aparelho nao suporta OpenGL ES 2.0 ", Toast.LENGTH_LONG).show();
			return;
		}

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		RelativeLayout layout = new RelativeLayout(this);
		LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		lp.addRule(Gravity.CENTER);
		layout.setLayoutParams(lp);
		layout.addView(mView);

		ImageView splashImage = new ImageView(this);
		splashImage.setBackgroundResource(R.drawable.intro_splash);
		lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lp.setMargins(0, 30, 0, 0);
		splashImage.setLayoutParams(lp);
		mSplashAnim = (AnimationDrawable) splashImage.getBackground();
		layout.addView(splashImage);

		ImageButton playButton = new ImageButton(this);
		playButton.setBackgroundResource(R.drawable.play_button_v1);
		lp = new RelativeLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		playButton.setLayoutParams(lp);
		playButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				play(v);
			}
		});
		layout.addView(playButton);

		ImageButton scoresButton = new ImageButton(this);
		scoresButton.setBackgroundResource(R.drawable.hiscores);
		lp = new RelativeLayout.LayoutParams(50, 50);
		lp.setMargins(0,0,5,5);
		lp.addRule(RelativeLayout.ALIGN_PARENT_END);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		scoresButton.setLayoutParams(lp);
		scoresButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hiScores(v);
			}
		});
		layout.addView(scoresButton);

		setContentView(layout);

	}

	public void play(View v){
		Intent i = new Intent(this, PlayActivity.class);
		startActivity(i);
	}

	public void hiScores(View v){
		Intent i = new Intent(this, HighScoresActivity.class);
		startActivity(i);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus) {
			mSplashAnim.start();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
