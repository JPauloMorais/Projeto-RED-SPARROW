package br.jp.redsparrow.engine.math;

/**
 * Created by JoaoPaulo on 09/09/2015.
 */
public class Mat4
{
	public float[] values = new float[16];

	public Mat4 ()
	{
		identity();
	}

	public Vec3 mult(final Vec3 v)
	{
		return new Vec3( v.x * values[0] + v.y * values[4] + v.z * values[8] + values[12],
		                 v.x * values[1] + v.y * values[5] + v.z * values[9] + values[13],
		                 v.x * values[2] + v.y * values[6] + v.z * values[10] + values[14] );
	}

	public Mat4 mult(final Mat4 m)
	{
		Mat4 res = new Mat4();

		res.values[0] = (m.values[0]*values[0]) + (m.values[1]*values[4]) +
		                (m.values[2]*values[8]);
		res.values[1] = (m.values[0]*values[1]) + (m.values[1]*values[5]) +
		                (m.values[2]*values[9]);
		res.values[2] = (m.values[0]*values[2]) + (m.values[1]*values[6]) +
		                (m.values[2]*values[10]);

		res.values[4] = (m.values[4]*values[0]) + (m.values[5]*values[4]) +
		                (m.values[6]*values[8]);
		res.values[5] = (m.values[4]*values[1]) + (m.values[5]*values[5]) +
		                (m.values[6]*values[9]);
		res.values[6] = (m.values[4]*values[2]) + (m.values[5]*values[6]) +
		                (m.values[6]*values[10]);

		res.values[8] = (m.values[8]*values[0]) + (m.values[9]*values[4]) +
		                (m.values[10]*values[8]);
		res.values[9] = (m.values[8]*values[1]) + (m.values[9]*values[5]) +
		                (m.values[10]*values[9]);
		res.values[10] = (m.values[8]*values[2]) + (m.values[9]*values[6]) +
		                 (m.values[10]*values[10]);

		res.values[12] = (m.values[12]*values[0]) + (m.values[13]*values[4]) +
		                 (m.values[14]*values[8]) + values[12];
		res.values[13] = (m.values[12]*values[1]) + (m.values[13]*values[5]) +
		                 (m.values[14]*values[9]) + values[13];
		res.values[14] = (m.values[12]*values[2]) + (m.values[13]*values[6]) +
		                 (m.values[14]*values[10]) + values[14];

		return res;
	}

	public void setLookAt(Vec3 loc, Vec3 look, Vec3 up)
	{
		Vec3 f = new Vec3(look.x - loc.x,
		                        look.y - loc.y,
		                        look.z - loc.z);

		float rlf = 1.0f / f.magnitude();
		Vec3.mult(f, rlf, f);

		Vec3 s = Vec3.cross(f,up);

		float rls = 1.0f / s.magnitude();
		Vec3.mult(s, rls, s);

		Vec3 u = Vec3.cross(s,f);

		values[0] = s.x;
		values[1] = u.x;
		values[2] = -f.x;
		values[3] = 0.0f;

		values[4] = s.y;
		values[5] = u.y;
		values[6] = -f.y;
		values[7] = 0.0f;

		values[8] = s.z;
		values[9] = u.z;
		values[10] = -f.z;
		values[11] = 0.0f;

		values[12] = 0.0f;
		values[13] = 0.0f;
		values[14] = 0.0f;
		values[15] = 1.0f;

		Vec3.mult(loc, -1.0f, loc);
		setTranslation(loc.x, loc.y, loc.z);
	}

	public void setOrtho(float left, float right,
	                     float bottom, float top,
	                     float near, float far)
	{
		if (left == right)
		{
//			ChemLog.logln("Erro: left == right");
			return;
		}
		if (bottom == top)
		{
//			ChemLog.logln("Erro: bottom == top");
			return;
		}
		if (near == far)
		{
//			ChemLog.logln("Erro: near == far");
			return;
		}

		final float r_width  = 1.0f / (right - left);
		final float r_height = 1.0f / (top - bottom);
		final float r_depth  = 1.0f / (far - near);
		final float x =  2.0f * (r_width);
		final float y =  2.0f * (r_height);
		final float z = -2.0f * (r_depth);
		final float tx = -(right + left) * r_width;
		final float ty = -(top + bottom) * r_height;
		final float tz = -(far + near) * r_depth;

		values[0] = x;
		values[5] = y;
		values[10] = z;
		values[12] = tx;
		values[13] = ty;
		values[14] = tz;
		values[15] = 1.0f;
		values[1] = 0.0f;
		values[2] = 0.0f;
		values[3] = 0.0f;
		values[4] = 0.0f;
		values[6] = 0.0f;
		values[7] = 0.0f;
		values[8] = 0.0f;
		values[9] = 0.0f;
		values[11] = 0.0f;
	}

