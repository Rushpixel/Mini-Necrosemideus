package game.ui;

import core.util.Shape;

public class Heart {
	
	public float HP = 300;
	
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
