package game.ui;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import core.util.Shape;

public class Button {

	public boolean pressed = false;

	public Texture tex;
	public float x;
	public float y;
	public float width;
	public float height;
	public float onU1;
	public float onV1;
	public float onU2;
	public float onV2;
	public float offU1;
	public float offV1;
	public float offU2;
	public float offV2;

	public Button(Texture tex, float x, float y, float width, float height, float onU1, float onV1, float onU2, float onV2, float offU1, float offV1, float offU2, float offV2) {
		this.tex = tex;

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.onU1 = onU1;
		this.onV1 = onV1;
		this.onU2 = onU2;
		this.onV2 = onV2;
		this.offU1 = offU1;
		this.offV1 = offV1;
		this.offU2 = offU2;
		this.offV2 = offV2;

	}

	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) || Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			pressed = true;
		} else {
			pressed = false;
		}
	}

	public void render(boolean state) {
		if (!state) {
			Shape.square(x, y, width, height, onU1, onV1, onU2, onV2, tex);
		} else {
			Shape.square(x, y, width, height, offU1, offV1, offU2, offV2, tex);
		}
	}

}
