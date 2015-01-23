package br.jp.redsparrow.game;

import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;
import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.Animation;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.HUDitem;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.AnimatedSpriteComponent;
import br.jp.redsparrow.engine.core.components.GunComponent;
import br.jp.redsparrow.engine.core.components.LifeBarComponent;
import br.jp.redsparrow.engine.core.components.RelSpriteComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.components.SpriteComponent;
import br.jp.redsparrow.engine.core.physics.AABB;
import br.jp.redsparrow.game.components.EnemyPhysicsComponent;
import br.jp.redsparrow.game.components.PlayerPhysicsComponent;
import br.jp.redsparrow.game.components.PlayerStatsComponent;
import br.jp.redsparrow.game.components.ProjectilePhysicsComponent;

public class ObjectFactory {

	public enum OBJECT_TYPE{
		PLAYER, 
		BASIC_ENEMY,
		PROJECTILE,
		DBG_BG,
		DBG_BG1,
		TEST
	}
	
	public static GameObject createObject(Context context, OBJECT_TYPE type,
			float x, float y) {
		
		GameObject obj = new GameObject(new AABB(new Vector2f(x, y), 0, 0));

		switch (type) {

		case TEST:

			
			
			break;

		case PLAYER:

			obj.setWidth(1f);
			obj.setHeight(1f);
			obj.setType(OBJECT_TYPE.PLAYER);

			obj.addComponent(new PlayerPhysicsComponent(obj));

			Animation animP = new Animation(4, 3);			
			obj.addComponent(new AnimatedSpriteComponent(context, R.drawable.player_ship, obj,
					animP, 0.1f, 0.1f));

			ArrayList<MediaPlayer> sounds = new ArrayList<MediaPlayer>();
			sounds.add(MediaPlayer.create(context, R.raw.test_shot));
			sounds.get(0).setVolume(0.005f, 0.005f);
			obj.addComponent(new SoundComponent(context, obj, sounds));

			obj.addComponent(new GunComponent(obj));
			
			obj.addComponent(new PlayerStatsComponent(obj, 5, 5));
			//			obj.addComponent(new AnimatedSpriteComponent(context, obj, R.drawable.nova_nave, 4));
			
			break;

		case BASIC_ENEMY:

			obj.setWidth(1f);
			obj.setHeight(1f);
			obj.setType(OBJECT_TYPE.BASIC_ENEMY);

			obj.addComponent(new EnemyPhysicsComponent(obj));
//			obj.addComponent(new SpriteComponent( context, R.drawable.enemy_ship, 
//					obj,
//					0.3f, 0.3f ));
			Animation anim = new Animation(1, 1);
			anim.setAmmoToWait(4);
			obj.addComponent(new AnimatedSpriteComponent(context, R.drawable.enemy_ship, obj,
					anim, 0.3f, 0.3f));

			ArrayList<MediaPlayer> soundsE = new ArrayList<MediaPlayer>();
			soundsE.add(MediaPlayer.create(context, R.raw.test_shot));
			soundsE.get(0).setVolume(0.005f, 0.005f);
			obj.addComponent(new SoundComponent(context, obj, soundsE));

			obj.addComponent(new GunComponent(obj));

			break;

		case PROJECTILE:

			obj.setWidth(0.2f);
			obj.setHeight(0.4f);
			obj.setType(OBJECT_TYPE.PROJECTILE);

			obj.addComponent(new ProjectilePhysicsComponent(obj));

			SpriteComponent psc = new SpriteComponent(context, R.drawable.projectile_1, obj, 0.2f,0.2f,
					2, 1, 0, 1);
			obj.addComponent(psc);
			
			break;

		case DBG_BG:
			
			obj.setWidth(200f);
			obj.setHeight(200f);
			obj.setType(OBJECT_TYPE.DBG_BG);
			
			obj.addComponent(new SpriteComponent(context, R.drawable.stars_test1, obj, 0, 0));
			
			break;
			
		case DBG_BG1:

			obj.setWidth(200f);
			obj.setHeight(200f);
			obj.setType(OBJECT_TYPE.DBG_BG1);
			
			obj.addComponent(new SpriteComponent(context, R.drawable.stars_test1, obj, 0, 0));
			
			break;
			
		default:

			break;
		}

		return obj;

	}
	
	public enum HUDITEM_TYPE {
		AMMO_DISP, LIFEBAR
	}

	public static HUDitem createHUDitem(Context context, HUDITEM_TYPE type ) {
		
		HUDitem item;
		
		switch (type) {
		case AMMO_DISP:
			
			item = new HUDitem(context, -19, 38, 10, 10);
			item.addComponent(new SpriteComponent(context, R.drawable.ammo_display_test, item, 0, 0));
			item.addComponent(new RelSpriteComponent(context, R.drawable.projectile_1, item,
					new Vector2f(0, 1f), 8, 8,
					2, 1, 0, 1));
			
			break;
			
		case LIFEBAR:
			
			item = new HUDitem(context, -14, 38, 9, 3);
			
			item.addComponent(new LifeBarComponent(item));
			
			//life slots
			item.addComponent(new RelSpriteComponent(context, R.drawable.life_gauge_test, item,
					new Vector2f(0, 0), 3, 3));
			item.addComponent(new RelSpriteComponent(context, R.drawable.life_gauge_test, item,
					new Vector2f(3, 0), 3, 3));
			item.addComponent(new RelSpriteComponent(context, R.drawable.life_gauge_test, item,
					new Vector2f(6, 0), 3, 3));
			item.addComponent(new RelSpriteComponent(context, R.drawable.life_gauge_test, item,
					new Vector2f(9, 0), 3, 3));
			item.addComponent(new RelSpriteComponent(context, R.drawable.life_gauge_test, item,
					new Vector2f(12, 0), 3, 3));
			
			break;

		default:
			item = new HUDitem(context, 0, 0, 10, 10);
			break;
		}	
		
		return item;
		
	}

}
