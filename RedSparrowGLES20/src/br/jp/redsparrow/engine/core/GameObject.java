package br.jp.redsparrow.engine.core;

import java.util.ArrayList;
import java.util.HashMap;

import br.jp.redsparrow.engine.core.components.Component;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.game.ObjectType;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.physics.AABB;
import br.jp.redsparrow.engine.core.physics.Bounds;

public class GameObject {

	private boolean isDead;
	private int mId;
	private ObjectType mType;
	
	private Bounds mBounds;
	private double mRotation;

	private HashMap<String, Component> mUpdatableComponents;
	private HashMap<String, Component> mRenderableComponents;
//	private ArrayList<Component> mUpdatableComponents;
//	private ArrayList<Component> mRenderableComponents;
	
	private ArrayList<Message> mCurMessages;
	private ArrayList<Message> mMessagesToRemove;
	
	public GameObject() {
		this(new AABB(new Vector2f(0, 0), 0, 0));
	}

	public GameObject(Bounds bounds) {

		mBounds = bounds;

		mUpdatableComponents = new HashMap<String,Component>();
		mRenderableComponents = new HashMap<String,Component>();
		
		isDead = false;
		
		mCurMessages = new ArrayList<Message>();
		mMessagesToRemove = new ArrayList<Message>();

	}

	public void update(Game game) {
		
		removeRecievedMessages();
		
		for (String key : mUpdatableComponents.keySet()) {
			((Updatable) mUpdatableComponents.get(key)).update(game, this);
		}
		
	}

	public void render(float[] projMatrix) {
		for (String key : mRenderableComponents.keySet()) {
			((Renderable) mRenderableComponents.get(key)).render(null, projMatrix);
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
	public void addComponent(String name, Component component) {
		if(component instanceof Updatable)	mUpdatableComponents.put(name, component);
		if(component instanceof Renderable) mRenderableComponents.put(name, component);
	}
	
//	public ArrayList<Component> getUpdatableComponents() {
//		return (ArrayList<Component>) mUpdatableComponents.values();
//	}
	
	public Component getUpdatableComponent(String name) {
		if(mUpdatableComponents.containsKey(name)) return mUpdatableComponents.get(name);
		else return new Component(this);
	}
	
	public void setUpdatableComponent(String name, Component component) {
		mUpdatableComponents.put(name, component);
	}
//	
//	public Component getUpdatableComponent(int indx) {
//		return mUpdatableComponents.values().;
//	}
	
	public void removeUpdatableComponent(String name) {
		mUpdatableComponents.remove(name);
	}
	
	public ArrayList<Component> getRenderableComponents() {
		return (ArrayList<Component>) mRenderableComponents.values();
	}
	
	public Component getRenderableComponent(String name) {
		if(mRenderableComponents.containsKey(name)) return mRenderableComponents.get(name);
		else return new Component(this);
	}
//	
//	public Component getRenderableComponent(int indx) {
//		return mRenderableComponents.get(indx);
//	}
	
	public void removeRenderableComponent(String name) {
		mRenderableComponents.remove(name);
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
	
	public ObjectType getType() {
		return mType;
	}
	
	public void setType(ObjectType type) {
		mType = type;
	}
	//-------------------------------
}
