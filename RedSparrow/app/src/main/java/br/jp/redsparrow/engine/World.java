package br.jp.redsparrow.engine;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.jp.redsparrow.engine.entities.Entity;
import br.jp.redsparrow.engine.entities.EntityType;
import br.jp.redsparrow.engine.math.Mat4;
import br.jp.redsparrow.engine.math.Vec2;
import br.jp.redsparrow.engine.misc.AutoArray;
import br.jp.redsparrow.engine.physics.AABB;
import br.jp.redsparrow.engine.physics.Particle;
import br.jp.redsparrow.engine.physics.Quadtree;
import br.jp.redsparrow.engine.rendering.LightPointShader;
import br.jp.redsparrow.engine.rendering.Quad;
import br.jp.redsparrow.engine.rendering.SimpleShader;
import br.jp.redsparrow.engine.rendering.Sprite;
import br.jp.redsparrow.engine.rendering.SpriteShader;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class World
{
	private static final String  TAG         = "World";
	public static        boolean initialized = false;

	public static final Mat4 modelMatrix      = new Mat4();
	public static final Mat4 viewMatrix       = new Mat4();
	public static final Mat4 projectionMatrix = new Mat4();
	public static final Mat4 MVPMatrix        = new Mat4();

	public static SpriteShader     spriteShader;
	public static LightPointShader lightPointShader;
	public static SimpleShader     simpleShader;

	public static Entity player;
	public static final AutoArray<EntityType>              entityTypes = new AutoArray<EntityType>();
	public static final AutoArray<Entity>                  entities    = new AutoArray<Entity>();
	public static final Quadtree                           quadtree    = new Quadtree(0, new Vec2(- 10, - 10), new Vec2(10, 10));
	public static final Map<EntityType, AutoArray<Entity>> renderMap   = new HashMap<EntityType, AutoArray<Entity>>();

	private static final float[] lightModelMatrix     = new float[16];
	private static final float[] lightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f}; //For light rendering
	private static final float[] lightPosInWorldSpace = new float[4];  //modelMatrix matrix * pos in modelMatrix space
	private static final float[] lightPosInEyeSpace   = new float[4]; //pos in world * viewMatrix matrix

	private static final float ENTITIES_LOCATION_Z = - 5.0f;
	private static final float CAMERA_LOCATION_Z   = 1.0f;
	private static final float CAMERA_LOOK_Z       = - 1.0f;
	private static final float LIGHT_LOCATION_Z    = - 4.5f;
	private static final float QUADTREE_HALF_SIZE  = 25.0f;

	public static void init ()
	{
		Log.d(TAG, TAG + " init");
		initialized = true;

		//----------------// TESTE //----------------------------------------------------------------------------------------//
		final Random r            = new Random();
		final Sprite playerSprite = new Sprite("white_tri", 1, 1);
		EntityType   playerType   = new EntityType(playerSprite);
		addEntityType(playerType);
		player = new Entity();
		player.loc = new Vec2(0, 0);
		player.scl = new Vec2(1.5f, 1.5f);
		player.setType((short) 0);
		player.setMass(10.0f);
		player.setCurrentTexCoords(playerType, 0);
		player.setCurrentColors(RGBA.BLUE, new RGBA(0.9f, 0.9f, 1.0f, 1.0f), new RGBA(0.9f, 0.9f, 1.0f, 1.0f), RGBA.BLUE);
		player.controller = new Entity.Controller()
		{
			@Override
			public void update (Entity parent, float delta)
			{
				parent.integrate(delta);
				parent.rot = (float) Math.toDegrees(Math.atan2(parent.vel.y, parent.vel.x) - 1.5707963268d);
			}
		};

		final Sprite enemySprite = new Sprite("white_tri", 1, 1);
		EntityType entityType1 = new EntityType(enemySprite);
		addEntityType(entityType1);
		int qd = 0;
		for (int i = 0; i < 300; i++)
		{
			Entity entity = new Entity();
			final Vec2 acl;
			if(qd == 4) qd = 0;
			switch (qd)
			{
				default:
				case 0:
					acl = new Vec2(-1 * (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)), (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)));
					entity.loc = new Vec2(-1 * (2 + r.nextInt(5)), 2 + r.nextInt(5));
					break;
				case 1:
					acl = new Vec2((r.nextFloat() * r.nextInt(50) / r.nextInt(100000)), (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)));
					entity.loc = new Vec2(2 + r.nextInt(5), 2 + r.nextInt(5));
					break;
				case 2:
					acl = new Vec2(-1 * (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)), -1 * (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)));
					entity.loc = new Vec2(-1 * (2 + r.nextInt(5)),-1 * (2 + r.nextInt(5)));
					break;
				case 3:
					acl = new Vec2((r.nextFloat() * r.nextInt(50) / r.nextInt(100000)), -1 * (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)));
					entity.loc = new Vec2(2 + r.nextInt(5), -1 * (2 + r.nextInt(5)));
					break;
			}
			qd++;

			entity.scl = new Vec2(2,2);
			entity.setType((short) 1);
			entity.setCurrentTexCoords(entityType1, r.nextInt(11));
			entity.setCurrentColors(RGBA.RED,new RGBA(1.0f, 0.9f, 0.9f, 1.0f),new RGBA(1.0f, 0.9f, 0.9f, 1.0f),RGBA.RED);
			entity.controller = new Entity.Controller()
			{
				private final Vec2 acel = acl;

				@Override
				public void update (Entity parent, float delta)
				{
//					Vec2.add(parent.vel, parent.acl, parent.vel);
//					parent.loc.x = parent.loc.x + parent.vel.x;
//					parent.loc.y = parent.loc.y + parent.vel.y;
					parent.integrate(delta);
					parent.rot = (float) Math.toDegrees(Math.atan2(parent.vel.y, parent.vel.x) - 1.5707963268d);
					parent.acl.set(acel.x, acel.y);
				}
			};
			addEntity(entity);
		}
		//----------------// TESTE //----------------------------------------------------------------------------------------//

		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glLineWidth(10.0f);

		spriteShader = new SpriteShader();
		lightPointShader = new LightPointShader();
		simpleShader = new SimpleShader();

		Matrix.setLookAtM(viewMatrix.values, 0,
		                  player.loc.x, player.loc.y, CAMERA_LOCATION_Z,
		                  player.loc.x, player.loc.y, CAMERA_LOOK_Z,
		                  0.0f, 1.0f, 0.0f);
	}

	public static void resize (int width, int height)
	{
		GLES20.glViewport(0, 0, width, height);

		final float ratio  = (float) width / height;
//		final float left   = - ratio;
//		final float right  = ratio;
//		final float bottom = - 1.0f;
//		final float top    = 1.0f;
		final float near   = 1.0f;
		final float far    = 1000.0f;

//		projectionMatrix.setOrtho(left, right, bottom, top, near, far);
//		projectionMatrix.setFrustum(left, right, bottom, top, near, far);
		projectionMatrix.setPerspective(90.0f, ratio, near, far);
	}
	static float y = 0.0f;
	public static void update (final float delta)
	{
		quadtree.clear(true);
		renderMap.clear();

		player.update(delta);
//		if(player.loc.x < quadtree.bounds.min.x || player.loc.y < quadtree.bounds.min.y ||
//		   player.loc.x > quadtree.bounds.max.x || player.loc.y > quadtree.bounds.max.y )
		quadtree.bounds = new AABB(new Vec2(player.loc.x - QUADTREE_HALF_SIZE, player.loc.y - QUADTREE_HALF_SIZE),
		                           new Vec2(player.loc.x + QUADTREE_HALF_SIZE, player.loc.y + QUADTREE_HALF_SIZE));
//		Log.d(TAG, "QD: " + quadtree.getQuadrant(player.loc));
		quadtree.add(player);

		for (int i = 0; i < entities.size; i++)
		{
			final Entity e = entities.get(i);
			if(e != null)
			{
				//TODO: Spatial Partitioning
				if(AABB.isInside(e.loc, quadtree.bounds.min, quadtree.bounds.max))
				{
					e.update(delta);
					quadtree.add(e);

					//Render map
					short typeId = e.getType();
					EntityType type = entityTypes.get(typeId);
					if(!renderMap.containsKey(type)) renderMap.put(type, new AutoArray<Entity>());
					renderMap.get(type).add(e);
				}
			}
		}

		AutoArray<Quadtree.Member> res = new AutoArray<Quadtree.Member>();
		quadtree.query(player.loc, res);
		Log.d(TAG, "Query size: " + res.size);

		/// Camera Update
		Matrix.setLookAtM(viewMatrix.values, 0,
		                  player.loc.x, player.loc.y, CAMERA_LOCATION_Z + y,
		                  player.loc.x, player.loc.y, CAMERA_LOOK_Z,
		                  0.0f, 1.0f, 0.0f);
		if(CAMERA_LOCATION_Z + y < 2.0f) y += 0.01f;
	}

	public static void render ()
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

		// Do a complete rotation every 10 seconds.
		long  time           = SystemClock.uptimeMillis() % 10000L;
		float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

		spriteShader.use();
		spriteShader.getLocations();

		Matrix.setIdentityM(lightModelMatrix, 0);
