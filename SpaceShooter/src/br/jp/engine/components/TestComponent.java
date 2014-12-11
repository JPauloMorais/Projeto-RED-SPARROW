package br.jp.engine.components;

import android.graphics.Canvas;
import br.jp.engine.core.Component;


public class TestComponent extends Component {

	public TestComponent() {
		super("TestComponent");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Canvas canvas) {
//		if(LogConfig.IS_DEBUGGING)Log.i( "COMPONENT" , this.getName() + " Updated");
	}
	
	@Override
	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		super.render(canvas);
	}

}
