package br.jp.redsparrow.engine;

/**
 * Created by JoaoPaulo on 12/08/2015.
 */
public class RGB
{
	private static final float ONE_OVER_255 = 1/255;
	public static final RGB PINK = new RGB(0.8f,0.0f,0.8f);
	public static final RGB BLACK = new RGB(0.0f,0.0f,0.0f);
	public static final RGB WHITE = new RGB(1.0f,1.0f,1.0f);
	public static final RGB GRAY_50 = new RGB(0.5f,0.5f,0.5f);
	public static final RGB RED = new RGB(1,0,0);
	public static final RGB BLUE = new RGB(0,0,1);

	public float r,g,b;

	public RGB (float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public RGB (int color)
	{
		int ir = (color >> 24);
		ir &= ~ ((~ 0 << (32 - 24)) >>> 0);
		int ig = (color >> 16);
		ig &= ~ ((~ 0 << (32 - 24)) >>> 0);
		int ib = (color >> 8);
		ib &= ~ ((~ 0 << (32 - 24)) >>> 0);
		r = (float) (ir * ONE_OVER_255);
		g = (float) (ig * ONE_OVER_255);
		b = (float) (ib * ONE_OVER_255);
	}

	@Override
	public String toString ()
	{
		return "(r: " + r + ",g: " + g + ",b: " + b + ")";
	}
}
