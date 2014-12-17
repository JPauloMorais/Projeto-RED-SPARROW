package br.jp.engine.core;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.jp.engine.components.PhysicsComponent;
import br.jp.engine.physics.Physics;
import android.util.Log;

public class World {

	private Grid grid;
	private List<GameObject> mObjects;
	private List<GameObject> toCheck;
	private List<Message> mMessageList;

	public World(){

		mMessageList = new ArrayList<Message>();
		mObjects = new ArrayList<GameObject>();
		grid = new Grid(64000, 64000, 64);
		toCheck = new ArrayList<GameObject>();

	}

	public synchronized void loop(GL10 gl){
		if(!mObjects.isEmpty()){ 
			Message message;
			for (GameObject object : mObjects) {
				try {
					message = mMessageList.get(mObjects.indexOf(object));
					if(message != null) {
						object.recieveMessage(message);
						mMessageList.remove(message);
					}
				} catch (Exception e) {
					//					e.printStackTrace();
					//TODO: Admin. correta da falta de mensagem
					message = new Message(0, -1);
				}
				if(message != null) {
					object.recieveMessage(message);
					mMessageList.remove(message);
				}
				object.update(gl);
				toCheck = grid.getLikelyToInteract(object);
				if(!toCheck.isEmpty()){
					for (GameObject object2 : toCheck) {
						if(mObjects.indexOf(object)!=mObjects.indexOf(object2)){						
							if(Physics.isColliding(object, object2)) 
							{
								for (Component cmp : object.getComponents()) {								
									if(cmp instanceof PhysicsComponent){
										grid.clear();
										addObject(object);
									}
								}
							}
						}
					}
				}
				object.render(gl);
			}
		}
	}

	public void addObject(GameObject object){
		//TODO: Sistema eficiente de IDs
		mObjects.add(object);
		//		mObjects.get(mObjects.indexOf(object)).setId(mObjects.indexOf(object));
		grid.addObject(object);
		if(LogConfig.IS_DEBUGGING) Log.d("WORLD", "Objeto de ID: " + mObjects.indexOf(object) +
				" adicionado em X:" + object.getX() + ",Y:" + object.getY() );
	}

	public List<GameObject> getObjects() {
		return mObjects;
	}

	public GameObject getObject(GameObject object){
		if(mObjects.contains(object)) return mObjects.get(mObjects.indexOf(object));
		else return null;
	}

	public void sendMessage(Message message){
		//uma mensagem por vez para cada objeto
		mMessageList.add(message.getObjectId(),message);	
	}
}
