package br.jp.redsparrow.engine.core;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.util.LogConfig;

public class World implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//TODO: Saveinstancestate stuff
	private static final String TAG = "World";
	private static boolean isRunning;

	private static GameObject mPlayer;
	private static ArrayList<GameObject> mGameObjects;

	private static final float mUPDATE_RANGE_X = 10.0f;
	private static final float mUPDATE_RANGE_Y = 15.0f;
	private static final float mRENDERING_RANGE_X = 10.0f;
	private static final float mRENDERING_RANGE_Y = 15.0f;

	private static SoundComponent bgmSoundComponent;
	private static float bgMusicRightVol = 0.5f;
	private static float bgMusicLeftVol = 0.5f;

	//	private static Thread physicsCheckT;

	public static void init(Context context){

		isRunning = false;

		mPlayer = new GameObject();
		mGameObjects = new ArrayList<GameObject>();

		bgmSoundComponent = new SoundComponent(context);
		bgmSoundComponent.addSound(R.raw.at_least_you_tried_greaf);
		bgmSoundComponent.addSound(R.raw.test_shot);

		//		physicsCheckT = new Thread(new Runnable() {
		//			public void run() {
		//				while (World.isRunning()) {
		//					for (int i = 0; i < mGameObjects.size(); i++) {
		//						for (int j = 0; j < mGameObjects.size(); j++) {
		//							if(Collision.areIntersecting(mGameObjects.get(i).getBounds(), mGameObjects.get(j).getBounds())){
		//								
		//								MessagingSystem.sendMessagesToObject(mGameObjects.get(i).getId(), new Message(i, "Collided", ""));
		//								MessagingSystem.sendMessagesToObject(mGameObjects.get(j).getId(), new Message(i, "Collided", ""));
		//
		//							}
		//						}
		//					}
		//				}
		//			}
		//		});

	}

	private static void onStart(){
		isRunning = true;
		//		physicsCheckT.start();
		float targetVol[] = { bgMusicLeftVol, bgMusicRightVol };
		bgmSoundComponent.fadeIn(0, targetVol, 1, true);
	}


	public static void loop(float[] projectionMatrix){
		if (isRunning) {
			//------LOOP DOS OBJETOS-----------
			if (mGameObjects != null ) {
				for (GameObject obj1 : mGameObjects) {
					//Realiza updates e renders somente qdo objeto se encontra no limite definido

//					for (GameObject obj2 : mGameObjects) {
//						if(Collision.areIntersecting(obj1.getBounds(), obj2.getBounds())) {
//							MessagingSystem.sendMessagesToObject(obj1.getId(), new Message(obj1.getId(), "Collision", ""));
//							MessagingSystem.sendMessagesToObject(obj2.getId(), new Message(obj2.getId(), "Collision", ""));
//						}
//					}

					if(obj1.getPosition().getX() < getPlayer().getPosition().getX()+mUPDATE_RANGE_X &&
							obj1.getPosition().getX() > getPlayer().getPosition().getX()-mUPDATE_RANGE_X &&
							obj1.getPosition().getY() < getPlayer().getPosition().getY()+mUPDATE_RANGE_Y &&
							obj1.getPosition().getY() > getPlayer().getPosition().getY()-mUPDATE_RANGE_Y)
					{
						obj1.update();
					}


					if(obj1.getPosition().getX() < getPlayer().getPosition().getX()+mRENDERING_RANGE_X &&
							obj1.getPosition().getX() > getPlayer().getPosition().getX()-mRENDERING_RANGE_X &&
							obj1.getPosition().getY() < getPlayer().getPosition().getY()+mRENDERING_RANGE_Y &&
							obj1.getPosition().getY() > getPlayer().getPosition().getY()-mRENDERING_RANGE_Y)
					{
						obj1.render(projectionMatrix);
					}

					//					for (GameObject obj2 : mGameObjects) {
					//
					//						if(Collision.areIntersecting(obj1.getBounds(), obj2.getBounds())){
					//							Log.i("Physics", "Collision");
					//
					//							//							MessagingSystem.sendMessagesToObject(mGameObjects.get(i).getId(), new Message(i, "Collided", ""));
					//							//							MessagingSystem.sendMessagesToObject(mGameObjects.get(j).getId(), new Message(i, "Collided", ""));
					//
					//						}
					//					}

				}
			}
			//------LOOP DO PLAYER-------------
			mPlayer.update();
			mPlayer.render(projectionMatrix);
			for (GameObject obj2 : mGameObjects) {
				if(Collision.areIntersecting(mPlayer.getBounds(), obj2.getBounds())) {
										
					PhysicsComponent ppc = ((PhysicsComponent) mPlayer.getUpdatableComponent(0));
					float[] vels = {
							ppc.getVelX(), ppc.getVelY()
					};
					PhysicsComponent opc = ((PhysicsComponent) obj2.getUpdatableComponent(0));
					float[] vels1 = {
							opc.getVelX(), opc.getVelY()
					};
					
					mPlayer.recieveMessage(new Message(mPlayer.getId(), "Collision", vels1));	
					mGameObjects.get(mGameObjects.indexOf(obj2)).recieveMessage(new Message(obj2.getId(), "Collision", vels));
				}
			}

		}else {
			onStart();
		}
	}

	public static void pause(){
		try {
			bgmSoundComponent.pauseSound(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void resume(){
		//		bgMusic.start();
	}

	public static void stop(){
		try {
			bgmSoundComponent.stopSound(0);
			bgmSoundComponent.releaseSound(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SoundComponent getBgmSoundComponent() {
		return bgmSoundComponent;
	}

	public static void setBgmSoundComponent(SoundComponent bgmSoundComponent) {
		World.bgmSoundComponent = bgmSoundComponent;
	}

	public static GameObject getPlayer() {
		if(mPlayer!=null) return mPlayer;
		else return new GameObject();
	}

	public static void setPlayer(GameObject mPlayer) {
		World.mPlayer = mPlayer;
	}

	public static GameObject getObject(int index){

		return mGameObjects.get(index);

	}

	public static GameObject getObject(GameObject object){

		return mGameObjects.get(mGameObjects.indexOf(object));

	}

	public static GameObject getObjectById(int id){

		if(mGameObjects != null){			
			for (GameObject gameObject : mGameObjects) {

				if(gameObject.getId()==id) return mGameObjects.get(mGameObjects.indexOf(gameObject));

			}
		}

		return null;

	}

	public static void addObject(GameObject object){

		//TODO: sistema eficiente de atribuicao de ids
		//Se id ja nao foi estabelecido, atribuir baseado em posicao no array

		if(object.getId() == -2) { 
			object.setId(mGameObjects.size()-1);
		}

		mGameObjects.add(object);

		if(LogConfig.ON) Log.i(TAG, "Objeto de id " + object.getId() + " add em " + object.getPosition().toString());
	}

	public static void addObjects(GameObject ... objects){
		for (int i = 0; i < objects.length; i++) {
			World.addObject(objects[i]);
		}
	}

	public static void removeObject(int index){
		mGameObjects.remove(index);
	}

	public static void removeObject(GameObject object){
		mGameObjects.remove(object);
	}

	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		World.isRunning = isRunning;
	}

	public static void sendMessages(final int objectId, final ArrayList<Message> curMessages) {
		try {
			getObject(objectId).recieveMessages(curMessages);
		} catch (Exception e) {	}
	}
}



