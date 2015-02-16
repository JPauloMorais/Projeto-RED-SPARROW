package br.jp.redsparrow.engine.core.game;

public abstract class GameSystem {

	protected Game game;
	
	public GameSystem(Game game) {
		this.game = game;
	}
	
	public void loop(Game game,float[] projectionMatrix) { }
	
}
