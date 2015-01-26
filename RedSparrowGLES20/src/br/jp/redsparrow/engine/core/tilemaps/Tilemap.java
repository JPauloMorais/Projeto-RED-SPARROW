package br.jp.redsparrow.engine.core.tilemaps;

import android.content.Context;
import android.content.Intent;

public class Tilemap {

	private static Tile[][] mTiles;
	private static Tile[][] mCurrentTiles;


	public static void create(Context context, int tilesInX, int tilesInY, float tileSize) {

		mCurrentTiles = new Tile[3][3];
		mTiles = new Tile[tilesInX][tilesInY];

		for (int i = 0; i < tilesInX; i++) {
			for (int j = 0; j < tilesInY; j++) {
				mTiles[i][j] = new Tile();
			}
		}
		
		Intent intent = new Intent(context, TilemapLoader.class);
		context.startService(intent);
		
	}
	
	public static void render() {
		
	}


	public static Tile[][] getCurrentTiles() {
		return mCurrentTiles;
	}


	public static synchronized void setCurrentTiles(int left, int top) {
		
		int k = 0;
		int l = 0;
		
		for (int i = left, right = left+2 ; i < right; i++) {
			for (int j = 0, bottom = top+2; j < bottom; j++) {
				mCurrentTiles[k][l] = mTiles[i][j];
			}
		}
		
	}

}
