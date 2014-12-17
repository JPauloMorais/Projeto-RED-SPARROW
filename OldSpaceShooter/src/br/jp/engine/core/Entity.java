package br.jp.engine.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import br.jp.engine.core.attributes.Renderable;
import br.jp.engine.core.attributes.Updatable;
import br.jp.spaceshooter.R;

public abstract class Entity extends GameObject implements Updatable, Renderable {
	
	private Animation defaultAnimation, deathAnimation;
	private Bitmap deathImage;
	private boolean isDead;
	
	public Entity(Context context, int w, int h) {
		this(context, w, h, 0);
	}

	public Entity(Context context, int w, int h, int colLayer) {
		super(context, w, h,colLayer);
		setDead(false);
		setOnScreen(true);
		setDeathImage(BitmapFactory.decodeResource(getResources(), 
				R.drawable.default_death_sheet));
		setDeathAnimation(new Animation(3, 1, 64, 64));
		getDeathAnimation().src = new Rect(0, 0, 64, 64);
		getDeathAnimation().dst = new Rect(getObjX(), getObjY(), getObjX()+w, getObjY()+h);
		getDeathAnimation().setAmmoToWait(2);
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean dead) {
		this.isDead = dead;
	}

	public Animation getDefaultAnimation() {
		return defaultAnimation;
	}

	public void setDefaultAnimation(Animation animation) {
		this.defaultAnimation = animation;
	}

	public Animation getDeathAnimation() {
		return deathAnimation;
	}

	public void setDeathAnimation(Animation deathAnimation) {
		this.deathAnimation = deathAnimation;
	}

	public Bitmap getDeathImage() {
		return deathImage;
	}

	public void setDeathImage(Bitmap deathImage) {
		this.deathImage = deathImage;
	}
	
	public void shoot(){
		
	}

}
