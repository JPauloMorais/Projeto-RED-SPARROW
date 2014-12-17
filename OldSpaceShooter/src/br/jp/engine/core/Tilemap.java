package br.jp.engine.core;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import br.jp.engine.core.attributes.Renderable;
import br.jp.engine.core.attributes.Updatable;

public class Tilemap implements Updatable,Renderable{

	private List<Tile> tiles;
	private List<Tile> visibleTiles;
	private GameObject relativeTo;

	public Tilemap(int tilesInX, int tilesInY, int tileWidth, int tileHeight, Bitmap image){

		tiles = new ArrayList<Tile>();
		visibleTiles = new ArrayList<Tile>();

		List<Tile> tmpList = new ArrayList<Tile>();
		for (int i = 0; i < tilesInY; i++) {
			for (int j = 0; j < tilesInX; j++) {
				Tile temp = new Tile(tileWidth*j, tileHeight*i, tileWidth, tileHeight);
				temp.setImage(image);
				tmpList.add(temp);
			}
		}
		tiles.addAll(tmpList);

	}

	@Override
	public void update(Canvas canvas) {
		setVisibleTiles(canvas);
	}

	@Override
	public void draw(Canvas canvas) {
		for (Tile tile : visibleTiles) {
			canvas.drawBitmap( tile.getImage(), tile.getSrc(), tile.getDst(), null);
		}
	}

	private void setVisibleTiles(Canvas canvas) {
		//limpa lista anterior de tiles visiveis
		visibleTiles.removeAll(visibleTiles);
		//adiciona tiles visiveis no momento
		List<Tile> tmpTilesList = new ArrayList<Tile>();
		for (int i = 0; i < tiles.size(); i++) {
			if(relativeTo != null) {
				
				if(tiles.get(i).getX()<relativeTo.getObjX() + canvas.getWidth()/2 &&
						tiles.get(i).getY()<relativeTo.getObjY() + canvas.getHeight()/2) tmpTilesList.add(tiles.get(i));
			
			}else if(tiles.get(i).getX()<canvas.getWidth() &&
					tiles.get(i).getY()<canvas.getHeight()) tmpTilesList.add(tiles.get(i));
		}
		visibleTiles.addAll(tmpTilesList);
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setPoints(List<Tile> points) {
		this.tiles = points;
	}

	public GameObject getRelativeTo() {
		return relativeTo;
	}

	public void setRelativeTo(GameObject relativeTo) {
		this.relativeTo = relativeTo;
	}

}
