package br.jp.spaceshooter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import br.jp.engine.core.Entity;
import br.jp.engine.core.GameController;
import br.jp.engine.core.Tilemap;
import br.jp.engine.core.World;

public class SpaceShooterController extends GameController implements SensorEventListener {

	private Backgound bg, gameOverBg;
	private World world;
	private static boolean gameOver;
	private LilEnemy lilEnemy;
	private Spaceship player;
	private Action action;
	//	private Paint cyanPaint;

	Sensor accelerometer;
	SensorManager sm;


	public SpaceShooterController(Context context) {
		super(context);

		bg = new Backgound(context,540,960,"a");
		gameOverBg = new Backgound(context, 540, 960,"b");

		player = new Spaceship(context, 64, 64, 1, lilEnemy);
		lilEnemy = new LilEnemy(context, 128, 128, 1, player);

		final List<Entity> entities = new ArrayList<Entity>();
		entities.add(player);

		final Tilemap map = new Tilemap( 50, 50, 64, 64, BitmapFactory.decodeResource(getResources(), 
				R.drawable.tile_test));
		//		map.setRelativeTo(player);

		world = new World(entities, map);

		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

		action = new Action(context);
		//		cyanPaint = new Paint();
		//		cyanPaint.setColor(Color.CYAN);

	}

	@Override
	public void updateObjs(Canvas canvas) {
		if (!gameOver) {
			bg.update(canvas);
			world.update(canvas);
			//			canvas.drawText("Stuff", 0, 0, cyanPaint);
			if (world.getEntities().isEmpty()) {
				gameOver=true;
				world.removeAllEntites();
			}else if(!world.getEntities().isEmpty() && !(world.getEntityById(0) instanceof Spaceship) && Action.isTouching==false){
				gameOver=true;
				world.removeAllEntites();
			}
		} else {
			gameOverBg.update(canvas);
			if (Action.isTouching) {
				world.removeAllEntites();
				gameOver = false;
			}
		}

	}

	@Override
	public void drawObjs(Canvas canvas) {
		if (!gameOver) {
			bg.draw(canvas);
			world.draw(canvas);
			//			canvas.drawText(text, 0, 0, paint);
		} else {
			gameOverBg.draw(canvas);
		}

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {	
		action.screenClicked(world);		
		return super.onTouchEvent(event);
	}

	public static boolean isGameOver() {
		return gameOver;
	}

	public static void setGameOver(boolean gameOver) {
		SpaceShooterController.gameOver = gameOver;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		if(event.values[0]<0) action.goRight(world);
		if(event.values[0]>0) action.goLeft(world);
		if(event.values[1]<0) action.goUp(world);
		if(event.values[1]>0) action.goDown(world);

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	//	private int getScreenOrientation() {
	//	    Display = getContext().getSystemService(Context.WINDOW_SERVICE);
	//		int rotation = getContext().getSystemService(Context.WINDOW_SERVICE).getDefaultDisplay().getRotation();
	//	    DisplayMetrics dm = new DisplayMetrics();
	//	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	//	    int width = dm.widthPixels;
	//	    int height = dm.heightPixels;
	//	    int orientation;
	//	    // if the device's natural orientation is portrait:
	//	    if ((rotation == Surface.ROTATION_0
	//	            || rotation == Surface.ROTATION_180) && height > width ||
	//	        (rotation == Surface.ROTATION_90
	//	            || rotation == Surface.ROTATION_270) && width > height) {
	//	        switch(rotation) {
	//	            case Surface.ROTATION_0:
	//	                orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
	//	                break;
	//	            case Surface.ROTATION_90:
	//	                orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	//	                break;
	//	            case Surface.ROTATION_180:
	//	                orientation =
	//	                    ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
	//	                break;
	//	            case Surface.ROTATION_270:
	//	                orientation =
	//	                    ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
	//	                break;
	//	            default:
	//	           
	//	                orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
	//	                break;              
	//	        }
	//	    }
	//	    // if the device's natural orientation is landscape or if the device
	//	    // is square:
	//	    else {
	//	        switch(rotation) {
	//	            case Surface.ROTATION_0:
	//	                orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	//	                break;
	//	            case Surface.ROTATION_90:
	//	                orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
	//	                break;
	//	            case Surface.ROTATION_180:
	//	                orientation =
	//	                    ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
	//	                break;
	//	            case Surface.ROTATION_270:
	//	                orientation =
	//	                    ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
	//	                break;
	//	            default:
	//	       
	//	                orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	//	                break;              
	//	        }
	//	    }
	//
	//	    return orientation;
	//	}

}
