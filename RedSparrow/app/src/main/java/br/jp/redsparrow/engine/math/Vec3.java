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

	public Vec3 ()
	{
		this(0.0f,0.0f,0.0f);
	}

	public Vec3(Vec3 v)
	{
		this(v.x, v.y, v.z);
	}

	public void zero()
	{
		x = y = z = 0.0f;
	}
	
	public Vec3 add(final Vec3 v)
	{
		return new Vec3(x+v.x, y+v.y, z+v.z);
	}

	public Vec3 add(final float n)
	{
		return new Vec3(x+n, y+n, z+n);
	}

	public Vec3 addScaled(final Vec3 v, float scale)
	{
		return new Vec3(x + (v.x*scale),
		                y + (v.y*scale),
		                z + (v.z*scale));
	}

	public Vec3 sub(final Vec3 v)
	{
		return new Vec3(x-v.x, y-v.y, z-v.z);
	}

	public Vec3 sub(final float n)
	{
		return new Vec3(x-n, y-n, z-n);
	}

	public Vec3 mult(final float n)
	{
		return new Vec3(x*n, y*n, z*n);
	}

	public Vec3 div(final float n)
	{
		return new Vec3(x/n, y/n, z/n);
	}

	public float magnitude()
	{
		return (float) Math.sqrt((x*x)+(y*y)+(z*z));
	}

	public float squareMagnitude()
	{
		return ((x*x)+(y*y)+(z*z));
	}

	public void negate()
	{
		x *= -1;
		y *= -1;
		z *= -1;
	}
	
	public void normalize()
	{
		float mag = this.magnitude();
		if(mag > 0)
		{			
			Vec3 r = this.div(mag);
			x = r.x;
			y = r.y;
			z = r.z;
		}
	}

	public float dot(Vec3 v)
	{
		return (x*v.x) + (y*v.y) + (z*v.z);
	}
	
	public boolean equals(Vec3 v)
	{
		return (x==v.x) && (y==v.y) && (z==v.z);
	}
	
	public static float magnitude(Vec3 v)
	{
		return (float) Math.sqrt((v.x*v.x)+(v.y*v.y)+(v.z*v.z));
	}

	public static float squareMagnitude(Vec3 v)
	{
		return ((v.x*v.x)+(v.y*v.y)+(v.z*v.z));
	}

	public static Vec3 cross(Vec3 a, Vec3 b)
	{
		float x = a.y*b.z - a.z*b.y;
		float y = a.z*b.x - a.x*b.z;
		float z = a.x*b.y - a.y*b.x;
		return new Vec3(x, y, z);
	}
	
	public static float distance(Vec3 a, Vec3 b)
	{
		float dx = a.x - b.x;
		float dy = a.y - b.y;
		float dz = a.z - b.z;
		return (float) Math.sqrt((dx*dx) + (dy*dy) + (dz*dz));
	}

	@Override
	public String toString ()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public Vec3 copy ()
	{
		return new Vec3(x,y,z);
	}

	public Vec3 componentProduct (Vec3 v)
	{
		return new Vec3(x*v.x, y*v.y, z*v.z);
	}
}
