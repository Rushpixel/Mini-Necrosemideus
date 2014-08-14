package game.world;

import java.util.Random;

import core.Game;
import game.entity.Player;

public class SceneGraph {
	
	public static Map map;
	public static Map prevmap;
	public static Map HUB;
	public static int prevDirection;
	public static Player player;
	
	public SceneGraph(){
		
	}
	
	public static void newGame(){
		Difficulty.frequency = .02f;
		HUB = HandleMap.newMapHub();
		map = HUB;
		prevmap = HUB;
		player = new Player(12 * 32 + 16, 15 * 32 + 16);
		Game.inMenu = false;
	}
	
	public static void Respawn(){
		map = HUB;
		prevmap = HUB;
		player.x = 12 * 32 + 16;
		player.y = 15 * 32 + 16;
		player.resetHearts();
		player.resetMovement();
		GameUtil.dropCoin(SaveGame.totalScore, 17 * 32 + 16, 12 * 32 + 16, 20, 5, new Random());
	}
	
	public static void enterCatacomb(){
		map = HandleMap.newMap();
		prevmap = HandleMap.newMap();
		player.x = ((Map_Level) map).westStartx * 32 + 16;
		player.y = ((Map_Level) map).westStarty * 32 + 16;
		player.resetMovement();
		SaveGame.totalScore = player.score;
		player.score = 0;
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