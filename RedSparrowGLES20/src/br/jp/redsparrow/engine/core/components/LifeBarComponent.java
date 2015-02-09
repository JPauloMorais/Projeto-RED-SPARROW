package br.jp.redsparrow.engine.core.components;

import br.jp.redsparrow.engine.core.Game;
import br.jp.redsparrow.engine.core.GameObject;
import br.jp.redsparrow.engine.core.Updatable;

public class LifeBarComponent extends Component implements Updatable {

	/*
	 * Component que remove slots de vida quando player os perde
	 * */
	
	private int lifeSlotCount;
	
	public LifeBarComponent(GameObject parent) {
		super("Lifebar", parent);

		lifeSlotCount = parent.getRenderableComponents().size();
		
	}

	@Override
	public void update(Game game, GameObject parent) {

		try {
			
			int dmg = Integer.valueOf(game.getWorld().getPlayer().getMessage("Damage").getMessage().toString());
									
			for (int i = 0; i < dmg; i++) {
				((RelSpriteComponent) parent.getRenderableComponent(lifeSlotCount - 1))
				.setVisible(false);
				lifeSlotCount -= 1;
			}
						
		} catch (Exception e) {

		}
		
	}

}
