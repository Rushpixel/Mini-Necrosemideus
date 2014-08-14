package game.entity;

import core.util.Shape;

public class Coin_5 extends Coin{
	
	public float size = 2;

	public Coin_5(float x, float y, float z, float xspeed, float yspeed, float zspeed) {
		super(x, y, z, xspeed, yspeed, zspeed);
		value = 5;
		gravity = 0.15f;
	}

	public void render() {
		Shape.cube(x, y, z, -1.5f, -1.5f, 0, 1.5f, 1.5f, 3, 0, 0, rotation, 0.5f, 0.3f, 0.1f, 1);
	}

}
