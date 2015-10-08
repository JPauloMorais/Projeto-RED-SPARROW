package br.jp.redsparrow.engine;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.jp.redsparrow.engine.entities.Entity;
import br.jp.redsparrow.engine.entities.EntityType;
import br.jp.redsparrow.engine.math.Mat4;
import br.jp.redsparrow.engine.math.Vec3;
import br.jp.redsparrow.engine.misc.AutoArray;
import br.jp.redsparrow.engine.rendering.BasicShader;
import br.jp.redsparrow.engine.rendering.Quad;
import br.jp.redsparrow.engine.rendering.Sprite;

/**
 * Created by JoaoPaulo on 07/10/2015.
 */
public class World
{
	private static final String  TAG         = "World";
	public static        boolean initialized = false;

	public static        Mat4    model      = new Mat4();
	public static        Mat4    view       = new Mat4();
	public static        Mat4    projection = new Mat4();
	public static        Mat4    mvp        = new Mat4();
	private static final float[] tempMatrix = new float[16];

	public static BasicShader basicShader;

	public static Entity player;
	public static AutoArray<EntityType> entityTypes = new AutoArray<EntityType>();
	public static AutoArray<Entity>     entities    = new AutoArray<Entity>();

	private static Map<Integer, List<Integer>> entityTypeEntityRenderListMap = new HashMap<Integer, List<Integer>>();

	private static float[] mLightModelMatrix = new float[16];

	private static int mMVPMatrixHandle;
	private static int mMVMatrixHandle;
	private static int mLightPosHandle;
	private static int mTextureUniformHandle;
	private static int mPositionHandle;
	private static int mColorHandle;
	private static int mNormalHandle;
	private static int mTextureCoordinateHandle;

	private static final float[] mLightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
	private static final float[] mLightPosInWorldSpace = new float[4];
	private static final float[] mLightPosInEyeSpace   = new float[4];

	private static int mProgramHandle;
	private static int mPointProgramHandle;

