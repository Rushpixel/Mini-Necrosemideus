package game.world.tile;

import core.Assets;
import core.util.MathUtil;
import core.util.Rectangle;
import core.util.Shape;
import game.entity.Entity;
import game.world.HandleMap;
import game.world.Map;
import game.world.SceneGraph;

public class Exit extends Tile {

	public static final int NORTH = 0;
	public static final int WEST = 1;
	public static final int SOUTH = 2;
	public static final int EAST = 3;

	public int orientation = 0;

	public Exit(int x, int y, Map map) {
		super(x, y, map);
		hasCollisions = true;
		solid = true;
		float cx = x * 32;
		float cy = y * 32;
		boundingBox = new Rectangle(cx, cy, cx + 32, cy, cx, cy + 32, cx + 32, cy + 32);
		boundingBox.calcAxis();
		if (x == 0) orientation = WEST;
		if (y == staticOwner.height - 1) orientation = SOUTH;
		if (x == staticOwner.width - 1) orientation = EAST;
		if (y == 0) orientation = NORTH;
	}

	public void reportCollision(Entity e) {
		if (e.equals(SceneGraph.player)) {
			if (orientation == NORTH) {
				HandleMap.goNorth();
				e.x = SceneGraph.map.southStartx * 32 + 16;
				e.y = SceneGraph.map.southStarty * 32 + 16;
			}                                        
			if (orientation == WEST) {               
				HandleMap.goWest();                  
				e.x = SceneGraph.map.eastStartx * 32 + 16; 
				e.y = SceneGraph.map.eastStarty * 32 + 16;
			}                                        
			if (orientation == SOUTH) {              
				HandleMap.goSouth();                 
				e.x = SceneGraph.map.northStartx * 32 + 16;
				e.y = SceneGraph.map.northStarty * 32 + 16;
			}
			if (orientation == EAST) {
				HandleMap.goEast();
				e.x = SceneGraph.map.westStartx * 32 + 16;
				e.y = SceneGraph.map.westStarty * 32 + 16;
			}
			e.xprev = e.x;
			e.yprev = e.y;
			e.updateBox();
		}
	}

	public void render(float x, float y) {
		if(MathUtil.distance(x, y, SceneGraph.player.x, SceneGraph.player.y) < 285){
			Shape.model(x + 16, y + 16, -4, 180, 0, 0, Assets.MODEL_FLOOR1, Assets.TEXTURE_FLOOR1);
		}
	}
	
	
	public void renderhitbox(){
		if(boundingBox != null){
			Shape.cube(boundingBox.UL.x, boundingBox.UL.y, 10, boundingBox.DR.x, boundingBox.DR.y, -4, 1, 0, 1, 1);
		}		
	}

}
