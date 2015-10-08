package br.jp.redsparrow.engine;

import android.app.Application;
import android.content.Context;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class App extends Application
{
	private static App instance;

	@Override
	public void onCreate ()
	{
		super.onCreate();
		instance = this;
	}

	public static Context getContext() {return instance;}
}
