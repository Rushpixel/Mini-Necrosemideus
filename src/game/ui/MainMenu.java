package game.ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import core.Assets;
import core.Main;
import core.util.Shape;
import core.util.Vertex2i;
import game.world.SceneGraph;

public class MainMenu extends Menu {

	public int scroll = 0;
	public int scrollTimer = 0;

	public float aRotate = 0;
	public float aOffset = 0;

	// this is the rising layers of rock on the menu
	// the vertex2i.x is the z position of the rock
	// the vertex2i.y = the kind of rock

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

		aOffset += 2f;
		aOffset %= 40;
		aRotate += 0.15f;
	}

	public void Render() {

		// render3d
		Main.camera.useView();
		Main.camera.setX(60);
		Main.camera.setY(60);
		Main.camera.setRz(aRotate);
		for (int i = 0; i <  30; i++) {
			float z = (i * 40 + aOffset);
			float b = z / 1200f;
			Shape.square(0, 0, z - 600, 120, 120, 0, 0, 0, b, b, b, Assets.TEXTURE_BACKGROUND_FALL1);
		}

		// render 2d
		Main.camera.setOrtho();
		for (Button b : UI) {
			b.render(false);
		}
		UI.get(scroll).render(true);
		Main.camera.setPerspective();
	}

}