	public static void init ()
	{
		Log.d(TAG, TAG + " init");
		initialized = true;

//		Entity e = new Entity(new Vec3(0, 0, 0), 0, new Vec3(1, 1, 1));
//		e.sprite = 0;
//		addEntity(e);
//
//		Sprite s = new Sprite("imgs/test.jpg", 1, 1);
//		addSprite(s);

		// X, Y, Z

		final Sprite testSprite = new Sprite("red_sparrow_test_2", 5, 2);
		EntityType   entityType = new EntityType(testSprite);
		addEntityType(entityType);
		player = new Entity();
		player.loc = new Vec3(0, 0, -4.9f);
		player.rot = 0;
		player.scl = new Vec3(1, 1, 1);
		player.typeId = 0;
		player.setCurrentTexCoords(entityType, 0);
		player.setCurrentColors(RGB.BLUE, RGB.BLUE, RGB.BLUE, RGB.BLUE);
		player.controller = new Entity.Controller() {
			@Override
			public void update (Entity parent, float delta)
			{
				parent.vel = parent.vel.add(parent.acl);
				parent.loc = parent.loc.add(parent.vel);

//				parent.acl = new Vec3(0.0001f, 0.0f, 0.0f);
			}
		};


		final Sprite testSprite1 = new Sprite("eyes_test", 12, 1);
		EntityType entityType1 = new EntityType(testSprite1);
		addEntityType(entityType1);
		final Random r = new Random();
		for (int i = 0; i < 200; i++)
		{
			Entity entity = new Entity();
			entity.loc = new Vec3((i%2!=0 ? -1 : 1) * r.nextInt(4),
			                      (i%2==0 ? -1 : 1) * r.nextInt(4),
			                      -5 + r.nextInt(100)/100);
			entity.scl = new Vec3(1,1,1);
			entity.rot = r.nextFloat() * r.nextInt();
			entity.typeId = 1;
			entity.setCurrentTexCoords(entityType1, r.nextInt(12));
			entity.setCurrentColors(new RGB(0.5f + r.nextFloat(), 0.5f + r.nextFloat(), 0.5f + r.nextFloat()),
			                        new RGB(0.5f + r.nextFloat(), 0.5f + r.nextFloat(), 0.5f + r.nextFloat()),
			                        new RGB(0.5f + r.nextFloat(), 0.5f + r.nextFloat(), 0.5f + r.nextFloat()),
			                        new RGB(0.5f + r.nextFloat(), 0.5f + r.nextFloat(), 0.5f + r.nextFloat()));
			entity.controller = new Entity.Controller() {
				@Override
				public void update (Entity parent, float delta)
				{
					parent.vel = parent.vel.add(parent.acl);
					parent.loc = parent.loc.add(parent.vel);

					parent.acl = new Vec3((r.nextInt(2)%2==0 ? -1 : 1) * 0.0001f * r.nextFloat(),
					                      (r.nextInt(2)%2==0 ? -1 : 1) *  0.0001f * r.nextFloat(), 0.0f);
				}
			};
			addEntity(entity);
		}

//		final float[] cubeColorData =
//				{
//						1.0f, 0.0f, 1.0f, // top left
//						1.0f, 1.0f, 1.0f, // bottom left
//						1.0f, 0.0f, 0.0f, // bottom right
//						1.0f, 1.0f, 1.0f, // top right
//				};
//		quadColors = ByteBuffer.allocateDirect(cubeColorData.length * Consts.BYTES_PER_FLOAT)
//		                       .order(ByteOrder.nativeOrder()).asFloatBuffer();
//		quadColors.put(cubeColorData).position(0);
//
//		final float[] cubeTextureCoordinateData =
//				{
//						0.0f, 0.0f, // top left
//						0.0f, 1.0f, // bottom left
//						1.0f, 1.0f, // bottom right
//						1.0f, 0.0f, // top right
//				};
//		quadTexCoords = ByteBuffer.allocateDirect(cubeTextureCoordinateData.length * Consts.BYTES_PER_FLOAT)
//		                          .order(ByteOrder.nativeOrder()).asFloatBuffer();
//		quadTexCoords.put(cubeTextureCoordinateData).position(0);

		GLES20.glClearColor(.0f, .0f, .0f, 1);

		GLES20.glEnable(GLES20.GL_CULL_FACE);
//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		final float eyeX = 0.0f;
		final float eyeY = 0.0f;
		final float eyeZ = -0.5f;
		final float lookX = 0.0f;
		final float lookY = 0.0f;
		final float lookZ = -1.0f;
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;
		Matrix.setLookAtM(view.values, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

		final String vertexShader = Assets.getShaderText("BasicVertex");
		final String fragmentShader = Assets.getShaderText("BasicFragment");
		final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
		final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
		mProgramHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
		                                      new String[] {"a_Position", "a_Color", "a_Normal", "a_TexCoordinate"});

		// Define a simple shader program for our point.
		final String pointVertexShader = "uniform mat4 u_MVPMatrix;      \t\t\n" +
		                                 "attribute vec4 a_Position;     \t\t\n" +
		                                 "\n" +
		                                 "void main()                    \n" +
		                                 "{                              \n" +
		                                 "\tgl_Position = u_MVPMatrix * a_Position;   \n" +
		                                 "    gl_PointSize = 5.0;         \n" +
		                                 "}";
		final String pointFragmentShader = "precision mediump float;\n" +
		                                   "       \t\t\t\t\t          \n" +
		                                   "void main()                    \n" +
		                                   "{                              \n" +
		                                   "\tgl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);             \n" +
		                                   "}";
		final int pointVertexShaderHandle   = compileShader(GLES20.GL_VERTEX_SHADER, pointVertexShader);
		final int pointFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, pointFragmentShader);
		mPointProgramHandle = createAndLinkProgram(pointVertexShaderHandle, pointFragmentShaderHandle,
		                                           new String[] {"a_Position"});

