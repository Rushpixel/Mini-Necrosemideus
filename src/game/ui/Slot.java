package game.ui;

import core.Assets;
import core.util.Shape;

public abstract class Slot {
	
	public int maxdurability;
	public int durability;
	
	public Slot(){}
	
	public void reportShot(){
		durability--;
	}
	
	public abstract void setValue(Heart h);
	
	public void renderUI(float x, float y){
		
	}
	
	public void renderCUI(float x, float y){
	}
	
	public void render3D(float x, float y, float rotation){
		Shape.cube(x, y, 0, -8, -8, 0, 8, 8, 8, 0, 0, rotation, 1, 1, 1, 1);
	}

}
