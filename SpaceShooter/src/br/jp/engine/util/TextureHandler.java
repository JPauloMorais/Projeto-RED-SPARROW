package br.jp.engine.util;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class TextureHandler {
	public void loadGLTexture(GL10 gl, Context context) {
//		//Get the texture from the Android resource directory
//		InputStream is = context.getResources().openRawResource(R.drawable.btr);
//		Bitmap bitmap = null;
//		try {
//			//BitmapFactory is an Android graphics utility for images
//			bitmap = BitmapFactory.decodeStream(is);
//
//		} finally {
//			//Always clear and close
//			try {
//				is.close();
//				is = null;
//			} catch (IOException e) {
//			}
//		}
//
//		//Generate there texture pointer
//		gl.glGenTextures(1, textures, 0);
//
//		//Create Linear Filtered Texture and bind it to texture
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
//		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
//		
//		//Clean up
//		bitmap.recycle();
		
	}
}
