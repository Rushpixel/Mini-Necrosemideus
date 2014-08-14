package game.ui;

import org.lwjgl.input.Keyboard;

import core.Assets;
import core.Game;
import core.Main;
import core.util.GameMouse;

public class PauseMenu extends Menu {

	public int scroll = 0;
	public int scrollTimer = 0;
	
	public float mouseScroll = 0;

	public PauseMenu() {
		UI.add(new Button(Assets.TEXTURE_BUTTONS, 20, 450, 100, 28, 0, 098f / 256f, 50f / 256f, 084f / 256f, 0, 112f / 256f, 50f / 256f, 098f / 256f));
		UI.add(new Button(Assets.TEXTURE_BUTTONS, 20, 415, 100, 28, 0, 126f / 256f, 50f / 256f, 112f / 256f, 0, 140f / 256f, 50f / 256f, 126f / 256f));
		UI.add(new Button(Assets.TEXTURE_BUTTONS, 20, 380, 100, 28, 0, 154f / 256f, 50f / 256f, 140f / 256f, 0, 168f / 256f, 50f / 256f, 154f / 256f));
		UI.add(new Button(Assets.TEXTURE_BUTTONS, 20, 345, 100, 28, 0, 182f / 256f, 50f / 256f, 168f / 256f, 0, 196f / 256f, 50f / 256f, 182f / 256f));
	}

	public void Update() {
		if (pause != 0) {
			pause--;
		} else {
			UI.get(scroll).update();

			if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP) || mouseScroll >= 50) {
				if (scrollTimer == 0) {
					scroll--;
					if (scroll < 0) scroll = UI.size() - 1;
					scrollTimer = 10;
					mouseScroll = 0;
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN) || mouseScroll <= -50) {
				if (scrollTimer == 0) {
					scroll++;
					scroll %= UI.size();
					scrollTimer = 10;
					mouseScroll = 0;
				}
			} else {
				scrollTimer = 0;
			}
			if (scrollTimer > 0) scrollTimer--;

			if (UI.get(scroll).pressed && scroll == 0) Game.pause = false;
			if (UI.get(scroll).pressed && scroll == 2) {
				Game.pause = false;
				Game.inMenu = true;
				Game.mainmenu.pause = 10;
			}
			if (UI.get(scroll).pressed && scroll == 3) System.exit(0);
			
			if(UI.get(scroll).pressed){
				UI.get(scroll).pressed = false;
			}
			if (mouseScroll >= 0 && GameMouse.vMov > 0) {
				mouseScroll = 0;
			}
			if (mouseScroll <= 0 && GameMouse.vMov < 0) {
				mouseScroll = 0;
			}
			if(Main.mouseMenu) mouseScroll -= GameMouse.vMov;
		}
	}

	public void Render() {
		Main.camera.setOrtho();
		for (Button b : UI) {
			b.render(false);
		}
		UI.get(scroll).render(true);
		Main.camera.setPerspective();
	}

}
