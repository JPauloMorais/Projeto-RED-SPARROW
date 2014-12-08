package br.jp.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import br.jp.engine.core.Animation;
import br.jp.engine.core.GameObject;
import br.jp.engine.core.attributes.Renderable;
import br.jp.engine.core.attributes.Updatable;

public class Backgound extends GameObject implements Updatable,Renderable{

	private Bitmap image;
	private Animation animation;

	public Backgound(Context context, int w, int h, String type) {
		super(context,w,h);
		switch (type) {
		case "a":
			image = BitmapFactory.decodeResource(getResources(), 
					R.drawable.black);
			animation = new Animation(1, 1, 1, 1);
			animation.src = new Rect(0, 0, 1, 1);
			animation.dst = new Rect(0, 0, 540, 960);
			break;
		case "b":
			image = BitmapFactory.decodeResource(getResources(), 
					R.drawable.background_game_over);
			animation = new Animation(1, 1, 54, 96);
			animation.src = new Rect(0, 0, 54, 96);
			animation.dst = new Rect(0, 0, 540, 960);
			break;

		default:
			image = BitmapFactory.decodeResource(getResources(), 
					R.drawable.black);
			animation = new Animation(1, 1, 1, 1);
			animation.src = new Rect(0, 0, 1, 1);
			animation.dst = new Rect(0, 0, 540, 960);
			break;
		}

	}

	@Override
	public void update(Canvas canvas) {
		animation.update(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap( image, animation.src, animation.dst, null);
	}

	@Override
	public Rect getBounds() {
		return animation.dst;
	}

	@Override
	protected void onCollisionTrue(GameObject ColWith) {

	}

	@Override
	protected void onCollisionFalse() {

	}

}
