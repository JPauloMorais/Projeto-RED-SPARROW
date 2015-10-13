package br.jp.redsparrow.engine;

/**
 * Created by JoaoPaulo on 12/08/2015.
 */
public class RGBA
{
	private static final float ONE_OVER_255 = 1 / 255;
	public static final  RGBA  RED         = new RGBA(1, 0, 0, 1);
	public static final  RGBA  GREEN         = new RGBA(0, 1, 0, 1);
	public static final  RGBA  BLUE         = new RGBA(0, 0, 1, 1);
	public static final  RGBA  WHITE         = new RGBA(1, 1, 1, 1);
	public static final  RGBA  BLACK         = new RGBA(0, 0, 0, 1);

	public float r, g, b, a;

	public RGBA (float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public RGBA (int color)
	{
		int ir = (color >> 24);
		ir &= ~ ((~ 0 << (32 - 24)));
		int ig = (color >> 16);
		ig &= ~ ((~ 0 << (32 - 24)));
		int ib = (color >> 8);
		ib &= ~ ((~ 0 << (32 - 24)));
		int ia = (color);
		ia &= ~ ((~ 0 << (32 - 24)));
		r = (float) (ir * ONE_OVER_255);
		g = (float) (ig * ONE_OVER_255);
		b = (float) (ib * ONE_OVER_255);
		a = (float) (ia * ONE_OVER_255);
	}

	@Override
	public String toString ()
	{
		return "(r: " + r + ",g: " + g + ",b: " + b + ",a: " + a + ")";
	}
}
