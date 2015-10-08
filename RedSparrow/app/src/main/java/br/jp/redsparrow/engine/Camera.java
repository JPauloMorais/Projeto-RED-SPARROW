package br.jp.redsparrow.engine;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import br.jp.redsparrow.engine.math.Vec3;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class Camera
{
	private static Vec3 loc, look, up;

	public static void init()
	{
		loc = new Vec3(0,0,-5);
		look = new Vec3(0,0,1);
		up = new Vec3(0,1,0);
	}

	public static void resize (int width, int height)
	{
		GLES20.glViewport(0, 0, width, height);

		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 10.0f;

		Matrix.frustumM(World.projection.values, 0, left, right, bottom, top, near, far);
	}

	public static void update ()
	{
//		loc = loc.sub(new Vec3(0,0,0.00001f));
//		Log.d("CAM", "Loc: " + loc);
		Matrix.setLookAtM(World.projection.values, 0,
		                  loc.x, loc.y, loc.z,
		                  look.x, look.y, look.z,
		                  up.x, up.y, up.z);
//		World.view.setLookAt(loc, look, up);
	}
}
