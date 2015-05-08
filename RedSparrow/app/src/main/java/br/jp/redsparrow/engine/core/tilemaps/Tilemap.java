package br.jp.redsparrow.engine.core.tilemaps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.RectF;
import android.opengl.Matrix;
import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.game.GameSystem;
import br.jp.redsparrow.engine.core.physics.Bounds;
import br.jp.redsparrow.engine.core.util.TextFileReader;

public class Tilemap extends GameSystem{

	/**
	 * Mapas compostos por tiles, com chunks relativos a um centro
	 * ()
	 */
	private static final String TAG = "Tilemap";

	private JSONObject mSource;
	private JSONArray tileArray;


	//TODO: distancias baseadas no tamanho do player e tamanho dos tiles
	public static final int DISTANCE_NEAR = 3;
	public static final int DISTANCE_MID = 5;
	public static final int DISTANCE_FAR = 7;

	private int mDistance;
	private float mTileSize;
	private int mTilesInX;
	private int mTilesInY;
	private RectF mBounds;
	
	private volatile Tile[][] mCurrentTiles;

	private int[] mCurCenterTile;
	private int[] mCenterTile;
	private int mCurQuadrant;
	private int mQuadrant;
	
	private final int[] mTileTextures;
	
	public Tilemap( int fileResID, Game game, int ... tileTextures) {
		super(game);
		
		mTileTextures = tileTextures;
		
		try {

			mSource = new JSONObject(TextFileReader.readTextFromFile(game.getContext(), fileResID));
			tileArray = mSource.getJSONArray("textures");

			create(mSource.getInt("tilesInX"), mSource.getInt("tilesInY"), mSource.getInt("tileSize"));

		} catch (Exception e) {
			e.printStackTrace();

			create(2, 2, 200);
		}

	}

	public Tilemap(Game game, 
			int tilesInX, int tilesInY, float tileSize,
			int ... tileTextures) {
		super(game);
		
		mTileTextures = tileTextures;
		create(tilesInX, tilesInY, tileSize);

	}

	private void create(int tilesInX, int tilesInY, float tileSize) {

		mDistance = DISTANCE_NEAR;
		mTilesInX = tilesInX;
		mTilesInY = tilesInY;
		mTileSize = tileSize;

		mCurrentTiles = new Tile[mDistance][mDistance];

		mBounds = new RectF(((tilesInX*tileSize)/2)*-1, ((tilesInY*tileSize)/2), (tilesInX*tileSize)/2, (tilesInY*tileSize)/2 * -1);

		mCurCenterTile = new int[2];
		mCenterTile = new int[]{mCurCenterTile[0], mCurCenterTile[1]};
		mCurQuadrant = getQuadrant(game.getWorld().getPlayer().getBounds());
		mQuadrant = mCurQuadrant;
		
		setCurrentTiles(mCurCenterTile[0], mCurCenterTile[1]);

	}

	@Override
	public void loop(Game game, float[] projectionMatrix) {
		
		setCurrentCenterTile(getGame().getWorld().getPlayer().getBounds());

		if(mCurCenterTile[0] != mCenterTile [0] || 
				mCurCenterTile[1] != mCenterTile [1]) {
			
			mCenterTile = new int[]{mCurCenterTile[0], mCurCenterTile[1]};
			setCurrentTiles(mCurCenterTile[0], mCurCenterTile[1]);
			
		}

		Matrix.translateM(projectionMatrix, 0, 0, 0, -5);
		for (int i = 0; i < mDistance; i++) {
			for (int j = 0; j < mDistance; j++) {
					mCurrentTiles[i][j].render(game, projectionMatrix);
			}
		}
		Matrix.translateM(projectionMatrix, 0, 0, 0, 5);
	}

	public Tile[][] getCurrentTiles() {
		return mCurrentTiles;
	}

	int left;
	int right;
	int top;
	int bottom;
	int lx;
	int ly;
	int tileFileLoc;
	
