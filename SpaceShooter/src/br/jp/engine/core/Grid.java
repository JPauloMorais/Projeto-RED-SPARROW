package br.jp.engine.core;

import java.util.ArrayList;
import java.util.List;

public class Grid {

	private Cell[][] grid = null;
	private List<GameObject> likelyToInteract;
	int gridRows, gridCols;
	int mCellSize;
	private int top;
	private int left;
	private int right;
	private int bottom;

	public Grid(int mapWidth, int mapHeight, int cellSize){
		mCellSize = cellSize;
		gridRows = (mapHeight + cellSize - 1) / cellSize;
		gridCols = (mapWidth + cellSize - 1) / cellSize;
		grid = new Cell[gridRows][gridCols];
//		for (int i = 0; i < gridRows; i++) {
//			for (int j = 0; j < gridCols; j++) {
//				grid[i][j] = new Cell();
//			}
//		}
		likelyToInteract = new ArrayList<GameObject>();
	}

	public void addObject(GameObject object){

		left   = (int) Math.max(0,(object.getX() / mCellSize));
		top    = (int) Math.max(0,(object.getY() / mCellSize));
		right  = (int) Math.min( gridCols-1,((object.getX() + object.getWidth() - 1) / mCellSize));
		bottom = (int) Math.min( gridRows-1,((object.getY() + object.getHeight() - 1) / mCellSize));
		
		for (int x = left; x <= right; x++)
		{
			for (int y = top; y <= bottom; y++)
			{
				if(grid[x][y]!=null){
					grid[x][y].addObject(object);
				}
			}
		}

	}

	public List<GameObject> getLikelyToInteract(GameObject object)
	{
		likelyToInteract.clear();

		int left   = (int) Math.max(0, object.getX() / mCellSize);
		int top    = (int) Math.max(0, object.getY() / mCellSize);
		int right  = (int) Math.min(gridCols-1, (object.getX() + object.getWidth() -1) / mCellSize);
		int bottom = (int) Math.min(gridRows-1, (object.getY() + object.getHeight() -1) / mCellSize);

		for (int x = left; x <= right; x++)
		{
			for (int y = top; y <= bottom; y++)
			{
				if(grid[x][y]!=null){					
					List<GameObject> cell = grid[x][y].getObjects();
					for (int i=0; i<cell.size(); i++)
					{
						GameObject rObject = cell.get(i);

						if (!likelyToInteract.contains(rObject))
							likelyToInteract.add(rObject);

					}
				}
			}
		}
		return likelyToInteract;
	}

	public GameObject getNearestObject(GameObject object)
	{

		GameObject nearest = null;
		long distance = Long.MAX_VALUE;

		List<GameObject> collidables = getLikelyToInteract(object);

		for (int i=0; i<collidables.size(); i++)
		{
			GameObject toCheck = collidables.get(i);

			long dist = (long) ((toCheck.getX()-object.getX())*
					(toCheck.getX()-object.getX()) + 
					(toCheck.getY()-object.getY())*
					(toCheck.getY()-object.getY()));
			if (dist < distance)
			{
				nearest = toCheck;
				distance = dist;
			}
		}
		return nearest;
	}

	public void clear()
	{
		for (int x=0; x<gridCols; x++)
		{
			for (int y=0; y<gridRows; y++)
			{
				grid[x][y] = new Cell();
			}
		}
	}

}
class Cell{
	private List<GameObject> mObjects;

	public Cell(){
		mObjects = new ArrayList<GameObject>();
	}
	
	public List<GameObject> getObjects() {
		return mObjects;
	}

	public void addObject(GameObject object) {
		this.mObjects.add(object);
	}
}
