package br.jp.redsparrow.engine.physics;

import br.jp.redsparrow.engine.math.Vec2;

/**
 * Created by JoaoPaulo on 12/10/2015.
 */
public class Particle implements Quadtree.Member
{
	public Vec2  loc;
	public Vec2  vel;
	public Vec2  acl;
	public Vec2  scl;
	public Vec2  acumForce;
	public float lDamp;
	public float invMass;

	@Override
	public Vec2 getCenter ()
	{
		return loc;
	}
}
