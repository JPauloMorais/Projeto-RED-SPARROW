package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

public class HUD {

	private static ArrayList<HUDitem> mHudItems;
	
	public HUD(){
		mHudItems = new ArrayList<HUDitem>();
	}
	
	public void loop(float[] projectionMatrix){
		
		for (HUDitem hItem : mHudItems) {
			hItem.update();
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
