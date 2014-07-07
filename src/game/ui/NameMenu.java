package game.ui;

import org.lwjgl.input.Keyboard;

public class NameMenu extends Menu{
	
	public int scroll = 0;
	public int scrollTimer = 0;
	
	public void Update() {
		if (pause != 0) {
			pause--;
		} else {
			UI.get(scroll).update();

			if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				if (scrollTimer == 0) {
					scroll--;
					if (scroll < 0) scroll = UI.size() - 1;
					scrollTimer = 10;
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				if (scrollTimer == 0) {
					scroll++;
					scroll %= UI.size();
					scrollTimer = 10;
				}
			} else {
				scrollTimer = 0;
			}
			if (scrollTimer > 0) scrollTimer--;
		}
	}

}
