package br.jp.redsparrow.engine.saves;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import br.jp.redsparrow.engine.util.TextFileReader;


public class SavedMap {

	private JSONObject mMapObject;
	private JSONArray mTileTextureIndexes;
	
	public SavedMap(Context context, int fileResID) {

		try {
			mMapObject = new JSONObject(TextFileReader.readTextFromFile(context, fileResID));
			mTileTextureIndexes = mMapObject.getJSONArray("textures");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
	}
	
	public int getTilesInX() {
		try {
			return mMapObject.getInt("tilesInX");
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getTilesInY() {
		try {
			return mMapObject.getInt("tilesInY");
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public float getTileSize() {
		try {
			return mMapObject.getInt("tileSize");
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getTileTexture(int tileIndexX, int tileIndexY) {
		
		try {
			
			if(tileIndexX < mMapObject.getInt("tilesInX")) {
				
			}
			
			return tileIndexY;
		} catch (JSONException e) {
			e.printStackTrace();
			return 1000;
		}
	}
	
}
