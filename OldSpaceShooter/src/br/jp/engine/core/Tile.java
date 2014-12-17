package br.jp.engine.core;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Tile {

	private Rect src, dst;
	private Bitmap image;
	private boolean isOnScreen;
	
	public Tile(int x, int y, int tileWidth, int tileHeight) {
		
		src = new Rect(0, 0, 32, 32);
		dst = new Rect(x, y, x+tileWidth, y+tileHeight);
		
	}

	public int getX() {
		return dst.left;
	}

	public int getY() {
		return dst.top;
	}

	public Rect getSrc() {
		return src;
	}

	public void setSrc(Rect src) {
		this.src = src;
	}

	public Rect getDst() {
		return dst;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public boolean isOnScreen() {
		return isOnScreen;
	}

	public void setOnScreen(boolean isOnScreen) {
		this.isOnScreen = isOnScreen;
	}
	
}
