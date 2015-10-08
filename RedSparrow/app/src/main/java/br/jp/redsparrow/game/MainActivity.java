package br.jp.redsparrow.game;

import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import br.jp.redsparrow.engine.rendering.Renderer;

public class MainActivity extends Activity
{
	private GLSurfaceView glSurfaceView;


	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		                                                 | View.SYSTEM_UI_FLAG_FULLSCREEN
		                                                 | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setEGLContextClientVersion(2);
		glSurfaceView.setRenderer(new Renderer());
		setContentView(glSurfaceView);

//		ImageView imageView = new ImageView(this);
//		try
//		{
//			imageView.setImageBitmap(BitmapFactory.decodeStream(getResources().getAssets().open("sprites/test.jpg")));
//			setContentView(imageView);
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}

	}

}
