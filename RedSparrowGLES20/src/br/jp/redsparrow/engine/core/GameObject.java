package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

import android.graphics.RectF;
import br.jp.redsparrow.engine.core.components.Component;
import br.jp.redsparrow.engine.core.messages.Message;

public class GameObject {

	private int mId = -2;

	private Vector2f mPosition;
	private float mWidth;
	private float mHeight;
	private RectF mBounds;
	private Vector2f mColOffset;

	private float[] mVertsData;

	//	private Vector2f mTexturePosition;

	private VertexArray mVertexArray;

	private ArrayList<Component> mUpdatableComponents;
	private ArrayList<Component> mRenderableComponents;

	private ArrayList<Message> mCurMessages;

	public GameObject(){
		this(0,0,0,0);
	}

	public GameObject(float x, float y){
		this(x,y,0,0);
	}

	public GameObject(float x, float y, float width, float height){

		mCurMessages = new ArrayList<Message>();

		mUpdatableComponents = new ArrayList<Component>();
		mRenderableComponents = new ArrayList<Component>();

		mPosition = new Vector2f(x, y);
		mWidth = width;
		mHeight = height;
		mBounds = new RectF(x, y, x+width, x+height);

		mColOffset = new Vector2f(-0.1f, -0.1f);

		//		mTexturePosition = new Vector2f(0,0);

		mVertsData = new float[24];
		updateVertsData(x, y);

	}

	public void update(){

		updateVertsData(mPosition.getX(), mPosition.getY());

		for (Component component : mUpdatableComponents) {
			((Updatable) component).update(this);
		}


	}

	public void render(float[] projectionMatrix){

		for (Component component : mRenderableComponents) {
			((Renderable) component).render(mVertexArray, projectionMatrix);
		}

	}

	//Redefine vertices
	public void updateVertsData(float x, float y){
		if (mBounds.centerX() != x || mBounds.centerY() != y) {
			//                     left                 top            right         bottom  
			mBounds.set( x-((mWidth/2)) , y-(mHeight/2), x+(mWidth/2), y+((mHeight/2)) );

			//   X   ,  Y   ,     S   , T
			mVertsData[0] = x;              mVertsData[1] =  y             ;     mVertsData[2] = 0.5f; mVertsData[3] = 0.5f; //centro
			mVertsData[4] = mBounds.right;   mVertsData[5] = mBounds.top ;     mVertsData[6] = 0f  ; mVertsData[7] = 1f  ; //inf. esq.
			mVertsData[8] = mBounds.left;  mVertsData[9] = mBounds.top ;     mVertsData[10] = 1f ; mVertsData[11] = 1f ; //inf. dir.
			mVertsData[12] = mBounds.left; mVertsData[13] = mBounds.bottom   ;     mVertsData[14] = 1f ; mVertsData[15] =  0f; //sup. dir.
			mVertsData[16] = mBounds.right ; mVertsData[17] = mBounds.bottom   ;     mVertsData[18] = 0f ; mVertsData[19] = 0f ; //sup. esq.
			mVertsData[20] = mBounds.right ; mVertsData[21] = mBounds.top;     mVertsData[22] = 0f ; mVertsData[23] = 1f;    //inf. esq.

			mVertexArray = new VertexArray(mVertsData);
		}

	}

	//Redefine vertices e mapeamento da textura
	public void updateVertsData(float x, float y, float s, float t) {  		
		if (mBounds.centerX() != x || mBounds.centerY() != y) {
			//              left                 top            right         bottom  
			mBounds.set(x + ((mWidth / 2) * -1), y + (mHeight / 2), x
					+ (mWidth / 2), y + ((mHeight / 2) * -1));
			//atualmente suporta apenas sheets com uma linha
			float[] tmp = {
					// X                 , Y     ,      S          , T
					x,
					y,
					s,
					t, //centro

					mBounds.left,
					mBounds.bottom,
					s - (mBounds.width() / 2),
					mBounds.height(), //inf. esq.

					mBounds.right, mBounds.bottom,
					s + (mBounds.width() / 2),
					mBounds.height(), //inf. dir.

					mBounds.right, mBounds.top, s + (mBounds.width() / 2),
					0f, //sup. dir.

					mBounds.left, mBounds.top, s - (mBounds.width() / 2),
					0f, //sup. esq.  

					mBounds.left, mBounds.bottom, s - (mBounds.width() / 2),
					mBounds.height() //inf. esq.

			};

			mVertexArray = new VertexArray(tmp);
		}

	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}
	
	public Component getUpdatableComponent(int id){
		return mUpdatableComponents.get(id);
	}
	
	public Component getRenderableComponent(int id){
		return mRenderableComponents.get(id);
	}

	public Component getComponent(String name){

		for (Component component : mUpdatableComponents) {
			if(component.getName().equals(name)) return component;
		}
		for (Component component : mRenderableComponents) {
			if(component.getName().equals(name)) return component;
		}

		return new Component("NullComponent");
	}

	public ArrayList<Component> getComponents() {
		ArrayList<Component> components = new ArrayList<Component>();
		components.addAll(mUpdatableComponents);
		components.addAll(mRenderableComponents);
		return components;
	}

	public ArrayList<Component> getUpdatableComponents() {
		return mUpdatableComponents;
	}

	public void setUpdatableComponents(ArrayList<Component> updatableComponents) {
		this.mUpdatableComponents = updatableComponents;
	}

	public ArrayList<Component> getRenderableComponents() {
		return mRenderableComponents;
	}

	public void setRenderableComponents(ArrayList<Component> renderableComponents) {
		this.mRenderableComponents = renderableComponents;
	}

	public void addComponent(Component component){
		if(component instanceof Updatable) {
			mUpdatableComponents.add(component);
			component.setId(mUpdatableComponents.indexOf(component));
		}
		if(component instanceof Renderable) {
			mRenderableComponents.add(component);
			component.setId(mRenderableComponents.indexOf(component));
		}
	}

	public Vector2f getPosition() {
		return mPosition;
	}

	public void setPosition(Vector2f position) {
		this.mPosition = position;
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

	public void recieveMessage(Message message) {
		mCurMessages.add(message);
	}

	public void recieveMessages(ArrayList<Message> messages) {
		mCurMessages = messages;
	}

	public Message getMessage(String operation) {

		for (Message message : mCurMessages) {
			if(message.getOperation().equals(operation)) {
				mCurMessages.remove(mCurMessages.indexOf(message));
				return message;
			}
		}

		return null;
	}

	public ArrayList<Message> getMessages() {
		return mCurMessages;
	}

	public RectF getBounds() {
		return new RectF(mBounds.left - mColOffset.getX(), mBounds.top - mColOffset.getY(), mBounds.right + mColOffset.getX(), mBounds.bottom + mColOffset.getY());
//		return mBounds;
	}

	public void setBounds(RectF bounds) {
		this.mBounds = bounds;
	}

}
