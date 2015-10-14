package br.jp.redsparrow.engine.physics;

import br.jp.redsparrow.engine.math.Vec2;

/**
 * Created by JoaoPaulo on 12/10/2015.
 */
public class Particle implements Quadtree.Member
{
	public Vec2 loc;
	public Vec2 vel;
	public float lifetime;

	@Override
	public Vec2 getCenter ()
	{
		return loc;
	}

	@Override
	public Vec2 getMin ()
	{
		return null;
	}

	@Override
	public Vec2 getMax ()
	{
		return null;
	}
}
