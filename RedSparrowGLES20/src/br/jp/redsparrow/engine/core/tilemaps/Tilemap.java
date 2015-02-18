package br.jp.redsparrow.engine.core.tilemaps;

import android.graphics.RectF;
import android.util.Log;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.game.GameSystem;
import br.jp.redsparrow.engine.core.physics.Bounds;

public class Tilemap extends GameSystem{

	/**
	 * Mapas compostos por tiles, com chunks relativos a um centro
	 * ()
	 */
	private static final String TAG = "Tilemap";
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

	private int[] mCurCenterTile;
	private int[] mCenterTile;
	private int mCurQuadrant;
	private int mQuadrant;

	public Tilemap(Game game, int tilesInX, int tilesInY, float tileSize) {
		super(game);

		mDistance = DISTANCE_NEAR;
		mTilesInX = tilesInX;
		mTilesInY = tilesInY;
		mTileSize = tileSize;

		mCurrentTiles = new Tile[mDistance][mDistance];
		mTiles = new Tile[tilesInX][tilesInY];

		mBounds = new RectF(((tilesInX*tileSize)/2)*-1, ((tilesInY*tileSize)/2), (tilesInX*tileSize)/2, (tilesInY*tileSize)/2 * -1);

		float firstX = mBounds.left + (tileSize/2);
		float firstY = mBounds.top - (tileSize/2);
		
		for (int i = 0; i < tilesInX; i++) {
			for (int j = 0; j < tilesInY; j++) {
				mTiles[i][j] = 
						new Tile( firstX + (i*tileSize), firstY - (j*tileSize));
			}
		}

		//		mTilemapLoader = new TilemapLoader(this);
		mCurCenterTile = new int[] {0,0};
		mCenterTile = getTileIndexes(game.getWorld().getPlayer().getBounds());
		mCurQuadrant = getQuadrant(game.getWorld().getPlayer().getBounds());
		mQuadrant = mCurQuadrant;

	}
	
	@Override
	public void loop(Game game, float[] projectionMatrix) {
		mCurCenterTile = getTileIndexes(getGame().getWorld().getPlayer().getBounds());

		if(mCurCenterTile[0] != mCenterTile [0] || mCurCenterTile[1] != mCenterTile [1]) {
			mCenterTile = mCurCenterTile;
			setCurrentTiles(mCenterTile[0], mCenterTile[1]);
		}
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
			for (int j = top; j < bottom ; j++) {
				mCurrentTiles[k][l] = mTiles[i][j]; //TODO: obter da db
				l++;
			}
			k++;
			l=0;
		}

	}

	public int getQuadrant(Bounds bounds){

		mCurQuadrant = -1;
		float middleX = mBounds.left + (mBounds.width()/2);
		float middleY = mBounds.top + (mBounds.height()/2);

		boolean upprQ =  (bounds.getCenter().getY()+(bounds.getHeight()/2) > middleY && 
				bounds.getCenter().getY()-(bounds.getHeight()/2) > middleY); 
		boolean lwrQ = (bounds.getCenter().getY()-(bounds.getHeight()/2) < middleY && 
				bounds.getCenter().getY()+(bounds.getHeight()/2) < middleY);

		if( bounds.getCenter().getX()+(bounds.getWidth()/2) < middleX ){
			if(upprQ) mCurQuadrant = 1;
			else if(lwrQ) mCurQuadrant = 2;
		}
		if( bounds.getCenter().getX()-(bounds.getWidth()/2) > middleX ){
			if(upprQ) mCurQuadrant = 0;
			else if(lwrQ) mCurQuadrant = 3;
		}
		
		if(mCurQuadrant==-1) mCurQuadrant = mQuadrant;
		else mQuadrant = mCurQuadrant;

		return mCurQuadrant;
	}

	//Obtem o index do tile com base nos bounds fornecidos
	public int[] getTileIndexes(Bounds bounds) {

		int[] tilesIndxs = mCurCenterTile;

		switch (getQuadrant(bounds)) {
		case 0:

			tilesIndxs[0] = (int) ( bounds.getCenter().getX() / mTileSize) + (mTilesInX/2);                        //right
			tilesIndxs[1] = ( (mTilesInY/2)-1) - (int) ( bounds.getCenter().getY() / mTileSize);                //top
			Log.i( TAG,"QD: "+0+", ("+tilesIndxs[0]+","+tilesIndxs[1]+")");

			break;
		case 1:

			tilesIndxs[0] = ( (mTilesInX/2)-1) - (int) Math.abs(( bounds.getCenter().getX() / mTileSize)); //left
			tilesIndxs[1] = ( ( (mTilesInY/2)-1) - (int) ( bounds.getCenter().getY() / mTileSize));                //top
			Log.i( TAG,"QD: "+1+", ("+tilesIndxs[0]+","+tilesIndxs[1]+")");

			break;
		case 2:

			tilesIndxs[0] = ( ( (mTilesInX/2)-1) - (int) Math.abs(( bounds.getCenter().getX() / mTileSize))); //left
			tilesIndxs[1] = (int) (Math.abs( bounds.getCenter().getY() / mTileSize) + (mTilesInY/2));          //bottom
			Log.i( TAG,"QD: "+2+", ("+tilesIndxs[0]+","+tilesIndxs[1]+")");
			
			break;
		case 3:

			tilesIndxs[0] = (int) ( bounds.getCenter().getX() / mTileSize) + (mTilesInX/2);                       //right
			tilesIndxs[1] = (int) (Math.abs( bounds.getCenter().getY() / mTileSize) + (mTilesInY/2));        //bottom
			Log.i( TAG,"QD: "+3+", ("+tilesIndxs[0]+","+tilesIndxs[1]+")");

			break;

		default:
			break;
		}


		return tilesIndxs;
	}

	public Tile[][] getTiles() {
		return mTiles;
	}

	public void setTiles(Tile[][] mTiles) {
		this.mTiles = mTiles;
	}

}
