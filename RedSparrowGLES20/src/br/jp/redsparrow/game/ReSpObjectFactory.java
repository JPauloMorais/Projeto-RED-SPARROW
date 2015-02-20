package br.jp.redsparrow.game;

import br.jp.redsparrow.R;
import br.jp.redsparrow.engine.core.Animation;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Vector2f;
import br.jp.redsparrow.engine.core.components.AnimatedSpriteComponent;
import br.jp.redsparrow.engine.core.components.SoundComponent;
import br.jp.redsparrow.engine.core.components.SpriteComponent;
import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.game.ObjectFactory;
import br.jp.redsparrow.engine.core.game.ObjectType;
import br.jp.redsparrow.engine.core.physics.AABB;
import br.jp.redsparrow.game.objecttypes.basicenemy.EnemyAIComponent;
import br.jp.redsparrow.game.objecttypes.basicenemy.EnemyGunComponent;
import br.jp.redsparrow.game.objecttypes.basicenemy.EnemyPhysicsComponent;
import br.jp.redsparrow.game.objecttypes.basicenemy.EnemyProjectilePhysicsComponent;
import br.jp.redsparrow.game.objecttypes.basicenemy.EnemyStatsComponent;
import br.jp.redsparrow.game.objecttypes.basicplayer.PlayerGunComponent;
import br.jp.redsparrow.game.objecttypes.basicplayer.PlayerPhysicsComponent;
import br.jp.redsparrow.game.objecttypes.basicplayer.PlayerStatsComponent;

public class ReSpObjectFactory extends ObjectFactory {

