package br.jp.redsparrow_zsdemo.engine.physics;

import br.jp.redsparrow_zsdemo.engine.Vector2f;

public class Collision {
	
	//Teste rapido de colisao entre dois limites
	public static boolean areIntersecting(Bounds aabb, Bounds aabb2) {
		
		if(Math.abs(aabb.getCenter().getX()-aabb2.getCenter().getX()) < (aabb.getWidth()/2 + aabb2.getWidth()/2)){
			if(Math.abs(aabb.getCenter().getY()-aabb2.getCenter().getY()) < (aabb.getHeight()/2 + aabb2.getHeight()/2)){
				return true;
			}
		}
		
		return false;
	}
	
/**
 * Determina se um ponto se encontra dentro dos limites
 */	
	public static boolean isInside(Vector2f a, Bounds b) {
		
		if(Math.abs(b.getCenter().getX() - a.getX()) < b.getWidth()/2){
			if(Math.abs(b.getCenter().getY() - a.getY()) < b.getHeight()/2){
				
				return true;
				
			}
		}
		
		return false;
		
	}
	
	//Teste de colisao usando o teorema da separacao dos eixos
	public static Vector2f getColVector(Bounds obj1, Bounds obj2) {
		float min1, max1,
			min2, max2,
			offsetx = 0, offsety = 0;
		
//		Colisoes em X
		if (obj1.getCenter().getX() < obj2.getCenter().getX())
		{
			min1 = obj1.getCenter().getX();
			max1 = obj1.getCenter().getX() + obj1.getWidth()/2;
			min2 = obj2.getCenter().getX() - obj2.getWidth()/2;
			max2 = obj2.getCenter().getX();
			offsetx = min2 - max1;

			if (min1 - max2 > 0 || min2 - max1 > 0)
				return null;
		}
		
		else if (obj1.getCenter().getX() > obj2.getCenter().getX())
		{
			min1 = obj1.getCenter().getX() - obj1.getWidth()/2;
			max1 = obj1.getCenter().getX();
			min2 = obj2.getCenter().getX();
			max2 = obj2.getCenter().getX() + obj2.getWidth()/2;
			offsetx = max2 - min1;

			if (min1 - max2 > 0 || min2 - max1 > 0)
				return null;
		}
		
		
//		Colisoes em Y
		if (obj1.getCenter().getY() < obj2.getCenter().getY())
		{
			min1 = obj1.getCenter().getY();
			max1 = obj1.getCenter().getY() + obj1.getHeight()/2;
			min2 = obj2.getCenter().getY() - obj2.getHeight()/2;
			max2 = obj2.getCenter().getY();
			offsety = min2 - max1;

			if (min1 - max2 > 0 || min2 - max1 > 0)
				return null;
			
		}
		
		else if (obj1.getCenter().getY() > obj2.getCenter().getY())
		{
			min1 = obj1.getCenter().getY() - obj1.getWidth()/2;
			max1 = obj1.getCenter().getY();
			min2 = obj2.getCenter().getY();
			max2 = obj2.getCenter().getY() + obj2.getHeight()/2;
			offsety = max2 - min1;

			if (min1 - max2 > 0 || min2 - max1 > 0)
				return null;
			
		}
		
		if (offsetx == 0 || offsety == 0)
		{

		}
		else
		{
			
			if (Math.abs(offsetx) < Math.abs(offsety))
				offsety = 0;
			else
				offsetx = 0;
		}
		
		return new Vector2f(offsetx, offsety);
	}
	
	
	
}
