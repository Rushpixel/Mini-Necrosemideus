package game.ui;

import core.util.Shape;

public class Heart {
	
	public float HP = 300;
	
	public Slot slot1 = new Slot_Empty();
	public Slot slot2 = new Slot_Empty();
	public Slot slot3 = new Slot_Empty();
	
	public int stat_split;
	public int stat_rapid;
	public int stat_impact;
	public int stat_range;
	public int stat_vampire;
	
	public void updateSlots(){
		stat_split = 0;
		stat_rapid = 0;
		stat_impact = 0;
		stat_range = 0;
		stat_vampire = 0;
		
		
		if(slot1.durability == 0) slot1 = new Slot_Empty();
		if(slot2.durability == 0) slot2 = new Slot_Empty();
		if(slot3.durability == 0) slot3 = new Slot_Empty();
		
		slot1.setValue(this);
		slot2.setValue(this);
		slot3.setValue(this);
	}
	
	public void render(float x, float y, boolean c){
		if(!c){
			Shape.square(x + 16, y - 5, 60f, -5, 1, 1, 1, 0.2f);
			Shape.square(x + 16, y - 5, HP / 5f, -5, 1, 1, 1, 0.6f);
		} else{
			Shape.square(x + 16, y - 5, 150f, -5, 1, 1, 1, 0.2f);
			Shape.square(x + 16, y - 5, HP / 2f, -5, 1, 1, 1, 1);
		}
	}

}
