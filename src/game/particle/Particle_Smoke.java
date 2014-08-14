package game.particle;

import core.util.Shape;

public class Particle_Smoke extends Particle{
	
	public int lifeSpan;
	public int life = 0;
	public float StartSize;
	public float size;
	public float EndGrowth;
	public float r, g, b, a, ra;
	
	public Particle_Smoke(int lifeSpan, float x, float y, float z, float xspeed, float yspeed, float StartSize, float EndGrowth, float r, float g, float b, float a){
		this.lifeSpan = lifeSpan;
		this.x = x;
		this.y = y;
		this.z = z;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.StartSize = StartSize;
		size = StartSize;
		this.EndGrowth = EndGrowth;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.ra = a;
		speed = 10;
		doFriction = true;
		friction = 0.99f;
		doGravity = true;
		gravity = -0.02f;
	}

	public void update() {
		physics();
		
		if (lifeSpan != -1) {
			life++;
			if (life >= lifeSpan) doKill = true;
			//ra = a * (1 - (float) life / lifeSpan);
		}
		
		size = StartSize + (EndGrowth * ((float)life / lifeSpan));
		gravity = -0.02f * (1 - (float) life / lifeSpan);
	}

	public void render() {
		Shape.cube(x, y, z, -size, -size, -size / 2, size, size, size / 2, 0, 0, 0, r, g, b, ra);
	}

}
