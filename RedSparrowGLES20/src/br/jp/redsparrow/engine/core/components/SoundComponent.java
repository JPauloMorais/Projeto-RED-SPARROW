package br.jp.redsparrow.engine.core.components;

import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;

public class SoundComponent extends Component implements Updatable {

	private Context mContext;

	private ArrayList<MediaPlayer> mSounds;
	private ArrayList<float[]> mSoundVolumes;

	public SoundComponent(Context context){
		this(context, new ArrayList<MediaPlayer>());
	}
	
	public SoundComponent(Context context, ArrayList<MediaPlayer> sounds) {
		super("Sound");
		mContext = context;
		
		mSounds = sounds;
		
		mSoundVolumes = new ArrayList<float[]>();
		for (int i = 0; i < mSounds.size(); i++) {			
			float defVols[] = { 1.0f, 1.0f };
			
			mSounds.get(i).setVolume( defVols[0], defVols[1] );
			mSoundVolumes.add(defVols);
		}
	}

	@Override
	public void update(GameObject parent) {
		try {
			parent.getMessage("Collision").getOperation();
			startSound(0, false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void startSound(int soundIndex, boolean toLoop){
		if(toLoop) mSounds.get(soundIndex).setLooping(true);
		mSounds.get(soundIndex).start();
	}
	
	public void pauseSound(int soundIndex){
		mSounds.get(soundIndex).pause();
	}
	
	public void stopSound(int soundIndex){
		mSounds.get(soundIndex).stop();
	}
	
	public void releaseSound(int soundIndex){
		mSounds.get(0).release();
	}

	public ArrayList<MediaPlayer> getSounds() {
		return mSounds;
	}

	public void addSound(int soundFileId){
		mSounds.add(MediaPlayer.create(mContext, soundFileId));
	}

	public void addSounds(ArrayList<MediaPlayer> sounds) {
		mSounds.addAll(sounds);
	}
	
	public void setSoundVolume(int soundIndex, float leftVolume, float rightVolume){
		float curVols[] = { rightVolume, leftVolume };
		mSoundVolumes.set(soundIndex, curVols);
		mSounds.get(soundIndex).setVolume(leftVolume, rightVolume);
	}
	
	

}
