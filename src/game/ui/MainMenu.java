package game.ui;

import org.lwjgl.input.Keyboard;

import core.Assets;
import core.Main;
import game.world.SceneGraph;

public class MainMenu extends Menu {

	public int scroll = 0;
	public int scrollTimer = 0;

	public MainMenu() {
		UI.add(new Button(Assets.TEXTURE_BUTTONS, 225, 200, 150, 42, 0, 14f / 256f, 50f / 256f, 0, 0, 28f / 256f, 50f / 256f, 14f / 256f));
		UI.add(new Button(Assets.TEXTURE_BUTTONS, 225, 150, 150, 42, 0, 42f / 256f, 50f / 256f, 28f / 256f, 0, 56f / 256f, 50f / 256f, 42f / 256f));
		UI.add(new Button(Assets.TEXTURE_BUTTONS, 225, 100, 150, 42, 0, 70f / 256f, 50f / 256f, 56f / 256f, 0, 84f / 256f, 50f / 256f, 70f / 256f));
	}

	public void Update() {
		if (pause != 0) {
			pause--;
		} else {
			UI.get(scroll).update();

			if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				if (scrollTimer == 0) {
					scroll--;
					if (scroll < 0) scroll = UI.size() - 1;
					scrollTimer = 10;
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				if (scrollTimer == 0) {
					scroll++;
					scroll %= UI.size();
					scrollTimer = 10;
				}
			} else {
				scrollTimer = 0;
			}
			if (scrollTimer > 0) scrollTimer--;

			if (UI.get(scroll).pressed && scroll == 0) SceneGraph.newGame();
			if (UI.get(scroll).pressed && scroll == 2) System.exit(0);
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
