package br.jp.spaceshooter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import br.jp.engine.core.Animation;
import br.jp.engine.core.Entity;
import br.jp.engine.core.GameController;
import br.jp.engine.core.GameObject;

public class Spawner extends Entity {

	@SuppressWarnings("unused")
	private int  pX, pY;

	public Spawner(Context context, int w, int h, int colLayer) {
		super(context, w, h, colLayer);

		setX((GameController.screenWidth/2)-(WIDTH/2));
		setY(GameController.screenHeight/2-HEIGHT);
		pX = 16;
		pY = 16;

		setImage(BitmapFactory.decodeResource(getResources(), 
				R.drawable.enemy_spawner_sheet));
		setDefaultAnimation(new Animation(3, 1, 42, 64));
		getDefaultAnimation().src = new Rect(0, 0, 42, 64);
		getDefaultAnimation().dst = new Rect(getObjX(), getObjY(), getObjX()+w, getObjY()+h);
		getDefaultAnimation().setAmmoToWait(30);
		
	}

	@Override
	public void update(Canvas canvas) {
		
		if (!this.isDead()) {
			
			setX( getObjX()+getpX() );

			getDefaultAnimation().update(getObjX(), getObjY(), getObjX()+WIDTH, getObjY()+HEIGHT);
			
		} else getDeathAnimation().update(getObjX(), getObjY(), getObjX()+WIDTH, getObjY()+HEIGHT);

	}
	@Override
	public void draw(Canvas canvas) {
		
		if (!this.isDead()) canvas.drawBitmap( getImage(), getDefaultAnimation().src, getDefaultAnimation().dst, null);
		else canvas.drawBitmap( getDeathImage(), getDeathAnimation().src, getDeathAnimation().dst, null);
	
	}

	@Override
	protected void onCollisionTrue(GameObject colWith) {
		if(colWith.getTarget().toString()==this.getClass().toString()) setDead(true);
	}

	@Override
	protected void onCollisionFalse() {

	}

	@Override
	public Rect getBounds() {
		if (!this.isDead()) return getDefaultAnimation().dst;
		else return new Rect(0, 0, 0, 0);
	}

}
