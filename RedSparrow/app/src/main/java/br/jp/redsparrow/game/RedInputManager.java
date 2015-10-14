package br.jp.redsparrow.game;

import android.util.Log;

import br.jp.redsparrow.engine.GameActivity;
import br.jp.redsparrow.engine.input.InputManager;
import br.jp.redsparrow.engine.input.SensorInput;
import br.jp.redsparrow.engine.input.TouchInput;
import br.jp.redsparrow.engine.math.Vec2;

/**
 * Created by JoaoPaulo on 14/10/2015.
 */
public class RedInputManager extends InputManager
{
	private Vec2 start = new Vec2();
	private Vec2 dir   = new Vec2();

	public RedInputManager ()
	{
		super(true, false);
	}

	@Override
	protected boolean onTouch_0 (GameActivity gameActivity, TouchInput touchInput)
	{
		switch (touchInput.type)
		{
			case TouchInput.TYPE_DOWN:
				start.set(touchInput.x, - touchInput.y);
				Log.d("In", "strt: " + start);
				break;
			case TouchInput.TYPE_MOVE:
				Vec2.sub(new Vec2(touchInput.x, - touchInput.y), start, dir);
				dir.normalize();
				Log.d("In", "Dir: " + dir);
				Vec2.mult(dir, 100.0f, dir);
				gameActivity.world.player.applyForce(dir);
				break;
			case TouchInput.TYPE_UP:
				Log.d("In", "0 Up");
				break;
		}
		return true;
	}

	@Override
	protected boolean onTouch_1 (GameActivity gameActivity, TouchInput touchInput)
	{
		return false;
	}

	@Override
	protected boolean onTouch_2 (GameActivity gameActivity, TouchInput touchInput)
	{
		return false;
	}

	@Override
	protected boolean onTouch_3 (GameActivity gameActivity, TouchInput touchInput)
	{
		return false;
	}

	@Override
	protected boolean onTouch_4 (GameActivity gameActivity, TouchInput touchInput)
	{
		return false;
	}

	@Override
	protected boolean onSensorChanged (SensorInput sensorInput)
	{
		Vec2 dir = new Vec2(sensorInput.dir.x, sensorInput.dir.y);
		dir.normalize();
		Log.d("Player", dir.toString());
		return true;
	}
}
