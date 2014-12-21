package br.jp.engine.core;

import java.util.List;

import br.jp.engine.components.Updatable;
import android.content.Context;
import android.os.AsyncTask;

public class UpdateTask extends AsyncTask< Component, Void , Void > {

	private Context mContext;
	private Task mTask;

	public UpdateTask(Context context, Task task){
		mContext = context;
		mTask = task;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Component... components) {
		
		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
