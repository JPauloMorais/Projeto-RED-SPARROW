package br.jp.redsparrow.engine.core;

import android.graphics.RectF;



public class Collision {
	public static boolean areIntersecting(RectF obj1, RectF obj2){
		return RectF.intersects(obj1, obj2);
	}
}
