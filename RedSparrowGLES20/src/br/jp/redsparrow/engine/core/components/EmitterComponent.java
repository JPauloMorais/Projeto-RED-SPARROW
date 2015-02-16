package br.jp.redsparrow.engine.core.components;

import java.util.ArrayList;

import android.graphics.Color;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.particles.ParticleEmitter;

public class EmitterComponent extends Component implements Updatable {
	
	private ArrayList<ParticleEmitter> mEmitters;
	private ArrayList<Integer> mLifetimes;

	public EmitterComponent(Game game, GameObject parent, 
			int lifetimeFrames, float[] relPos, float dispAngle, float speedVar) {
		super(parent);

		mEmitters = new ArrayList<ParticleEmitter>();
		mEmitters.add(new ParticleEmitter(new float[]{parent.getX()+relPos[0],parent.getY()+relPos[1],relPos[2]},
				new float[]{ 0,0,0.1f},
				Color.rgb(128, 128, 128),
				dispAngle,
				speedVar));
		
		mLifetimes = new ArrayList<Integer>();
		mLifetimes.add(lifetimeFrames);
		

	}

	@Override
	public void update(Game game, GameObject parent) {
		// TODO Auto-generated method stub

	}

	public void addEmitter(ParticleEmitter emitter, int lifetimeFrames) {
		mEmitters.add(emitter);
		mLifetimes.add(lifetimeFrames);
	}

	public void setPosition(int indx, float x, float y, float z) {
		mEmitters.get(indx).setPosition(mParent.getX()+x, mParent.getY()+y, z);
	}

	public void setDirection(int indx, float x, float y, float z) {
		mEmitters.get(indx).setDirection(x, y, z);
	}

	public void setColor(int indx, int color) {
		mEmitters.get(indx).setColor(color);
	}

	public void setDispAngle(int indx, float dispAngle) {
		mEmitters.get(indx).setDispAngle(dispAngle);
	}

	public void setSpeed(int indx, float speedVar) {
		mEmitters.get(indx).setSpeed(speedVar);
	}

}
