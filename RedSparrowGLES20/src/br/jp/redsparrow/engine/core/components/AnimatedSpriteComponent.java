package br.jp.redsparrow.engine.core.components;

import android.content.Context;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;

public class AnimatedSpriteComponent extends SpriteComponent implements Updatable {

	private int curFrame = 1;
	private int mFrameCount;

	public AnimatedSpriteComponent(Context context, GameObject parent, int sheetId, int frameCount) {
		super("AnimatedSprite", context, sheetId);

		mFrameCount = frameCount;
		
		parent.updateVertsData(parent.getPosition().getX(), parent.getPosition().getY(), 
							((1/frameCount)*curFrame)-(1/frameCount)/2, 0.5f, 
							(1/frameCount), 1);
		
	}

	private void updateAnimation(){
		if(curFrame < mFrameCount) curFrame++;
		else curFrame = 0;
	}

	@Override
	public void update(GameObject parent) {

		updateAnimation();
		
		parent.updateVertsData(parent.getPosition().getX(), parent.getPosition().getY(), 
							((1/mFrameCount)*curFrame)-(1/mFrameCount)/2, 0.5f, 
							(1/mFrameCount), 1);
		
	}

}
