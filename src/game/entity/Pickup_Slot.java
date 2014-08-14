package game.entity;

import core.Assets;
import core.util.Audio;
import core.util.Shape;
import core.util.Vertex2f;
import game.ui.Slot;
import game.ui.Slot_Empty;
import game.world.SceneGraph;

public class Pickup_Slot extends Entity{
	
	public Slot slot;
	public float c = 1;
	public boolean rising = false;
	
	public Pickup_Slot(float x, float y, Slot slot){
		doFriction = true;
		this.x = x;
		this.y = y;
		this.slot = slot;
		this.speed = 10;
		c1 = new Vertex2f(-8f, -8f);
		c2 = new Vertex2f(8f, -8f);
		c3 = new Vertex2f(-8f, 8f);
		c4 = new Vertex2f(8f, 8f);
		initBox();
	}

	public void update() {
		physics();
		updateBox();
		collisions();
		animate();
		
		rotation++;
	}
	
	public void animate(){
		if(rising){
			c += 0.025f;
		} else{
			c -= 0.025f;
		}
		if(c > 1){
			c = 1;
			rising = false;
		}
		if(c < 0){
			c = 0;
			rising = true;
		}
	}
	
	public void entityCollision(float x, float y, Entity other) {
		if(other == SceneGraph.player){
			if (((Player) other).pickup(slot)){
				slot = new Slot_Empty();
				doKill = true;
				Audio.playSoundEffect(Assets.AUDIO_PICKUP1, 1, x, y, 0);
			}
		}
	}

	public void render() {
		slot.render3D(x, y, rotation);
		Shape.cube(x, y, 0, -8, -8, 0, 8, 8, 15, 0, 0, rotation, c, c, c, 1);
	}

}
