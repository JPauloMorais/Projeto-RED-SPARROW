package br.jp.redsparrow.engine.math;

/**
 * Created by JoaoPaulo on 09/09/2015.
 */
public class Mat3
{
	public float[] values = new float[9];

	public Mat3 ()
	{
		identity();
	}

	public Vec3 mult(final Vec3 v)
	{
		return new Vec3(v.x * values[0] + v.y * values[3] + v.z * values[6],
		                v.x * values[1] + v.y * values[4] + v.z * values[7],
		                v.x * values[2] + v.y * values[5] + v.z * values[8]);
	}

	public Mat3 mult(final Mat3 m)
	{
		Mat3 res = new Mat3();
		res.values = new float[]
				{
						values[0]*m.values[0] + values[3]*m.values[1] + values[6]*m.values[2], //0
						values[1]*m.values[0] + values[4]*m.values[1] + values[7]*m.values[2], //3
						values[2]*m.values[0] + values[5]*m.values[1] + values[8]*m.values[2], //6

						values[0]*m.values[3] + values[3]*m.values[4] + values[6]*m.values[5], //1
						values[1]*m.values[3] + values[4]*m.values[4] + values[7]*m.values[5], //4
						values[2]*m.values[3] + values[5]*m.values[4] + values[8]*m.values[5], //7

						values[0]*m.values[6] + values[3]*m.values[7] + values[6]*m.values[8], //2
						values[1]*m.values[6] + values[4]*m.values[7] + values[7]*m.values[8], //5
						values[2]*m.values[6] + values[5]*m.values[7] + values[8]*m.values[8]  //8
				};
		return res;
	}

	public void setInverse(final Mat3 m)
	{
		float t4 = m.values[0]*m.values[4];
		float t6 = m.values[0]*m.values[7];
		float t8 = m.values[3]*m.values[1];
		float t10 = m.values[6]*m.values[1];
		float t12 = m.values[3]*m.values[2];
		float t14 = m.values[6]*m.values[2];

		float t16 = (t4*m.values[8] - t6*m.values[5] - t8*m.values[8] +
		             t10*m.values[5] + t12*m.values[7] - t14*m.values[4]);

		if (t16 == 0.0f) return;
		float t17 = 1.0f/t16;

		values[0] = (m.values[4]*m.values[8]-m.values[7]*m.values[5])*t17;
		values[3] = -(m.values[3]*m.values[8]-m.values[6]*m.values[5])*t17;
		values[6] = (m.values[3]*m.values[7]-m.values[6]*m.values[4])*t17;
		values[1] = -(m.values[1]*m.values[8]-m.values[7]*m.values[2])*t17;
		values[4] = (m.values[0]*m.values[8]-t14)*t17;
		values[7] = -(t6-t10)*t17;
		values[2] = (m.values[1]*m.values[5]-m.values[4]*m.values[2])*t17;
		values[5] = -(m.values[0]*m.values[5]-t12)*t17;
		values[8] = (t4-t8)*t17;
	}

	public Mat3 inverse()
	{
		Mat3 res = new Mat3();
		res.setInverse(this);
		return res;
	}

	public void invert()
	{
		setInverse(this);
	}

	public void setTranspose(final Mat3 m)
	{
		values[0] = m.values[0];
		values[3] = m.values[1];
		values[6] = m.values[2];
		values[1] = m.values[3];
		values[4] = m.values[4];
		values[7] = m.values[5];
		values[2] = m.values[6];
		values[5] = m.values[7];
		values[8] = m.values[8];
	}

	public Mat3 transpose()
	{
		Mat3 res = new Mat3();
		res.setTranspose(this);
		return res;
	}

//	public void setOrientation(final Quat q)
//	{
//		values[0] = 1 - (2*q.j*q.j + 2*q.k*q.k);
//		values[3] = 2*q.i*q.j + 2*q.k*q.r;
//		values[6] = 2*q.i*q.k - 2*q.j*q.r;
//		values[1] = 2*q.i*q.j - 2*q.k*q.r;
//		values[4] = 1 - (2*q.i*q.i + 2*q.k*q.k);
//		values[7] = 2*q.j*q.k + 2*q.i*q.r;
//		values[2] = 2*q.i*q.k + 2*q.j*q.r;
//		values[5] = 2*q.j*q.k - 2*q.i*q.r;
//		values[8] = 1 - (2*q.i*q.i + 2*q.j*q.j);
//	}

	public void setBlockInertiaTensor(final Vec3 halfSizes, float mass)
	{
		Vec3 squares = new Vec3();
		Vec3.componentProduct(halfSizes, halfSizes, squares);
		setInertiaTensorCoeffs(0.3f*mass*(squares.y + squares.z),
		                       0.3f*mass*(squares.x + squares.z),
		                       0.3f*mass*(squares.x + squares.y),
		                       0,0,0);
	}

	void setInertiaTensorCoeffs(float ix, float iy, float iz,
	                            float ixy, float ixz, float iyz)
	{
		values[0] = ix;
		values[3] = values[1] = -ixy;
		values[6] = values[2] = -ixz;
		values[4] = iy;
		values[7] = values[5] = -iyz;
		values[8] = iz;
	}
	
	public void identity()
	{
		values[0] = 1.0f;/*0*/  values[1] = 0.0f;/*3*/  values[2] = 0.0f;/*6*/
		values[3] = 0.0f;/*1*/  values[4] = 1.0f;/*4*/  values[5] = 0.0f;/*7*/
		values[6] = 0.0f;/*2*/  values[7] = 0.0f;/*5*/  values[8] = 1.0f;/*8*/
	}
}
