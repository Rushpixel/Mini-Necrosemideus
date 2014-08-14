package game.entity;

import core.util.Shape;

public class Coin_50 extends Coin{
	
	public float size = 4;

	public Coin_50(float x, float y, float z, float xspeed, float yspeed, float zspeed) {
		super(x, y, z, xspeed, yspeed, zspeed);
		value = 50;
		gravity = 0.3f;
	}

	public void render() {
		Shape.cube(x, y, z, -2f, -2f, 0, 2f, 2f, 4, 0, 0, rotation, 0.7f, 0.8f, 0.9f, 1);
	}

}
