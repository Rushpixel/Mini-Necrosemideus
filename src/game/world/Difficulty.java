package game.world;

import java.util.Random;

import game.entity.Spectre1;
import game.entity.Spectre2;
import game.entity.Spectre3;

public class Difficulty {
	
	public static float frequency = 0.02f;
	
	public static void spawnEnemy(Random random, Map map, float x, float y){
		float a = random.nextFloat();
		if(a <= 0.4f){
			map.entitylist.add(new Spectre1(x * 32, y * 32, random.nextFloat() * 2.3f + 3, 70));
		}
		if(a > 0.4f && a < 0.7f){
			map.entitylist.add(new Spectre2(x * 32, y * 32, random.nextFloat() * 0.6f + 2, 150));
		}
		if(a >= 0.7f){
			map.entitylist.add(new Spectre3(x * 32, y * 32, random.nextFloat() * 2.3f + 1f, 30));
		}
	}

}
