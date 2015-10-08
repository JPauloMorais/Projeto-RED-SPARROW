package br.jp.redsparrow.engine;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.jp.redsparrow.engine.entities.Entity;
import br.jp.redsparrow.engine.entities.EntityType;
import br.jp.redsparrow.engine.math.Mat4;
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

	public static AutoArray<EntityType> entityTypes = new AutoArray<EntityType>();
	public static AutoArray<Entity>     entities    = new AutoArray<Entity>();

	private static Map<Integer, AutoArray<Integer>> entityTypeEntityRenderListMap = new HashMap<Integer, AutoArray<Integer>>();

	private static float[] mLightModelMatrix = new float[16];

	private static Entity     entity;
	private static EntityType entityType;

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

		final Sprite testSprite = new Sprite("nave_f", 1, 1);
		entityType = new EntityType(testSprite);
		entity = new Entity();
		entity.setCurrentTexCoords(entityType, 0);
		entity.setCurrentColors(RGB.BLUE, RGB.WHITE, RGB.WHITE, RGB.BLUE);

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

		GLES20.glClearColor(.1f, .1f, .1f, 1);

		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		// Position the eye in front of the origin.
		final float eyeX = 0.0f;
		final float eyeY = 0.0f;
		final float eyeZ = -0.5f;

		// We are looking toward the distance
		final float lookX = 0.0f;
		final float lookY = 0.0f;
		final float lookZ = -1.0f;

		// Set our up vector. This is where our head would be pointing were we holding the camera.
		final float upX = 0.0f;
		final float upY = 1.0f;
		final float upZ = 0.0f;

		// Set the view matrix. This matrix can be said to represent the camera position.
		// NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
		// view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
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
//		Camera.update();
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

		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, entityType.sprite.textureId);
		GLES20.glUniform1i(mTextureUniformHandle, 0);

		// Calculate position of the light. Rotate and then push into the distance.
		Matrix.setIdentityM(mLightModelMatrix, 0);
		Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, - 5.0f);
		Matrix.rotateM(mLightModelMatrix, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);
		Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, 2.0f);

		Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
		Matrix.multiplyMV(mLightPosInEyeSpace, 0, view.values, 0, mLightPosInWorldSpace, 0);

	// Draw some cubes.
		Matrix.setIdentityM(model.values, 0);
		Matrix.translateM(model.values, 0, 4.0f, 0.0f, - 7.0f);
//		Matrix.rotateM(model.values, 0, angleInDegrees, 1.0f, 0.0f, 0.0f);
		drawCube();

		Matrix.setIdentityM(model.values, 0);
		Matrix.translateM(model.values, 0, - 4.0f, 0.0f, - 7.0f);
		Matrix.rotateM(model.values, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);
		drawCube();

		Matrix.setIdentityM(model.values, 0);
		Matrix.translateM(model.values, 0, 0.0f, 4.0f, - 7.0f);
		Matrix.rotateM(model.values, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);
		drawCube();

		Matrix.setIdentityM(model.values, 0);
		Matrix.translateM(model.values, 0, 0.0f, - 4.0f, - 7.0f);
		drawCube();

		Matrix.setIdentityM(model.values, 0);
		x += 0.005f;
		Matrix.translateM(model.values, 0, 0.0f + x, 0.0f, - 5.0f);
//		Matrix.rotateM(model.values, 0, angleInDegrees, 1.0f, 1.0f, 0.0f);
		drawCube();

		// Draw a point to indicate the light.
		GLES20.glUseProgram(mPointProgramHandle);
		drawLight();

//		for (int i = 0; i < entities.size; i++)
//		{
//			Entity e = entities.get(i);
//			if(e != null)
//			{
//				model.identity();
////				model.translate(e.loc);
//				Log.d(TAG, "e #" + e.uid + ": loc: " + e.loc + ", rot: " + e.rot + ", scl: " + e.scl +
//				           ", sprite: " + e.sprite);
//				Matrix.translateM(model.values, 0, e.loc.x, e.loc.y, e.loc.z);
//				Matrix.rotateM(model.values, 0, e.rot, 0, 1, 0);
//				Matrix.scaleM(model.values, 0, e.scl.x, e.scl.y, e.scl.z);
//
//				Matrix.multiplyMM(tempMatrix, 0, model.values, 0, view.values, 0);
//				Matrix.multiplyMM(mvp.values, 0, tempMatrix, 0, projection.values, 0);
////				mvp = model.mult(view);
////				mvp = mvp.mult(projection);
//				GLES20.glUniformMatrix4fv(basicShader.mvpMatrixUniform, 1, false, mvp.values, 0);
//
//				//TODO: Render calls to 1x sprite binding
//				Sprite sprite = sprites.get(e.sprite);
//				GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, sprite.texture);
//				GLES20.glUniform1i(basicShader.textureUnitUniform, 0);
//
//				sprite.texCoordDataBuffer.position(0);
//				GLES20.glVertexAttribPointer(basicShader.texcoordAttribute, Sprite.TEXTCOORD_DATA_SIZE_EL, GLES20.GL_FLOAT, false,
//				                             Sprite.STRIDE, sprite.texCoordDataBuffer);
//				GLES20.glEnableVertexAttribArray(basicShader.texcoordAttribute);
//
//				GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
//			}
//	}
	}

	private static void drawCube ()
	{
		Quad.POSITION_DATA_BUFFER.position(0);
		GLES20.glVertexAttribPointer(mPositionHandle, Quad.POSITION_DATA_SIZE, GLES20.GL_FLOAT, false, 0, Quad.POSITION_DATA_BUFFER);
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		Quad.NORMAL_DATA_BUFFER.position(0);
		GLES20.glVertexAttribPointer(mNormalHandle, Quad.NORMAL_DATA_SIZE, GLES20.GL_FLOAT, false, 0, Quad.NORMAL_DATA_BUFFER);
		GLES20.glEnableVertexAttribArray(mNormalHandle);

		entity.colorDataBuffer.position(0);
		GLES20.glVertexAttribPointer(mColorHandle, Entity.COLOR_DATA_SIZE, GLES20.GL_FLOAT, false, 0, entity.colorDataBuffer);
		GLES20.glEnableVertexAttribArray(mColorHandle);

		entity.texCoordDataBuffer.position(0);
		GLES20.glVertexAttribPointer(mTextureCoordinateHandle, Entity.TEXCOORD_DATA_SIZE, GLES20.GL_FLOAT, false, 0, entity.texCoordDataBuffer);
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
