package br.jp.redsparrow.game;

import br.jp.redsparrow.engine.GameActivity;

public class MainActivity extends GameActivity
{
	public MainActivity ()
	{
		super(new RedWorld(),
		      new RedInputManager());
	}
}
