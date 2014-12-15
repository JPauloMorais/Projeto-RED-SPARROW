package br.jp.engine.components;

import static javax.microedition.khronos.opengles.GL10.GL_CW;
import static javax.microedition.khronos.opengles.GL10.GL_FLOAT;
import static javax.microedition.khronos.opengles.GL10.GL_TRIANGLE_STRIP;
import static javax.microedition.khronos.opengles.GL10.GL_VERTEX_ARRAY;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import br.jp.engine.core.Component;
import br.jp.engine.core.GameObject;

public class SpriteComponent extends Component {

	private FloatBuffer vertexBuffer;

	private float vertices[];
	private float mR, mG, mB, mA;
	//coords da textura
//	private float texture[] = {
//								0.0f, 0.0f, 
//								1.0f, 0.0f, 
//								0.0f, 1.0f, 
//								1.0f, 1.0f,
//											};

	public SpriteComponent() {
		this(null, 0.5f, 0.5f, 1.0f, 1.0f);
	}         

	public SpriteComponent(GameObject parent, float r, float g, float b, float a) {
		super("SpriteComponent");
		
		mParent = parent;
		mR = r;
		mG = g;
		mB = b;
		mA = a;
		
		// vertices para 2 triangulos (x,y,z)
		float v[] = { 
				mParent.getX(), mParent.getY(), 0.0f, //Bottom Left
				mParent.getX() + mParent.getWidth(), mParent.getY(), 0.0f, 	//Bottom Right
				mParent.getX(), mParent.getY() + mParent.getHeight(), 0.0f, 	//Top Left
				mParent.getX() + mParent.getWidth(), mParent.getY() + mParent.getHeight(), 0.0f 	//Top Right
				};
		vertices = v;

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
	}

	@Override
	public void render(GL10 gl) {
		
		gl.glPushMatrix();
		//Set the face rotation
		gl.glFrontFace(GL_CW);

		//Point to our vertex buffer
		gl.glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);

		//Enable vertex buffer
		gl.glEnableClientState(GL_VERTEX_ARRAY);

		//Set The Color To Blue
		gl.glColor4f(mR, mG, mB, mA);	

		//Draw the vertices as triangle strip
		gl.glDrawArrays(GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glPopMatrix();
	}

	public float getmR() {
		return mR;
	}

	public void setmR(float mR) {
		this.mR = mR;
	}

	public float getmG() {
		return mG;
	}

	public void setmG(float mG) {
		this.mG = mG;
	}

	public float getmB() {
		return mB;
	}

	public void setmB(float mB) {
		this.mB = mB;
	}

	public float getmA() {
		return mA;
	}

	public void setmA(float mA) {
		this.mA = mA;
	}

}
