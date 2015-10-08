package br.jp.redsparrow.engine;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class App extends Application
{
	private static App instance;

	private static SensorManager sensorManager;
	private static Sensor        accelerometer;

	@Override
	public void onCreate ()
	{
		super.onCreate();
		instance = this;

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	public static void registerAccelerometerListener(SensorEventListener sel)
	{
		sensorManager.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	public static void unregisterAccelerometerListener(SensorEventListener sel)
	{
		sensorManager.unregisterListener(sel);
	}

	public static Context getContext() {return instance;}
}
