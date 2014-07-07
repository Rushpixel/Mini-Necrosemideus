package game.world;

import java.util.Random;

import game.entity.Zombie;

public class Difficulty {
	
	public static float frequency = 0.02f;
	
	public static void spawnEnemy(Random random, Map map, float x, float y){
		map.entitylist.add(new Zombie(x * 32, y * 32, random.nextFloat() * 2.3f + 3, 70));
	}

}
