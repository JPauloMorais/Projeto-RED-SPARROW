package br.jp.redsparrow.engine.input;

/**
 * Created by JoaoPaulo on 03/10/2015.
 */
public class TouchInput
{
	public static final int TYPE_DOWN = 0;
	public static final int TYPE_MOVE = 1;
	public static final int TYPE_UP   = 2;

	public final int type;
	public final float x,y;

	public TouchInput (int type, float x, float y)
	{
		this.type = type;
		this.x = x;
		this.y = y;
	}
}
