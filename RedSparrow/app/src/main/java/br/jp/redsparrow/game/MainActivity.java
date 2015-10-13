package br.jp.redsparrow.game;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.jp.redsparrow.engine.App;
import br.jp.redsparrow.engine.World;
import br.jp.redsparrow.engine.input.InputManager;
import br.jp.redsparrow.engine.input.SensorInput;
import br.jp.redsparrow.engine.input.TouchInput;
import br.jp.redsparrow.engine.math.Vec2;
import br.jp.redsparrow.engine.math.Vec3;
import br.jp.redsparrow.engine.rendering.Renderer;

public class MainActivity extends Activity implements View.OnTouchListener, SensorEventListener
{
	private GLSurfaceView glSurfaceView;
	public static TextView fpsView;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		                                                 | View.SYSTEM_UI_FLAG_FULLSCREEN
		                                                 | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		RelativeLayout layout = new RelativeLayout(this);

		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setEGLContextClientVersion(2);
		glSurfaceView.setRenderer(new Renderer(this));
		glSurfaceView.setOnTouchListener(this);
		App.registerAccelerometerListener(this);
		layout.addView(glSurfaceView);

		fpsView = new TextView(this);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		fpsView.setLayoutParams(layoutParams);
		fpsView.setTextColor(Color.WHITE);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/DisposableDroidBB.otf");
		fpsView.setTypeface(tf);
		fpsView.setText("FPS: 0");
		fpsView.setAlpha(0.5f);
		fpsView.setTextSize(20);
		fpsView.setVisibility(View.VISIBLE);
		layout.addView(fpsView);

		setContentView(layout);

		InputManager.set(new InputManager(true, false)
		{
			private Vec2 start = new Vec2();
			private Vec2 dir = new Vec2();

			@Override
			protected boolean onTouch_0 (TouchInput touchInput)
			{
				switch (touchInput.type)
				{
					case TouchInput.TYPE_DOWN:
						start.set(touchInput.x, - touchInput.y);
						Log.d("In", "strt: " + start);
						break;
					case TouchInput.TYPE_MOVE:
						Vec2.sub(new Vec2(touchInput.x, - touchInput.y), start, dir);
						dir.normalize();
						Log.d("In", "Dir: " + dir);
						Vec2.mult(dir, 0.01f, dir);
						World.player.applyForce(dir);
//						Vec2.add(World.player.acl, dir, World.player.acl);
//						World.player.acl.normalize();
//						Vec2.mult(World.player.acl, 0.001f, World.player.acl);
						break;
					case TouchInput.TYPE_UP:
						Log.d("In", "0 Up");
						break;
				}
				return true;
			}

			@Override
			protected boolean onTouch_1 (TouchInput touchInput)
			{
				return false;
			}

			@Override
			protected boolean onTouch_2 (TouchInput touchInput)
			{
				return false;
			}

			@Override
			protected boolean onTouch_3 (TouchInput touchInput)
			{
				return false;
			}

			@Override
			protected boolean onTouch_4 (TouchInput touchInput)
			{
				return false;
			}

			@Override
			protected boolean onSensorChanged (SensorInput sensorInput)
			{
				Vec2 dir = new Vec2(sensorInput.dir.x, sensorInput.dir.y);
				dir.normalize();
				Log.d("Player", dir.toString());
//				World.player.acl = new Vec3(dir.y, dir.x, 0.0f);
				return true;
			}
		});
	}

	@Override
	public boolean onTouch (View v, MotionEvent event)
	{
		if(InputManager.useTouch)
		{
			int pointerID = 0;
			int pointerIndex = 0;
			switch (event.getActionMasked())
			{
				case MotionEvent.ACTION_POINTER_DOWN:
					pointerIndex = event.getActionIndex();
					pointerID = event.getPointerId(pointerIndex);
				case MotionEvent.ACTION_DOWN:
					InputManager.setLastTouchInput(pointerID, new TouchInput(TouchInput.TYPE_DOWN, event.getX(pointerIndex), event.getY(pointerIndex)));
					break;
				case MotionEvent.ACTION_MOVE:
					for (int i = 0; i < event.getPointerCount(); i++)
						InputManager.setLastTouchInput(event.getPointerId(i), new TouchInput(TouchInput.TYPE_MOVE, event.getX(i), event.getY(i)));
					break;
				case MotionEvent.ACTION_POINTER_UP:
					pointerIndex = event.getActionIndex();
					pointerID = event.getPointerId(pointerIndex);
				case MotionEvent.ACTION_UP:
					InputManager.setLastTouchInput(pointerID, new TouchInput(TouchInput.TYPE_UP, event.getX(pointerIndex), event.getY(pointerIndex)));
					break;
			}

			return true;
		}

		return false;
	}

	@Override
	public void onSensorChanged (SensorEvent event)
	{
		if(InputManager.useSensor)
			InputManager.setLastSensorInput(new SensorInput(event.values[0], event.values[1], event.values[2]));
	}

	@Override
	public void onAccuracyChanged (Sensor sensor, int accuracy) {}

	@Override
	protected void onPause ()
	{
		super.onPause();
		App.unregisterAccelerometerListener(this);
	}

	@Override
	protected void onResume ()
	{
		super.onResume();
		App.registerAccelerometerListener(this);
	}

	@Override
	protected void onStop ()
	{
		super.onStop();
		App.unregisterAccelerometerListener(this);
	}
}
