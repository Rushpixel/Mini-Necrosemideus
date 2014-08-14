package game.entity;

import core.util.Shape;

public class Coin_1 extends Coin{
	
	public float size = 4;

	public Coin_1(float x, float y, float z, float xspeed, float yspeed, float zspeed) {
		super(x, y, z, xspeed, yspeed, zspeed);
		value = 1;
		gravity = 0.1f;
	}

	public void render() {
		Shape.cube(x, y, z, -1f, -1f, 0, 1f, 1f, 2, 0, 0, rotation, 0.5f, 0.3f, 0.1f, 1);
	}

}
