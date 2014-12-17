package br.jp.spaceshooter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import br.jp.engine.core.Animation;
import br.jp.engine.core.GameObject;
import br.jp.engine.core.attributes.Renderable;
import br.jp.engine.core.attributes.Updatable;

public class Projectile extends GameObject implements Renderable,Updatable {

	private Animation defaultAnimation;
	private boolean hasHit;

	public Projectile(Context context, int x, int y, int w, int h, int colLayer, GameObject shooter, GameObject target) {
		super(context, w, h, colLayer);

		this.hasHit = false;
		this.setX(x);
		this.setY(y);
		this.setpX(0);
		this.setpY(-20);	

		this.setShooter(shooter);
		this.setTarget(target);

		this.setOnScreen(true);
		this.setImage(BitmapFactory.decodeResource(getResources(), 
				R.drawable.default_shot));
		this.setDefaultAnimation(new Animation(1, 1, 32, 32));
		this.getDefaultAnimation().src = new Rect(0, 0, 32, 32);
		this.getDefaultAnimation().dst = new Rect(getObjX(), getObjY(), getObjX()+w, getObjY()+h);
		this.getDefaultAnimation().setAmmoToWait(60);

	}

	@Override
	public void update(Canvas canvas) {
		if(!isColliding){
			this.setY(getObjY()+getpY());
			this.getDefaultAnimation().update(getObjX(), getObjY(), getObjX()+getWIDTH(), getObjY()+getHEIGHT());
		}else{
			this.hasHit = true;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		if(!isColliding) 
			canvas.drawBitmap( getImage(), getDefaultAnimation().src, getDefaultAnimation().dst, null);
	}

	@Override
	protected void onCollisionTrue(GameObject collidingWith) {
		if(collidingWith.getClass()==getTarget().getClass()){
			//			setImage(BitmapFactory.decodeResource(getResources(), 
			//					R.drawable.default_shot));
			hasHit = true;
			setOnScreen(false);
		}
	}

	@Override
	protected void onCollisionFalse() {
		// TODO Auto-generated method stub

	}

	@Override
	public Rect getBounds() {
		return defaultAnimation.dst;
	}

	public Animation getDefaultAnimation() {
		return defaultAnimation;
	}

	public void setDefaultAnimation(Animation defaultAnimation) {
		this.defaultAnimation = defaultAnimation;
	}

	public boolean hasHit() {
		return hasHit;
	}

	public void setHasHit(boolean hasHit) {
		try {
			this.hasHit = hasHit;
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof java.util.ConcurrentModificationException) {
				setHasHit(hasHit);
			}
		}
	}

}
