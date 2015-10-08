package br.jp.redsparrow.engine.math;

public class Vec2
{
	public static final Vec2 ZERO = new Vec2(0, 0);
	public float x, y;

	public Vec2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vec2(Vec2 v)
	{
		this(v.x, v.y);
	}

	public void zero()
	{
		x = y = 0.0f;
	}
	
	public Vec2 add(Vec2 v)
	{
		return new Vec2(x+v.x, y+v.y);
	}

	public Vec2 add(float n)
	{
		return new Vec2(x+n, y+n);
	}

	public Vec2 sub(Vec2 v)
	{
		return new Vec2(x-v.x, y-v.y);
	}

	public Vec2 sub(float n)
	{
		return new Vec2(x-n, y-n);
	}

	public Vec2 mult(float n)
	{
		return new Vec2(x*n, y*n);
	}

	public Vec2 div(float n)
	{
		return new Vec2(x/n, y/n);
	}

	public float magnitude()
	{
		return (float) Math.sqrt((x*x)+(y*y));
	}

	public float squareMagnitude()
	{
		return ((x*x)+(y*y));
	}

	public void negate()
	{
		x *= -1;
		y *= -1;
	}

	public void normalize()
	{
		float mag = this.magnitude();
		if(mag > 0)
		{			
			Vec2 r = this.div(mag);
			x = r.x;
			y = r.y;
		}
	}

	public float dot(Vec2 v)
	{
		return (x*v.x) + (y*v.y);
	}
	
	public boolean equals(Vec2 v)
	{
		return (x==v.x) && (y==v.y);
	}
	
	public static float magnitude(Vec2 v)
	{
		return (float) Math.sqrt((v.x*v.x)+(v.y*v.y));
	}

	public static float squareMagnitude(Vec2 v)
	{
		return ((v.x*v.x)+(v.y*v.y));
	}

	public static float distance(Vec2 a, Vec2 b)
	{
		float dx = a.x - b.x;
		float dy = a.y - b.y;
		return (float) Math.sqrt((dx*dx) + (dy*dy));
	}

	@Override
	public String toString ()
	{
		return "(" + x + ", " + y + ")";
	}
}
