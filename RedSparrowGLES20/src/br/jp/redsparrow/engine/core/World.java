package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;

import br.jp.redsparrow.R;

public class World {

	static Context mContext;
	//	private static Intent updateIntent;

	private static GameObject mPlayer;
	private static ArrayList<GameObject> mGameObjects;

	private static final float mUPDATE_RANGE_X = 10.0f;
	private static final float mUPDATE_RANGE_Y = 15.0f;
	private static final float mRENDERING_RANGE_X = 10.0f;
	private static final float mRENDERING_RANGE_Y = 15.0f;

	static MediaPlayer bgMusic;
	static MediaPlayer bgMusic1;
	static float bgMusicRightVol = 0.04f;
	static float bgMusicLeftVol = 0.04f;

	public static void init(Context context){
		mContext = context;

		//		updateIntent = new Intent("UPDATE");

		mPlayer = new GameObject();
		mGameObjects = new ArrayList<GameObject>();

		bgMusic = MediaPlayer.create( context, R.raw.at_least_you_tried_greaf);
		bgMusic.setVolume(bgMusicLeftVol, bgMusicRightVol);
		bgMusic.setLooping(true);
		//		bgMusic.start();

		//		bgMusic1 = MediaPlayer.create( context, R.raw.test_shot);
		//		bgMusic1.setLooping(true);
		//		bgMusic1.start();
	}

	public static void loop(float[] projectionMatrix){
		if (true) {
			//------LOOP DOS OBJETOS-----------
			if (mGameObjects != null ) {
				for (GameObject gameObject : mGameObjects) {
					//Realiza updates e renders somente qdo objeto se encontra no alcance definido
					if(gameObject.getPosition().getX() < getPlayer().getPosition().getX()+mUPDATE_RANGE_X &&
							gameObject.getPosition().getX() > getPlayer().getPosition().getX()-mUPDATE_RANGE_X &&
							gameObject.getPosition().getY() < getPlayer().getPosition().getY()+mUPDATE_RANGE_Y &&
							gameObject.getPosition().getY() > getPlayer().getPosition().getY()-mUPDATE_RANGE_Y)
					{
						gameObject.update();
					}
					
					if(gameObject.getPosition().getX() < getPlayer().getPosition().getX()+mRENDERING_RANGE_X &&
							gameObject.getPosition().getX() > getPlayer().getPosition().getX()-mRENDERING_RANGE_X &&
							gameObject.getPosition().getY() < getPlayer().getPosition().getY()+mRENDERING_RANGE_Y &&
							gameObject.getPosition().getY() > getPlayer().getPosition().getY()-mRENDERING_RANGE_Y)
					{
						gameObject.render(projectionMatrix);
					}

				}
			}
			//------LOOP DO PLAYER-------------
			mPlayer.update();
			mPlayer.render(projectionMatrix);
		}

	}

	public static void pause(){
		bgMusic.pause();
	}

	public static void resume(){
		//		bgMusic.start();
	}

	public static void stop(){
		bgMusic.stop();
		bgMusic.release();
	}

	public static GameObject getPlayer() {
		if(mPlayer!=null) return mPlayer;
		else return new GameObject();
	}

	public static void setPlayer(GameObject mPlayer) {
		World.mPlayer = mPlayer;
		bgMusic.start();
	}

	public static GameObject getObject(int index){

		return mGameObjects.get(index);

	}

	public static GameObject getObject(GameObject object){

		return mGameObjects.get(mGameObjects.indexOf(object));

	}

	public static GameObject getObjectById(int id){

		if(mGameObjects!=null){			
			for (GameObject gameObject : mGameObjects) {
				if(gameObject.getId()==id) return mGameObjects.get(mGameObjects.indexOf(gameObject));
			}
		}

		return null;
	}

	public static void addObject(GameObject object){
		//TODO: sistema eficiente de atribuicao de ids
		//Se id ja nao foi estabelecido, atribuir baseado em posicao
		if(object.getId() == -2) { 
			object.setId(mGameObjects.size()-1);
		}
		mGameObjects.add(object);
	}

	public static void removeObject(int index){
		mGameObjects.remove(index);
	}

	public static void removeObject(GameObject object){
		mGameObjects.remove(object);
	}

	public static void sendMessages(final int objectId, final ArrayList<Message> curMessages) {
		new Runnable() {
			public void run() {
				try {
					getObject(objectId).recieveMessages(curMessages);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.run();
	}

}
