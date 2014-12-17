package br.jp.spaceshooter;

import android.content.Context;
import android.media.MediaPlayer;
import br.jp.engine.core.GameController;
import br.jp.engine.core.World;

public class Action {

	public static boolean isTouching;
	private static MediaPlayer mp;
	private Context context;
	
	public Action(Context context) {
		mp = new MediaPlayer(); 
		this.context = context;
	}

	public void goRight(World world) {
		isTouching=true;

		if (world.getEntityCount()>0 && world.getEntityById(0) instanceof Spaceship &&
				world.getEntityById(0).getObjX()+world.getEntityById(0).getWIDTH()<GameController.screenWidth){
			world.getEntityById(0).setpX(10);
		}
		isTouching=false;
	}
	
	public void goLeft(World world) {
		isTouching=true;
		if (world.getEntityCount()>0 && world.getEntityById(0) instanceof Spaceship &&
				world.getEntityById(0).getObjX()>0){
			world.getEntityById(0).setpX(-10);
		}
		isTouching=false;
	}
	
	public void screenClicked(World world){
		isTouching=true;
		if (world.getEntityCount()>=5 && world.getEntityById(0) instanceof Spaceship) {
			world.addProjectile(new Projectile(context, 
					world.getEntityById(0).getObjX()+world.getEntityById(0).getWIDTH()/2 - 32, 
					world.getEntityById(0).getObjY()-66,
					64, 64, 1, world.getEntityById(0), world.getEntityById(1)));
			mp = MediaPlayer.create(context, R.raw.test_shot);
			mp.start();
		}else if(!world.getEntities().isEmpty() && world.getEntityById(0) instanceof Spaceship) {
			world.addEntity(new LilEnemy(context, 64, 64, 1, world.getEntityById(0)));
			world.addProjectile(new Projectile(context, 
					world.getEntityById(0).getObjX()+world.getEntityById(0).getWIDTH()/2 - 32, 
					world.getEntityById(0).getObjY()-66,
					64, 64, 1, world.getEntityById(0), world.getEntityById(1)));
			mp = MediaPlayer.create(context, R.raw.test_shot);
			mp.start();
		}else{
//			world.addEntity(new Spaceship(context, 64, 64, 1));
		}
		isTouching=false;
	}

	public void goUp(World world) {
		isTouching=true;
		if (world.getEntityCount()>0 && world.getEntityById(0) instanceof Spaceship &&
				world.getEntityById(0).getObjY()>0){
			world.getEntityById(0).setpY(-10);
		}
		isTouching=false;
	}

	public void goDown(World world) {
		isTouching=true;
		if (world.getEntityCount()>0 && world.getEntityById(0) instanceof Spaceship &&
				world.getEntityById(0).getObjY()+world.getEntityById(0).getHEIGHT()<GameController.screenHeight){
			world.getEntityById(0).setpY(10);
		}
		isTouching=false;		
	}

}
