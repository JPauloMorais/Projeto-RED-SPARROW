package br.jp.redsparrow.game;

import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.LifeComponent;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.ProjectilePhysicsComponent;
import br.jp.redsparrow.engine.core.components.SolidColSpritecomponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.components.SpriteComponent;
import br.jp.redsparrow.R;

public class ObjectFactory {

	public enum OBJ_TYPE{
		PLAYER, B_ENEMY, TEST, DBG_BG, PROJECTL
	}

	public static GameObject createObject(Context context, OBJ_TYPE type, float x, float y, float width, float height){

		GameObject obj = new GameObject(x, y, width, height);

		switch (type) {

		case TEST:

			obj.addComponent(new PhysicsComponent(obj));
			obj.addComponent(new SolidColSpritecomponent(context));
			
			break;

		case PLAYER:
			
			obj.setType(OBJ_TYPE.PLAYER);

			obj.addComponent(new PhysicsComponent(obj));
			obj.addComponent(new SpriteComponent( context, R.drawable.nova_nave ));

			ArrayList<MediaPlayer> sounds = new ArrayList<MediaPlayer>();
			sounds.add(MediaPlayer.create(context, R.raw.test_shot));
			sounds.get(0).setVolume(0.005f, 0.005f);
			obj.addComponent(new SoundComponent(context, sounds));
			
			obj.addComponent(new GunComponent());
			//			obj.addComponent(new AnimatedSpriteComponent(context, obj, R.drawable.nova_nave, 4));

			break;

		case B_ENEMY:
			
			obj.setType(OBJ_TYPE.B_ENEMY);
			
			obj.addComponent(new PhysicsComponent(obj));
			obj.addComponent(new SpriteComponent( context, R.drawable.enemy_ship ));
			
			ArrayList<MediaPlayer> soundsE = new ArrayList<MediaPlayer>();
			soundsE.add(MediaPlayer.create(context, R.raw.test_shot));
			soundsE.get(0).setVolume(0.005f, 0.005f);
			obj.addComponent(new SoundComponent(context, soundsE));
			
			obj.addComponent(new GunComponent());

			break;
		
		case DBG_BG:
			
			obj.setType(OBJ_TYPE.DBG_BG);
			
			obj.addComponent(new SpriteComponent(context, R.drawable.dbg_bg));			
			
			break;
			
		case PROJECTL:
			
			obj.setType(OBJ_TYPE.PROJECTL);
			
			obj.addComponent(new ProjectilePhysicsComponent());
			obj.addComponent(new LifeComponent(obj));
			
			obj.addComponent(new SpriteComponent(context, R.drawable.shot_test));
			
			break;

		default:

			break;
		}

		return obj;

	}

}
