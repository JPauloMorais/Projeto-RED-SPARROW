package br.jp.engine.core;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.View;
import br.jp.engine.core.attributes.Renderable;
import br.jp.engine.core.attributes.Updatable;
import br.jp.spaceshooter.Projectile;

public class World implements Updatable, Renderable {

	private List<Entity> entities;
	private List<Projectile> projectiles;
	private Tilemap map;

	@SuppressLint("NewApi")
	public World(List<Entity> entities, Tilemap map){
		this.entities = entities;
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).setId(View.generateViewId());
			entities.get(i).setWorldItBelongs(this);
		}
		if(map!=null) this.map = map;
		projectiles = new ArrayList<Projectile>();
	}

	public World(List<Entity> entities) {
		this(entities,null);
	}

	@Override
	public void update(Canvas canvas) {
		try {

			if(map!=null) map.update(canvas);

			for (int i = 0; i < entities.size(); i++) {

				//////////Checando colisao com outras entidades///////
				for (Entity entity2 : entities){
					entities.get(i).isColliding = Collision.IsColliding(entities.get(i), entity2);
					entity2.isColliding = Collision.IsColliding(entity2, entities.get(i));
				}

				if(entities.get(i).getObjX()>canvas.getWidth() || 
						entities.get(i).getObjY()>canvas.getHeight()) {
					entities.get(i).setOnScreen(false);
				}else entities.get(i).setOnScreen(true);

				///////////Checando colisao com projeteis////
				if (projectiles.isEmpty()==false) {

					for (Projectile proj : projectiles) {

						entities.get(i).isColliding = Collision.IsColliding(entities.get(i), proj);
						proj.isColliding = Collision.IsColliding(entities.get(i), proj);

						proj.update(canvas);

						if(proj.getObjX()>canvas.getWidth() || 
								proj.getObjY()>canvas.getHeight() || proj.hasHit()) {
							proj.setOnScreen(false);
						}else proj.setOnScreen(true);

					}

				}
				entities.get(i).update(canvas);
			}

			removeDeadEntities();
			removeHitProjectiles();

		} catch (Exception e) {
			if(e instanceof java.util.ConcurrentModificationException){
				update(canvas);
			}
		}

	}

	@Override
	public void draw(Canvas canvas) {
		try{
			//TODO implement layer based render order
			if(map!=null) map.draw(canvas);

			for (Entity entity : entities) {
				if(entity.isOnScreen()) entity.draw(canvas);
			}

			if (!projectiles.isEmpty()) {
				for (Projectile proj : projectiles) {
					if(proj.isOnScreen()) proj.draw(canvas);
				}
			}

		} catch (Exception e) {
			if(e instanceof java.util.ConcurrentModificationException){
				update(canvas);
			}
		}

	}

	public void removeDeadEntities() {
		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (entity.getDeathAnimation().hasLoopedOnce()) {
				toRemove.add(entity);
			}
		}
		try {
			entities.removeAll(toRemove);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof java.util.ConcurrentModificationException){
				removeDeadEntities();
			}
		}
	}


	public List<Entity> getEntities() {
		return entities;
	}

	public Entity getEntityById(int id) {
		return entities.get(id);
	}

	@SuppressLint("NewApi")
	public void addEntity(Entity entity){
		try {
			entities.add(entity);
			entities.get(entities.size()-1).setId(View.generateViewId());
			entities.get(entities.size()-1).setWorldItBelongs(this);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof java.util.ConcurrentModificationException) {
				addEntity(entity);
			}
		}
	}

	public void removeAllEntites(){
		this.entities.clear();
	}

	public int getEntityCount(){
		return entities.size();
	}


	public Tilemap getMap() {
		return map;
	}

	public void setMap(Tilemap map) {
		this.map = map;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public Projectile getProjectileById(int id) {
		return projectiles.get(id);
	}

	public void setProjectiles(List<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	@SuppressLint("NewApi")
	public void addProjectile(Projectile proj){
		try {
			projectiles.add(proj);
			projectiles.get(projectiles.size()-1).setId(View.generateViewId());
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof java.util.ConcurrentModificationException) {
				addProjectile(proj);
			}
		}
	}

	public void removeHitProjectiles() {
		ArrayList<Projectile> toRemove = new ArrayList<Projectile>();
		for (Projectile proj : projectiles) {
			if (proj.hasHit() || !proj.isOnScreen()) {
				toRemove.add(proj);
			}
		}
		try {
			projectiles.removeAll(toRemove);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof java.util.ConcurrentModificationException){
				removeDeadEntities();
			}
		}
	}


}
