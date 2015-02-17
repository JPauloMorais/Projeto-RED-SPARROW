package br.jp.redsparrow.engine.core.tilemaps;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;



public class TilemapLoader extends IntentService {

	private static final String TAG = "TilemapLoaderService";

	private Tilemap mTilemap;
	private int[] mCurPlayerTile;
	private int[] mPlayerTile;

	public TilemapLoader(Tilemap tilemap) {
		super(TAG);

		mCurPlayerTile = new int[2];
		mPlayerTile = new int[2];

		mTilemap = tilemap;
		Intent i = new Intent(mTilemap.getGame().getContext(), this.getClass());
		mTilemap.getGame().getContext().startService(i);

	}

	@Override
	protected void onHandleIntent(Intent intent) {

		mPlayerTile = mTilemap.getTileIndexes(mTilemap.getGame().getWorld().getPlayer().getBounds());

		while (mTilemap.getGame().getWorld().isRunning()) {
			Log.i("Tilemap", "Cool");

			mCurPlayerTile = mTilemap.getTileIndexes(mTilemap.getGame().getWorld().getPlayer().getBounds());
			
			if(mCurPlayerTile[0] != mPlayerTile [0] || mCurPlayerTile[1] != mPlayerTile [1]) {
				mPlayerTile = mCurPlayerTile;
				mTilemap.setCurrentTiles(mPlayerTile[0], mPlayerTile[1]);
			}

		}

	}


}
