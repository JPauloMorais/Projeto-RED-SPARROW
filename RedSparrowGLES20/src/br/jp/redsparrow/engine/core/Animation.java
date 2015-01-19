package br.jp.redsparrow.engine.core;



public class Animation {

	private float[] mUVs;

	private int nCols, nRows;
	private int nFramesX, nFramesY;
	private float wFrame, hFrame;
	private int curRow, curCol;
	private int lastDir;
	private int wait, ammoToWait;
	private boolean loopedOnce;

	public Animation(int nCols, int nRows) {

		this.nCols = nCols;
		setnFramesX(nCols);
		this.nRows = nRows;
		setnFramesY(nRows);
		wFrame = (float) 1/nCols;
		hFrame = (float) 1/nRows;
		curCol = 0;
		curRow = 0;

		mUVs = new float[4];
		mUVs[0] = 0;
		mUVs[1] = 0;
		mUVs[2] = wFrame;
		mUVs[3] = hFrame;

		wait = 0;
		ammoToWait = 0;
		loopedOnce = false;
		
		update();
	}

	//Considera que SpriteSheet contem uma direçao por linha de frames
	public void update(int curDir){
		
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
		
		mUVs[0] = curCol*wFrame;
		mUVs[1] = curRow*hFrame;
		mUVs[2] = mUVs[0]+wFrame;
		mUVs[3] = mUVs[1]+hFrame;

	}

	//Para spriteSheets sem multiplas direcoes
	public void update(){

		if(wait>ammoToWait){
			curCol++;
			if (curCol>=nCols || curCol >= nFramesX) {
				curCol=0;
				curRow++;
				if (curRow>=nRows || curRow >= nFramesY) {
					curRow=0;
					loopedOnce = true;
				}
			}
			wait=0;
		}else wait++;

		mUVs[0] = curCol*wFrame; //left
		mUVs[1] = curRow*hFrame; //top
		mUVs[2] = mUVs[0]+wFrame;//right
		mUVs[3] = mUVs[1]+hFrame;//bottom

	}
	
	public int getFrameCount(){
		return nRows*nCols;
	}

	public float[] getUVs() {
		return mUVs;
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

	public void setnFramesX(int nFramesX) {
		this.nFramesX = nFramesX;
	}

	public void setnFramesY(int nFramesY) {
		this.nFramesY = nFramesY;
	}

}
