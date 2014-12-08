package br.jp.engine.core;

import android.graphics.Canvas;
import br.jp.engine.core.attributes.Updatable;

@SuppressWarnings("unused")
public class Camera implements Updatable {

	private int x, y;
	private GameObject relativeTo;
	
	public Camera(int x, int y, GameObject relativeTo) {
		super();
		this.x = x;
		this.y = y;
		this.relativeTo = relativeTo;
	}

	@Override
	public void update(Canvas canvas) {
		
	}

}
