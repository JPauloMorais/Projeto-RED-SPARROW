package br.jp.redsparrow.engine.physics;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import br.jp.redsparrow.engine.Consts;
import br.jp.redsparrow.engine.World;
import br.jp.redsparrow.engine.math.Vec2;
import br.jp.redsparrow.engine.misc.AutoArray;

/**
 * Created by JoaoPaulo on 11/10/2015.
 */
public class Quadtree
{
	public static final int POINT_OUT_OF_BOUNDS = - 2;
	public static final int IN_BETWEEN          = - 1;

	private static final int MAX_MEMBERS = 10;
	private static final int MAX_LEVELS  = 4;

	private int  level;
	public  AABB bounds;
	private final AutoArray<Member> members = new AutoArray<Member>(MAX_MEMBERS);
	private final Quadtree[]   nodes   = new Quadtree[4];

	public Quadtree (int level, final Vec2 min, final Vec2 max)
	{
		this.level = level;
		this.bounds = new AABB(min, max);
	}

	/*--------------max
	* |       |       |
	* |   0   |   1   |
	* |       |       |
	* --------+--------
	* |       |       |
	* |   2   |   3   |
	* |       |       |
	* min--------------
	* */
	private void split ()
	{
		final float halfWidth  = (bounds.max.x - bounds.min.x) * 0.5f;
		final float halfHeight = (bounds.max.y - bounds.min.y) * 0.5f;
		final float middleX = bounds.min.x + halfWidth;
		final float middleY = bounds.min.y + halfHeight;
		final int level = this.level + 1;

		nodes[0] = new Quadtree(level, new Vec2(bounds.min.x, middleY),      new Vec2(middleX, bounds.max.y));
		nodes[1] = new Quadtree(level, new Vec2(middleX, middleY),           new Vec2(bounds.max.x, bounds.max.y));
		nodes[2] = new Quadtree(level, new Vec2(bounds.min.x, bounds.min.y), new Vec2(middleX, middleY));
		nodes[3] = new Quadtree(level, new Vec2(middleX, bounds.min.y),      new Vec2(bounds.max.x, middleY));
	}

	public int getQuadrant(final Vec2 p)
	{
		final float halfWidth = (bounds.max.x - bounds.min.x) * 0.5f;
		final float halfHeight = (bounds.max.y - bounds.min.y) * 0.5f;
		final float middleX = bounds.min.x + halfWidth;
		final float middleY = bounds.min.y + halfHeight;

		final boolean upper = p.y >= middleY;
		final boolean lower = p.y < middleY;

		int qd = IN_BETWEEN;
		if(p.x <= middleX)
		{
			if(upper) qd = 0;
			else if(lower) qd = 2;
		}
		else if(p.x > middleX)
		{
			if(upper) qd = 1;
			else if(lower) qd = 3;
		}

		return qd;
	}

//	public void add (M m)
//	{
//		if(nodes[0] != null)
//		{
//			final Vec2 min = m.getMin();
//			final Vec2 max = m.getMax();
//			final int index = getQuadrant(min,max);
//
//			if(index != -1)  nodes[index].add(m);
//		}
//
//		members.add(m);
//
//		if(members.size > MAX_MEMBERS && level < MAX_LEVELS)
//		{
//			if(nodes[0] == null) split();
//
//			for (int i = 0; i < members.size; i++)
//			{
//				final M member = members.get(i);
//				final Vec2 min = member.getMin();
//				final Vec2 max = member.getMax();
//				final int index = getQuadrant(min,max);
//
//				if(index != -1)
//				{
//					nodes[index].add(member);
//					members.remove(i);
//				}
//			}
//		}
//	}
//
//	public void query (final Vec2 min, final Vec2 max, AutoArray<M> result)
//	{
//		final int qd = getQuadrant(min,max);
//		if(qd != -1 && nodes[0] != null) nodes[qd].query(min,max,result);
//
//		result.add(members);
//	}

	public void add (Member m)
	{
		if(nodes[0] != null)
		{
			final Vec2 p = m.getCenter();
			final int index = getQuadrant(p);

			if(index != IN_BETWEEN) nodes[index].add(m);
		}

		members.add(m);

		if(members.size > MAX_MEMBERS && level < MAX_LEVELS)
		{
			if(nodes[0] == null) split();

			for (int i = 0; i < members.size; i++)
			{
				final Member member = members.get(i);
				final Vec2 p = member.getCenter();
				final int qd = getQuadrant(p);

				if(qd != IN_BETWEEN)
				{
					nodes[qd].add(member);
					members.remove(i);
				}
			}
		}
	}

	public void query (final Vec2 p, AutoArray<Member> result)
	{
		final int qd = getQuadrant(p);
		if(qd != IN_BETWEEN && nodes[0] != null)
			nodes[qd].query(p,result);

		result.add(members);
	}

	public void dbgDraw()
	{
		World.modelMatrix.identity();
		Matrix.translateM(World.modelMatrix.values, 0, 0, 0, -5.0f);
		Matrix.multiplyMM(World.MVPMatrix.values, 0, World.viewMatrix.values, 0, World.modelMatrix.values, 0);
		Matrix.multiplyMM(World.MVPMatrix.values, 0, World.projectionMatrix.values, 0, World.MVPMatrix.values, 0);
		World.simpleShader.setMVPMatrixUniform(World.MVPMatrix.values);

		final float[] positions =
				{
						bounds.min.x, bounds.min.y,
						bounds.min.x, bounds.max.y,
						bounds.max.x, bounds.max.y,
						bounds.max.x, bounds.min.y,
						bounds.min.x, bounds.min.y
				};
		final FloatBuffer positionBuffer = ByteBuffer.allocateDirect(positions.length * Consts.BYTES_PER_FLOAT)
		                                             .order(ByteOrder.nativeOrder())
		                                             .asFloatBuffer()
		                                             .put(positions);
		World.simpleShader.setPositionAttribute(positionBuffer);
		final float[] colors =
				{
						1,0,0, 0.1f,
						1,0,0, 0.1f,
						1,0,0, 0.1f,
						1,0,0, 0.1f,
						1,0,0, 0.1f
				};
		FloatBuffer colorBuffer = ByteBuffer.allocateDirect(colors.length * Consts.BYTES_PER_FLOAT)
		                                    .order(ByteOrder.nativeOrder())
		                                    .asFloatBuffer()
		                                    .put(colors);
		World.simpleShader.setColorAttribute(colorBuffer);
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 5);

		if(nodes[0] != null)
			for (int i = 0; i < 4; i++) nodes[i].dbgDraw();
	}

	public void clear(boolean trimBranches)
	{
		members.clear();
		if(nodes[0] != null)
		{
			for (int i = 0; i < 4; i++)
			{
				if(trimBranches) nodes[i] = null;
				else nodes[i].clear(false);
			}
		}
	}

	public interface Member
	{
		Vec2 getCenter();
	}
}
