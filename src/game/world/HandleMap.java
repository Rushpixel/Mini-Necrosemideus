package game.world;

import java.util.Random;

import core.Assets;
import game.world.tile.Exit;

public class HandleMap {

	public static Random random = new Random();

	public static void goNorth() {
		if (SceneGraph.prevDirection == Exit.NORTH) {
			prevMap();
		} else {
			nextMap();
		}
		SceneGraph.prevDirection = Exit.SOUTH;
	}

	public static void goEast() {
		if (SceneGraph.prevDirection == Exit.EAST) {
			prevMap();
		} else {
			nextMap();
		}
		SceneGraph.prevDirection = Exit.WEST;
	}

	public static void goSouth() {
		if (SceneGraph.prevDirection == Exit.SOUTH) {
			prevMap();
		} else {
			nextMap();
		}
		SceneGraph.prevDirection = Exit.NORTH;
	}

	public static void goWest() {
		if (SceneGraph.prevDirection == Exit.WEST) {
			prevMap();
		} else {
			nextMap();
		}
		SceneGraph.prevDirection = Exit.EAST;
	}
	
	public static void prevMap(){
		SceneGraph.map.particlelist.clear();
		Map tempMap = SceneGraph.map;
		SceneGraph.map = SceneGraph.prevmap;
		SceneGraph.prevmap = tempMap;
	}
	
	public static void nextMap(){
		Difficulty.frequency = 0.02f + ((SceneGraph.player.score / 300000f));
		if(Difficulty.frequency > 0.05f) Difficulty.frequency = 0.05f;
		//System.out.println(Difficulty.frequency);
		
		SceneGraph.prevmap = SceneGraph.map;
		SceneGraph.map = newMap();
		for(int i = 0; i < SceneGraph.map.height * SceneGraph.map.width; i++){
			SceneGraph.map.Map[i % SceneGraph.map.width][i / SceneGraph.map.height].rollEnemy(random, SceneGraph.map);
		}
	}
	
	public static Map newMapHub(){
		Map map = Assets.MAP_HUBMAIN.clone();
		for(int i = 0; i < map.height * map.width; i++){
			map.Map[i % map.width][i / map.height].rollTile(random, map);
		}
		for(int i = 0; i < map.height * map.width; i++){
			map.Map[i % map.width][i / map.height].rollEnemy(random, map);
		}
		return map;
	}

	public static Map newMap() {
		Map newMap = randomMap();
		return newMap;
	}
	
	public static Map randomMap(){
		Map map = Assets.StaticMapsCatacombs[random.nextInt(Assets.StaticMapsCatacombs.length)].clone();
		for(int i = 0; i < map.height * map.width; i++){
			map.Map[i % map.width][i / map.height].rollTile(random, map);
		}
		return map;
	}

}
