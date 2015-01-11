package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

import android.content.Context;
<<<<<<< HEAD
import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.util.LogConfig;

public class World {

	private static final String TAG = "World";
	
	private static boolean isRunning;

=======
import android.media.MediaPlayer;
import br.jp.redsparrow.R;

public class World {

>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	private static GameObject mPlayer;
	private static ArrayList<GameObject> mGameObjects;

	private static final float mUPDATE_RANGE_X = 10.0f;
	private static final float mUPDATE_RANGE_Y = 15.0f;
	private static final float mRENDERING_RANGE_X = 10.0f;
	private static final float mRENDERING_RANGE_Y = 15.0f;

<<<<<<< HEAD
	private static SoundComponent bgmSoundComponent;
	private static float bgMusicRightVol = 0.04f;
	private static float bgMusicLeftVol = 0.04f;

//	private static Thread physicsCheckT;

	public static void init(Context context){

		isRunning = false;

		mPlayer = new GameObject();
		mGameObjects = new ArrayList<GameObject>();

		bgmSoundComponent = new SoundComponent(context);
		bgmSoundComponent.addSound(R.raw.at_least_you_tried_greaf);
		bgmSoundComponent.getSounds().get(0).setVolume(bgMusicLeftVol, bgMusicRightVol);
		bgmSoundComponent.addSound(R.raw.test_shot);

//		physicsCheckT = new Thread(new Runnable() {
//			public void run() {
//				while (World.isRunning()) {
//					for (int i = 0; i < mGameObjects.size(); i++) {
//						for (int j = 0; j < mGameObjects.size(); j++) {
//							if(Collision.areIntersecting(mGameObjects.get(i).getVertices(), mGameObjects.get(j).getVertices())){
//								
//								ArrayList<Message> collisionMessages = new ArrayList<Message>();
//								collisionMessages.add(new Message(i, "Collided", ""));
//								
//								MessagingSystem.sendMessagesToObject(mGameObjects.get(i).getId(), collisionMessages);
//								MessagingSystem.sendMessagesToObject(mGameObjects.get(j).getId(), collisionMessages);
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
	}

	public static void loop(float[] projectionMatrix){
		if (isRunning) {
=======
	static MediaPlayer bgMusic;
	static float bgMusicRightVol = 0.04f;
	static float bgMusicLeftVol = 0.04f;

	public static void init(Context context){

		mPlayer = new GameObject();
		mGameObjects = new ArrayList<GameObject>();

		bgMusic = MediaPlayer.create( context, R.raw.at_least_you_tried_greaf);
		bgMusic.setVolume(bgMusicLeftVol, bgMusicRightVol);
		bgMusic.setLooping(true);
		
	}

	public static void loop(float[] projectionMatrix){
		if (true) {
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
			//------LOOP DOS OBJETOS-----------
			if (mGameObjects != null ) {
				for (int i = 0; i < mGameObjects.size(); i++) {
					//Realiza updates e renders somente qdo objeto se encontra no limite definido
					//
					//
					if(mGameObjects.get(i).getPosition().getX() < getPlayer().getPosition().getX()+mUPDATE_RANGE_X &&
							mGameObjects.get(i).getPosition().getX() > getPlayer().getPosition().getX()-mUPDATE_RANGE_X &&
							mGameObjects.get(i).getPosition().getY() < getPlayer().getPosition().getY()+mUPDATE_RANGE_Y &&
							mGameObjects.get(i).getPosition().getY() > getPlayer().getPosition().getY()-mUPDATE_RANGE_Y)
					{
						mGameObjects.get(i).update();
					}

					if(mGameObjects.get(i).getPosition().getX() < getPlayer().getPosition().getX()+mRENDERING_RANGE_X &&
							mGameObjects.get(i).getPosition().getX() > getPlayer().getPosition().getX()-mRENDERING_RANGE_X &&
							mGameObjects.get(i).getPosition().getY() < getPlayer().getPosition().getY()+mRENDERING_RANGE_Y &&
							mGameObjects.get(i).getPosition().getY() > getPlayer().getPosition().getY()-mRENDERING_RANGE_Y)
					{
						mGameObjects.get(i).render(projectionMatrix);
					}

				}
			}
			//------LOOP DO PLAYER-------------
			mPlayer.update();
			mPlayer.render(projectionMatrix);
<<<<<<< HEAD
			
		}else {
			onStart();
=======
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
		}

	}

	public static void pause(){
<<<<<<< HEAD
		try {
			bgmSoundComponent.pauseSound(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
=======
		bgMusic.pause();
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

	public static void resume(){
		//		bgMusic.start();
	}

	public static void stop(){
<<<<<<< HEAD
		try {
			bgmSoundComponent.stopSound(0);
			bgmSoundComponent.releaseSound(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
=======
		bgMusic.stop();
		bgMusic.release();
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

	public static GameObject getPlayer() {
		if(mPlayer!=null) return mPlayer;
		else return new GameObject();
	}

	public static void setPlayer(GameObject mPlayer) {
		World.mPlayer = mPlayer;
<<<<<<< HEAD
		bgmSoundComponent.startSound(0, true);
=======
		bgMusic.start();
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

	public static GameObject getObject(int index){

		return mGameObjects.get(index);

	}

	public static GameObject getObject(GameObject object){

		return mGameObjects.get(mGameObjects.indexOf(object));

	}

	public static GameObject getObjectById(int id){

<<<<<<< HEAD
		if(mGameObjects != null){			
			for (GameObject gameObject : mGameObjects) {
				
				if(gameObject.getId()==id) return mGameObjects.get(mGameObjects.indexOf(gameObject));
			
=======
		if(mGameObjects!=null){			
			for (GameObject gameObject : mGameObjects) {
				if(gameObject.getId()==id) return mGameObjects.get(mGameObjects.indexOf(gameObject));
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
			}
		}

		return null;
	}

	public static void addObject(GameObject object){
<<<<<<< HEAD
		
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
=======
		//TODO: sistema eficiente de atribuicao de ids
		//Se id ja nao foi estabelecido, atribuir baseado em posicao
		if(object.getId() == -2) { 
			object.setId(mGameObjects.size()-1);
		}
		mGameObjects.add(object);
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

	public static void removeObject(int index){
		mGameObjects.remove(index);
	}

	public static void removeObject(GameObject object){
		mGameObjects.remove(object);
	}

<<<<<<< HEAD
	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		World.isRunning = isRunning;
=======
	public static void sendMessages(final int objectId, final ArrayList<Message> curMessages) {
		try {
			getObject(objectId).recieveMessages(curMessages);
		} catch (Exception e) {	}
>>>>>>> d5d0451bb12a9e70ba3b465314a37e64836bdd2f
	}

}
