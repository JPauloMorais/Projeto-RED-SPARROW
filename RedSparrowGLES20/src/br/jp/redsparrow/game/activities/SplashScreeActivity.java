package br.jp.redsparrow.game.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import br.jp.redsparrow.R;

public class SplashScreeActivity extends Activity {

	RelativeLayout mRelLayout;
	AnimationDrawable mSplashAnim;
	ImageView mSplash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mRelLayout = new RelativeLayout(this);
		
		mSplash = new ImageView(this);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		mSplash.setLayoutParams(layoutParams);
		mSplash.setBackgroundResource(R.drawable.intro_splash);
		mSplashAnim = (AnimationDrawable) mSplash.getBackground();
		final Intent i = new Intent(this, MenuActivity.class);
		mSplash.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(i);
			}
		});
		
		mRelLayout.addView(mSplash);
		
		setContentView(mRelLayout);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus) {
			mSplashAnim.start();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_scree, menu);
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
}
