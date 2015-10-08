package br.jp.redsparrow.engine.input;


import br.jp.redsparrow.engine.math.Vec3;

/**
 * Created by JoaoPaulo on 03/10/2015.
 */
public class SensorInput
{
	public final Vec3 dir;

	public SensorInput (float x, float y, float z)
	{
		dir = new Vec3(x, y, z);
	}
}
