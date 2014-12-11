package br.jp.engine.core;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public abstract class GameController extends SurfaceView implements Runnable, SurfaceHolder.Callback {

	private static final int NO_DELAYS_PER_YIELD = 10;
	private Thread thread;
	private boolean isRunning = false;
	private SurfaceHolder holder;
	private static int MAX_FRAME_SKIPS = 15;
	public static int screenWidth, screenHeight;
	

	public GameController(Context context) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    DisplayMetrics metrics = new DisplayMetrics();
	    display.getMetrics(metrics);
	    screenWidth = metrics.widthPixels;
	    screenHeight = metrics.heightPixels;
	}
	public abstract void updateObjs(Canvas canvas);
	public abstract void drawObjs(Canvas canvas);

	@Override
	public void run() {

		long beforeTime, timeDiff, sleepTime, afterTime;   
		beforeTime = System.currentTimeMillis( );
		long overSleepTime = 0L;
		int noDelays = 0;   
		long excess = 0L;  
		int period = 2;
		
		while(isRunning){

			if (!holder.getSurface().isValid()) continue;

			Canvas canvas = holder.lockCanvas();
			
			updateObjs(canvas);
			drawObjs(canvas);

			holder.unlockCanvasAndPost(canvas);

			afterTime = System.currentTimeMillis( );
			timeDiff =  afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;
			
			if (sleepTime > 0) { 
				try {        
					Thread.sleep(sleepTime/1000000L); 
				}catch(InterruptedException ex){
					ex.printStackTrace();
				} 
				overSleepTime = ( System.currentTimeMillis( ) - afterTime) - sleepTime;
			}else{
				excess -= sleepTime;
				overSleepTime = 0L;       
				if (++noDelays >= NO_DELAYS_PER_YIELD) {         
					Thread.yield( );        
					noDelays = 0;       
				}        
			}
		}

		beforeTime = System.currentTimeMillis();

		int skips = 0;
		while((excess > period) && (skips < MAX_FRAME_SKIPS)){
			excess -= period;       
			
			if (!holder.getSurface().isValid()) continue;
			Canvas canvas = holder.lockCanvas();
			updateObjs(canvas);
			holder.unlockCanvasAndPost(canvas);

			skips++;
		}

	}



	public void resume(){
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}

	public void pause(){
		isRunning = false;
	}

	public void stop(){
		isRunning = false;
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
