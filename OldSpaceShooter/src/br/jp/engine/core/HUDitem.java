package br.jp.engine.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import br.jp.engine.core.attributes.Renderable;
import br.jp.engine.core.attributes.Updatable;

public class HUDitem extends View implements Renderable, Updatable {

	private final int x,y;
	private Bitmap image;
	
	public HUDitem(Context context, int x, int y, Bitmap image) {
		super(context);
		this.x = x;
		this.y = y;
		this.image = image;
	}

	@Override
	public void update(Canvas canvas) {

	}
	
	@Override
	public void draw(Canvas canvas){
		canvas.drawBitmap(image, x, y, null);
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
