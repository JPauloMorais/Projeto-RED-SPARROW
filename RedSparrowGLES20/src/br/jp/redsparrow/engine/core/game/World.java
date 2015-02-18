package br.jp.redsparrow.engine.core.game;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.RectF;
import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Quadtree;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.particles.ParticleSystem;
import br.jp.redsparrow.engine.core.physics.AABB;
import br.jp.redsparrow.engine.core.physics.Collision;
import br.jp.redsparrow.engine.core.util.LogConfig;

public class World extends GameSystem{

	private final String TAG = "World";
	private boolean isRunning;

	private GameObject mPlayer;
	private ArrayList<GameObject> mGameObjects;
	private ArrayList<GameObject> mToRemove;
	private int mNextObjID;

	private ArrayList<GameObject> mToCheck;
	private Quadtree mQuadTree;

	private AABB mUpdatingBounds;
	private AABB mRenderingBounds;

	private SoundComponent bgmSoundComponent;
	private float bgMusicRightVol = 0.1f;
	private float bgMusicLeftVol = 0.1f;

	private ParticleSystem mBottomParticleSystem;
	private ParticleSystem mTopParticleSystem;

	public World(Context context, Game game){
		super(game);

		isRunning = false;

		//Objetos
		mPlayer = new GameObject();
		mGameObjects = new ArrayList<GameObject>();
		mToRemove = new ArrayList<GameObject>();
		mNextObjID = -1;

		//Quadtree
		mToCheck = new ArrayList<GameObject>();
		mQuadTree = new Quadtree(0, new RectF(-200, -200, 200, 200));

		//Update/Render bounds
		mUpdatingBounds = new AABB(new Vector2f(0, 0), 52, 70);
		mRenderingBounds = new AABB(new Vector2f(0, 0), 32, 50);

		//BGM
		bgmSoundComponent = new SoundComponent(context, new GameObject(), R.raw.at_least_you_tried_greaf);

		//Particulas
		mBottomParticleSystem = new ParticleSystem(10000, context);		
		mTopParticleSystem = new ParticleSystem(10000, context);		

		//		mEmiters.add(new ParticleEmitter(pos, dir, Color.rgb(255, 0, 0), 90, 1, 100));
		//		mEmiters.add(new ParticleEmitter(pos, dir, Color.rgb(255, 255, 0), 45, 1));		

	}

	private void onStart(){
		isRunning = true;
		float targetVol[] = { bgMusicLeftVol, bgMusicRightVol };
		bgmSoundComponent.fadeIn(0, targetVol, 1, true);
	}

	@Override
	public void loop(Game game, float[] projectionMatrix){
		if (isRunning) {
			//-------LIMPANDO---------
			mQuadTree.clear();
			removeDead(game);

			//-----SETANDO BOUNDS--------------
			mUpdatingBounds.setCenter(mPlayer.getPosition());
			mRenderingBounds.setCenter(mPlayer.getPosition());

			//------PREECHENDO QUADTREE-------
			for (int k=0; k < mGameObjects.size(); k++) {
				if(Collision.isInside(mGameObjects.get(k).getPosition(), mUpdatingBounds))
				{
					mQuadTree.add(mGameObjects.get(k));
				}else if(mGameObjects.get(k).getType().getSuperType().getName().equals("Projectile")) mGameObjects.get(k).die();
			}

			//-----PARTICULAS BOTTOM-----------
			mBottomParticleSystem.render(projectionMatrix);

			//------LOOP DO PLAYER-------------
			mToCheck.clear();
			mQuadTree.getToCheck(mToCheck, mPlayer.getBounds());
			if(LogConfig.ON)Log.i("Quadtree", mToCheck.size() + " objs na leaf");

			for (int i = 0; i < mToCheck.size(); i++) {
				if (mToCheck.get(i).containsUpdatableComponent("Physics")) {
					if (Collision.areIntersecting(mPlayer.getBounds(), mToCheck
							.get(i).getBounds())) {

						((PhysicsComponent) mToCheck.get(i)
								.getUpdatableComponent("Physics"))
								.collide(mPlayer);
						if (mPlayer.containsUpdatableComponent("Physics"))
							((PhysicsComponent) mPlayer
									.getUpdatableComponent("Physics"))
									.collide(mToCheck.get(i));

					}
				}
			}

			mPlayer.update(game);
			mPlayer.render(projectionMatrix);

			//------LOOP DOS OBJETOS-----------
			if(mGameObjects!=null && !mGameObjects.isEmpty()){

				for (int i=0; i < mGameObjects.size(); i++) {
					mToCheck.clear();
					mQuadTree.getToCheck(mToCheck, mGameObjects.get(i).getBounds());

					if (mGameObjects.get(i).containsUpdatableComponent("Physics")) {
						//CHECANDO COLISAO
						for (int j = 0; j < mToCheck.size(); j++) {

							if (Collision.areIntersecting(mGameObjects.get(i)
									.getBounds(), mToCheck.get(j).getBounds())) {

								((PhysicsComponent) mGameObjects.get(i)
										.getUpdatableComponent("Physics"))
										.collide(mToCheck.get(j));

							}

						}
					}
					//Update e Render se dentro limite
					if(Collision.isInside(mGameObjects.get(i).getPosition(), mUpdatingBounds))
					{
						mGameObjects.get(i).update(game);
					}

					if(Collision.isInside(mGameObjects.get(i).getPosition(), mRenderingBounds))
					{						
						mGameObjects.get(i).render(projectionMatrix);
					}

				}


			}

			//------PARTICULAS TOP-----------

			mTopParticleSystem.render(projectionMatrix);


		}else {
			onStart();
		}
	}

