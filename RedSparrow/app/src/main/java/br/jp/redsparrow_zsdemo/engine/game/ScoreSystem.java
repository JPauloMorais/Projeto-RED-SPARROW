package br.jp.redsparrow_zsdemo.engine.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Environment;
import android.util.Log;

public class ScoreSystem extends GameSystem {

	private final String TAG = "ScoreSystem";
	private String dirName;
	private File scoresFile;

	public ScoreSystem(Game game, String dirName) {
		super(game);
		this.dirName = dirName;
		try {

			String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

			File dirFile = new File(dir, dirName);
			dirFile.mkdirs();

			String fileName = ".scores";
			scoresFile = new File(dirFile, fileName);

			if(scoresFile.createNewFile()) {				

				FileWriter writer = new FileWriter(scoresFile);
				BufferedWriter buffWriter = new BufferedWriter(writer);

				buffWriter.write("p Zamooni Slayer");
				buffWriter.newLine();
				buffWriter.write("s 10001019");
				buffWriter.newLine();
				buffWriter.write("s 9399323");

				buffWriter.close();
			}

			Log.i( TAG, scoresFile.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void addScore(String playerName, long killCount) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(scoresFile));
			String line = null;
			String curPlayer = null;
			Map<String, List<Long>> scores = new HashMap<String, List<Long>>();

			while ((line = reader.readLine())!=null){
				String[] lineParts = line.split("[ ]+");

				StringBuilder builder;
				if (lineParts[0].equals("p")) {
					builder = new StringBuilder();
					for (int i = 1; i < lineParts.length; i++) {
						builder.append(lineParts[i]);
					}
					curPlayer = builder.toString();
					scores.put(curPlayer, new ArrayList<Long>());
				} else if (lineParts[0].equals("s")) {
					scores.get(curPlayer).add(Long.parseLong(lineParts[1]));
				}
			}

			if(scores.get(playerName)!=null){
				scores.get(playerName).add(killCount);
			}else{
				scores.put(playerName, new ArrayList<Long>());
				scores.get(playerName).add(killCount);
			}

			FileWriter writer = new FileWriter(scoresFile);
			BufferedWriter buffWriter = new BufferedWriter(writer);

			for(String name : scores.keySet()){
				buffWriter.write("p "+name);
				buffWriter.newLine();
				for(long score : scores.get(name)){
					buffWriter.write("s "+score);
					buffWriter.newLine();
				}
			}

			buffWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
