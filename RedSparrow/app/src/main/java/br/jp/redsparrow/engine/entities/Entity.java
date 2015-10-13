package br.jp.redsparrow.engine.entities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import br.jp.redsparrow.engine.Consts;
import br.jp.redsparrow.engine.RGBA;
import br.jp.redsparrow.engine.math.Vec2;
import br.jp.redsparrow.engine.physics.AABB;
import br.jp.redsparrow.engine.physics.Particle;
import br.jp.redsparrow.engine.physics.Quadtree;
import br.jp.redsparrow.engine.rendering.Sprite;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class Entity extends Particle
{
	public static final int TYPE_BEHAVIOUR_ON = 0x1;

	private int uid;
	public int flags;

	public float rot;
	public float aVel;
	public Vec2 scl;
	public AABB bounds;

	public int         tmIndex;
	public FloatBuffer texCoordDataBuffer;
	public FloatBuffer colorDataBuffer;

	public Controller controller;

	public Entity ()
	{
		uid = -1;
		flags = TYPE_BEHAVIOUR_ON;
		setType((short) -1);
		setId(- 1);
		loc = new Vec2();
		vel = new Vec2();
		acl = new Vec2();
		rot = 0.0f;
		aVel = 0.0f;
		scl = new Vec2(1.0f,1.0f);
		tmIndex = 0;
		texCoordDataBuffer = null;
		colorDataBuffer = null;
		controller = null;
		invMass = 1.0f;
		acumForce = new Vec2();
		lDamp = 0.7f;
		bounds = new AABB();
	}

	public void integrate(final float delta)
	{
		Vec2.add(loc, vel, loc);
//		Vec2.add(loc, vel, delta, loc);

		Vec2 resAcl = acl.copy();
		Vec2.add(resAcl, acumForce, invMass, resAcl);
		Vec2.add(vel, resAcl, vel);
//		Vec2.add(vel, resAcl, delta, vel);
		Vec2.mult(vel, (float) Math.pow(lDamp, delta), vel); //Drag

		acumForce.zero();
		acl.zero();
	}

	public void applyForce(final Vec2 f)
	{
		Vec2.add(acumForce, f, acumForce);
	}

	public void setCurrentTexCoords (EntityType type, int tmIndex)
	{
		Sprite s = type.sprite;
		if(s != null)
		{
			if(tmIndex >= s.textureMaps.length)
				tmIndex = s.textureMaps.length - 1;
			this.tmIndex = tmIndex;
			Sprite.TextureMap tm = s.textureMaps[tmIndex];
			final float[] uvData = {
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

	public void update(float delta) { if(controller!=null) controller.update(this, delta); }

	public void setCurrentColors (RGBA lt, RGBA lb, RGBA rb, RGBA rt)
	{
		final float[] colors =
				{
						lt.r, lt.g, lt.b, lt.a,
						lb.r, lb.g, lb.b, lb.a,
						rb.r, rb.g, rb.b, rb.a,
						rt.r, rt.g, rt.b, rt.a
				};
		colorDataBuffer = ByteBuffer
				.allocateDirect(colors.length * Consts.BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(colors);
		colorDataBuffer.position(0);
	}

	public void setMass(float mass)
	{
		invMass = 1.0f/mass;
	}

	private void setUid(final short type, final int id) { uid = (((int)type) << 24 | id); }

	public void setType (short typeId)
	{
		typeId &= ~ ((~ 0 << (8)));
		setUid(typeId, getId());
	}

	public short getType()
	{
		short type = (short)(uid >> 24);
		type &= ~ ((~ 0 << (8)));
		return type;
	}

	public void setId (int id)
	{
		id &= ~ ((~ 0 << (24)));
		setUid(getType(), id);
	}

	public int getId()
	{
		int id = uid;
		id &= ~ ((~ 0 << (24)));
		return id;
	}

	public static abstract class Controller
	{
		public abstract void update(Entity parent, float delta);
	}
}
