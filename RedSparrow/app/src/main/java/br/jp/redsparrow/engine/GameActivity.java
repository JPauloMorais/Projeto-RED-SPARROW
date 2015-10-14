package br.jp.redsparrow.engine;

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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import br.jp.redsparrow.engine.input.InputManager;
import br.jp.redsparrow.engine.input.SensorInput;
import br.jp.redsparrow.engine.input.TouchInput;

/**
 * Created by JoaoPaulo on 14/10/2015.
 */
public class GameActivity extends Activity implements View.OnTouchListener, SensorEventListener
{
	protected RelativeLayout layout;
	protected GLSurfaceView  glSurfaceView;
	protected TextView       overlaidTextView;

	public    World        world;
	protected InputManager inputManager;

	protected int width, height;
	protected float halfWidth, halfHeight;

	public GameActivity (final World world, final InputManager inputManager)
	{
		this.world = world;
		this.inputManager = inputManager;
	}

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		final GameActivity ga = this;

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		                                                 | View.SYSTEM_UI_FLAG_FULLSCREEN
		                                                 | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		layout = new RelativeLayout(this);

		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setEGLContextClientVersion(2);
		glSurfaceView.setRenderer(new GLSurfaceView.Renderer()
		{
			long startTime = System.nanoTime();
			int frames = 0;

			long lastTime;
			long time;

			@Override
			public void onSurfaceCreated (GL10 gl, EGLConfig config)
			{
				world.onInit();
			}

			@Override
			public void onSurfaceChanged (GL10 gl, int w, int h)
			{
				width = w;
				halfWidth = ((float)width) * 0.5f;
				height = h;
				halfHeight = ((float)height) * 0.5f;

				world.onResize(width, height);
			}

			@Override
			public void onDrawFrame (GL10 gl)
			{
				double elapsed = (double)((time - lastTime) / Consts.NS_PER_MS);
				float delta = 0.0f;
				if(elapsed > 0) delta = (float) (elapsed / 60000.0D);
				Log.d("Delta", "d: " + delta);
				lastTime = time + 0L;
				time = System.nanoTime();

				frames++;
				if(time - startTime >= Consts.NS_PER_S)
				{
					Log.d("FPS", "Fps:" + frames);
					final int fps = frames;
					runOnUiThread(new Runnable()
					{
						@Override
						public void run ()
						{
							overlaidTextView.setText("FPS: " + fps);
						}
					});
					frames = 0;
					startTime = time;
				}

				inputManager.processInputs(ga);
				world.onUpdate(ga,delta);
				world.onRender();
			}
		});
		glSurfaceView.setOnTouchListener(this);
		App.registerAccelerometerListener(this);
		layout.addView(glSurfaceView);

		overlaidTextView = new TextView(this);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		overlaidTextView.setLayoutParams(layoutParams);
		overlaidTextView.setTextColor(Color.WHITE);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/DisposableDroidBB.otf");
		overlaidTextView.setTypeface(tf);
//		overlaidTextView.setText("FPS: 0");
		overlaidTextView.setAlpha(0.5f);
		overlaidTextView.setTextSize(20);
//		overlaidTextView.setVisibility(View.VISIBLE);
		layout.addView(overlaidTextView);

		setContentView(layout);
	}

	@Override
	public boolean onTouch (View v, MotionEvent event)
	{
		if(inputManager.useTouch)
		{
			int pointerID = 0;
			int pointerIndex = 0;
			switch (event.getActionMasked())
			{
				case MotionEvent.ACTION_POINTER_DOWN:
					pointerIndex = event.getActionIndex();
					pointerID = event.getPointerId(pointerIndex);
				case MotionEvent.ACTION_DOWN:
					inputManager.setLastTouchInput(pointerID, new TouchInput(TouchInput.TYPE_DOWN, event.getX(pointerIndex), event.getY(pointerIndex)));
					break;
				case MotionEvent.ACTION_MOVE:
					for (int i = 0; i < event.getPointerCount(); i++)
						inputManager.setLastTouchInput(event.getPointerId(i), new TouchInput(TouchInput.TYPE_MOVE, event.getX(i), event.getY(i)));
					break;
				case MotionEvent.ACTION_POINTER_UP:
					pointerIndex = event.getActionIndex();
					pointerID = event.getPointerId(pointerIndex);
				case MotionEvent.ACTION_UP:
					inputManager.setLastTouchInput(pointerID, new TouchInput(TouchInput.TYPE_UP, event.getX(pointerIndex), event.getY(pointerIndex)));
					break;
			}
			return true;
		}
		return false;
	}

	@Override
	public void onSensorChanged (SensorEvent event)
	{
		if(inputManager.useSensor)
			inputManager.setLastSensorInput(new SensorInput(event.values[0], event.values[1], event.values[2]));
	}

	@Override
	public void onAccuracyChanged (Sensor sensor, int accuracy) {}

	@Override
	protected void onPause ()
	{
		super.onPause();
		world.pause();
		App.unregisterAccelerometerListener(this);
	}

	@Override
	protected void onResume ()
	{
		super.onResume();
		world.resume();
		App.registerAccelerometerListener(this);
	}

	@Override
	protected void onStop ()
	{
		super.onStop();
		world.stop();
		App.unregisterAccelerometerListener(this);
	}
}