	public void setFrustum(float left, float right,
	                       float bottom, float top,
	                       float near, float far)
	{
		if (left == right)
		{
//			ChemLog.logln("Erro: left == right");
			return;
		}
		if (bottom == top)
		{
//			ChemLog.logln("Erro: bottom == top");
			return;
		}
		if (near == far)
		{
//			ChemLog.logln("Erro: near == far");
			return;
		}

		final float r_width  = 1.0f / (right - left);
		final float r_height = 1.0f / (top - bottom);
		final float r_depth  = 1.0f / (near - far);
		final float x = 2.0f * (near * r_width);
		final float y = 2.0f * (near * r_height);
		final float A = (right + left) * r_width;
		final float B = (top + bottom) * r_height;
		final float C = (far + near) * r_depth;
		final float D = 2.0f * (far * near * r_depth);

		values[0] = x;
		values[5] = y;
		values[8] = A;
		values[9] = B;
		values[10] = C;
		values[14] = D;
		values[11] = -1.0f;
		values[1] = 0.0f;
		values[2] = 0.0f;
		values[3] = 0.0f;
		values[4] = 0.0f;
		values[6] = 0.0f;
		values[7] = 0.0f;
		values[12] = 0.0f;
		values[13] = 0.0f;
		values[15] = 0.0f;
	}

	public void setPerspective (float fovy, float ratio, float near, float far)
	{
		float f = 1.0f / (float) Math.tan(fovy * (Math.PI / 360.0));
		float rangeReciprocal = 1.0f / (near - far);

		values[0] = f / ratio;
		values[1] = 0.0f;
		values[2] = 0.0f;
		values[3] = 0.0f;

		values[4] = 0.0f;
		values[5] = f;
		values[6] = 0.0f;
		values[7] = 0.0f;

		values[8] = 0.0f;
		values[9] = 0.0f;
		values[10] = (far + near) * rangeReciprocal;
		values[11] = -1.0f;

		values[12] = 0.0f;
		values[13] = 0.0f;
		values[14] = 2.0f * far * near * rangeReciprocal;
		values[15] = 0.0f;
	}

	public void setTranslation (float x, float y, float z)
	{
		for (int i = 0 ; i < 4 ; i++)
			values[12 + i] += values[i] * x + values[4 + i] * y + values[8 + i] * z;
	}

	public float getDeterminant()
	{
		return values[2]*values[5]*values[8]+
		       values[1]*values[6]*values[8]+
		       values[2]*values[4]*values[9]-
		       values[0]*values[6]*values[9]-
		       values[1]*values[4]*values[10]+
		       values[0]*values[5]*values[10];
	}

	public void setInverse(final Mat4 m)
	{
		float det = getDeterminant();
		if (det == 0) return;
		det = 1.0f/det;

		values[0] = (-m.values[6]*m.values[9]+m.values[5]*m.values[10])*det;
		values[1] = (m.values[2]*m.values[9]-m.values[1]*m.values[10])*det;
		values[2] = (-m.values[2]*m.values[5]+m.values[1]*m.values[6]* m.values[15])*det;

		values[4] = (m.values[6]*m.values[8]-m.values[4]*m.values[10])*det;
		values[5] = (-m.values[2]*m.values[8]+m.values[0]*m.values[10])*det;
		values[6] = (m.values[8]*m.values[1]-m.values[0]*m.values[9]* m.values[15])*det;

		values[8] = (-m.values[5]*m.values[8]+m.values[4]*m.values[9]* m.values[15])*det;
		values[9] = (+m.values[1]*m.values[8]-m.values[0]*m.values[9]* m.values[15])*det;
		values[10] = (-m.values[1]*m.values[4]+m.values[0]*m.values[5]* m.values[15])*det;

		values[12] = (m.values[6]*m.values[9]*m.values[12]
		              -m.values[5]*m.values[10]*m.values[12]
		              -m.values[6]*m.values[8]*m.values[13]
		              +m.values[4]*m.values[10]*m.values[13]
		              +m.values[5]*m.values[8]*m.values[14]
		              -m.values[4]*m.values[9]*m.values[14])*det;
		values[13] = (-m.values[2]*m.values[9]*m.values[12]
		              +m.values[1]*m.values[10]*m.values[12]
		              +m.values[2]*m.values[8]*m.values[13]
		              -m.values[0]*m.values[10]*m.values[13]
		              -m.values[1]*m.values[8]*m.values[14]
		              +m.values[0]*m.values[9]*m.values[14])*det;
		values[14] =(m.values[2]*m.values[5]*m.values[12]
		             -m.values[1]*m.values[6]*m.values[12]
		             -m.values[2]*m.values[4]*m.values[13]
		             +m.values[0]*m.values[6]*m.values[13]
		             +m.values[1]*m.values[4]*m.values[14]
		             -m.values[0]*m.values[5]*m.values[14])*det;
	}

