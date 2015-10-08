package br.jp.redsparrow.engine.entities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import br.jp.redsparrow.engine.Consts;
import br.jp.redsparrow.engine.RGB;
import br.jp.redsparrow.engine.math.Vec3;
import br.jp.redsparrow.engine.rendering.Sprite;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class Entity
{
	public static final int TEXCOORD_DATA_SIZE = 2;
	public static final int COLOR_DATA_SIZE = 3;

	public int   uid;

	public Vec3  loc;
	public Vec3  vel;
	public Vec3  acl;

	public float rot;
	public float aVel;

	public Vec3  scl;

	public int         typeId;
	public int         tmIndex;
	public FloatBuffer texCoordDataBuffer;
	public FloatBuffer colorDataBuffer;

	public void setCurrentTexCoords (EntityType type, int tmIndex)
	{
		Sprite s = type.sprite;
		if(s != null)
		{
			if(tmIndex >= s.textureMaps.length)
				tmIndex = s.textureMaps.length - 1;
			this.tmIndex = tmIndex;
			Sprite.TextureMap tm = s.textureMaps[tmIndex];
			final float[] uvData = new float[]
					{
							tm.uvs[0].x, tm.uvs[0].y, // top left
							tm.uvs[1].x, tm.uvs[1].y, // bottom left
							tm.uvs[2].x, tm.uvs[2].y, // bottom right
							tm.uvs[3].x, tm.uvs[3].y, // top right
					};
			texCoordDataBuffer = ByteBuffer
					.allocateDirect(uvData.length * Consts.BYTES_PER_FLOAT)
					.order(ByteOrder.nativeOrder())
					.asFloatBuffer()
					.put(uvData);
			texCoordDataBuffer.position(0);
		}
	}

	public void setCurrentColors (RGB lt, RGB lb, RGB rb, RGB rt)
	{
		final float[] colors =
				{
						lt.r, lt.g, lt.b,
						lb.r, lb.g, lb.b,
						rb.r, rb.g, rb.b,
						rt.r, rt.g, rt.b
				};
		colorDataBuffer = ByteBuffer
				.allocateDirect(colors.length * Consts.BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(colors);
		colorDataBuffer.position(0);
	}
}
