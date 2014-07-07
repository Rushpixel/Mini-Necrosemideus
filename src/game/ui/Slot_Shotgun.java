package game.ui;

public class Slot_Shotgun extends Slot{
	
	public Slot_Shotgun(){
		maxdurability = 30;
		durability = maxdurability;
	}
	
	public void update(Heart heart, boolean shoot){
		super.update(heart, shoot);
		heart.stat_shots += 1;
	}

}