	public synchronized void setCurrentTiles(int tileX, int tileY) {

		left = tileX - mDistance/2;
//		if(left<0) left = tileX;
		right = tileX + mDistance/2;
//		if(right>mTilesInX) right = tileX;

		top = tileY - mDistance/2;
//		if(top<0) top = tileY;
		bottom = tileY + mDistance/2;
//		if(bottom>mTilesInY) bottom = tileY;


		ly = 0;
		lx = 0;
		//localizacao do index da textura no array de tiles do json
		tileFileLoc = mTilesInX * (top ) - (mTilesInX - (left ));
		Log.i(TAG, "left: " + left + 
				";right: " + right +
				";top: " + top +
				";bottom: " + bottom);
		Log.i("Tilemap", "CHANGE!! - TileFileLoc Top Left: " + tileFileLoc);


		try {
			
		for (int y = top; y <= bottom ; y++){
			
			for ( int x = left; x <= right; x++) {
				
				mCurrentTiles[lx][ly] = new Tile( this,
						(x*mTileSize)+mTileSize/2, (y*mTileSize)+mTileSize/2,
						(tileArray.getInt(tileFileLoc) <= mTileTextures.length ? mTileTextures[tileArray.getInt(tileFileLoc)] : R.drawable.stars_test1));

				lx++;
				tileFileLoc++;
			}
			ly++;
			lx=0;
		}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		//		Log.i(TAG, mCurrentTiles[0][0].getT()+mCurrentTiles[0][1].getT()+mCurrentTiles[0][2].getT()+"/n"+
		//				mCurrentTiles[1][0].getT()+mCurrentTiles[1][1].getT()+mCurrentTiles[1][2].getT()+"/n"+
		//				mCurrentTiles[2][0].getT()+mCurrentTiles[2][1].getT()+mCurrentTiles[2][2].getT());
	}

	float middleX;
	float middleY;
	boolean upprQ;
	boolean lwrQ;
	
	public int getQuadrant(Bounds bounds){

		mCurQuadrant = -1;
		middleX = mBounds.left + (mBounds.width()/2);
		middleY = mBounds.top + (mBounds.height()/2);

		upprQ =  (bounds.getCenter().getY()+(bounds.getHeight()/2) > middleY && 
				bounds.getCenter().getY()-(bounds.getHeight()/2) > middleY); 
		lwrQ = (bounds.getCenter().getY()-(bounds.getHeight()/2) < middleY && 
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

	//Atribui valores aos index do tile central atual
	public void setCurrentCenterTile(Bounds bounds) {

		switch (getQuadrant(bounds)) {
		case 0:

			mCurCenterTile[0] = (int) ( bounds.getCenter().getX() / mTileSize) + (mTilesInX/2);                        //right
			mCurCenterTile[1] = ( (mTilesInY/2)-1) - (int) ( bounds.getCenter().getY() / mTileSize);                //top
			Log.i( TAG,"QD: "+0+", ("+mCurCenterTile[0]+","+mCurCenterTile[1]+")" );

			break;
		case 1:

			mCurCenterTile[0] = ( (mTilesInX/2)-1) - (int) Math.abs(( bounds.getCenter().getX() / mTileSize)); //left
			mCurCenterTile[1] = ( ( (mTilesInY/2)-1) - (int) ( bounds.getCenter().getY() / mTileSize));                //top
			Log.i( TAG,"QD: "+1+", ("+mCurCenterTile[0]+","+mCurCenterTile[1]+")");

			break;
		case 2:

			mCurCenterTile[0] = ( ( (mTilesInX/2)-1) - (int) Math.abs(( bounds.getCenter().getX() / mTileSize))); //left
			mCurCenterTile[1] = (int) (Math.abs( bounds.getCenter().getY() / mTileSize) + (mTilesInY/2));          //bottom
			Log.i( TAG,"QD: "+2+", ("+mCurCenterTile[0]+","+mCurCenterTile[1]+")");

			break;
		case 3:

			mCurCenterTile[0] = (int) ( bounds.getCenter().getX() / mTileSize) + (mTilesInX/2);                       //right
			mCurCenterTile[1] = (int) (Math.abs( bounds.getCenter().getY() / mTileSize) + (mTilesInY/2));        //bottom
			Log.i( TAG,"QD: "+3+", ("+mCurCenterTile[0]+","+mCurCenterTile[1]+")");

			break;

		default:
			break;
		}

	}

	public float getTileSize() {
		return mTileSize;
	}

}
