package br.jp.redsparrow.engine.core;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.components.StatsComponent;
import br.jp.redsparrow.engine.core.messages.Message;
import br.jp.redsparrow.engine.core.particles.ParticleEmitter;
import br.jp.redsparrow.engine.core.particles.ParticleSystem;
import br.jp.redsparrow.engine.core.physics.Bounds;
import br.jp.redsparrow.engine.core.physics.Collision;
import br.jp.redsparrow.engine.core.util.LogConfig;
import br.jp.redsparrow.game.ObjectFactory.OBJECT_TYPE;
import br.jp.redsparrow.game.components.ProjectilePhysicsComponent;

public class World extends GameSystem{
	
	//TODO: Saveinstancestate stuff

	private static final String TAG = "World";
	private static boolean isRunning;

	private static GameObject mPlayer;
	private static ArrayList<GameObject> mGameObjects;
	private static ArrayList<GameObject> mToRemove;

	private static ArrayList<GameObject> mToCheck;
	private static Quadtree mQuadTree;

	private static final float mUPDATE_RANGE_X = 26.0f;
	private static final float mUPDATE_RANGE_Y = 30.0f;
	private static final float mRENDERING_RANGE_X = 16.0f;
	private static final float mRENDERING_RANGE_Y = 20.0f;

	private static SoundComponent bgmSoundComponent;
	private static float bgMusicRightVol = 0.1f;
	private static float bgMusicLeftVol = 0.1f;

	private static ParticleSystem mParticleSystem;
	private static ArrayList<ParticleEmitter> mEmiters;

	public World(Context context, Game game){
		super(game);
		
		isRunning = false;

		//Objetos
		mPlayer = new GameObject();
		mGameObjects = new ArrayList<GameObject>();
		mToRemove = new ArrayList<GameObject>();

		//Quadtree
		mToCheck = new ArrayList<GameObject>();
		mQuadTree = new Quadtree(0, new RectF(-200, -200, 200, 200));

		//BGM
		bgmSoundComponent = new SoundComponent(context, new GameObject());
		bgmSoundComponent.addSound(R.raw.at_least_you_tried_greaf);

		//Particulas
		mParticleSystem = new ParticleSystem(1000, context);		
		mEmiters = new ArrayList<ParticleEmitter>();
		float[] pos = {0,0,0};
		float[] dir = {0,0,0.5f};
		mEmiters.add(new ParticleEmitter(pos, dir, Color.rgb(255, 0, 0), 90, 1));
		mEmiters.add(new ParticleEmitter(pos, dir, Color.rgb(255, 255, 0), 45, 1));		

	}

	private void onStart(){
		isRunning = true;
		//		physicsCheckT.start();
		float targetVol[] = { bgMusicLeftVol, bgMusicRightVol };
		bgmSoundComponent.fadeIn(0, targetVol, 1, true);
	}
	
