package game.entity;

import org.lwjgl.util.vector.Vector2f;

import core.Main;
import core.util.MathUtil;
import core.util.PathFinding;
import core.util.Shape;
import core.util.Vertex2f;
import game.world.SceneGraph;

public class Zombie extends Entity {

	public PathFinding path;

	public boolean seen;
	public Vector2f goal;
	public Vector2f goalprev;
	public Vector2f lastUpdated;
	
	public float HP = 100;
	public float points = 0;
	
	public int pause = 0;

	public float acc;
	public float damage;

	public Zombie(float x, float y, float speed, float damage) {
		this.x = x;
		this.y = y;
		rotation = 0;
		this.speed = speed;
		this.damage = damage;
		acc = speed / 30f;
		points += (int) (speed * 25) + damage;
		c1 = new Vertex2f(-10f, -5f);
		c2 = new Vertex2f(10f, -5f);
		c3 = new Vertex2f(-10f, 5f);
		c4 = new Vertex2f(10f, 5f);
		initBox();
		goal = new Vector2f(x, y);
		goalprev = new Vector2f(x, y);
		lastUpdated = new Vector2f(x, y);
		path = new PathFinding(new Vertex2f(x, y), new Vertex2f(goal.x, goal.y), 20, 20);
	}

	public void update() {
		AI();
		physics();
		updateBox();
		collisions();
		if(pause > 0) pause--;
	}

	public void AI() {
		// range = 235;
		if (true) {
			goal.x = SceneGraph.player.x;
			goal.y = SceneGraph.player.y;
		}
		if (MathUtil.distance(x, y, goal.x, goal.y) < 235) {
			seen = true;
		} else {
			seen = false;
		}
		if (seen) {
			if (MathUtil.distance(goal.x, goal.y, goalprev.x, goalprev.y) > 32 || MathUtil.distance(x, y, lastUpdated.x, lastUpdated.y) > 32) {
				path = new PathFinding(new Vertex2f(x, y), new Vertex2f(goal.x, goal.y), 20, 20);
				goalprev.x = goal.x;
				goalprev.y = goal.y;
				lastUpdated.x = x;
				lastUpdated.y = y;
			}

			float posx = goal.x;
			float posy = goal.y;
			if (path.Path.size() > 1) {
				float pDist = MathUtil.distance(path.Path.get(path.Path.size() - 1).parent.x * 32 + path.x, path.Path.get(path.Path.size() - 1).parent.y * 32 + path.y, path.Path.get(path.Path.size() - 1).x * 32 + path.x, path.Path.get(path.Path.size() - 1).y * 32 + path.y);
				float Dist = MathUtil.distance(x, y, path.Path.get(path.Path.size() - 1).parent.x * 32 + path.x, path.Path.get(path.Path.size() - 1).parent.y * 32 + path.y);
				if ( pDist > Dist) {
					path.Path.remove(path.Path.size() - 1);
				}
			}
			if (path.Path.size() >= 2) {
				//float pos1x = path.Path.get(path.Path.size() - 1).x * 32 + path.x;
				//float pos1y = path.Path.get(path.Path.size() - 1).y * 32 + path.y;
				float pos2x = path.Path.get(path.Path.size() - 1).x * 32 + path.x;
				float pos2y = path.Path.get(path.Path.size() - 1).y * 32 + path.y;
				posx = (pos2x);
				posy = (pos2y);
				// posx = pos1x;
				// posy = pos1y;

			}
			
			if (path.Path.size() != 0) {
				float dir = MathUtil.direction(x, y, posx, posy) + 180;
				rotation = MathUtil.direction(x, y, goal.x, goal.y) + 90;
				xspeed += MathUtil.getXSpeed(dir, acc);
				yspeed += MathUtil.getYSpeed(dir, acc);
			} 
		}else{
				xspeed = 0;
				yspeed = 0;
			}
	}

	public void render() {
		Shape.cube(x, y, 0, c1.x, c1.y, 0, c4.x, c4.y, 32, 0, 0, rotation, 0.4f, 0.7f, 0.5f, 1);
		if (path != null && Main.AStarDraw) path.render();
	}
	
	public void entityCollision(float x, float y, Entity other) {
		super.entityCollision(x, y, other);
		if(other == SceneGraph.player && pause == 0){
			SceneGraph.player.Damage(50 * (MathUtil.distance(0, 0, xspeed, yspeed) / speed));
			pause = 60;
			xspeed = -xspeed / 2;
			yspeed = -yspeed / 2;
		}
	}
	
	public void Damage(float damage){
		HP -= damage;
		if(HP <= 0){
			doKill = true;
			SceneGraph.player.score += points;
		}
	}

}
