package br.jp.redsparrow.engine.core;

import java.util.Random;

import android.content.Context;
import android.opengl.Matrix;

public class TiledBackground {

	private static Tile[][] mTilesLayer1;
	private static Tile[][] mTilesLayer2;

	private static int mWidthInTiles;
	private static int mHeightInTiles;
	private static float mTileSize;

	public static void init(final Context context, int widthInTiles, int heightInTiles, float tileSize, final int ... src){

		mWidthInTiles = widthInTiles;
		mHeightInTiles = heightInTiles;
		mTileSize = tileSize;

		mTilesLayer1 = new Tile[widthInTiles][heightInTiles];
		mTilesLayer2 = new Tile[widthInTiles][heightInTiles];

		Random r = new Random();

		for (int i = 0; i < widthInTiles; i++) {
			for (int j = 0; j < heightInTiles; j++) {
				mTilesLayer1[i][j] = new Tile(context, src[r.nextInt(src.length)], (tileSize/2)*i, (tileSize/2)*j, -1, tileSize);
				mTilesLayer2[i][j] = new Tile(context, src[r.nextInt(src.length)], (tileSize/2)*i, (tileSize/2)*j, -1, tileSize);
			}
		}


	}

	public static void render(float[] projectionMatrix) {

		for (int i = 0; i < mWidthInTiles; i++) {
			for (int j = 0; j < mHeightInTiles; j++) {
				if(
						mTilesLayer1[i][j].getPosition().getX() < World.getPlayer().getPosition().getX()+World.getRenderingRange().getX()+mTileSize/2 &&
						mTilesLayer1[i][j].getPosition().getX() > World.getPlayer().getPosition().getX()-World.getRenderingRange().getX()-mTileSize/2 &&
						mTilesLayer1[i][j].getPosition().getY() < World.getPlayer().getPosition().getY()+World.getRenderingRange().getY()+mTileSize/2 &&
						mTilesLayer1[i][j].getPosition().getY() > World.getPlayer().getPosition().getY()-World.getRenderingRange().getY()-mTileSize/2
						){

					mTilesLayer1[i][j].render(null, projectionMatrix);

				}
			}
		}
		
		Matrix.translateM(projectionMatrix, 0, 0, 0, 5f);
		
		for (int i = 0; i < mWidthInTiles; i++) {
			for (int j = 0; j < mHeightInTiles; j++) {
				if(
						mTilesLayer1[i][j].getPosition().getX() < World.getPlayer().getPosition().getX()+World.getRenderingRange().getX()+mTileSize &&
						mTilesLayer1[i][j].getPosition().getX() > World.getPlayer().getPosition().getX()-World.getRenderingRange().getX()-mTileSize &&
						mTilesLayer1[i][j].getPosition().getY() < World.getPlayer().getPosition().getY()+World.getRenderingRange().getY()+mTileSize &&
						mTilesLayer1[i][j].getPosition().getY() > World.getPlayer().getPosition().getY()-World.getRenderingRange().getY()-mTileSize
						){

					mTilesLayer2[i][j].render(null, projectionMatrix);

				}
			}
		}
		
		Matrix.translateM(projectionMatrix, 0, 0, 0, 15f);

	}

}
