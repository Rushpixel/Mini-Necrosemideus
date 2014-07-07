package game.ui;

import org.newdawn.slick.opengl.Texture;

public class Letter extends Button{

	public Letter(Texture tex, float x, float y, float width, float height, float onU1, float onV1, float onU2, float onV2, float offU1, float offV1, float offU2, float offV2) {
		super(tex, x, y, width, height, onU1, onV1, onU2, onV2, offU1, offV1, offU2, offV2);
	}

}
