package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateService extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	class Updater extends Thread{
		private ArrayList<GameObject> mGameObjects;
		
		@Override
		public void run() {
			for (GameObject gameObject : mGameObjects) {
				gameObject.update();
			}
		}
	}
}