	@Override
	public void loop(Game game, float[] projectionMatrix){
		if (isRunning) {
			//-------LIMPANDO---------
			mQuadTree.clear();
			removeDead(game);

			//------PREECHENDO QUADTREE-------
			for (int k=0; k < mGameObjects.size(); k++) {
				if(mGameObjects.get(k).getPosition().getX() < getPlayer().getPosition().getX()+mUPDATE_RANGE_X &&
						mGameObjects.get(k).getPosition().getX() > getPlayer().getPosition().getX()-mUPDATE_RANGE_X &&
						mGameObjects.get(k).getPosition().getY() < getPlayer().getPosition().getY()+mUPDATE_RANGE_Y &&
						mGameObjects.get(k).getPosition().getY() > getPlayer().getPosition().getY()-mUPDATE_RANGE_Y)
				{
					mQuadTree.add(mGameObjects.get(k));
				}else if(mGameObjects.get(k).getType().equals(OBJECT_TYPE.PROJECTILE)) mGameObjects.get(k).die();
			}

			//------LOOP DOS OBJETOS-----------
			if(mGameObjects!=null && !mGameObjects.isEmpty()){
				for (int i=0; i < mGameObjects.size(); i++) {
					mToCheck.clear();
					mQuadTree.getToCheck(mToCheck, mGameObjects.get(i).getBounds());

					//CHECANDO COLISAO
					for (int j=0; j < mToCheck.size(); j++) {

						if (Collision.areIntersecting(mGameObjects.get(i).getBounds(), mToCheck.get(j).getBounds())) {

							if(!mGameObjects.get(i).getType().equals(OBJECT_TYPE.PROJECTILE) &&
									!mToCheck.get(j).getType().equals(OBJECT_TYPE.PROJECTILE)){

								((PhysicsComponent) mGameObjects.get(i).getUpdatableComponent(0)).collide(
										Collision.getColVector( mGameObjects.get(i).getBounds(), mToCheck.get(j).getBounds()));


							}else if(!mToCheck.get(j).getType().equals(OBJECT_TYPE.PROJECTILE)){
								
								if(!((ProjectilePhysicsComponent) mGameObjects.get(i).getUpdatableComponent(0))
										.getShootertype().equals(mToCheck.get(j).getType())) {

									((StatsComponent) mToCheck.get(j).getUpdatableComponent(3))
									.takeDamage(
											((ProjectilePhysicsComponent) mGameObjects.get(i).getUpdatableComponent(0))
											.getDamage());

									mGameObjects.get(i).die();
								}

							}

						}

					}			

					//Update e Render se dentro limite
					if(mGameObjects.get(i).getX() < getPlayer().getX()+mUPDATE_RANGE_X &&
							mGameObjects.get(i).getX() > getPlayer().getX()-mUPDATE_RANGE_X &&
							mGameObjects.get(i).getY() < getPlayer().getY()+mUPDATE_RANGE_Y &&
							mGameObjects.get(i).getY() > getPlayer().getY()-mUPDATE_RANGE_Y)
					{
						mGameObjects.get(i).update(game);
					}

					if(mGameObjects.get(i).getX() < getPlayer().getX()+mRENDERING_RANGE_X &&
							mGameObjects.get(i).getX() > getPlayer().getX()-mRENDERING_RANGE_X &&
							mGameObjects.get(i).getY() < getPlayer().getY()+mRENDERING_RANGE_Y &&
							mGameObjects.get(i).getY() > getPlayer().getY()-mRENDERING_RANGE_Y)
					{						
						mGameObjects.get(i).render(projectionMatrix);
					}

				}
			}

			//------LOOP DO PLAYER-------------
			mToCheck.clear();
			mQuadTree.getToCheck(mToCheck, mPlayer.getBounds());

			for (int i = 0; i < mToCheck.size(); i++) {
				if(Collision.areIntersecting((Bounds) mPlayer.getBounds(), (Bounds) mToCheck.get(i).getBounds())){

					if(!mToCheck.get(i).getType().equals(OBJECT_TYPE.PROJECTILE)){

						((PhysicsComponent) mToCheck.get(i).getUpdatableComponent(0)).collide(
								Collision.getColVector((Bounds) mToCheck.get(i).getBounds(), (Bounds) mPlayer.getBounds()));

					}else {
						if(!((ProjectilePhysicsComponent) mToCheck.get(i).getUpdatableComponent(0))
								.getShootertype().equals(OBJECT_TYPE.PLAYER)) {

							((StatsComponent) mPlayer.getUpdatableComponent(3)).takeDamage(5);
							((PhysicsComponent) mPlayer.getUpdatableComponent(0)).collide(
									Collision.getColVector(mPlayer.getBounds(), mToCheck.get(i).getBounds()).div(2));

						}
					}

				}
			}

			//------PARTICULAS-----------
			mEmiters.get(0).setPosition(new float[] {
					mPlayer.getX(), mPlayer.getY(),0
			});
			mEmiters.get(1).setPosition(new float[] {
					mPlayer.getX(), mPlayer.getY(),0
			});

			for (ParticleEmitter emitter : mEmiters) {
				emitter.addParticles(mParticleSystem, mParticleSystem.getCurTime(), 20);
			}
			mParticleSystem.render(projectionMatrix);

			mPlayer.update(game);
			mPlayer.render(projectionMatrix);


		}else {
			onStart();
		}
	}

	public void pause(){
		try {
			bgmSoundComponent.pauseSound(0);
		} catch (Exception e) {

		}
	}

	public void resume(){
		//		bgMusic.start();
	}

	public void stop(){
		try {
			bgmSoundComponent.stopSound(0);
			bgmSoundComponent.releaseSound(0);
		} catch (Exception e) {

		}
	}

	public SoundComponent getBgmSoundComponent() {
		return bgmSoundComponent;
	}

	public void setBgmSoundComponent(SoundComponent bgmSoundComponent) {
		World.bgmSoundComponent = bgmSoundComponent;
	}

	public GameObject getPlayer() {
		if(mPlayer!=null) return mPlayer;
		else return new GameObject();
	}

	public void setPlayer(GameObject mPlayer) {
		World.mPlayer = mPlayer;
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

	public Vector2f getUpdatingRange(){
		return new Vector2f(mUPDATE_RANGE_X, mUPDATE_RANGE_Y);
	}

	public Vector2f getRenderingRange(){
		return new Vector2f(mRENDERING_RANGE_X, mRENDERING_RANGE_X);
	}

	public void addObject(GameObject object){

		//TODO: sistema eficiente de atribuicao de ids
		//Se id ja nao foi estabelecido, atribuir baseado em posicao no array
		if(object.getId() == -2) { 
			object.setId(mGameObjects.size()-1);
		}

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
		mGameObjects.set(0, game.getObjFactory().createObject(GameRenderer.getContext(), OBJECT_TYPE.PLAYER, mGameObjects.get(0).getX(), mGameObjects.get(0).getY()));
		this.setPlayer(mGameObjects.get(0));
		mGameObjects.remove(0);
		//------------------
	}

	public void removeObject(int index){
		mGameObjects.remove(index);
	}

	public void removeObject(GameObject object){
		mGameObjects.remove(object);
	}

	public void addEmitter(ParticleEmitter emitter) {
		mEmiters.add(emitter);
	}

	public ArrayList<ParticleEmitter> getEmitters() {
		return mEmiters;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		World.isRunning = isRunning;
	}

	public void sendMessages(final int objectId, final ArrayList<Message> curMessages) {
		try {
			this.getObject(objectId).recieveMessages(curMessages);
		} catch (Exception e) {	}
	}
}