	public ReSpObjectFactory(Game game) {
		super(game);

		//-----SUPERTYPES-----------
		mSupertypes.add(new ObjectType("Player", null) {
			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				return null;
			}
		});
		mSupertypes.add(new ObjectType("Enemy", null) {
			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				return null;
			}
		});
		mSupertypes.add(new ObjectType("Projectile", null) {
			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				return null;
			}
		});
		mSupertypes.add(new ObjectType("BG", null) {
			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				return null;
			}
		});

		
		//------TYPES------------
		ObjectType basicPlayer = new ObjectType("BasicPlayer", mSupertypes.get(0)) {

			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				GameObject obj = new GameObject(new AABB(new Vector2f(positionX, positionY), 1, 1));
				
				obj.setType(this);

				obj.addComponent( "Physics", new PlayerPhysicsComponent(obj));

				Animation animP = new Animation(1, 1);			
				obj.addComponent("AnimatedSprite",
						new AnimatedSpriteComponent(game.getContext(), R.drawable.nova_nave, obj,
								animP, 0.1f, 0.1f));
				((AnimatedSpriteComponent) obj.getRenderableComponent("AnimatedSprite")).addAnimation( game.getContext(), R.drawable.explosion_test,new Animation(5, 4));

				obj.addComponent("Sound",
						new SoundComponent(game.getContext(), obj, R.raw.test_shot));
				((SoundComponent) obj.getUpdatableComponent("Sound"))
				.setSoundVolume(0, 0.005f, 0.005f);

				obj.addComponent("Gun",
						new PlayerGunComponent(obj));

				obj.addComponent("Stats",
						new PlayerStatsComponent(obj, 500));

				return obj;
			}

		};
		mTypes.put(basicPlayer.getName(), basicPlayer);
		
		ObjectType basicEnemy1 = new ObjectType("BasicEnemy1", mSupertypes.get(1)) {

			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				GameObject obj = new GameObject(new AABB(new Vector2f(positionX, positionY), 1, 1));

				obj.setType(this);
				
				obj.addComponent("Physics", new EnemyPhysicsComponent(obj));

				Animation anim = new Animation(1, 1);
				anim.setAmmoToWait(4);
				obj.addComponent("AnimatedSprite", new AnimatedSpriteComponent(game.getContext(), R.drawable.basic_enemy_ship, obj,
						anim, 0.3f, 0.3f));
				((AnimatedSpriteComponent) obj.getRenderableComponent("AnimatedSprite")).addAnimation( game.getContext(), R.drawable.explosion_test,new Animation(5, 4));

				obj.addComponent("Sound",
						new SoundComponent(game.getContext(), obj, R.raw.test_shot));
				((SoundComponent) obj.getUpdatableComponent("Sound"))
				.setSoundVolume(0, 0.005f, 0.005f);

				obj.addComponent("Gun", new EnemyGunComponent(obj, 0, 0));

				obj.addComponent("Stats", new EnemyStatsComponent(obj, 5));		
				
				obj.addComponent("AI", new EnemyAIComponent(obj));

				return obj;
			}

		};
		mTypes.put(basicEnemy1.getName(), basicEnemy1);
		
		ObjectType basicEnemy2 = new ObjectType("BasicEnemy2", mSupertypes.get(1)) {

			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				GameObject obj = new GameObject(new AABB(new Vector2f(positionX, positionY), 1, 1));
				
				obj.setType(this);

				obj.addComponent("Physics", new EnemyPhysicsComponent(obj));

				Animation anim = new Animation(1, 1);
				anim.setAmmoToWait(4);
				obj.addComponent("AnimatedSprite", new AnimatedSpriteComponent(game.getContext(), R.drawable.basic_enemy_shippp, obj,
						anim, 0.3f, 0.3f));
				((AnimatedSpriteComponent) obj.getRenderableComponent("AnimatedSprite")).addAnimation( game.getContext(), R.drawable.explosion_test,new Animation(5, 4));

				obj.addComponent("Sound",
						new SoundComponent(game.getContext(), obj, R.raw.test_shot));
				((SoundComponent) obj.getUpdatableComponent("Sound"))
				.setSoundVolume(0, 0.005f, 0.005f);

				obj.addComponent("Gun", new EnemyGunComponent(obj, 0, 0));

				obj.addComponent("Stats", new EnemyStatsComponent(obj, 5));		

				return obj;
			}

		};
		mTypes.put( basicEnemy2.getName(), basicEnemy2);
		
		ObjectType basicEnemy3 = new ObjectType("BasicEnemy3", mSupertypes.get(1)) {
			
			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				GameObject obj = new GameObject(new AABB(new Vector2f(positionX, positionY), 1, 1));

				obj.setType(this);
				
				obj.addComponent("Physics", new EnemyPhysicsComponent(obj));

				Animation anim = new Animation(1, 1);
				anim.setAmmoToWait(4);
				obj.addComponent("AnimatedSprite", new AnimatedSpriteComponent(game.getContext(), R.drawable.basic_enemy_shippp, obj,
						anim, 0.3f, 0.3f));
				((AnimatedSpriteComponent) obj.getRenderableComponent("AnimatedSprite")).addAnimation( game.getContext(), R.drawable.explosion_test,new Animation(5, 4));

				obj.addComponent("Sound",
						new SoundComponent(game.getContext(), obj, R.raw.test_shot));
				((SoundComponent) obj.getUpdatableComponent("Sound"))
				.setSoundVolume(0, 0.005f, 0.005f);

				obj.addComponent("Gun", new EnemyGunComponent(obj, 0, 0));

				obj.addComponent("Stats", new EnemyStatsComponent(obj, 5));		

				return obj;
			}
			
		};
		mTypes.put( basicEnemy3.getName(), basicEnemy3);
		
		ObjectType basicProjectile = new ObjectType("BasicProjectile", mSupertypes.get(2)) {

			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				GameObject obj = new GameObject(new AABB(new Vector2f(positionX, positionY), .08f, .08f));
				
				obj.setType(this);

				obj.addComponent("Physics", new EnemyProjectilePhysicsComponent(obj,1));

				obj.addComponent("Sprite", new SpriteComponent(game.getContext(), R.drawable.player_projectile_1, obj, 0.2f,0.2f,
						2, 1, 0, 1));
				
				return obj;
			}
			
		};
		mTypes.put(basicProjectile.getName(), basicProjectile);
		
		ObjectType basicEnemyProjectile = new ObjectType("BasicEnemyProjectile", mSupertypes.get(2)) {

			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				GameObject obj = new GameObject(new AABB(new Vector2f(positionX, positionY), .03f, .03f));
				
				obj.setType(this);

				obj.addComponent("Physics", new EnemyProjectilePhysicsComponent(obj,1));

				obj.addComponent("Sprite", new SpriteComponent(game.getContext(), R.drawable.enemy_projectile_1, obj, 0.2f,0.2f,
						2, 1, 0, 1));
				
				return obj;
			}
			
		};
		mTypes.put(basicEnemyProjectile.getName(), basicEnemyProjectile);
		
		ObjectType bg1 = new ObjectType("BG1", mSupertypes.get(3)) {

			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				GameObject obj = new GameObject(new AABB(new Vector2f(positionX, positionY), 200, 200));
				
				obj.setType(this);
				
				obj.addComponent("Sprite", new SpriteComponent(game.getContext(), R.drawable.stars_test1, obj, 0, 0));
				
				return obj;
			}
			
		};
		mTypes.put( bg1.getName(), bg1);
		
		ObjectType bg2 = new ObjectType("BG2", mSupertypes.get(3)) {
			
			@Override
			public GameObject create(Game game, float positionX, float positionY) {
				GameObject obj = new GameObject(new AABB(new Vector2f(positionX, positionY), 200, 200));
				
				obj.setType(this);
				
				obj.addComponent("Sprite", new SpriteComponent(game.getContext(), R.drawable.stars_test1, obj, 0, 0));
				
				return obj;
			}
			
		};
		mTypes.put(bg2.getName(), bg2);

	}

	@Override
	public GameObject create(String typeName, float x, float y) {

		if(mTypes.containsKey(typeName)) return mTypes.get(typeName).create(game, x, y);
		else return new GameObject(new AABB(new Vector2f(x, y), 0, 0));
		
	}

}
