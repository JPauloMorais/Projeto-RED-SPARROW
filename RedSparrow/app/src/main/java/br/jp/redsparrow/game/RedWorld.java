package br.jp.redsparrow.game;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import br.jp.redsparrow.engine.Consts;
import br.jp.redsparrow.engine.GameActivity;
import br.jp.redsparrow.engine.RGBA;
import br.jp.redsparrow.engine.World;
import br.jp.redsparrow.engine.entities.Entity;
import br.jp.redsparrow.engine.entities.EntityType;
import br.jp.redsparrow.engine.math.Vec2;
import br.jp.redsparrow.engine.misc.AutoArray;
import br.jp.redsparrow.engine.physics.AABB;
import br.jp.redsparrow.engine.physics.Quadtree;
import br.jp.redsparrow.engine.rendering.Quad;
import br.jp.redsparrow.engine.rendering.Sprite;

/**
 * Created by JoaoPaulo on 14/10/2015.
 */
public class RedWorld extends World
{
	@Override
	protected void init ()
	{
		FloatBuffer particleDataBuffer = ByteBuffer
				.allocateDirect(10 * 5 * Consts.BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		particleDataBuffer.position(1);
		particleDataBuffer.put(new float[]
				                       {
						                       0.0f,
						                       1.0f,
						                       2.0f
				                       });
		Log.d(TAG, "Particles: " + particleDataBuffer.get(2));

		final Random r            = new Random();
		final Sprite playerSprite = new Sprite("white_tri", 1, 1);
		EntityType   playerType   = new EntityType(playerSprite);
		addEntityType(playerType);
		player = new Entity();
		player.loc = new Vec2(0, 0);
		player.scl = new Vec2(1.5f, 1.5f);
		player.setType((short) 0);
		player.setMass(10.0f);
		player.maxLinerVel = player.scl.magnitude() * 500.0f;
		player.setCurrentTexCoords(playerType, 0);
		player.setCurrentColors(RGBA.BLUE, new RGBA(0.9f, 0.9f, 1.0f, 1.0f), new RGBA(0.9f, 0.9f, 1.0f, 1.0f), RGBA.BLUE);
		player.controller = new Entity.Controller()
		{
			@Override
			public void update (GameActivity gameActivity, Entity parent, float delta)
			{
				parent.integrate(delta);
				parent.rot = (float) Math.toDegrees(Math.atan2(parent.vel.y, parent.vel.x) - 1.5707963268d);
			}
		};

		final Sprite enemySprite = new Sprite("white_tri", 1, 1);
		final EntityType enemyType = new EntityType(enemySprite);
		enemyType.controller = new Entity.Controller()
		{
			int totalCount = 0;
			int count = 0;

			@Override
			public void update (GameActivity gameActivity, Entity parent, float delta)
			{
				parent.integrate(delta);
				parent.rot = (float) Math.toDegrees(Math.atan2(parent.vel.y, parent.vel.x) - 1.5707963268d);
				Vec2 runDir = new Vec2();
				Vec2.sub(player.loc, parent.loc, runDir);
				if(runDir.magnitude() >= 3.0f)
				{
					runDir.mult(runDir, 10.0f);
					runDir.add(runDir, r.nextInt(10));
					parent.acl.set(runDir.x, runDir.y);
				}

				if(totalCount <= 240000 && count == 800)
				{
					Entity entity = new Entity();
					Vec2.add(parent.loc, new Vec2(2, - 2), entity.loc);
					entity.scl = new Vec2(2, 2);
					entity.setType((short) 1);
					entity.setCurrentTexCoords(enemyType, r.nextInt(11));
					entity.setCurrentColors(RGBA.RED, new RGBA(1.0f, 0.9f, 0.9f, 1.0f), new RGBA(1.0f, 0.9f, 0.9f, 1.0f), RGBA.RED);
					addEntity(entity);
					totalCount += count;
					count = 0;
				}
				count++;
			}
		};
		addEntityType(enemyType);

		int qd = 0;
		for (int i = 0; i < 4; i++)
		{
			Entity entity = new Entity();
//			entity.flags = 0;
			final Vec2 acl;
			if(qd == 4) qd = 0;
			switch (qd)
			{
				default:
				case 0:
					acl = new Vec2(- 1 * (r.nextInt(50)), (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)));
					entity.loc = new Vec2(- 1 * (2 + r.nextInt(5)), 2 + r.nextInt(5));
					break;
				case 1:
					acl = new Vec2((r.nextFloat() * r.nextInt(50) / r.nextInt(10)), (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)));
					entity.loc = new Vec2(2 + r.nextInt(5), 2 + r.nextInt(5));
					break;
				case 2:
					acl = new Vec2(- 1 * (r.nextFloat() * r.nextInt(50) / r.nextInt(10)), - 1 * (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)));
					entity.loc = new Vec2(- 1 * (2 + r.nextInt(5)), - 1 * (2 + r.nextInt(5)));
					break;
				case 3:
					acl = new Vec2((r.nextFloat() * r.nextInt(50) / r.nextInt(10)), - 1 * (r.nextFloat() * r.nextInt(50) / r.nextInt(100000)));
					entity.loc = new Vec2(2 + r.nextInt(5), - 1 * (2 + r.nextInt(5)));
					break;
			}
			qd++;

