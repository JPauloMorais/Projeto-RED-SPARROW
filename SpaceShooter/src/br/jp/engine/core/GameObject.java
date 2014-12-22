package br.jp.engine.core;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import br.jp.engine.components.Renderable;
import br.jp.engine.components.Updatable;



public class GameObject{

//	private Context mContext;
	private float mX; 
	private float mY;
	private float mWidth;
	private float mHeight;
	private float mRotation;
	private byte mLayer;
	private List<Component> mComponents;
	private List<Message> curMessages;

	public GameObject(Context context,float x, float y, float size){ this(context,x, y, size, size); }

	public GameObject(Context context, float x, float y, float width, float height){ this(context,x, y, width, height, null); }

	public GameObject(Context context, float x, float y, float width, float height, List<Component> components) {

//		mContext = context;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setRotation(0);
		setLayer(0);
		mComponents = components;

	}

	public  void update(GL10 gl) {
		for (Component component : mComponents) {
			if(component instanceof Updatable) ((Updatable) component).update(gl, this);
		}
	}
	public  void render(GL10 gl) {
		for (Component component : mComponents) {
			if(component instanceof Renderable) {
				((Renderable) component).render(gl, this);
				gl.glLoadIdentity();
			}
		}
	}

	public float getX() {
		return mX;
	}

	public void setX(float mX) {
		this.mX = mX;
	}

	public float getCenterX(){
		return (mX+mWidth)/2;
	}

	public float getCenterY(){
		return (mY+mHeight)/2;
	}

	public float getY() {
		return mY;
	}

	public void setY(float mY) {
		this.mY = mY;
	}

	public float getWidth() {
		return mWidth;
	}

	public void setWidth(float width) {
		this.mWidth = width;
	}

	public float getHeight() {
		return mHeight;
	}

	public void setHeight(float height) {
		this.mHeight = height;
	}

	public List<Component> getComponents() {
		return mComponents;
	}
	public Component getComponent(Component component) {
		if (mComponents.contains(component)) 
			return mComponents.get(mComponents.lastIndexOf(component));
		return null;
	}

	public void addComponent(Component component) {
		mComponents.add(component);
	}

	public void setComponents(List<Component> mComponents) {
		this.mComponents = mComponents;
	}

	public float getLayer() {
		return mLayer;
	}

	public void setLayer(float mLayer) {
		this.mLayer = (byte) ((mLayer + 6.5f) * -1);
	}

	public float getRotation() {
		return mRotation;
	}

	public void setRotation(float mRotation) {
		this.mRotation = mRotation;
	}

	public List<Message> getCurMessages() {
		return curMessages;
	}

	public void setCurMessages(List<Message> messages) {
		this.curMessages = messages;
	}
	
	public void recieveMessage(List<Message> message){
		setCurMessages(message);
	}
	
	public void removeMessage(Message message){
		try {
			curMessages.remove(message);
		} catch (Exception e) {
			e.printStackTrace();
			removeMessage(message);
		}
	}

}
