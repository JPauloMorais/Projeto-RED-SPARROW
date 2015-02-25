package br.jp.redsparrow.engine.core.game;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.os.Environment;
import android.util.JsonWriter;


public class ScoreSystem extends GameSystem {

	private JSONObject mScores;
	
	public ScoreSystem(Game game) {
		super(game);

		try {

			String dirName = "RedSparrow";
			String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

			File dirFile = new File(dir, dirName);
			dirFile.mkdirs();

			String fileName = "scores.json";
			File file = new File(dirFile, fileName);
			
			if(file.createNewFile()) {				
				
				FileWriter writer = new FileWriter(file);
				JsonWriter jw = new JsonWriter(writer);
				jw.beginObject().name("Teste").value(true).endObject();
				jw.close();
			
			}else {

				
				JSONParser parser = new JSONParser();
				mScores = (JSONObject) parser.parse(new FileReader(file));
				System.out.println(mScores.getString("Teste"));
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
