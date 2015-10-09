package br.jp.redsparrow_zsdemo.engine;

import java.util.ArrayList;

import android.graphics.RectF;
import br.jp.redsparrow_zsdemo.engine.physics.Bounds;

public class Quadtree {

	/*
	 * Estrutura de dados para auxiliar na broad phase da deteccao de colisao
	 * */
	public static final String TAG = "Quadtree";

	private int MAX_OBJS = 10;
	private int MAX_LVLS = 10;

	private int mLevel;
	private Quadtree[] mNodes;
	private ArrayList<GameObject> mObjects;
	private RectF mBounds;

	public Quadtree(int level, RectF bounds) {

		mLevel = level;
		mBounds = bounds;		
		mObjects = new ArrayList<GameObject>();
		mNodes = new Quadtree[4];

	}

	private void split(){

		float lWidth = mBounds.width()/2;
		float lHeight = mBounds.height()/2;
		float lX = mBounds.left;
		float lY = mBounds.top;

		mNodes[0] = new Quadtree(mLevel++, new RectF( lX+lWidth, lY        , 2*lWidth, lHeight));
		mNodes[1] = new Quadtree(mLevel++, new RectF( lX       , lY        , lWidth  , lHeight));
		mNodes[2] = new Quadtree(mLevel++, new RectF( lX       , lY+lHeight, lWidth, 2*lHeight));
		mNodes[3] = new Quadtree(mLevel++, new RectF( lX+lWidth, lY+lHeight, 2*lWidth, 2*lHeight));

	}

	public void add(GameObject obj){

		if(mNodes[0] != null) {
			int indx = getQuadrant(obj.getBounds());
			if(indx != -1){
				mNodes[indx].add(obj);
			}
		}

		mObjects.add(obj);

		if(mObjects.size() > MAX_OBJS && mLevel < MAX_LVLS){
			if(mNodes[0]==null) split();

			for (int i = 0; i < mObjects.size(); i++) {
				int indx = getQuadrant(mObjects.get(i).getBounds());
				if(indx != -1) mNodes[indx].add(mObjects.remove(i));
			}
		}

	}

	public ArrayList<GameObject> getToCheck(ArrayList<GameObject> toCheck, Bounds objBounds){

		int qd = getQuadrant(objBounds);
		if(qd != -1 && mNodes[0] != null) mNodes[qd].getToCheck(toCheck, objBounds);

		toCheck.addAll(mObjects);

		return toCheck;
	}

	public int getQuadrant(Bounds bounds){

		int qd = -1;
		float middleX = mBounds.left + (mBounds.width()/2);
		float middleY = mBounds.top + (mBounds.height()/2);

		boolean upprQ =  (bounds.getCenter().getY()+(bounds.getHeight()/2) > middleY && 
				bounds.getCenter().getY()-(bounds.getHeight()/2) > middleY); 
		boolean lwrQ = (bounds.getCenter().getY()-(bounds.getHeight()/2) < middleY && 
				bounds.getCenter().getY()+(bounds.getHeight()/2) < middleY);

		if( bounds.getCenter().getX()+(bounds.getWidth()/2) < middleX ){
			if(upprQ) qd = 1;
			else if(lwrQ) qd = 2;
		}
		if( bounds.getCenter().getX()-(bounds.getWidth()/2) > middleX ){
			if(upprQ) qd = 0;
			else if(lwrQ) qd = 3;
		}

		return qd;
	}

	public void clear(){

		mObjects.clear();

		for (int i = 0; i < mNodes.length; i++) {
			if(mNodes[i] != null){
				mNodes[i].clear();
				mNodes[i] = null;
			}
		}
	}

}