		// Load the texture
//		try
//		{
//			final InputStream is = App.getContext().getResources().getAssets().open("sprites/test1.png");
//			mTextureDataHandle = Renderer.loadBitmap(BitmapFactory.decodeStream(is));
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}

//		GLES20.glEnable(GLES20.GL_CULL_FACE);
//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//
//		final float eyeX = 0.0f;
//		final float eyeY = 0.0f;
//		final float eyeZ = -0.5f;
//		final float lookX = 0.0f;
//		final float lookY = 0.0f;
//		final float lookZ = -5.0f;
//		final float upX = 0.0f;
//		final float upY = 1.0f;
//		final float upZ = 0.0f;
//		Matrix.setLookAtM(view.values, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
//
//		for (int i = 0; i < sprites.size; i++)
//		{
//			Sprite sprite = sprites.get(i);
//			if(sprite != null)
//			{
//				sprite.loadTexture();
//				Log.d("Sprite", "texture: " + sprites.get(i).texture);
//			}
//		}
//
//		Quad.vertexVBO = Renderer.toVBO(Quad.vertexPositionBuffer);
//
//		basicShader = new BasicShader();
//
	}

	public static void resize(int width, int height)
	{
		// Set the OpenGL viewport to the same size as the surface.
		GLES20.glViewport(0, 0, width, height);

		// Create a new perspective projection matrix. The height will stay the same
		// while the width will vary as per aspect ratio.
		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 10.0f;

		projection.setFrustum(left, right, bottom, top, near, far);
	}

	public static void update (float delta)
	{
		final float eyeX = player.loc.x;
		final float eyeY = player.loc.y;
		final float eyeZ = -0.5f;
		final float lookX = player.loc.x;
		final float lookY = player.loc.y;
		final float lookZ = player.loc.z;
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;
		Matrix.setLookAtM(view.values, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

		player.update(delta);

		entityTypeEntityRenderListMap.clear();
		for (int i = 0; i < entities.size; i++)
		{
			final Entity e = entities.get(i);
			if(e != null)
			{
				//TODO: Spatial Partitioning
				e.update(delta);

				int type = e.typeId;

				if(!entityTypeEntityRenderListMap.containsKey(type))
				{
					ArrayList<Integer> eIds = new ArrayList<Integer>();
					entityTypeEntityRenderListMap.put(type, eIds);
				}

				entityTypeEntityRenderListMap.get(type).add(e.uid);
			}
		}
	}

	static float x = 0.0f;
	static int cur = 0;
	public static void render ()
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		// Do a complete rotation every 10 seconds.
		long  time           = SystemClock.uptimeMillis() % 10000L;
		float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

		// Set our per-vertex lighting program.
		GLES20.glUseProgram(mProgramHandle);

		// Set program handles for cube drawing.
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_MVPMatrix");
		mMVMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_MVMatrix");
		mLightPosHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_LightPos");
		mTextureUniformHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_Texture");
		mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Position");
		mColorHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Color");
		mNormalHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_Normal");
		mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_TexCoordinate");

		// Calculate position of the light. Rotate and then push into the distance.
		Matrix.setIdentityM(mLightModelMatrix, 0);
		Matrix.translateM(mLightModelMatrix, 0, player.loc.x, player.loc.y, player.loc.z + 0.2f);
//		Matrix.rotateM(mLightModelMatrix, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);
//		Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, 2.0f);

		Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
		Matrix.multiplyMV(mLightPosInEyeSpace, 0, view.values, 0, mLightPosInWorldSpace, 0);

		setupQuadData();
		for (int typeId : entityTypeEntityRenderListMap.keySet())
		{
			EntityType type = entityTypes.get(typeId);

			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, type.sprite.textureId);
			GLES20.glUniform1i(mTextureUniformHandle, 0);

			List<Integer> entityIds = entityTypeEntityRenderListMap.get(typeId);
			for (int i = 0; i < entityIds.size(); i++)
			{
				final Entity e = entities.get(entityIds.get(i));

				e.colorDataBuffer.position(0);
				GLES20.glVertexAttribPointer(mColorHandle, Entity.COLOR_DATA_SIZE, GLES20.GL_FLOAT, false, 0, e.colorDataBuffer);
				GLES20.glEnableVertexAttribArray(mColorHandle);

				e.texCoordDataBuffer.position(0);
				GLES20.glVertexAttribPointer(mTextureCoordinateHandle, Entity.TEXCOORD_DATA_SIZE, GLES20.GL_FLOAT, false, 0, e.texCoordDataBuffer);
				GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

				model.identity();
				model.setTranslation(e.loc);
				Matrix.rotateM(model.values, 0, e.rot, 0, 0, 1);
				Matrix.scaleM(model.values, 0, e.scl.z, e.scl.y, e.scl.z);

				Matrix.multiplyMM(mvp.values, 0, view.values, 0, model.values, 0);
				GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mvp.values, 0);

				Matrix.multiplyMM(mvp.values, 0, projection.values, 0, mvp.values, 0);
				GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvp.values, 0);

				GLES20.glUniform3f(mLightPosHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);

				GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, Quad.INDEX_DATA_BUFFER);
			}
		}

		renderEntity(player);

		GLES20.glUseProgram(mPointProgramHandle);
		drawLight();
	}

	private static void setupQuadData()
	{
		Quad.POSITION_DATA_BUFFER.position(0);
		GLES20.glVertexAttribPointer(mPositionHandle, Quad.POSITION_DATA_SIZE, GLES20.GL_FLOAT, false, 0, Quad.POSITION_DATA_BUFFER);
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		Quad.NORMAL_DATA_BUFFER.position(0);
		GLES20.glVertexAttribPointer(mNormalHandle, Quad.NORMAL_DATA_SIZE, GLES20.GL_FLOAT, false, 0, Quad.NORMAL_DATA_BUFFER);
		GLES20.glEnableVertexAttribArray(mNormalHandle);
	}

	private static void renderEntity(Entity e)
	{
		EntityType type = entityTypes.get(e.typeId);
		if(type != null)
		{
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, type.sprite.textureId);
			GLES20.glUniform1i(mTextureUniformHandle, 0);

			e.colorDataBuffer.position(0);
			GLES20.glVertexAttribPointer(mColorHandle, Entity.COLOR_DATA_SIZE, GLES20.GL_FLOAT, false, 0, e.colorDataBuffer);
			GLES20.glEnableVertexAttribArray(mColorHandle);

			e.texCoordDataBuffer.position(0);
			GLES20.glVertexAttribPointer(mTextureCoordinateHandle, Entity.TEXCOORD_DATA_SIZE, GLES20.GL_FLOAT, false, 0, e.texCoordDataBuffer);
			GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

			model.identity();
			model.setTranslation(e.loc);
			Matrix.rotateM(model.values, 0, e.rot, 0, 0, 1);
			Matrix.scaleM(model.values, 0, e.scl.z, e.scl.y, e.scl.z);

			Matrix.multiplyMM(mvp.values, 0, view.values, 0, model.values, 0);
			GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mvp.values, 0);

			Matrix.multiplyMM(mvp.values, 0, projection.values, 0, mvp.values, 0);
			GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvp.values, 0);

			GLES20.glUniform3f(mLightPosHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);

			GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, Quad.INDEX_DATA_BUFFER);
		}
	}

	private static void drawCube ()
	{
//		Quad.POSITION_DATA_BUFFER.position(0);
//		GLES20.glVertexAttribPointer(mPositionHandle, Quad.POSITION_DATA_SIZE, GLES20.GL_FLOAT, false, 0, Quad.POSITION_DATA_BUFFER);
//		GLES20.glEnableVertexAttribArray(mPositionHandle);
//
//		Quad.NORMAL_DATA_BUFFER.position(0);
//		GLES20.glVertexAttribPointer(mNormalHandle, Quad.NORMAL_DATA_SIZE, GLES20.GL_FLOAT, false, 0, Quad.NORMAL_DATA_BUFFER);
//		GLES20.glEnableVertexAttribArray(mNormalHandle);

		player.colorDataBuffer.position(0);
		GLES20.glVertexAttribPointer(mColorHandle, Entity.COLOR_DATA_SIZE, GLES20.GL_FLOAT, false, 0, player.colorDataBuffer);
		GLES20.glEnableVertexAttribArray(mColorHandle);

		player.texCoordDataBuffer.position(0);
		GLES20.glVertexAttribPointer(mTextureCoordinateHandle, Entity.TEXCOORD_DATA_SIZE, GLES20.GL_FLOAT, false, 0, player.texCoordDataBuffer);
		GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

		Matrix.multiplyMM(mvp.values, 0, view.values, 0, model.values, 0);
		GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mvp.values, 0);

		Matrix.multiplyMM(mvp.values, 0, projection.values, 0, mvp.values, 0);
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvp.values, 0);

		GLES20.glUniform3f(mLightPosHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, Quad.INDEX_DATA_BUFFER);
	}

	private static void drawLight ()
	{
		final int pointMVPMatrixHandle = GLES20.glGetUniformLocation(mPointProgramHandle, "u_MVPMatrix");
		final int pointPositionHandle  = GLES20.glGetAttribLocation(mPointProgramHandle, "a_Position");

		// Pass in the position.
		GLES20.glVertexAttrib3f(pointPositionHandle, mLightPosInModelSpace[0], mLightPosInModelSpace[1], mLightPosInModelSpace[2]);

		// Since we are not using a buffer object, disable vertex arrays for this attribute.
		GLES20.glDisableVertexAttribArray(pointPositionHandle);

		// Pass in the transformation matrix.
		Matrix.multiplyMM(mvp.values, 0, view.values, 0, mLightModelMatrix, 0);
		Matrix.multiplyMM(mvp.values, 0, projection.values, 0, mvp.values, 0);
		GLES20.glUniformMatrix4fv(pointMVPMatrixHandle, 1, false, mvp.values, 0);

		// Draw the point.
		GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
	}

	public static void addEntityType (final EntityType type) {entityTypes.add(type);}
	public static void addEntity (final Entity e) {e.uid = entities.add(e);}


	public static int compileShader (final int shaderType, final String shaderSource)
	{
		int shaderHandle = GLES20.glCreateShader(shaderType);

		if (shaderHandle != 0)
		{
			// Pass in the shader source.
			GLES20.glShaderSource(shaderHandle, shaderSource);

			// Compile the shader.
			GLES20.glCompileShader(shaderHandle);

			// Get the compilation status.
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			// If the compilation failed, delete the shader.
			if (compileStatus[0] == 0)
			{
				Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle = 0;
			}
		}

		if (shaderHandle == 0)
		{
			throw new RuntimeException("Error creating shader.");
		}

		return shaderHandle;
	}

	public static int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes)
	{
		int programHandle = GLES20.glCreateProgram();

		if (programHandle != 0)
		{
			// Bind the vertex shader to the program.
			GLES20.glAttachShader(programHandle, vertexShaderHandle);

			// Bind the fragment shader to the program.
			GLES20.glAttachShader(programHandle, fragmentShaderHandle);

			// Bind attributes
			if (attributes != null)
			{
				final int size = attributes.length;
				for (int i = 0; i < size; i++)
				{
					GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
				}
			}

			// Link the two shaders together into a program.
			GLES20.glLinkProgram(programHandle);

			// Get the link status.
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

			// If the link failed, delete the program.
			if (linkStatus[0] == 0)
			{
				Log.e(TAG, "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
			}
		}

		if (programHandle == 0)
		{
			throw new RuntimeException("Error creating program.");
		}

		return programHandle;
	}
}
