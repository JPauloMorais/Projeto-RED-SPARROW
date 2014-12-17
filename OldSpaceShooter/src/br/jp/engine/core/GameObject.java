package br.jp.engine.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;


public abstract class GameObject extends View {

	private Bitmap image;
	private int x, y,pX,pY;
	protected final int WIDTH, HEIGHT;
	public int clickEffect = 1;
	private int colLayer;
	public boolean isColliding;
	private boolean isOnScreen;
	private GameObject collsWith;
	private GameObject shooter, target; 
	private World worldItBelongs;

	public GameObject(Context context, int w, int h) {
		this(context,w,h,0);
	}
	public GameObject(Context context, int w, int h, int colLayer) {
		super(context);
		this.x = 0;
		this.y = 0;
		this.WIDTH = w;
		this.HEIGHT = h;
		this.setColLayer(colLayer);
		this.isColliding = false;
		this.setOnScreen(false);
	}

	protected abstract void onCollisionTrue(GameObject collidingWith);
	protected abstract void onCollisionFalse();

	public abstract Rect getBounds();

	public int getWIDTH() {
		return WIDTH;
	}
	public int getHEIGHT() {
		return HEIGHT;
	}
	public int getObjX() {
		return x;
	}
	public int getObjY() {
		return y;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public int getColLayer() {
		return colLayer;
	}
	public void setColLayer(int colLayer) {
		this.colLayer = colLayer;
	}
	public boolean isOnScreen() {
		return isOnScreen;
	}
	public void setOnScreen(boolean isOnScreen) {
		try {
			this.isOnScreen = isOnScreen;
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof java.util.ConcurrentModificationException){
				
				setOnScreen(isOnScreen);
			}
		}
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getpX() {
		return pX;
	}
	public void setpX(int pX) {
		try {
			this.pX = pX;			
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof java.util.ConcurrentModificationException) {
				
				setpX(pX);
			}
		}
	}
	public int getpY() {
		return pY;
	}
	public void setpY(int pY) {
		try {
			this.pY = pY;			
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof java.util.ConcurrentModificationException) {
								
				setpY(pY);			
			}
		}
	}
	public GameObject getShooter() {
		return shooter;
	}
	public void setShooter(GameObject shooter) {
		this.shooter = shooter;
	}
	public GameObject getTarget() {
		return target;
	}
	public void setTarget(GameObject target) {
		this.target = target;
	}
	public World getWorldItBelongs() {
		return worldItBelongs;
	}
	public void setWorldItBelongs(World worldItBelongs) {
		try {
			this.worldItBelongs = worldItBelongs;
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof java.util.ConcurrentModificationException) {
				
				setWorldItBelongs(worldItBelongs);
			}
		}
	}
	public GameObject getCollsWith() {
		return collsWith;
	}
	public void setCollsWith(GameObject collsWith) {
		this.collsWith = collsWith;
	}

}
