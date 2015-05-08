package br.jp.redsparrow.engine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.Resources;


public class TextFileReader {

	public static String readTextFromFile(Context context, int fileId){
		StringBuilder txt = new StringBuilder();
		
		try {
			InputStream inputStream =
					context.getResources().openRawResource(fileId);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String nextLine;
			while ((nextLine = bufferedReader.readLine()) != null) {
			txt.append(nextLine);
			txt.append('\n');
			}
		} catch (IOException e) {
			throw new RuntimeException("Arquivo nao pode ser aberto: " + fileId, e);
		}catch (Resources.NotFoundException e) {
			throw new RuntimeException("Arquivo nao encontrado: " + fileId,e);
		}
		
		return txt.toString();
	}
	
}
