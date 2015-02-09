package br.jp.redsparrow.engine.core.tilemaps;



public class TilemapLoader implements Runnable {

	private Thread mThread;
	private boolean isRunning;
	
	@SuppressWarnings("unused")
	private Tilemap mTilemap;
//	private int mCurPlayerTile;
//	private int mPlayerTile;
	
	public TilemapLoader(Tilemap tilemap) {

//		mCurPlayerTile = World.getPlayer().
		mThread = new Thread(this, "TilemapLoader");
		
		mTilemap = tilemap;
		
	}
	
	public void start() {
		isRunning = true;
		mThread.start();
	}

	@Override
	public void run() {

		while (isRunning) {
			
//			Tilemap.setCurrentTiles(, top);
			
		}
		
	}
	
	public void stop() {
		isRunning = false;
	}
	

}
