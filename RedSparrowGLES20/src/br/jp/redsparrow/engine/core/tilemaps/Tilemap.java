package br.jp.redsparrow.engine.core.tilemaps;

import android.graphics.RectF;
import android.util.Log;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.game.GameSystem;
import br.jp.redsparrow.engine.core.physics.Bounds;

public class Tilemap extends GameSystem{

	//TODO: distancias baseadas no tamanho do player e tamanho dos tiles
	public static final int DISTANCE_NEAR = 3;
	public static final int DISTANCE_MID = 5;
	public static final int DISTANCE_FAR = 7;

	@SuppressWarnings("unused")
	private TilemapLoader mTilemapLoader;
	private int mDistance;
	private float mTileSize;
	private int mTilesInX;
	private int mTilesInY;
	private Tile[][] mTiles;
	private Tile[][] mCurrentTiles;
	private RectF mBounds;
	
	private int[] mCurPlayerTile;
	private int[] mPlayerTile;

	public Tilemap(Game game, int tilesInX, int tilesInY, float tileSize) {
		super(game);

		mDistance = DISTANCE_NEAR;
		mTilesInX = tilesInX;
		mTilesInY = tilesInY;
		mTileSize = tileSize;

		mCurrentTiles = new Tile[mDistance][mDistance];
		mTiles = new Tile[tilesInX][tilesInY];

		mBounds = new RectF(-((tilesInX*tileSize)/2), -((tilesInY*tileSize)/2), (tilesInX*tileSize)/2, (tilesInY*tileSize)/2);

		for (int i = 0; i < tilesInX; i++) {
			for (int j = 0; j < tilesInY; j++) {
				mTiles[i][j] = new Tile((i*tileSize)+tileSize/2, (j*tileSize)+tileSize/2);
			}
		}

		for (int i = 0; i < mDistance; i++) {
			for (int j = 0; j < mCurrentTiles.length; j++) {
				mCurrentTiles[i][j] = mTiles[i][j];
			}
		}

		//		mTilemapLoader = new TilemapLoader(this);
		mCurPlayerTile = new int[] {0,0};
		mPlayerTile = new int[] {0,0};
		mPlayerTile = getTileIndexes(game.getWorld().getPlayer().getBounds());

	}

	public void render(float[] projMatrix) {

		mCurPlayerTile = getTileIndexes(getGame().getWorld().getPlayer().getBounds());

		if(mCurPlayerTile[0] != mPlayerTile [0] || mCurPlayerTile[1] != mPlayerTile [1]) {
			mPlayerTile = mCurPlayerTile;
			setCurrentTiles(mPlayerTile[0], mPlayerTile[1]);
		}
		
		Log.i("Tilemap", "("+mPlayerTile[0]+","+mPlayerTile[1]+")");
	}

	public Tile[][] getCurrentTiles() {
		return mCurrentTiles;
	}

	public void setCurrentTiles(int tileX, int tileY) {

		int left = tileX - mDistance/2;
		if(left<0) left = tileX;
		int right = tileX + mDistance/2;
		if(right>mTilesInX) right = tileX;

		int top = tileY - mDistance/2;
		if(top<0) top = tileY;
		int bottom = tileY + mDistance/2;
		if(bottom>mTilesInY) bottom = tileY;

		int k = 0;
		int l = 0;

		for ( int i = left; i < right; i++) {
			k++;
			for (int j = top = 0; j < bottom ; j++) {
				mCurrentTiles[k][l] = mTiles[i][j]; //TODO: obter da db
				l++;
			}
		}

	}

	public int getQuadrant(Bounds bounds){

		int qd = -1;
		float middleX = mBounds.left + (mBounds.width()/2);
		float middleY = mBounds.top + (mBounds.height()/2);

		boolean upprQ =  (bounds.getCenter().getY()+(bounds.getHeight()/2) > middleY && 
				bounds.getCenter().getY()-(bounds.getHeight()/2) > middleY); 
		boolean lwrQ = (bounds.getCenter().getY()-(bounds.getHeight()/2) < middleY && 
				bounds.getCenter().getY()+(bounds.getHeight()/2) < middleY);

		if( bounds.getCenter().getX()+(bounds.getWidth()/2) < middleX ){
			if(upprQ) qd = 1;
			else if(lwrQ) qd = 2;
		}
		if( bounds.getCenter().getX()-(bounds.getWidth()/2) > middleX ){
			if(upprQ) qd = 0;
			else if(lwrQ) qd = 3;
		}

		return qd;
	}

	//Obtem o index do tile com base nos bounds fornecidos
	public int[] getTileIndexes(Bounds bounds) {

		int[] tilesIndxs = new int[2];

		int qd = getQuadrant(bounds);

		switch (qd) {
		case 0:

			tilesIndxs[0] = (int) ( bounds.getCenter().getX() / mTileSize) + (mTilesInX/2);
			tilesIndxs[1] = (int) ( bounds.getCenter().getY() / mTileSize);

			break;
		case 1:

			tilesIndxs[0] = (int) ( bounds.getCenter().getX() / mTileSize) * -1;
			tilesIndxs[1] = (int) ( bounds.getCenter().getY() / mTileSize);

			break;
		case 2:

			tilesIndxs[0] = (int) ( bounds.getCenter().getX() / mTileSize) * -1;
			tilesIndxs[1] = (int) (( bounds.getCenter().getY() / mTileSize) + (mTilesInY/2)) * -1;

			break;
		case 3:

			tilesIndxs[0] = (int) ( bounds.getCenter().getX() / mTileSize) + (mTilesInX/2);
			tilesIndxs[1] = (int) (( bounds.getCenter().getY() / mTileSize) + (mTilesInY/2)) * -1;

			break;

		default:
			break;
		}

		return tilesIndxs;
	}


}
