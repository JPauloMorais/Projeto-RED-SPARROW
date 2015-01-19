package br.jp.redsparrow.engine.core.physics;

import br.jp.redsparrow.engine.core.Vector2f;

public class Collision {
	
//	public static boolean areIntersecting(RectF a, RectF b){
//		return false;
//		//left
//		obj1.getCenter().getX() - obj1.getWidth()/2;
//		//top
//		obj1.getCenter().getY() - obj1.getHeight()/2;
//		//right
//		obj1.getCenter().getX() + obj1.getWidth()/2;
//		//bottom
//		obj1.getCenter().getY() + obj1.getHeight()/2;
//	}
	
//	public static boolean areIntersecting(BCircle a, BCircle b){
//		
//		// Calculate squared distance between centers 
//		Vector2f d = a.getCenter().sub(b.getCenter()); 
//		float dist2 = d.dot(d); 
//		// Spheres intersect if squared distance is less than squared sum of radii 
//		float radiusSum = a.getWidth() + b.getWidth(); 
//		return (dist2 <= radiusSum * radiusSum);
//		
//	}
	
	public static boolean areIntersecting(AABB aabb, AABB aabb2) {
		
		if(Math.abs(aabb.getCenter().getX()-aabb2.getCenter().getX()) < (aabb.getWidth()/2 + aabb2.getWidth()/2)){
			if(Math.abs(aabb.getCenter().getY()-aabb2.getCenter().getY()) < (aabb.getHeight()/2 + aabb2.getHeight()/2)){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isInside(Vector2f a, AABB b) {
		
		if(Math.abs(b.getCenter().getX() - a.getX()) < b.getWidth()/2){
			if(Math.abs(b.getCenter().getY() - a.getY()) < b.getHeight()/2){
				
				return true;
				
			}
		}
		
		return false;
		
	}
}
