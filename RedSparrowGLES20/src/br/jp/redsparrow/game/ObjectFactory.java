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
import br.jp.redsparrow.game.components.EnemyStatsComponent;
import br.jp.redsparrow.game.components.PlayerPhysicsComponent;
import br.jp.redsparrow.game.components.PlayerStatsComponent;
import br.jp.redsparrow.game.components.ProjectilePhysicsComponent;

public class ObjectFactory {

	public enum OBJECT_TYPE{
		PLAYER, 
		BASIC_ENEMY,
		BASIC_ENEMY_2,
		BASIC_ENEMY_3,
		PROJECTILE,
		DBG_BG,
		DBG_BG1,

	}

	public static GameObject createObject(Context context, OBJECT_TYPE type,
			float x, float y) {

		GameObject obj = new GameObject(new AABB(new Vector2f(x, y), 0, 0));

		switch (type) {

		case PLAYER:

			obj.setWidth(1f);
			obj.setHeight(1f);
			obj.setType(OBJECT_TYPE.PLAYER);

			obj.addComponent(new PlayerPhysicsComponent(obj));

			Animation animP = new Animation(1, 1);			
			obj.addComponent(new AnimatedSpriteComponent(context, R.drawable.nova_nave, obj,
					animP, 0.1f, 0.1f));
			((AnimatedSpriteComponent) obj.getRenderableComponent(0)).addAnimation( context, R.drawable.explosion_test,new Animation(5, 4));

			ArrayList<MediaPlayer> sounds = new ArrayList<MediaPlayer>();
			sounds.add(MediaPlayer.create(context, R.raw.test_shot));
			sounds.get(0).setVolume(0.005f, 0.005f);
			obj.addComponent(new SoundComponent(context, obj, sounds));

			obj.addComponent(new GunComponent(obj));

			obj.addComponent(new PlayerStatsComponent(obj, 5));

			break;

		case BASIC_ENEMY:

			obj.setWidth(1f);
			obj.setHeight(1f);
			obj.setType(OBJECT_TYPE.BASIC_ENEMY);

			//Upd 0
			obj.addComponent(new EnemyPhysicsComponent(obj));

			Animation anim = new Animation(1, 1);
			anim.setAmmoToWait(4);
			//RDB 0
			obj.addComponent(new AnimatedSpriteComponent(context, R.drawable.basic_enemy_ship, obj,
					anim, 0.3f, 0.3f));
			((AnimatedSpriteComponent) obj.getRenderableComponent(0)).addAnimation( context, R.drawable.explosion_test,new Animation(5, 4));

			ArrayList<MediaPlayer> soundsE = new ArrayList<MediaPlayer>();
			soundsE.add(MediaPlayer.create(context, R.raw.test_shot));
			soundsE.get(0).setVolume(0.005f, 0.005f);
			//UPD 1
			obj.addComponent(new SoundComponent(context, obj, soundsE));

			//UPD 2
			obj.addComponent(new GunComponent(obj));

			//UPD 3
			EnemyStatsComponent esc = new EnemyStatsComponent(obj, 5);
			obj.addComponent(esc);			

			break;


		case BASIC_ENEMY_2:

			obj.setWidth(1f);
			obj.setHeight(1f);
			obj.setType(OBJECT_TYPE.BASIC_ENEMY_2);

			obj.addComponent(new EnemyPhysicsComponent(obj));

			Animation anim_2 = new Animation(1, 1);
			anim_2.setAmmoToWait(4);
			obj.addComponent(new AnimatedSpriteComponent(context, R.drawable.basic_enemy_shippp, obj,
					anim_2, 0.3f, 0.3f));
			((AnimatedSpriteComponent) obj.getRenderableComponent(0)).addAnimation( context, R.drawable.explosion_test,new Animation(5, 4));


			ArrayList<MediaPlayer> soundsE_2 = new ArrayList<MediaPlayer>();
			soundsE_2.add(MediaPlayer.create(context, R.raw.test_shot));
			soundsE_2.get(0).setVolume(0.005f, 0.005f);
			obj.addComponent(new SoundComponent(context, obj, soundsE_2));

			obj.addComponent(new GunComponent(obj));
			
			//UPD 3
			EnemyStatsComponent esc2 = new EnemyStatsComponent(obj, 5);
			obj.addComponent(esc2);	

			break;

			
		case BASIC_ENEMY_3:

			obj.setWidth(1f);
			obj.setHeight(1f);
			obj.setType(OBJECT_TYPE.BASIC_ENEMY_2);

			obj.addComponent(new EnemyPhysicsComponent(obj));

			Animation anim_3 = new Animation(1, 1);
			anim_3.setAmmoToWait(4);
			obj.addComponent(new AnimatedSpriteComponent(context, R.drawable.basic_enemy_shippp, obj,
					anim_3, 0.3f, 0.3f));
			((AnimatedSpriteComponent) obj.getRenderableComponent(0)).addAnimation( context, R.drawable.explosion_test,new Animation(5, 4));


			ArrayList<MediaPlayer> soundsE_3 = new ArrayList<MediaPlayer>();
			soundsE_3.add(MediaPlayer.create(context, R.raw.test_shot));
			soundsE_3.get(0).setVolume(0.005f, 0.005f);
			obj.addComponent(new SoundComponent(context, obj, soundsE_3));

			obj.addComponent(new GunComponent(obj));

			break;
			
		case PROJECTILE:

			obj.setWidth(0.2f);
			obj.setHeight(0.4f);
			obj.setType(OBJECT_TYPE.PROJECTILE);

			obj.addComponent(new ProjectilePhysicsComponent(obj,1));

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
