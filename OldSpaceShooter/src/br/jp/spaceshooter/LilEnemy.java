package br.jp.spaceshooter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import br.jp.engine.core.Animation;
import br.jp.engine.core.Entity;
import br.jp.engine.core.GameObject;

public class LilEnemy extends Entity {

	private MediaPlayer mp;
	private boolean wp;

	private int  pX = 16, pY = 5;
	private int wait = 0;
	private int ammoToWait = 50;
	
	
	
	public LilEnemy(Context context, int w, int h, int colLayer, GameObject collswith) {
		super(context, w, h,colLayer);
		setX(50);
		setY(50);
		
		setImage(BitmapFactory.decodeResource(getResources(), 
				R.drawable.enemy_default_sheet));
		setDefaultAnimation(new Animation(1, 1, 50, 50));
		getDefaultAnimation().src = new Rect(0, 0, w, h);
		getDefaultAnimation().dst = new Rect( getObjX(), getObjY(), 0, 0);

		mp = new MediaPlayer();
		wp = false;
		
		this.setCollsWith(collswith);
	}

	@Override
	public void update(Canvas canvas) {

		if (!isColliding && !isDead()) {
			
			if (getObjX()>=(canvas.getWidth()-WIDTH) || getObjX()<=0) {
				pX = -pX;
			}
			if (getObjY()>=(canvas.getHeight()-HEIGHT)-196 || getObjY()<=0) {
				pY = -pY;
			}

			if(getObjX()+pX>canvas.getWidth()-WIDTH) setX(canvas.getWidth()-WIDTH);
			else setX(getObjX() + pX);
			if(getObjY()+pY>canvas.getHeight()) setY(canvas.getHeight()-HEIGHT);
			else setY(getObjY() + pY);
			
			if (wait>ammoToWait) {
				getWorldItBelongs().addProjectile(new Projectile(getContext(), 
						(getObjX()+getWIDTH())/2 - 16,
						getObjY()+getHEIGHT(),
						50, 50, 1,
						this,
						getWorldItBelongs().getEntityById(0)));
				getWorldItBelongs().getProjectileById(getWorldItBelongs().getProjectiles().size()-1).setpY(5);
				
				mp = MediaPlayer.create(getContext(), R.raw.test_shot);
				mp.start();
				wait = 0;
				
				if(ammoToWait>=17) ammoToWait-=2;
				else ammoToWait = 15;
			} else wait++;

			getDefaultAnimation().update(getObjX(), getObjY(), getObjX()+WIDTH, getObjY()+HEIGHT);
		} else {
			this.setDead(true);
			if(!wp){
				mp = MediaPlayer.create(getContext(), R.raw.test_sound);
				mp.start();
				wp = true;
			}
			getDeathAnimation().update(getObjX(), getObjY(), getObjX()+WIDTH, getObjY()+HEIGHT);
		}
		
	}
	
	@Override
	public void draw(Canvas canvas) {

		if (!this.isDead()) canvas.drawBitmap( getImage(), getDefaultAnimation().src, getDefaultAnimation().dst, null);
		else canvas.drawBitmap( getDeathImage(), getDeathAnimation().src, getDeathAnimation().dst, null);

	}

	@Override
	protected void onCollisionTrue(GameObject colWith) {
		
		if( colWith.getTarget()!=null && colWith.getTarget().getClass()==getClass()){
			setDead(true);
			mp = MediaPlayer.create(getContext(), R.raw.test_sound);
			mp.start();
			pX *= -1;
			pY *= -1;
		}else if(colWith.getClass() != getClass() && colWith.getTarget().getClass()==getClass()){
			setDead(true);
			mp = MediaPlayer.create(getContext(), R.raw.test_sound);
			mp.start();
			pX *= -1;
			pY *= -1;
		}
		
		
	}

	@Override
	protected void onCollisionFalse() {
		// TODO Auto-generated method stub

	}

	@Override
	public Rect getBounds() {
		if (!isDead()) return getDefaultAnimation().dst;
		else return getDeathAnimation().dst;
	}

}
