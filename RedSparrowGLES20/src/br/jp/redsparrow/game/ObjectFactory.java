package br.jp.redsparrow.game;

import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.components.PhysicsComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.components.SpriteComponent;

import br.jp.redsparrow.R;

public class ObjectFactory {

	public enum OBJ_TYPE{
		PLAYER, B_ENEMY, TEST, DBG_BG
	}

	public static GameObject createObject(Context context, OBJ_TYPE type, float x, float y, float width, float height){

		GameObject obj = new GameObject(x, y, width, height);

		switch (type) {

		case TEST:

			break;

		case PLAYER:


			obj.addComponent(new PhysicsComponent(obj));
			obj.addComponent(new SpriteComponent( context, R.drawable.player_ship ));

			ArrayList<MediaPlayer> sounds = new ArrayList<MediaPlayer>();
			sounds.add(MediaPlayer.create(context, R.raw.test_rocket));
			sounds.get(0).setVolume(0.005f, 0.005f);
			obj.addComponent(new SoundComponent(context, sounds));
			//			obj.addComponent(new AnimatedSpriteComponent(context, obj, R.drawable.nova_nave, 4));

			break;

		case B_ENEMY:
			
			obj.addComponent(new PhysicsComponent(obj));
			obj.addComponent(new SpriteComponent( context, R.drawable.enemy_ship ));

			break;
		
		case DBG_BG:
			
			obj.addComponent(new SpriteComponent(context, R.drawable.dbg_bg));			
			
			break;

		default:

			break;
		}

		return obj;

	}

}