//		y += 0.001f;
		Matrix.translateM(lightModelMatrix, 0, player.loc.x, player.loc.y, LIGHT_LOCATION_Z);
		Matrix.multiplyMV(lightPosInWorldSpace, 0, lightModelMatrix, 0, lightPosInModelSpace, 0);
		Matrix.multiplyMV(lightPosInEyeSpace, 0, viewMatrix.values, 0, lightPosInWorldSpace, 0);

		spriteShader.setPositionAttribute(Quad.POSITION_DATA_BUFFER);
		spriteShader.setNormalAttribute(Quad.NORMAL_DATA_BUFFER);
//		for (int i = 0; i < entities.size; i++)
//			renderEntity(entities.get(i));
		for (EntityType type : renderMap.keySet())
		{
			spriteShader.setTextureUniform(type.sprite.textureId);

			AutoArray<Entity> es = renderMap.get(type);
			for (int i = 0; i < es.size; i++)
			{
				Entity e = es.get(i);

				spriteShader.setColorAttribute(e.colorDataBuffer);
				spriteShader.setTexCoordAttribute(e.texCoordDataBuffer);

				modelMatrix.identity();
				modelMatrix.setTranslation(e.loc.x, e.loc.y, ENTITIES_LOCATION_Z);
				modelMatrix.setTranslation(type.sprite.offset.x, type.sprite.offset.y, 0.0f);
				Matrix.rotateM(modelMatrix.values, 0, e.rot, 0, 0, 1);
				Matrix.scaleM(modelMatrix.values, 0, e.scl.x, e.scl.y, 1.0f);

				Matrix.multiplyMM(MVPMatrix.values, 0, viewMatrix.values, 0, modelMatrix.values, 0);
				spriteShader.setMVMatrixUniform(MVPMatrix.values);

				Matrix.multiplyMM(MVPMatrix.values, 0, projectionMatrix.values, 0, MVPMatrix.values, 0);
				spriteShader.setMVPMatrixUniform(MVPMatrix.values);

				spriteShader.setLightPosEyeSpaceUniform(lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);

				GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, Quad.INDEX_DATA_BUFFER);
			}
		}

