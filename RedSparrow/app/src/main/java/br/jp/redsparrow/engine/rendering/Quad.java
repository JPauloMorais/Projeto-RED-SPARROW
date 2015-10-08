package br.jp.redsparrow.engine.rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import br.jp.redsparrow.engine.Consts;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class Quad
{
	public static final int POSITION_DATA_SIZE = 2;
	public static final int NORMAL_DATA_SIZE   = 2;

	public static final ShortBuffer INDEX_DATA_BUFFER;
	public static final FloatBuffer POSITION_DATA_BUFFER;
	public static final FloatBuffer NORMAL_DATA_BUFFER;

	static
	{
		final short[] indices = {0, 1, 2, 0, 2, 3};
		INDEX_DATA_BUFFER = ByteBuffer.allocateDirect(indices.length * Consts.BYTES_PER_SHORT)
		                              .order(ByteOrder.nativeOrder())
		                              .asShortBuffer()
		                              .put(indices);
		INDEX_DATA_BUFFER.position(0);

		final float[] positions =
				{
						-0.5f,  0.5f, // top left
						-0.5f, -0.5f, // bottom left
						 0.5f, -0.5f, // bottom right
						 0.5f,  0.5f, //top right
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
