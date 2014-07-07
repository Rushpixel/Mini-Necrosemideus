package game.ui;

public abstract class Slot {
	
	public int maxdurability;
	public int durability;
	
	public Slot(){}
	
	public void update(Heart heart, boolean shoot){
		durability--;
	}
	
	public void render(float x, float y){
		
	}

}
