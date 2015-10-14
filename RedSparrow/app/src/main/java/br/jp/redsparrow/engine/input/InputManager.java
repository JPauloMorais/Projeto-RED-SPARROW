package br.jp.redsparrow.engine.input;


import br.jp.redsparrow.engine.GameActivity;

/**
 * Created by JoaoPaulo on 03/10/2015.
 */
public abstract class InputManager
{
	public boolean useTouch;
	public boolean useSensor;

	protected final TouchInput[] lastTouchInputs = new TouchInput[5];
	protected SensorInput lastSensorInput;

	public InputManager (boolean useTouch, boolean useSensor)
	{
		this.useTouch = useTouch;
		this.useSensor = useSensor;
	}

	public void setLastTouchInput(int index, TouchInput touchInput)
	{
		if(index > 4) index = 4;
		lastTouchInputs[index] = touchInput;
	}

	public void setLastSensorInput(SensorInput sensorInput)
	{
		lastSensorInput = sensorInput;
	}

	public void processInputs(GameActivity gameActivity)
	{
		if(lastTouchInputs[0] != null)
			lastTouchInputs[0] = (onTouch_0(gameActivity,lastTouchInputs[0]) ? null : lastTouchInputs[0]);
		if(lastTouchInputs[1] != null)
			lastTouchInputs[1] = (onTouch_1(gameActivity,lastTouchInputs[1]) ? null : lastTouchInputs[1]);
		if(lastTouchInputs[2] != null)
			lastTouchInputs[2] = (onTouch_2(gameActivity,lastTouchInputs[2]) ? null : lastTouchInputs[2]);
		if(lastTouchInputs[3] != null)
			lastTouchInputs[3] = (onTouch_3(gameActivity,lastTouchInputs[3]) ? null : lastTouchInputs[3]);
		if(lastTouchInputs[4] != null)
			lastTouchInputs[4] = (onTouch_4(gameActivity,lastTouchInputs[4]) ? null : lastTouchInputs[4]);

		if(lastSensorInput != null)
			onSensorChanged(lastSensorInput);
	}

	protected abstract boolean onTouch_0(GameActivity gameActivity, TouchInput touchInput);
	protected abstract boolean onTouch_1(GameActivity gameActivity, TouchInput touchInput);
	protected abstract boolean onTouch_2(GameActivity gameActivity, TouchInput touchInput);
	protected abstract boolean onTouch_3(GameActivity gameActivity, TouchInput touchInput);
	protected abstract boolean onTouch_4(GameActivity gameActivity, TouchInput touchInput);
	protected abstract boolean onSensorChanged(SensorInput sensorInput);
}
