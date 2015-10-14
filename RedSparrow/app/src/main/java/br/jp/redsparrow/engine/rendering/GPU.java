package br.jp.redsparrow.engine.rendering;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/**
 * Created by JoaoPaulo on 14/10/2015.
 */
public class GPU
{
	public static int loadBitmap(Bitmap b)
	{
		if(b == null) return 0;

		final int[] textureObjectIds = new int[1];
		GLES20.glGenTextures(1, textureObjectIds, 0);
		if (textureObjectIds[0] == 0) return 0;

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0]);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST_MIPMAP_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, b, 0);

		b.recycle();
		b = null;

		GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

		return textureObjectIds[0];	}
}