//		for (short typeId : entityTypeEntityRenderListMap.keySet())
//		{
//			EntityType type = entityTypes.get(typeId);
//
//			spriteShader.setTextureUniform(type.sprite.textureId);
//
//			List<Integer> entityIds = entityTypeEntityRenderListMap.get(typeId);
//			for (int i = 0; i < entityIds.size(); i++)
//			{
//				final Entity e = entities.get(entityIds.get(i));
//
//				spriteShader.setColorAttribute(e.colorDataBuffer);
//
//				spriteShader.setTexCoordAttribute(e.texCoordDataBuffer);
//
//				modelMatrix.identity();
//				modelMatrix.setTranslation(e.loc.x, e.loc.y, - 5.0f);
//				modelMatrix.setTranslation(type.sprite.offset.x, type.sprite.offset.y, 0.0f);
//				Matrix.rotateM(modelMatrix.values, 0, e.rot, 0, 0, 1);
//				Matrix.scaleM(modelMatrix.values, 0, e.scl.x, e.scl.y, 1.0f);
//
//				Matrix.multiplyMM(MVPMatrix.values, 0, viewMatrix.values, 0, modelMatrix.values, 0);
//				spriteShader.setMVMatrixUniform(MVPMatrix.values);
//
//				Matrix.multiplyMM(MVPMatrix.values, 0, projectionMatrix.values, 0, MVPMatrix.values, 0);
//				spriteShader.setMVPMatrixUniform(MVPMatrix.values);
//
//				spriteShader.setLightPosEyeSpaceUniform(lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);
//
//				GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, Quad.INDEX_DATA_BUFFER);
//			}
//		}

		renderEntity(player);

		simpleShader.use();
		simpleShader.getLocations();
		quadtree.dbgDraw();
	}

	private static void renderEntity (Entity e)
	{
		EntityType type = entityTypes.get(e.getType());
		if(type != null)
		{
			spriteShader.setTextureUniform(type.sprite.textureId);

			spriteShader.setColorAttribute(e.colorDataBuffer);

			spriteShader.setTexCoordAttribute(e.texCoordDataBuffer);

			modelMatrix.identity();
			modelMatrix.setTranslation(e.loc.x, e.loc.y, ENTITIES_LOCATION_Z);
			modelMatrix.setTranslation(type.sprite.offset.x, type.sprite.offset.y, 0.0f);
			Matrix.rotateM(modelMatrix.values, 0, e.rot, 0, 0, 1);
			Matrix.scaleM(modelMatrix.values, 0, e.scl.x, e.scl.y, 1.0f);

			Matrix.multiplyMM(MVPMatrix.values, 0, viewMatrix.values, 0, modelMatrix.values, 0);
			spriteShader.setMVMatrixUniform(MVPMatrix.values);

			Matrix.multiplyMM(MVPMatrix.values, 0, projectionMatrix.values, 0, MVPMatrix.values, 0);
			spriteShader.setMVPMatrixUniform(MVPMatrix.values);

			spriteShader.setLightPosEyeSpaceUniform(lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);

			GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, Quad.INDEX_DATA_BUFFER);

//			// DBG Bounds
//			simpleShader.use();
//			simpleShader.getLocations();
//
//			simpleShader.setMVPMatrixUniform(MVPMatrix.values);
//
//			final Vec2 min = e.getMin();
//			final Vec2 max = e.getMax();
//			final float[] positions =
//					{
//							min.x, min.y,
//							min.x, max.y,
//							max.x, max.y,
//							max.x, min.y,
//							min.x, min.y
//					};
//			final FloatBuffer positionBuffer = ByteBuffer.allocateDirect(positions.length * Consts.BYTES_PER_FLOAT)
//			                                             .order(ByteOrder.nativeOrder())
//			                                             .asFloatBuffer()
//			                                             .put(positions);
//			World.simpleShader.setPositionAttribute(positionBuffer);
//			final float[] colors =
//					{
//							0,0,1,
//							0,0,1,
//							0,0,1,
//							0,0,1,
//							0,0,1
//					};
//			FloatBuffer colorBuffer = ByteBuffer.allocateDirect(colors.length * Consts.BYTES_PER_FLOAT)
//			                                    .order(ByteOrder.nativeOrder())
//			                                    .asFloatBuffer()
//			                                    .put(colors);
//			simpleShader.setColorAttribute(colorBuffer);
//			GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 5);
		}
	}

	private static void drawLight ()
	{
		lightPointShader.use();
		lightPointShader.getLocations();

		modelMatrix.identity();
		Matrix.translateM(modelMatrix.values, 0, player.loc.x, player.loc.y, 0.0f);
		Matrix.multiplyMM(MVPMatrix.values, 0, viewMatrix.values, 0, modelMatrix.values, 0);
		Matrix.multiplyMM(MVPMatrix.values, 0, projectionMatrix.values, 0, MVPMatrix.values, 0);
		lightPointShader.setMVPMatrixUniform(MVPMatrix.values);

		GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
	}

	public static int addEntityType (final EntityType type)
	{
		int id = entityTypes.add(type);
		return id;
	}

	public static int addEntity (final Entity e)
	{
		e.setId(entities.add(e));
		return e.getId();
	}
}
