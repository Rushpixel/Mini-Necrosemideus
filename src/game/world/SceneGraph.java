package game.world;

import core.Game;
import game.entity.Player;

public class SceneGraph {
	
	public static Map map;
	public static Map prevmap;
	public static int prevDirection;
	public static Player player;
	
	public SceneGraph(){
		
	}
	
	public static void newGame(){
		map = HandleMap.randomMap();
		prevmap = HandleMap.newMap();
		player = new Player(map.westStartx * 32 + 16, map.westStarty * 32 + 16);
		Game.inMenu = false;
	}
	
	public static void update(){
		player.update();
		map.update();
	}
	
	public static void render(){
		map.render();
		player.render();
	}

}