package br.jp.redsparrow.engine.math;

public class Vec3
{
	public static final Vec3 ZERO   = new Vec3(0, 0, 0);
	public static final Vec3 UNIT_X = new Vec3(1, 0, 0);
	public static final Vec3 UNIT_Y = new Vec3(0, 1, 0);
	public static final Vec3 UNIT_Z = new Vec3(0, 0, 1);

	public float x, y, z;

	public Vec3 (float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3 () { this(0.0f,0.0f,0.0f); }

	public void zero()
	{
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}

	public float magnitude() { return (float) Math.sqrt((x*x)+(y*y)+(z*z)); }

	public float squareMagnitude() { return ((x*x)+(y*y)+(z*z)); }

	public void negate()
	{
		x *= -1;
		y *= -1;
		z *= -1;
	}
	
	public void normalize()
	{
		final float mag = this.magnitude();
		if(mag > 0.0f)
		{			
			x = x / mag;
			y = y / mag;
			z = z / mag;
		}
	}

	public float dot (Vec3 v) { return (x*v.x) + (y*v.y) + (z*v.z); }
	
	public boolean equals (Vec3 v) { return (x==v.x) && (y==v.y) && (z==v.z); }

	public static void add (final Vec3 a, final Vec3 b, final Vec3 res)
	{
		res.x = a.x + b.x;
		res.y = a.y + b.y;
		res.z = a.z + b.z;
	}

	public static void add (final Vec3 v, final float n, final Vec3 res)
	{
		res.x = v.x + n;
		res.y = v.y + n;
		res.z = v.z + n;
	}

	public static void sub (final Vec3 a, final Vec3 b, final Vec3 res)
	{
		res.x = a.x - b.x;
		res.y = a.y - b.y;
		res.z = a.z - b.z;
	}

	public static void sub (final Vec3 v, final float n, final Vec3 res)
	{
		res.x = v.x - n;
		res.y = v.y - n;
		res.z = v.z - n;
	}

	public static void mult (final Vec3 v, final float n, final Vec3 res)
	{
		res.x = v.x * n;
		res.y = v.y * n;
		res.z = v.z * n;
	}

	public static void div (final Vec3 a, final Vec3 b, final Vec3 res)
	{
		res.x = a.x / b.x;
		res.y = a.y / b.y;
		res.z = a.z / b.z;
	}

	public static void div (final Vec3 v, final float n, final Vec3 res)
	{
		res.x = v.x / n;
		res.y = v.y / n;
		res.z = v.z / n;
	}

	public static float distance (Vec3 a, Vec3 b)
	{
		float dx = a.x - b.x;
		float dy = a.y - b.y;
		float dz = a.z - b.z;
		return (float) Math.sqrt((dx*dx) + (dy*dy) + (dz*dz));
	}

	public static Vec3 cross (Vec3 a, Vec3 b)
	{
		float x = a.y*b.z - a.z*b.y;
		float y = a.z*b.x - a.x*b.z;
		float z = a.x*b.y - a.y*b.x;
		return new Vec3(x,y,z);
	}

	public static void componentProduct (final Vec3 a, final Vec3 b, final Vec3 res)
	{
		res.x = a.x * b.x;
		res.y = a.y * b.y;
		res.z = a.z * b.z;
	}

	@Override
	public String toString () { return "(" + x + ", " + y + ", " + z + ")"; }
	public Vec3 copy () { return new Vec3(x,y,z); }
}
