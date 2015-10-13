package br.jp.redsparrow.engine.physics;

import br.jp.redsparrow.engine.math.Vec2;

/**
 * Created by JoaoPaulo on 11/10/2015.
 */
public class AABB
{
	public Vec2 min, max;

	public AABB (Vec2 min, Vec2 max)
	{
		this.min = min;
		this.max = max;
	}

	public AABB ()
	{
		this(new Vec2(), new Vec2());
	}

	public static boolean isInside(Vec2 aMin, Vec2 aMax, Vec2 bMin, Vec2 bMax)
	{
		return aMin.x > bMin.x && aMin.y > bMin.y &&
	           aMax.x < bMax.x && aMax.y < bMax.y;
	}

	public static boolean isInside(Vec2 p, Vec2 bMin, Vec2 bMax)
	{
		return p.x > bMin.x && p.y > bMin.y &&
		       p.x < bMax.x && p.y < bMax.y;
	}
}
