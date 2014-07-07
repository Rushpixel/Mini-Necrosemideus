package game.ui;

import core.util.Shape;

public class Heart {
	
	public float HP = 300;
	
	public Slot slot1 = new Slot_Empty();
	public Slot slot2 = new Slot_Empty();
	public Slot slot3 = new Slot_Empty();
	
	public int stat_shots;
	public int stat_rapid;
	
	public void updateSlots(boolean shot){
		slot1.update(this, shot);
		slot1.update(this, shot);
		slot1.update(this, shot);
	}
	
	public void render(float x, float y, boolean c){
		if(!c){
			Shape.square(x + 16, y, 60f, 5, 1, 1, 1, 0.2f);
			Shape.square(x + 16, y, HP / 5f, 5, 1, 1, 1, 0.6f);
		} else{
			Shape.square(x + 16, y, 150f, 5, 1, 1, 1, 0.2f);
			Shape.square(x + 16, y, HP / 2f, 5, 1, 1, 1, 1);
		}
	}

}
