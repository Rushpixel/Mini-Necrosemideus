package game.ui;

import core.Assets;
import core.util.Shape;

public class Slot_Vampire extends Slot{
	
	public Slot_Vampire(){
		maxdurability = 30;
		durability = maxdurability;
	}
	
	public void setValue(Heart h){
		h.stat_vampire += 1;
	}
	
	public void renderUI(float x, float y){
		Shape.square(x, y, 16, 16, 1f, 1f, 1f, 0.5f);
		Shape.square(x, y, 16, 16, Assets.TEXTURE_SLOT_VAMPIRE);
		Shape.square(x, y + 16, 16, -16 + 16 / ((float) maxdurability / durability), 0f, 0f, 0f, 0.4f);
	}
	
	public void renderCUI(float x, float y){
		Shape.square(x, y, 32, 32, 1f, 1f, 1f, 0.5f);
		Shape.square(x, y, 32, 32, Assets.TEXTURE_SLOT_VAMPIRE);
		Shape.number(durability, x - 17, y + 3, 16, 22, Assets.TEXTURE_NUM);
	}
	
	public void render3D(float x, float y, float rotation){
		Shape.squarer(x, y, 16, 16, 16, 0, 0, rotation, Assets.TEXTURE_SLOT_VAMPIRE);
	}

}
