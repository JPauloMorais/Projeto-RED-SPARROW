package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

<<<<<<< HEAD
import android.graphics.RectF;
import br.jp.redsparrow.engine.core.components.Component;
import br.jp.redsparrow.engine.core.messages.Message;
=======
import br.jp.redsparrow.engine.core.components.Component;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

public class GameObject {

	private int mId = -2;

	private Vector2f mPosition;
	private float mWidth;
	private float mHeight;
<<<<<<< HEAD
	private RectF mBounds;
	
//	private Vector2f mTexturePosition;
=======

	private float[] mVerts;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
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

<<<<<<< HEAD
		mCurMessages = new ArrayList<Message>();
		
=======
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
		mUpdatableComponents = new ArrayList<Component>();
		mRenderableComponents = new ArrayList<Component>();

		mPosition = new Vector2f(x, y);
		mWidth = width;
		mHeight = height;
<<<<<<< HEAD
		mBounds = new RectF();
		
//		mTexturePosition = new Vector2f(0,0);
		
=======

		mVerts = new float[8];
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
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
<<<<<<< HEAD
		//      left                 top            right         bottom  
		mBounds.set( x+((mWidth/2) *-1) , y+(mHeight/2), x+(mWidth/2), y+((mHeight/2) *-1));

		float[] tmp = {
				//   X   ,  Y   ,     S   , T
				x            , y             ,  0.5f, 0.5f, //centro
				mBounds.left , mBounds.bottom,  0f  , 1f  , //inf. esq.
				mBounds.right, mBounds.bottom,  1f  , 1f  , //inf. dir.
				mBounds.right, mBounds.top   ,  1f  , 0f  , //sup. dir.
				mBounds.left , mBounds.top   ,  0f  , 0f  , //sup. esq.
				mBounds.left , mBounds.bottom,  0f  , 1f    //inf. esq.
=======
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
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f

		};

		mVertexArray = new VertexArray(tmp);

	}

	//Redefine vertices e mapeamento da textura
<<<<<<< HEAD
	public void updateVertsData(float x, float y, float s, float t) {  		
		if (mBounds.centerX()!=x || mBounds.centerY()!=y) {
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
=======
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

>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
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

<<<<<<< HEAD
	public void setUpdatableComponents(ArrayList<Component> updatableComponents) {
		this.mUpdatableComponents = updatableComponents;
=======
	public void setUpdatableComponents(ArrayList<Component> mUpdatableComponents) {
		this.mUpdatableComponents = mUpdatableComponents;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

	public ArrayList<Component> getRenderableComponents() {
		return mRenderableComponents;
	}

<<<<<<< HEAD
	public void setRenderableComponents(ArrayList<Component> renderableComponents) {
		this.mRenderableComponents = renderableComponents;
=======
	public void setRenderableComponents(ArrayList<Component> mRenderableComponents) {
		this.mRenderableComponents = mRenderableComponents;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

	public void addComponent(Component component){
		if(component instanceof Updatable) mUpdatableComponents.add(component);
		if(component instanceof Renderable) mRenderableComponents.add(component);
	}

	public Vector2f getPosition() {
		return mPosition;
	}

<<<<<<< HEAD
	public void setPosition(Vector2f position) {
		this.mPosition = position;
=======
	public void setPosition(Vector2f mPosition) {
		this.mPosition = mPosition;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

	public float getWidth() {
		return mWidth;
	}

<<<<<<< HEAD
	public void setWidth(float width) {
		this.mWidth = width;
=======
	public void setWidth(float mWidth) {
		this.mWidth = mWidth;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

	public float getHeight() {
		return mHeight;
	}

<<<<<<< HEAD
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
=======
	public void setHeight(float mHeight) {
		this.mHeight = mHeight;
	}

	public void recieveMessages(ArrayList<Message> curMessages) {
		mCurMessages = curMessages;
	}

	public Message getMessageByOperation(String operation) {

		for (Message message : mCurMessages) {
			if(message.getOperation()==operation) {
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
				mCurMessages.remove(mCurMessages.indexOf(message));
				return message;
			}
		}

		return null;
	}

	public ArrayList<Message> getMessages() {
		return mCurMessages;
	}

<<<<<<< HEAD
	public RectF getBounds() {
		return mBounds;
	}

	public void setBounds(RectF bounds) {
		this.mBounds = bounds;
=======
	public float[] getVertices() {
		return mVerts;
	}

	public void setVertices(float[] mVerts) {
		this.mVerts = mVerts;
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

}
