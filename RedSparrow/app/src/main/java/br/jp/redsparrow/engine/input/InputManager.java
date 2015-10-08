package br.jp.redsparrow.engine.input;


/**
 * Created by JoaoPaulo on 03/10/2015.
 */
public abstract class InputManager
{
	protected static InputManager impl;
	public static    boolean      useTouch;
	public static    boolean      useSensor;

	protected final TouchInput[] lastTouchInputs = new TouchInput[5];
	protected SensorInput lastSensorInput;

	public InputManager (boolean useTouch, boolean useSensor)
	{
		InputManager.useTouch = useTouch;
		InputManager.useSensor = useSensor;
	}

	public static void set (InputManager impl)
	{
		InputManager.impl = impl;
	}

	public static void setLastTouchInput(int index, TouchInput touchInput)
	{
		if(index > 4) index = 4;
		impl.lastTouchInputs[index] = touchInput;
	}

	public static void setLastSensorInput(SensorInput sensorInput)
	{
		impl.lastSensorInput = sensorInput;
	}

	public static void processInputs()
	{
		if(impl.lastTouchInputs[0] != null)
			impl.lastTouchInputs[0] = (impl.onTouch_0(impl.lastTouchInputs[0]) ? null : impl.lastTouchInputs[0]);
		if(impl.lastTouchInputs[1] != null)
			impl.lastTouchInputs[1] = (impl.onTouch_1(impl.lastTouchInputs[1]) ? null : impl.lastTouchInputs[1]);
		if(impl.lastTouchInputs[2] != null)
			impl.lastTouchInputs[2] = (impl.onTouch_2(impl.lastTouchInputs[2]) ? null : impl.lastTouchInputs[2]);
		if(impl.lastTouchInputs[3] != null)
			impl.lastTouchInputs[3] = (impl.onTouch_3(impl.lastTouchInputs[3]) ? null : impl.lastTouchInputs[3]);
		if(impl.lastTouchInputs[4] != null)
			impl.lastTouchInputs[4] = (impl.onTouch_4(impl.lastTouchInputs[4]) ? null : impl.lastTouchInputs[4]);

		if(impl.lastSensorInput != null)
			impl.onSensorChanged(impl.lastSensorInput);
	}

	protected abstract boolean onTouch_0(TouchInput touchInput);
	protected abstract boolean onTouch_1(TouchInput touchInput);
	protected abstract boolean onTouch_2(TouchInput touchInput);
	protected abstract boolean onTouch_3(TouchInput touchInput);
	protected abstract boolean onTouch_4(TouchInput touchInput);
	protected abstract boolean onSensorChanged(SensorInput sensorInput);
}
