package br.jp.redsparrow.engine.rendering;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;

import br.jp.redsparrow.engine.App;
import br.jp.redsparrow.engine.Assets;
import br.jp.redsparrow.engine.World;
import br.jp.redsparrow.engine.math.Vec2;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class Sprite
{
	public static final String TAG = "Sprite";

	public int          textureId;
	public Bitmap       spriteSheet;
	public TextureMap[] textureMaps;
	public Vec2 offset;

	public Sprite (String spriteSheetName, int spritesAlongX, int spritesAlongY)
	{
		offset = new Vec2();
		textureId = - 1;
		spriteSheet = Assets.getBitmap(spriteSheetName);

		if(spritesAlongY <= 0 || spritesAlongX <= 0)
			spritesAlongX = spritesAlongY = 1;

			float sizeX = 1.0f / spritesAlongX;
			Log.d(TAG, "sizeX: " + sizeX);
			float sizeY = 1.0f / spritesAlongY;
			Log.d(TAG, "sizeY: " + sizeY);

			textureMaps = new TextureMap[spritesAlongY * spritesAlongX];
			Log.d(TAG, "texture map count: " + textureMaps.length);
			int indx = 0;
			for (int y = 0; y < spritesAlongY; y++)
			{
				for (int x = 0; x < spritesAlongX; x++)
				{
					float minX = x * sizeX;
					float minY = y * sizeY + sizeY; //1.0f - ((y+1) * sizeY);
					textureMaps[indx++] = new TextureMap(new Vec2(minX, minY),
					                                     new Vec2(minX + sizeX, minY - sizeY));
					Log.d(TAG, "texture map #" + (indx - 1) + ": " + textureMaps[indx - 1].toString());
				}
			}
		}


	public void loadTexture()
	{
		if(textureId < 0)
			textureId = GPU.loadBitmap(spriteSheet);
	}

	public class TextureMap
	{
		public final Vec2[] uvs = new Vec2[4];

		public TextureMap (Vec2 min, Vec2 max)
		{
			uvs[0] = new Vec2(min.x, max.y);
			uvs[1] = min;
			uvs[2] = new Vec2(max.x, min.y);
			uvs[3] = max;
		}

		@Override
		public String toString ()
		{
			return "0: " + uvs[0] + ", 1:" + uvs[1] + ", 2:" + uvs[2] + ", 3:" + uvs[3];
		}
	}
}
