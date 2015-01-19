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

	public SoundComponent(Context context, GameObject parent){
		this(context, parent, new ArrayList<MediaPlayer>());
	}
	
	public SoundComponent(Context context, GameObject parent, ArrayList<MediaPlayer> sounds) {
		super("Sound", parent);
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
//			parent.getMessage("Collision");
//			startSound(0, false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void startSound(int soundIndex, boolean toLoop){
		if(toLoop) mSounds.get(soundIndex).setLooping(true);
		if(!mSounds.get(soundIndex).isPlaying()) mSounds.get(soundIndex).start();
		else mSounds.get(soundIndex).seekTo(0);

	}
	
	public void fadeIn(final int soundIndex, final float[] targetVol, final int fadeVel, final boolean toLoop){
		new Thread(new Runnable() {
			public void run() {
				startSound(soundIndex, toLoop);
				setSoundVolume(soundIndex, 0, 0);
				float curVols[] = mSoundVolumes.get(soundIndex);

				while (curVols[0] < targetVol[0] && curVols[1] < targetVol[1]) {
					curVols = mSoundVolumes.get(soundIndex);
					setSoundVolume(soundIndex,(float) (curVols[0] + 0.005*fadeVel),(float) (curVols[1] + 0.005*fadeVel));
				}
				
			}
		}).start();
	}
	
	public void fadeOut(final int soundIndex, final int fadeVel){
		new Thread(new Runnable() {
			public void run() {
				
				float curVols[] = mSoundVolumes.get(soundIndex);

				while (curVols[0] > 0 && curVols[1] > 0) {
					curVols = mSoundVolumes.get(soundIndex);
					setSoundVolume(soundIndex,(float) (curVols[0] - 0.005*fadeVel),(float) (curVols[1] - 0.005*fadeVel));
				}
				
				stopSound(0);
				
			}
		}).start();
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
		float curVols[] = { 1.0f, 1.0f };
		mSoundVolumes.add(curVols);
	}

	public void addSounds(ArrayList<MediaPlayer> sounds) {
		mSounds.addAll(sounds);
		float curVols[] = { 1.0f, 1.0f };
		for (int i = 0; i < sounds.size(); i++) {
			mSoundVolumes.add(curVols);
		}
	}
	
	public void setSoundVolume(int soundIndex, float leftVolume, float rightVolume){
		float curVols[] = { rightVolume, leftVolume };
		mSoundVolumes.set(soundIndex, curVols);
		try { mSounds.get(soundIndex).setVolume(leftVolume, rightVolume); } catch (Exception e) {}
	}
	
	

}
