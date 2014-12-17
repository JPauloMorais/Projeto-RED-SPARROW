package br.jp.spaceshooter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import br.jp.engine.core.Animation;
import br.jp.engine.core.Entity;
import br.jp.engine.core.GameController;
import br.jp.engine.core.GameObject;

public class Spaceship extends Entity {

	private MediaPlayer mp;
	private boolean wp;

	public Spaceship(Context context, int w, int h) {
		this(context, w, h, 0, null);
	}

	public Spaceship(Context context, int w, int h, int colLayer, GameObject collsWith) {
		super(context, w, h, colLayer);

		this.setX((GameController.screenWidth/2)-(WIDTH/2));
		this.setY(GameController.screenHeight-HEIGHT);
		setpX(0);
		setpY(0);

		setImage(BitmapFactory.decodeResource(getResources(), 
				R.drawable.spaceship_sheet));

		setDefaultAnimation(new Animation(3, 1, 64, 64));
		getDefaultAnimation().src = new Rect(0, 0, w, h);
		getDefaultAnimation().dst = new Rect(getObjX(), getObjY(), getObjX()+w, getObjY()+h);

		this.setCollsWith(collsWith);
		
		mp = new MediaPlayer();
		wp = false;
	}

	@Override
	public void update(Canvas canvas) {

		if (!isDead() && !isColliding) {
			setX(getObjX() + getpX());
			setY(getObjY() + getpY());

			setpX(0);
			setpY(0);

			getDefaultAnimation().update( getObjX(), getObjY(), getObjX()+WIDTH, getObjY()+HEIGHT);	

		} else	{
			setDead(true);
			if(!wp){
				mp = MediaPlayer.create(getContext(), R.raw.test_sound);
				mp.start();
				wp = true;
			}
			getDeathAnimation().update( getObjX(), getObjY(), getObjX()+WIDTH, getObjY()+HEIGHT);
		}



	}

	@Override
	public void draw(Canvas canvas) {
		if (!this.isDead()) canvas.drawBitmap( getImage(), getDefaultAnimation().src, getDefaultAnimation().dst, null);
		else canvas.drawBitmap( getDeathImage(), getDeathAnimation().src, getDeathAnimation().dst, null);
	}

	@Override
	public Rect getBounds() {
		if (!isDead()) return getDefaultAnimation().dst;
		else return new Rect(10000, 10000, 1, 1);
	}

	@Override
	public void onCollisionTrue(GameObject collidingWith) {

		if(collidingWith instanceof Projectile && 
				collidingWith.getShooter().getClass().toString()!=getClass().toString()) {
			setDead(true);
			mp = MediaPlayer.create(getContext(), R.raw.test_sound);
			mp.start();
		}else{
			setDead(true);
			mp = MediaPlayer.create(getContext(), R.raw.test_sound);
			mp.start();
		}


	}

	@Override
	public void onCollisionFalse() {
		setImage(BitmapFactory.decodeResource(getResources(), 
				R.drawable.spaceship_sheet));		
	}

}
