package br.jp.redsparrow.game.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.game.GameView;
import br.jp.redsparrow.game.ReSpMenuRenderer;

public class MenuActivity extends Activity implements OnTouchListener {

	private GameView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		mView = new GameView(this);

		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo confInfo = activityManager.getDeviceConfigurationInfo();
		final boolean supES2 = confInfo.reqGlEsVersion >= 0x20000;

		if(supES2){

			mView.setEGLContextClientVersion(2);

			mView.setOnTouchListener(this);
			
			mView.setRenderer(new ReSpMenuRenderer(this));
			mView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
			//			rendererSet = true;

		}else{
			Toast.makeText(this, " Aparelho não suporta OpenGL ES 2.0 ", Toast.LENGTH_LONG).show();
			return;
		}
		
		 DisplayMetrics metrics = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		RelativeLayout relLayout = new RelativeLayout(this);
		
		relLayout.addView(mView);
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);	
		
		TextView tv = new TextView(this);
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sertig.otf");
		tv.setTypeface(font);
		tv.setText("RED SPARROW");
		tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		tv.setTextSize(30);
		
		tv.setTextColor(Color.RED);
		tv.setLayoutParams(layoutParams);		
		relLayout.addView(tv);
		
		RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
		ImageButton playB = new ImageButton(this);
		playB.setBackgroundResource(R.drawable.play_button_v1);
		playB.setLayoutParams(layoutParams1);
		
		final Intent intent = new Intent(this, PlayActivity.class);
		playB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(intent);
				onStop();
			}
		});
		relLayout.addView(playB);
		
		setContentView(relLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
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
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
