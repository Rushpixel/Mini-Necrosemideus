package game.world;

import java.util.Random;

import game.entity.*;
import game.ui.Slot_Impact;
import game.ui.Slot_Range;
import game.ui.Slot_Rapid;
import game.ui.Slot_Split;
import game.ui.Slot_Vampire;

public class GameUtil {
	
	public static void dropCoin(int num, float x, float y, float z, float speed, Random r){
		while(num >= 1000){
			float xs = speed * r.nextFloat();
			float ys = speed * r.nextFloat();
			float zs = 0;
			SceneGraph.map.entitylist.add(new Coin_1000(x, y, z, xs, ys, zs));
			num -= 1000;
		}
		while(num >= 200){
			float xs = speed * r.nextFloat();
			float ys = speed * r.nextFloat();
			float zs = 0;
			SceneGraph.map.entitylist.add(new Coin_200(x, y, z, xs, ys, zs));
			num -= 200;
		}
		while(num >= 50){
			float xs = speed * r.nextFloat();
			float ys = speed * r.nextFloat();
			float zs = 0;
			SceneGraph.map.entitylist.add(new Coin_50(x, y, z, xs, ys, zs));
			num -= 50;
		}
		while(num >= 5){
			float xs = speed * r.nextFloat();
			float ys = speed * r.nextFloat();
			float zs = 0;
			SceneGraph.map.entitylist.add(new Coin_5(x, y, z, xs, ys, zs));
			num -= 5;
		}
		while(num >= 1){
			float xs = speed * r.nextFloat();
			float ys = speed * r.nextFloat();
			float zs = 0;
			SceneGraph.map.entitylist.add(new Coin_1(x, y, z, xs, ys, zs));
			num -= 1;
		}
	}
	
	public static void dropPickup(float chance, float x, float y, Random r){
		float c = r.nextFloat();
		if (c > 0 && c < 0.05f) {
			SceneGraph.map.entitylist.add(new Pickup_Slot(x, y, new Slot_Split()));
		}
		if (c > 0.05f && c < 0.1f) {
			SceneGraph.map.entitylist.add(new Pickup_Slot(x, y, new Slot_Rapid()));
		}
		if (c > 0.1f && c < 0.15f) {
			SceneGraph.map.entitylist.add(new Pickup_Slot(x, y, new Slot_Vampire()));
		}
		if (c > 0.15f && c < 0.175f) {
			SceneGraph.map.entitylist.add(new Pickup_Slot(x, y, new Slot_Range()));
		}
		if (c > 0.175f && c < 0.2f) {
			SceneGraph.map.entitylist.add(new Pickup_Slot(x, y, new Slot_Impact()));
		}
	}

}
