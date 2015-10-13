package br.jp.redsparrow.engine.rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import br.jp.redsparrow.engine.Consts;
import br.jp.redsparrow.engine.math.Vec2;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class Quad
{
	public static final ShortBuffer INDEX_DATA_BUFFER;
	public static final FloatBuffer POSITION_DATA_BUFFER;
	public static final FloatBuffer NORMAL_DATA_BUFFER;

	public static final Vec2 VERTEX_POSITION_LEFT_TOP;
	public static final Vec2 VERTEX_POSITION_LEFT_BOTTOM;
	public static final Vec2 VERTEX_POSITION_RIGHT_BOTTOM;
	public static final Vec2 VERTEX_POSITION_RIGHT_TOP;

	static
	{
		VERTEX_POSITION_LEFT_TOP = new Vec2(- 0.5f, 0.5f);
		VERTEX_POSITION_LEFT_BOTTOM = new Vec2(- 0.5f, - 0.5f);
		VERTEX_POSITION_RIGHT_BOTTOM = new Vec2(0.5f, - 0.5f);
		VERTEX_POSITION_RIGHT_TOP = new Vec2(0.5f, 0.5f);

		final short[] indices = {0, 1, 2, 0, 2, 3};
		INDEX_DATA_BUFFER = ByteBuffer.allocateDirect(indices.length * Consts.BYTES_PER_SHORT)
		                              .order(ByteOrder.nativeOrder())
		                              .asShortBuffer()
		                              .put(indices);
		INDEX_DATA_BUFFER.position(0);

		final float[] positions =
				{
						VERTEX_POSITION_LEFT_TOP.x,     VERTEX_POSITION_LEFT_TOP.y,
						VERTEX_POSITION_LEFT_BOTTOM.x,  VERTEX_POSITION_LEFT_BOTTOM.y,
						VERTEX_POSITION_RIGHT_BOTTOM.x, VERTEX_POSITION_RIGHT_BOTTOM.y,
						VERTEX_POSITION_RIGHT_TOP.x,    VERTEX_POSITION_RIGHT_TOP.y
				};
		POSITION_DATA_BUFFER = ByteBuffer.allocateDirect(positions.length * Consts.BYTES_PER_FLOAT)
		                                 .order(ByteOrder.nativeOrder())
		                                 .asFloatBuffer()
		                                 .put(positions);
		POSITION_DATA_BUFFER.position(0);

		final float[] normals =
				{
						0.0f, 0.0f, 1.0f, // top left
						0.0f, 0.0f, 1.0f, // bottom left
						0.0f, 0.0f, 1.0f, // bottom right
						0.0f, 0.0f, 1.0f, // top right
				};
		NORMAL_DATA_BUFFER = ByteBuffer.allocateDirect(normals.length * Consts.BYTES_PER_FLOAT)
		                               .order(ByteOrder.nativeOrder())
		                               .asFloatBuffer()
		                               .put(normals);
		NORMAL_DATA_BUFFER.position(0);
	}
}
