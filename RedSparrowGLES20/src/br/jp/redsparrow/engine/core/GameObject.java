package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

import br.jp.redsparrow.engine.core.components.Component;

public class GameObject {

	private int mId = -2;

	private Vector2f mPosition;
	private float mWidth;
	private float mHeight;

	private float[] mVerts;
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

		mUpdatableComponents = new ArrayList<Component>();
		mRenderableComponents = new ArrayList<Component>();

		mPosition = new Vector2f(x, y);
		mWidth = width;
		mHeight = height;

		mVerts = new float[8];
		updateVertsData(x, y);

	}

	public void update(){

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
		//   X                                  Y
		mVerts[0] = x+((mWidth/2) *-1);    mVerts[1] = y+(mHeight/2);
		mVerts[2] = x+(mWidth/2);          mVerts[3] = y+(mHeight/2);
		mVerts[4] = x+(mWidth/2);          mVerts[5] = y+((mHeight/2) *-1);
		mVerts[6] = x+((mWidth/2) *-1);    mVerts[7] = y+((mHeight/2) *-1);

		float[] tmp = {
				//   X   ,  Y   ,     S   , T
				x, y,                  0.5f, 0.5f, //centro
				mVerts[6], mVerts[7],  0f  , 1f  ,
				mVerts[4], mVerts[5],  1f  , 1f  ,
				mVerts[2], mVerts[3],  1f  , 0f  ,
				mVerts[0], mVerts[1],  0f  , 0f  ,
				mVerts[6], mVerts[7],  0f  , 1f 

		};

		mVertexArray = new VertexArray(tmp);

	}

	//Redefine vertices e mapeamento da textura
	public void updateVertsData(float x, float y, int s, float t, float width, float height) {  
		//   X                                  Y
		mVerts[0] = x+((mWidth/2) *-1);    mVerts[1] = y+(mHeight/2);
		mVerts[2] = x+(mWidth/2);          mVerts[3] = y+(mHeight/2);
		mVerts[4] = x+(mWidth/2);          mVerts[5] = y+((mHeight/2) *-1);
		mVerts[6] = x+((mWidth/2) *-1);    mVerts[7] = y+((mHeight/2) *-1);

		//atualmente suporta apenas sheets com uma linha
		float[] tmp = {
				// X                 , Y                  ,    S          , T
				x        , y        ,          s          , t     , //centro
				mVerts[6], mVerts[7],          s-(width/2), height, //inf. esq.
				mVerts[4], mVerts[5],          s+(width/2), height, //inf. dir.
				mVerts[2], mVerts[3],          s+(width/2), 0f    , //sup. dir.
				mVerts[0], mVerts[1],          s-(width/2), 0f    , //sup. esq.  
				mVerts[6], mVerts[7],          s-(width/2), height  //inf. esq.

		};

		mVertexArray = new VertexArray(tmp);

	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
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

	public void setUpdatableComponents(ArrayList<Component> mUpdatableComponents) {
		this.mUpdatableComponents = mUpdatableComponents;
	}

	public ArrayList<Component> getRenderableComponents() {
		return mRenderableComponents;
	}

	public void setRenderableComponents(ArrayList<Component> mRenderableComponents) {
		this.mRenderableComponents = mRenderableComponents;
	}

	public void addComponent(Component component){
		if(component instanceof Updatable) mUpdatableComponents.add(component);
		if(component instanceof Renderable) mRenderableComponents.add(component);
	}

	public Vector2f getPosition() {
		return mPosition;
	}

	public void setPosition(Vector2f mPosition) {
		this.mPosition = mPosition;
	}

	public float getWidth() {
		return mWidth;
	}

	public void setWidth(float mWidth) {
		this.mWidth = mWidth;
	}

	public float getHeight() {
		return mHeight;
	}

	public void setHeight(float mHeight) {
		this.mHeight = mHeight;
	}

	public void recieveMessages(ArrayList<Message> curMessages) {
		mCurMessages = curMessages;
	}

	public Message getMessageByOperation(String operation) {

		for (Message message : mCurMessages) {
			if(message.getOperation()==operation) {
				mCurMessages.remove(mCurMessages.indexOf(message));
				return message;
			}
		}

		return null;
	}

	public ArrayList<Message> getMessages() {
		return mCurMessages;
	}

	public float[] getVertices() {
		return mVerts;
	}

	public void setVertices(float[] mVerts) {
		this.mVerts = mVerts;
	}

}
