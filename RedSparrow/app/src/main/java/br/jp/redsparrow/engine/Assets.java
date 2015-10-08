package br.jp.redsparrow.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by JoaoPaulo on 08/10/2015.
 */
public class Assets
{
	private static final String TAG = "Assets";

	public static Bitmap getBitmap (String name)
	{
		InputStream is = null;
		try
		{
			is = App.getContext().getResources().getAssets().open("images/" + name + (name.contains(".") ? "" : ".png"));
		} catch (IOException e) { e.printStackTrace(); }
		return BitmapFactory.decodeStream(is);
	}

	public static String getShaderText (String name)
	{
		String text = "";

		try
		{
			InputStream inputStream = App.getContext().getResources().getAssets().open("shaders/" + name + ".glsl");
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String nextLine;

			while ((nextLine = bufferedReader.readLine()) != null)
				text += nextLine + "\n";

			return text;
		} catch (IOException e) { Log.w(TAG, "Shader pode ser aberto: " + name);}
		catch (Resources.NotFoundException e) {Log.w(TAG, "Shader nao encontrado: " + name);}

		return "";
	}
}
