package br.jp.engine.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class GameController extends SurfaceView implements Runnable {

	private Thread thread;
	private SurfaceHolder holder;
	private boolean isRunning    = false;
	public static final double UPS = 60.0;
	
	public GameController(Context context) {
		super(context);
		holder = getHolder();
	}
	
	public abstract void update(Canvas canvas);
	public abstract void render(Canvas canvas);

	@Override
	public void run() {
		
		long timer = System.currentTimeMillis();
		long lasTime = System.nanoTime();
		double ns    = 1000000000.0 / UPS;
		double delta = 0;
		int updates  = 0;
		int frames   = 0;
		
		while (isRunning) {
			
			long now = System.nanoTime();
			delta += (now-lasTime) / ns;
			lasTime = now;
			
			if (!holder.getSurface().isValid()) continue;
			Canvas canvas = holder.lockCanvas();
			
			if(delta>=1){
				update(canvas);
				updates++;
				delta--;
			}
			
			canvas.drawColor(Color.rgb(0, 191, 255));
			render(canvas);
			frames++;
			
			holder.unlockCanvasAndPost(canvas);
			
			if(System.currentTimeMillis() - timer > 1000){
				if(LogConfig.IS_DEBUGGING) Log.i("FPS/UPS", "UPS: " + updates + ", FPS: " + frames);
				timer  += 1000;
				updates = 0;
				frames  = 0;
			}
			
		}
		
	}
	
	public synchronized void resume(){
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}

	public synchronized void pause(){
		isRunning = false;
	}

	public synchronized void stop(){
		isRunning = false;
	}

}
