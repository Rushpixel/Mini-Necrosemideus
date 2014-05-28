package game.world;

import java.util.Random;

import core.Assets;
import game.entity.Zombie;
import game.world.tile.Exit;

public class HandleMap {

	public static Random random = new Random();

	public static void goNorth() {
		if (SceneGraph.prevDirection == Exit.NORTH) {
			Map tempMap = SceneGraph.map;
			SceneGraph.map = SceneGraph.prevmap;
			SceneGraph.prevmap = tempMap;
		} else {
			SceneGraph.prevmap = SceneGraph.map;
			SceneGraph.map = newMap();
		}
		SceneGraph.prevDirection = Exit.SOUTH;
	}

	public static void goEast() {
		if (SceneGraph.prevDirection == Exit.EAST) {
			Map tempMap = SceneGraph.map;
			SceneGraph.map = SceneGraph.prevmap;
			SceneGraph.prevmap = tempMap;
		} else {
			SceneGraph.prevmap = SceneGraph.map;
			SceneGraph.map = newMap();
		}
		SceneGraph.prevDirection = Exit.WEST;
	}

	public static void goSouth() {
		if (SceneGraph.prevDirection == Exit.SOUTH) {
			Map tempMap = SceneGraph.map;
			SceneGraph.map = SceneGraph.prevmap;
			SceneGraph.prevmap = tempMap;
		} else {
			SceneGraph.prevmap = SceneGraph.map;
			SceneGraph.map = newMap();
		}
		SceneGraph.prevDirection = Exit.NORTH;
	}

	public static void goWest() {
		if (SceneGraph.prevDirection == Exit.WEST) {
			Map tempMap = SceneGraph.map;
			SceneGraph.map = SceneGraph.prevmap;
			SceneGraph.prevmap = tempMap;
		} else {
			SceneGraph.prevmap = SceneGraph.map;
			SceneGraph.map = newMap();
		}
		SceneGraph.prevDirection = Exit.EAST;
	}

	public static Map newMap() {
		Map newMap = randomMap();
		int num = 20;
		for (int i = 0; i < num; i++) {
			newMap.entitylist.add(new Zombie(random.nextFloat() * (newMap.width * 32), random.nextFloat() * (newMap.height * 32), random.nextFloat() * 2.3f + 3, 70));
		}
		return newMap;
	}
	
	public static Map randomMap(){
		Map map = Assets.StaticMaps[random.nextInt(Assets.StaticMaps.length)].clone();
		for(int i = 0; i < map.height * map.width; i++){
			map.Map[i % map.width][i / map.height].rollTile(random);
		}
		return map;
	}

}
