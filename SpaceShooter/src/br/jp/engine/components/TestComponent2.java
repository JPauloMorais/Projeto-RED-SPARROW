package br.jp.engine.components;

import android.graphics.Canvas;
import br.jp.engine.core.Component;

public class TestComponent2 extends Component {

	public TestComponent2() {
		super("TestComponent2");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Canvas canvas) {
//		if(LogConfig.IS_DEBUGGING) Log.i( "COMPONENT" , this.getName() + " Updated");
	}

}
