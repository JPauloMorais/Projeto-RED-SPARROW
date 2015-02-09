package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

import br.jp.redsparrow.engine.core.components.Component;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.physics.AABB;
import br.jp.redsparrow.engine.core.physics.Bounds;
import br.jp.redsparrow.game.ObjectFactory.OBJECT_TYPE;

public class GameObject {

	private boolean isDead;
	private int mId;
	private OBJECT_TYPE mType;
	
	private Bounds mBounds;
	private double mRotation;

	private ArrayList<Component> mUpdatableComponents;
	private ArrayList<Component> mRenderableComponents;
	
	private ArrayList<Message> mCurMessages;
	private ArrayList<Message> mMessagesToRemove;
	
	public GameObject() {
		this(new AABB(new Vector2f(0, 0), 0, 0));
	}

	public GameObject(Bounds bounds) {

		mBounds = bounds;
		setRotation(0);

		mUpdatableComponents = new ArrayList<Component>();
		mRenderableComponents = new ArrayList<Component>();
		
		isDead = false;
		
		mCurMessages = new ArrayList<Message>();
		mMessagesToRemove = new ArrayList<Message>();

	}

	public void update(Game game) {
		
		removeRecievedMessages();
		
		for (Component component : mUpdatableComponents) {
			((Updatable) component).update(game, this);
		}
	}

	public void render(float[] projMatrix) {
		for (Component component : mRenderableComponents) {
			((Renderable) component).render(null, projMatrix);
		}
	}

	//----------POSICAO------------------
	public Vector2f getPosition() {
		return mBounds.getCenter();
	}
	
	public void setPosition(Vector2f position) {
		mBounds.setCenter(position);
	}
	
	public float getX() {
		return mBounds.getCenter().getX();
	}

	public void setX(float x) {
		mBounds.setCenter(new Vector2f(x, mBounds.getCenter().getY()));
	}

	public float getY() {
		return mBounds.getCenter().getY();
	}

	public void setY(float y) {
		mBounds.setCenter(new Vector2f(mBounds.getCenter().getX(), y));
	}

	public float getWidth() {
		return mBounds.getWidth();
	}

	public void setWidth(float w) {
		mBounds.setWidth(w);
	}

	public float getHeight() {
		return mBounds.getHeight();
	}

	public void setHeight(float h) {
		mBounds.setHeight(h);
	}

	public Bounds getBounds() {
		return mBounds;
	}

	public void setBounds(Bounds mBounds) {
		this.mBounds = mBounds;
	}
	
	public double getRotation() {
		return mRotation;
	}
	
	public void setRotation(double mRotation) {
		this.mRotation = mRotation;
	}
	//--------------------------------

	//-----COMPONENTS-----------------
	public void addComponent(Component component) {
		if(component instanceof Updatable) mUpdatableComponents.add(component);
		if(component instanceof Renderable) mRenderableComponents.add(component);
	}
	
	public ArrayList<Component> getUpdatableComponents() {
		return mUpdatableComponents;
	}
	
	public Component getUpdatableComponent(String name) {
		for (Component component : mUpdatableComponents) {
			if(component.getName().equals(name)) return component;
		}
		return new Component("Null", this);
	}
	
	public Component getUpdatableComponent(int indx) {
		return mUpdatableComponents.get(indx);
	}
	
	public void removeUpdatableComponent(int indx) {
		mUpdatableComponents.remove(indx);
	}
	
	public ArrayList<Component> getRenderableComponents() {
		return mRenderableComponents;
	}
	
	public Component getRenderableComponent(String name) {
		for (Component component : mRenderableComponents) {
			if(component.getName().equals(name)) return component;
		}
		return new Component("Null", this);
	}
	
	public Component getRenderableComponent(int indx) {
		return mRenderableComponents.get(indx);
	}
	
	public void removeRenderableComponent(int indx) {
		mRenderableComponents.remove(indx);
	}
	//-------------------------------
	
	//------MESSAGES---------------
	private void removeRecievedMessages(){
		mMessagesToRemove.clear();
		for (int i = 0; i < mCurMessages.size(); i++) {
			if (mCurMessages.get(i).hasBeenRecieved()) {
				mMessagesToRemove.add(mCurMessages.get(i));
			}
		}
		mCurMessages.removeAll(mMessagesToRemove);
	}

	public void recieveMessage(Message message) {
		mCurMessages.add(message);
	}

	public void recieveMessages(ArrayList<Message> messages) {
		mCurMessages.addAll(messages);
	}

	public Message getMessage(String operation) {

		for (Message message : mCurMessages) {
			if(message.getOperation().equals(operation)) {
				message.recieve();
				return message;
			}
		}

		return null;
	}

	public ArrayList<Message> getMessages() {
		return mCurMessages;
	}
	//-----------------------------

	//-----ETC----------------------
	public boolean isDead() {
		return isDead;
	}

	public void die() {
		this.isDead = true;
	}
	
	public int getId() {
		return mId;
	}
	
	public void setId(int mId) {
		this.mId = mId;
	}
	
	public OBJECT_TYPE getType() {
		return mType;
	}
	
	public void setType(OBJECT_TYPE mType) {
		this.mType = mType;
	}
	//-------------------------------
}
