package br.jp.redsparrow.engine.core;

import android.graphics.RectF;

public class Collision {
	
	public static float FRIC = 0.01f;
	
	public static boolean areIntersecting(RectF obj1, RectF obj2){
		return obj1.intersect(obj2);
	}
	
}
