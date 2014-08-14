package game.entity;

import core.util.Shape;

public class Coin_200 extends Coin{
	
	public float size = 6;

	public Coin_200(float x, float y, float z, float xspeed, float yspeed, float zspeed) {
		super(x, y, z, xspeed, yspeed, zspeed);
		value = 200;
		gravity = 0.4f;
	}

	public void render() {
		Shape.cube(x, y, z, -2.5f, -2.5f, 0, 2.5f, 2.5f, 5, 0, 0, rotation, 0.9f, 0.9f, 0, 1f);
	}

}
