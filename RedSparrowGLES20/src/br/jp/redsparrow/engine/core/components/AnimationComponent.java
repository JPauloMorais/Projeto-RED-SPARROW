package br.jp.redsparrow.engine.core.components;

import android.content.Context;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;

@SuppressWarnings("unused")
public class AnimationComponent extends Component implements Updatable {

	private int nRows;
	private int nCols;
	
	private int curFrame;
	private int mFrameCount;

	public AnimationComponent(Context context, GameObject parent, int sheetId, int rows, int cols) {
		super("Animation");

		nRows = rows;
		nCols = cols;
		
		mFrameCount = rows * cols;
		
		curFrame = 0;
		
	}

	private void updateAnimation(){
		if(curFrame < mFrameCount) curFrame++;
		else curFrame = 0;
	}

	@Override
	public void update(GameObject parent) {

		updateAnimation();
		
		parent.updateVertsData(parent.getPosition().getX(), parent.getPosition().getY(), 
							((1/mFrameCount)*curFrame)-(1/mFrameCount)/2, 0.5f);
		
	}

}
