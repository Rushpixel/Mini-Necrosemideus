package game.world.tile;

import java.util.Random;

import core.util.Rectangle;
import core.util.Shape;
import game.entity.Entity;
import game.particle.Particle;
import game.world.Map;

public abstract class Tile{
	
	public float x;
	public float y;
	public Map staticOwner;
	
	public Rectangle boundingBox = null;
	public boolean hasCollisions = false;
	public boolean solid = false;
	
	public Tile(float x, float y, Map map) {
		this.x = x;
		this.y = y;
		staticOwner = map;
	}
	
	public void rollTile(Random r, Map map){}
	
	public void rollEnemy(Random r, Map map){}
	
	public boolean doesCollide(Entity e){
		return hasCollisions;
	}
	
	public boolean isSolidFor(Entity e){
		return solid;
	}
	
	public void reportCollision(Entity e){}

	public void render(float x, float y) {
	}
	
	public void renderhitbox(){
		if(boundingBox != null){
			Shape.cube(boundingBox.UL.x, boundingBox.UL.y, 10, boundingBox.DR.x, boundingBox.DR.y, -4, 1, 0, 0, 1);
		}		
	}

	public boolean doesCollide(Particle particle) {
		return hasCollisions;
	}

	public void reportCollision(Particle particle) {}

	public boolean isSolidFor(Particle particle) {
		return solid;
	}

}
