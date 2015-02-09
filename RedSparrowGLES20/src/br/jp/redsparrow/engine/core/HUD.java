package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

public class HUD extends GameSystem {

	private static ArrayList<HUDitem> mHudItems;
	
	public HUD(Game game){
		super(game);
		mHudItems = new ArrayList<HUDitem>();
	}
	
	@Override
	public void loop(Game game, float[] projectionMatrix){
		
		for (HUDitem hItem : mHudItems) {
			hItem.update(game);
			hItem.render(projectionMatrix);
		}
		
	}
	
	public void addItem(HUDitem item) {
		mHudItems.add(item);
	}

	public HUDitem getItem(int indx) {
		return mHudItems.get(indx);
	}


}