			entity.scl = new Vec2(2, 2);
			entity.setType((short) 1);
			entity.setCurrentTexCoords(enemyType, 0);
			entity.setCurrentColors(RGBA.RED, new RGBA(1.0f, 0.9f, 0.9f, 1.0f), new RGBA(1.0f, 0.9f, 0.9f, 1.0f), RGBA.RED);
//			entity.controller = new Entity.Controller()
//			{
//				private final Vec2 acel = acl;
//
//				@Override
//				public void update (GameActivity gameActivity, Entity parent, float delta)
//				{
//					parent.acl.set(acel.x, acel.y);
//				}
//			};
			addEntity(entity);
		}
	}

	@Override
	public void onResize (int width, int height)
	{
		GLES20.glViewport(0, 0, width, height);

		final float ratio = (float) width / height;
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

	@Override
	public void onUpdate (GameActivity gameActivity, float delta)
	{
		quadtree.clear(true);
		renderMap.clear();

		player.update(gameActivity,delta);
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
				short typeId = e.getType();
				EntityType type = entityTypes.get(typeId);
				//Type behaviour
				if((type.controller != null) && ((e.flags & Entity.TYPE_BEHAVIOUR_ON) != 0))
					type.controller.update(gameActivity,e,delta);
				//TODO: Spatial sparseness scheme?
				e.update(gameActivity,delta);

				if(AABB.isInside(e.loc, quadtree.bounds.min, quadtree.bounds.max))
				{
					quadtree.add(e);

					//Render map
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
		                  player.loc.x, player.loc.y, CAMERA_LOCATION_Z,
		                  player.loc.x, player.loc.y, CAMERA_LOOK_Z,
		                  0.0f, 1.0f, 0.0f);
//		if(CAMERA_LOCATION_Z + y < 2.0f) y += 0.01f;

		Log.d(TAG, "Entity Count: " + entities.size);
	}

	@Override
	public void onRender ()
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

		simpleShader.use();
		simpleShader.getLocations();
		quadtree.dbgDraw(this);

		spriteShader.use();
		spriteShader.getLocations();

		Matrix.setIdentityM(lightModelMatrix, 0);
		Matrix.translateM(lightModelMatrix, 0, player.loc.x, player.loc.y, LIGHT_LOCATION_Z);
		Matrix.multiplyMV(lightPosInWorldSpace, 0, lightModelMatrix, 0, lightPosInModelSpace, 0);
		Matrix.multiplyMV(lightPosInEyeSpace, 0, viewMatrix.values, 0, lightPosInWorldSpace, 0);

		spriteShader.setPositionAttribute(Quad.POSITION_DATA_BUFFER);
		spriteShader.setNormalAttribute(Quad.NORMAL_DATA_BUFFER);

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

		renderEntity(player);
	}

	@Override
	public void pause ()
	{

	}

	@Override
	public void resume ()
	{

	}

	@Override
	public void stop ()
	{

	}
}
