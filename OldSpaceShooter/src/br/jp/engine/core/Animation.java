package br.jp.engine.core;

import android.graphics.Rect;

public class Animation {

	public Rect src,dst;

	private int nCols, nRows;
	private int wFrame, hFrame;
	private int curRow, curCol;
	private int lastDir;
	private int wait, ammoToWait;
	private boolean loopedOnce;

	public Animation(int nCols, int nRows, int wFrame, int hFrame) {

		this.nCols = nCols;
		this.nRows = nRows;
		this.wFrame = wFrame;
		this.hFrame = hFrame;
		curCol = 0;
		curRow = 0;

		src = new Rect(0, 0, wFrame, hFrame);
		dst = new Rect(0, 0, 0, 0);

		wait = 0;
		ammoToWait = 5;
		loopedOnce = false;
	}

	//Considera que SpriteSheet contem uma direçao por linha de frames
	public void update(int curDir, int x, int y, int dstX, int dstY){
		if(wait<ammoToWait && curDir==lastDir) wait++;
		else {
			if(nRows==4){
				switch (curDir) {
				case 0:
					curRow=2;
					curCol++;
					if (curCol>=nCols) {
						curCol=0;
						loopedOnce = true;
					}
					break;
				case 1:
					curRow=0;
					curCol++;
					if (curCol>=nCols) {
						curCol=0;
						loopedOnce = true;
					}
					break;
				case 2:
					curRow=1;
					curCol++;
					if (curCol>=nCols) {
						curCol=0;
						loopedOnce = true;
					}
					break;
				case 3:
					curRow=3;
					curCol++;
					if (curCol>=nCols) {
						curCol=0;
						loopedOnce = true;
					}
					break;

				default:
					break;
				}
			} else if(nRows==1){
				curRow=0;
				curCol++;
				if (curCol>=nCols) {
					curCol=0;
					loopedOnce = true;
				}
			}
			wait=0;
		}


		lastDir = curDir;
		
		int l = curCol*wFrame;
		int t = curRow*hFrame;
		int r = l+wFrame;
		int b = t+hFrame;

		src = new Rect(l, t, r, b);
		dst = new Rect( x, y, dstX,dstY);
	}

	//Para spriteSheets sem multiplas direcoes
	public void update(int x, int y, int dstX, int dstY){

		if(wait>ammoToWait){
			curCol++;
			if (curCol>=nCols) {
				curCol=0;
				curRow++;
				if (curRow>=nRows) {
					curRow=0;
					loopedOnce = true;
				}
			}
			wait=0;
		}else wait++;

		int l = curCol*wFrame;
		int t = curRow*hFrame;
		int r = l+wFrame;
		int b = t+hFrame;

		src = new Rect(l, t, r, b);
		dst = new Rect( x, y, dstX, dstY);
	}

	public int getWait() {
		return wait;
	}

	public void setWait(int wait) {
		this.wait = wait;
	}

	public int getAmmoToWait() {
		return ammoToWait;
	}

	public void setAmmoToWait(int ammoToWait) {
		this.ammoToWait = ammoToWait;
	}

	public boolean hasLoopedOnce() {
		return loopedOnce;
	}

}
