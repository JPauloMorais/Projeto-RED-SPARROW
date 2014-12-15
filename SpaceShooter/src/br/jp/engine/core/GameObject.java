package br.jp.engine.core;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import br.jp.engine.components.SpriteComponent;



public class GameObject{

	private int id;
	private float mX, mY;
	private float mWidth, mHeight;
	private float mLayer;
	private List<Component> mComponents;
	private List<String> mComponentsNames;

	public GameObject(Context context,float x, float y, float size){ this(context,x, y, size, size); }

	public GameObject(Context context, float x, float y, float width, float height){ this(context,x, y, width, height, null); }

	public GameObject(Context context, float x, float y, float width, float height, List<Component> components) {
		
		id = 0;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setLayer(0);
		mComponents = components;
		mComponentsNames = new ArrayList<String>();
		for (Component component : components) {
			mComponentsNames.add(component.getName());
		}
		
	}

	public void update(GL10 gl) {
		for (Component component : mComponents) {
			component.update(gl);
		}
	}
	public void render(GL10 gl) {
		for (Component component : mComponents) {
			if(component.getName().equals(SpriteComponent.mName)) component.render(gl);
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
	public Component getComponentByName(String name) {
		if (mComponentsNames.contains(name)) 
			return mComponents.get(mComponentsNames.lastIndexOf(name));
		return null;
	}

	public void addComponent(Component component) {
		mComponents.add(component);
		mComponentsNames.add(component.getName());
	}
	
	public void setComponents(List<Component> mComponents) {
		this.mComponents = mComponents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getLayer() {
		return mLayer;
	}

	public void setLayer(float mLayer) {
		this.mLayer = (mLayer + 8.5f) * -1;
	}


}
