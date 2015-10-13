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
	
	public Vec2() { this(0.0f, 0.0f); }

	public void zero()
	{
		x = 0.0f;
		y = 0.0f;
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
			x = x / mag;
			y = y / mag;
		}
	}

	public static void add (final Vec2 a, final Vec2 b, final Vec2 res)
	{
		res.x = a.x + b.x;
		res.y = a.y + b.y;
	}

	public static void add (final Vec2 a, final Vec2 b, final float scale, final Vec2 res)
	{
		res.x = a.x + (b.x * scale);
		res.y = a.y + (b.y * scale);
	}

	public static void add (final Vec2 v, final float n, final Vec2 res)
	{
		res.x = v.x + n;
		res.y = v.y + n;
	}

	public static void sub (final Vec2 a, final Vec2 b, final Vec2 res)
	{
		res.x = a.x - b.x;
		res.y = a.y - b.y;
	}

	public static void sub (final Vec2 v, final float n, final Vec2 res)
	{
		res.x = v.x - n;
		res.y = v.y - n;
	}

	public static void mult (final Vec2 v, final float n, final Vec2 res)
	{
		res.x = v.x * n;
		res.y = v.y * n;
	}

	public static void div (final Vec2 a, final Vec2 b, final Vec2 res)
	{
		res.x = a.x / b.x;
		res.y = a.y / b.y;
	}

	public static void div (final Vec2 v, final float n, final Vec2 res)
	{
		res.x = v.x / n;
		res.y = v.y / n;
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

	public void set (float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Vec2 copy () {return new Vec2(x,y);}
}
