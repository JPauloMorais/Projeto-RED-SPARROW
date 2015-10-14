package br.jp.redsparrow.engine;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.lang.reflect.Member;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
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
public abstract class World
{
	protected static final String TAG = "World";

	public boolean initialized = false;

	public final Mat4 modelMatrix      = new Mat4();
	public final Mat4 viewMatrix       = new Mat4();
	public final Mat4 projectionMatrix = new Mat4();
	public final Mat4 MVPMatrix        = new Mat4();

	public SpriteShader     spriteShader;
	public LightPointShader lightPointShader;
	public SimpleShader     simpleShader;

	public       Entity                             player      = new Entity();
	public final AutoArray<EntityType>              entityTypes = new AutoArray<EntityType>();
	public final AutoArray<Entity>                  entities    = new AutoArray<Entity>();
	public final AutoArray<Entity>                  activeEntities = new AutoArray<Entity>();
	public final Quadtree                           quadtree    = new Quadtree(0, new Vec2(- 10, - 10), new Vec2(10, 10));
	public final Map<EntityType, AutoArray<Entity>> renderMap   = new HashMap<EntityType, AutoArray<Entity>>();

	public final float[] lightModelMatrix     = new float[16];
	public final float[] lightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f}; //For light rendering
	public final float[] lightPosInWorldSpace = new float[4];  //modelMatrix matrix * pos in modelMatrix space
	public final float[] lightPosInEyeSpace   = new float[4]; //pos in world * viewMatrix matrix

	public final float ENTITIES_LOCATION_Z = - 5.0f;
	public final float CAMERA_LOCATION_Z   = 10.0f;
	public final float CAMERA_LOOK_Z       = - 1.0f;
	public final float LIGHT_LOCATION_Z    = - 4.5f;
	public final float QUADTREE_HALF_SIZE  = 25.0f;

	protected abstract void init ();
	public abstract void onResize (int width, int height);
	public abstract void onUpdate (final GameActivity gameActivity, final float delta);
	public abstract void onRender ();
	public abstract void pause ();
	public abstract void resume ();
	public abstract void stop ();

	public void onInit ()
	{
		initialized = true;

		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glLineWidth(5.0f);

		spriteShader = new SpriteShader();
		lightPointShader = new LightPointShader();
		simpleShader = new SimpleShader();

		Matrix.setLookAtM(viewMatrix.values, 0,
		                  player.loc.x, player.loc.y, 10.0f,
		                  player.loc.x, player.loc.y, -1.0f,
		                  0.0f, 1.0f, 0.0f);

		init();
	}

	public int addEntityType (final EntityType type)
	{
		if(initialized)
		{
			type.sprite.loadTexture();
			int id = entityTypes.add(type);
			return id;
		}
		else return -1;
	}

	public int addEntity (final Entity e)
	{
		e.setId(entities.add(e));
		return e.getId();
	}

	protected void renderEntity (Entity e)
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
		}
	}

	protected void drawLight ()
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

	protected void drawBounds (Entity e)
	{
		//DBG Bounds
		simpleShader.use();
		simpleShader.getLocations();
		Vec2 min = new Vec2(e.loc.x - (0.5f * e.scl.x), e.loc.y - (0.5f * e.scl.y));
		Vec2 max = new Vec2(e.loc.x + (0.5f * e.scl.x), e.loc.y + (0.5f * e.scl.y));

		modelMatrix.identity();
		Matrix.translateM(modelMatrix.values, 0, 0, 0, ENTITIES_LOCATION_Z);
		Matrix.multiplyMM(MVPMatrix.values, 0, viewMatrix.values, 0, modelMatrix.values, 0);
		Matrix.multiplyMM(MVPMatrix.values, 0, projectionMatrix.values, 0, MVPMatrix.values, 0);
		simpleShader.setMVPMatrixUniform(MVPMatrix.values);

		final float[] positions =
				{
						min.x, min.y,
						min.x, max.y,
						max.x, max.y,
						max.x, min.y,
						min.x, min.y
				};
		final FloatBuffer positionBuffer = ByteBuffer.allocateDirect(positions.length * Consts.BYTES_PER_FLOAT)
		                                             .order(ByteOrder.nativeOrder())
		                                             .asFloatBuffer()
		                                             .put(positions);
		simpleShader.setPositionAttribute(positionBuffer);
		final float[] colors =
				{
						0,0,1, 1.0f,
						0,0,1, 1.0f,
						0,0,1, 1.0f,
						0,0,1, 1.0f,
						0,0,1, 1.0f
				};
		FloatBuffer colorBuffer = ByteBuffer.allocateDirect(colors.length * Consts.BYTES_PER_FLOAT)
		                                    .order(ByteOrder.nativeOrder())
		                                    .asFloatBuffer()
		                                    .put(colors);
		simpleShader.setColorAttribute(colorBuffer);
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 5);
	}
}
