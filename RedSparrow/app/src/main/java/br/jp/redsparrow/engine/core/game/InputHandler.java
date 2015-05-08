package br.jp.redsparrow.engine.core.game;


public class InputHandler extends GameSystem {

	public final String TAG = "Input";

	public InputHandler(Game game) {
		super(game);
	}

	@Override
	public void loop(Game game, float[] projectionMatrix) {}
	
	public void handleTouchPress(float normalizedX, float normalizedY){}
	public void handleTouchRelease(float normalizedX, float normalizedY){}
	public void handleTouchDrag(float normalizedX, float normalizedY){}
	public void handleSensorChange(float[] values){}

}