	public void pause(){
		isRunning = false;
		try {
			bgmSoundComponent.pauseSound(0);
		} catch (Exception e) {

		}
	}

	public void resume(){
		//		bgMusic.start();
	}

	public void stop(){
		isRunning = false;
		try {
			bgmSoundComponent.stopSound(0);
			bgmSoundComponent.releaseSound(0);
		} catch (Exception e) {

		}
	}

	public SoundComponent getBgmSoundComponent() {
		return bgmSoundComponent;
	}

	public void setBgmSoundComponent(SoundComponent soundComponent) {
		bgmSoundComponent = soundComponent;
	}

	public GameObject getPlayer() {
		if(mPlayer!=null) return mPlayer;
		else {
			GameObject tmp = new GameObject(new AABB(new Vector2f(0, 0), 0, 0));
			return tmp;
		}
	}

	public void setPlayer(GameObject player) {
		mPlayer = player;
	}

	public GameObject getObject(int index){
		return mGameObjects.get(index);
	}

	public GameObject getObject(GameObject object){
		return mGameObjects.get(mGameObjects.indexOf(object));
	}

	public GameObject getObjectById(int id){

		if(mGameObjects != null){			
			for (GameObject gameObject : mGameObjects) {

				if(gameObject.getId()==id) return mGameObjects.get(mGameObjects.indexOf(gameObject));

			}
		}

		return null;

	}

	public AABB getUpdatingBounds(){
		return mUpdatingBounds;
	}

	public AABB getRenderingBounds(){
		return mRenderingBounds;
	}

	public void addObject(GameObject object){

		object.setId(mNextObjID);
		mNextObjID++;

		mGameObjects.add(object);

		if(LogConfig.ON) Log.i(TAG, "Objeto de id " + object.getId() + " add em " + object.getPosition().toString());
	}

	public void addObjects(int layer, GameObject ... objects){
		for (int i = 0; i < objects.length; i++) {
			this.addObject(objects[i]);
		}
	}

	public int getObjectCount(){
		return mGameObjects.size();
	}

	public ArrayList<GameObject> getObjects() {
		return mGameObjects;
	}

	private void removeDead(Game game){
		mToRemove.clear();
		for (int i = 0; i < mGameObjects.size(); i++) {
			if(mGameObjects.get(i).isDead()) {
				mToRemove.add(mGameObjects.get(i));
			}
		}

		if(LogConfig.ON && mToRemove.size() > 0) Log.i(TAG, mToRemove.size() + " objeto(s) morto(s) removido(s)");
		mGameObjects.removeAll(mToRemove);

		if(mPlayer.isDead() && !mGameObjects.isEmpty()) {
			onPlayerDeath(game);
		}
	}

	private void onPlayerDeath(Game game) {
		//-----Teste------
		//Isso e inevitavel Mr. Anderson.
		//		mGameObjects.set(0, game.getObjFactory().createObject(game.getContext(), OBJECT_TYPE.PLAYER, mGameObjects.get(0).getX(), mGameObjects.get(0).getY()));

//		mGameObjects.get(0).setUpdatableComponent("Physics", new PlayerPhysicsComponent(mGameObjects.get(0)));

		setPlayer(mGameObjects.get(0));
		mGameObjects.remove(0);
		//------------------
	}

	public void removeObject(int index){
		mGameObjects.remove(index);
	}

	public void removeObject(GameObject object){
		mGameObjects.remove(object);
	}

	public ParticleSystem getBottomParticleSystem() {
		return mBottomParticleSystem;
	}

	public void setBottomParticleSystem(
			ParticleSystem mBottomParticleSystem) {
		this.mBottomParticleSystem = mBottomParticleSystem;
	}

	public ParticleSystem getTopParticleSystem() {
		return mTopParticleSystem;
	}

	public void setTopParticleSystem(ParticleSystem mTopParticleSystem) {
		this.mTopParticleSystem = mTopParticleSystem;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void sendMessages(final int objectId, final ArrayList<Message> curMessages) {
		try {
			this.getObject(objectId).recieveMessages(curMessages);
		} catch (Exception e) {	}
	}

}



