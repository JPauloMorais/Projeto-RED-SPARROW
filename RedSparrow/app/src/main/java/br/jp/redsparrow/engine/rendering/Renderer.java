package br.jp.redsparrow.engine.rendering;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import br.jp.redsparrow.engine.Consts;
import br.jp.redsparrow.engine.World;
import br.jp.redsparrow.engine.input.InputManager;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class Renderer implements GLSurfaceView.Renderer
{
	public static final long NS_PER_MS = 1000000;
	public static final long NS_PER_S = 1000000000;
	public static final long MS_PER_S = 1000;

	private static long startTime = System.nanoTime();
	private static int frames = 0;

	private static long lastTime;
	private static long time;

	@Override
	public void onSurfaceCreated (GL10 gl, EGLConfig config)
	{
		World.init();
	}

	@Override
	public void onSurfaceChanged (GL10 gl, int width, int height)
	{
		World.resize(width, height);
	}

	@Override
	public void onDrawFrame (GL10 gl)
	{
		float delta = (float)((time - lastTime) / NS_PER_S);
		lastTime = time + 0L;
		time = System.nanoTime();

		frames++;
		if(time - startTime >= NS_PER_S)
		{
			Log.d("FPS", "Fps:" + frames);
			frames = 0;
			startTime = time;
		}

		InputManager.processInputs();
		World.update(delta);
		World.render();
	}

	public static int toIBO (ShortBuffer buffer)
	{
		int[] ibo = new int[1];
		GLES20.glGenBuffers(1, ibo, 0);
		buffer.position(0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,
		                    buffer.capacity() * Consts.BYTES_PER_SHORT,
		                    buffer, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		return ibo[0];
	}

	public static int toVBO (FloatBuffer buffer)
	{
		int[] vbo = new int[1];
		GLES20.glGenBuffers(1, vbo, 0);
		buffer.position(0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,
		                    buffer.capacity() * 4,
		                    buffer, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		return vbo[0];
	}

	public static int loadBitmap(Bitmap bmp)
	{
		if(bmp == null) return 0;

		final int[] textureObjectIds = new int[1];
		GLES20.glGenTextures(1, textureObjectIds, 0);
		if (textureObjectIds[0] == 0) return 0;

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0]);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST_MIPMAP_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

		bmp.recycle();

		GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

		return textureObjectIds[0];
	}
}