	public Mat4 inverse()
	{
		Mat4 res = new Mat4();
		res.setInverse(this);
		return res;
	}

	public void invert()
	{
		setInverse(this);
	}

//	public void setOrientAndLoc (final Quat q, final Vec3 p)
//	{
//		values[0] = 1 - (2*q.j*q.j + 2*q.k*q.k);
//		values[4] = 2*q.i*q.j + 2*q.k*q.r;
//		values[8] = 2*q.i*q.k - 2*q.j*q.r;
//		values[12] = p.x;
//		values[1] = 2*q.i*q.j - 2*q.k*q.r;
//		values[5] = 1 - (2*q.i*q.i + 2*q.k*q.k);
//		values[9] = 2*q.j*q.k + 2*q.i*q.r;
//		values[13] = p.y;
//		values[2] = 2*q.i*q.k + 2*q.j*q.r;
//		values[6] = 2*q.j*q.k - 2*q.i*q.r;
//		values[10] = 1 - (2*q.i*q.i + 2*q.j*q.j);
//		values[14] = p.z;
//	}

	public static Vec3 localToWorld(final Vec3 local, final Mat4 transform)
	{
		return transform.mult(local);
	}

	public static Vec3 worldToLocal(final Vec3 world, final Mat4 transform)
	{
		Mat4 inverseTransform = new Mat4();
		inverseTransform.setInverse(transform);
		return inverseTransform.mult(world);
	}

	public static Vec3 worldToLocal_1(final Vec3 world, final Mat4 transform)
	{
		return transform.transformInverse(world);
	}

	public Vec3 getTranslation()
	{
		return new Vec3(values[12], values[13], values[14]);
	}

	public Vec3 transformInverse(final Vec3 v)
	{
		Vec3 tmp = v.copy();
		tmp.x -= values[12];
		tmp.y -= values[13];
		tmp.z -= values[14];

		return new Vec3( tmp.x * values[0] + tmp.y * values[1] + tmp.z * values[2],
		                 tmp.x * values[4] + tmp.y * values[5] + tmp.z * values[6],
		                 tmp.x * values[8] + tmp.y * values[9] + tmp.z * values[10]);
	}

	public Vec3 transformDirection(final Vec3 v)
	{
		return new Vec3(v.x * values[0] + v.y * values[4] + v.z * values[8],
		                v.x * values[1] + v.y * values[5] + v.z * values[9],
		                v.x * values[2] + v.y * values[6] + v.z * values[10]);
	}

	public Vec3 transformInverseDirection(final Vec3 v)
	{
		return new Vec3(v.x * values[0] + v.y * values[1] + v.z * values[2],
		                v.x * values[4] + v.y * values[5] + v.z * values[6],
		                v.x * values[8] + v.y * values[9] + v.z * values[10]);
	}

	public static Vec3 localToWorldDir(final Vec3 local, final Mat4 transform)
	{
		return transform.transformDirection(local);
	}

	public static Vec3 worldToLocalDir(final Vec3 world, final Mat4 transform)
	{
		return transform.transformInverseDirection(world);
	}

	public void identity()
	{
		values[0]  = 1.0f;/*0*/  values[1]  = 0.0f;/*4*/  values[2]  = 0.0f;/*8 */  values[3]  = 0.0f;/*12*/
		values[4]  = 0.0f;/*1*/  values[5]  = 1.0f;/*5*/  values[6]  = 0.0f;/*9 */  values[7]  = 0.0f;/*13*/
		values[8]  = 0.0f;/*2*/  values[9]  = 0.0f;/*6*/  values[10] = 1.0f;/*10*/  values[11] = 0.0f;/*14*/
		values[12] = 0.0f;/*3*/  values[13] = 0.0f;/*7*/  values[14] = 0.0f;/*11*/  values[15] = 1.0f;/*15*/
	}
}
