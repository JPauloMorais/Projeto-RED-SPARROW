package br.jp.engine.core;

import br.jp.spaceshooter.Projectile;
import android.graphics.Rect;





public class Collision {

	public static void checkIfColliding(GameObject g1, GameObject g2){
		try {
			if (g1.getColLayer()==g2.getColLayer() && g1.getId() != g2.getId() &&
					g1.isOnScreen() && g2.isOnScreen()) {

				if(Rect.intersects(g1.getBounds(),g2.getBounds())){
					g1.isColliding = true;
					g2.isColliding = true;
					g1.onCollisionTrue(g2);
					g2.onCollisionTrue(g1);
				}else{
					g1.isColliding = false;
					g2.isColliding = false;
					g1.onCollisionFalse();
					g2.onCollisionFalse();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof java.util.ConcurrentModificationException) {
				checkIfColliding(g1, g2);
			}
		}

	}

	public static boolean IsColliding(GameObject g1, GameObject g2){
		if (g1.getColLayer()==g2.getColLayer() && 
				g1.getId() != g2.getId() &&
				g1.isOnScreen() && 
				g2.isOnScreen() &&
				Rect.intersects(g1.getBounds(),g2.getBounds())) {

			if(g1.getCollsWith() != null &&
					g1.getCollsWith().getClass() == g2.getClass()) return true;
			else return false;
		}
		else return false;
	}

	public static boolean IsColliding(GameObject g1, Projectile proj){
		if (g1.getColLayer()==proj.getColLayer() && 
				g1.isOnScreen() && 
				proj.isOnScreen() &&
				g1.getClass() != proj.getShooter().getClass() &&
				g1.getClass() == proj.getTarget().getClass() &&
				Rect.intersects(g1.getBounds(),proj.getBounds())) return true;
		else return false;
	}

}
