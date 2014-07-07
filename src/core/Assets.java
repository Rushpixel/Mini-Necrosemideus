
package core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.image.BufferedImage;
import java.io.File;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import core.util.Model;
import core.util.ModelLoader;
import core.util.Shape;
import core.util.SpriteLoader;
import game.world.ColorSwitch;
import game.world.Map;
import game.world.SceneGraph;

public class Assets{
	
	public static Map[] StaticMaps;
	
	public static int numImg = 16;
	public static Texture TEXTURE_FOG;
	public static Texture TEXTURE_NUM;
	public static Texture TEXTURE_BUTTONS;
	public static Texture TEXTURE_BACKGROUND_FALL1;
	public static Texture TEXTURE_FOAM;
	public static Texture TEXTURE_FLOOR1;
	public static Texture TEXTURE_FLOOR2;
	public static Texture TEXTURE_FLOOR3;
	public static Texture TEXTURE_FLOOR4;
	public static Texture TEXTURE_FLOOR5;
	public static Texture TEXTURE_FLOOR6;
	public static Texture TEXTURE_FLOOR7;
	public static Texture TEXTURE_WALL1;
	public static Texture TEXTURE_PLAYER_LEG;
	public static Texture TEXTURE_PLAYER_BODY;
	public static Texture TEXTURE_PLAYER_HEAD;
	
	
	public static int numOBJ = 7;
	public static Model MODEL_FOAM;
	public static Model MODEL_FLOOR1;
	public static Model MODEL_WALL1;
	public static Model MODEL_PLAYER_RIGHTLEG;
	public static Model MODEL_PLAYER_LEFTLEG;
	public static Model MODEL_PLAYER_BODY;
	public static Model MODEL_PLAYER_HEAD;
	
	public static float MapWeight = 0.2f;
	public static float TexWeight = 0.5f;
	public static float ModWeight = 0.3f;
	public static float percent = 0;
	
	public static void load(){
		renderBar();
		loadMaps();
		loadTextures();
		loadModel();
	}
	
	public static void renderBar(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		Main.camera.setOrtho();
		
		Shape.square(50, 310, 500, 10, 0.5f, 0.5f, 0.5f, 1f);
		for(int i = 0; i < percent * 25f; i++){
			Shape.square(50 + i * 20, 305, 19, 20, 1, 1, 1, 1f);
		}
		
		Main.camera.setPerspective();
		
		Display.update();
	}
	
	public static void loadMaps(){
		long timer = System.currentTimeMillis();
		File file = new File("Resources/Maps/");
		File[] files = file.listFiles();
		StaticMaps = new Map[files.length];
		for(int i = 0; i < files.length; i++){
			BufferedImage image = SpriteLoader.loadImage(files[i]);
			StaticMaps[i] = new Map(image.getWidth(), image.getHeight());
			int[] map = new int[StaticMaps[i].width * StaticMaps[i].height];
			image.getRGB(0, 0, image.getWidth(), image.getWidth(), map, 0, image.getWidth());
			for(int j = 0; j < map.length; j++){
				if(map[j] == ColorSwitch.START && j / StaticMaps[i].height == 1) StaticMaps[i].northStartx = j % StaticMaps[i].width;
				if(map[j] == ColorSwitch.START && j % StaticMaps[i].width == StaticMaps[i].width - 2) StaticMaps[i].eastStarty = j / StaticMaps[i].height;
				if(map[j] == ColorSwitch.START && j / StaticMaps[i].height == StaticMaps[i].height - 2) StaticMaps[i].southStartx = j % StaticMaps[i].width;
				if(map[j] == ColorSwitch.START && j % StaticMaps[i].width == 1) StaticMaps[i].westStarty = j / StaticMaps[i].height;
				StaticMaps[i].Map[j % StaticMaps[i].width][j / StaticMaps[i].height] = ColorSwitch.getTile(map[j], j % StaticMaps[i].width, j / StaticMaps[i].height, StaticMaps[i]);
			}
			percent += MapWeight / files.length;
			renderBar();
		}
		System.out.println("Map loading took " + (System.currentTimeMillis() - timer) + " milliseconds");
	}

	public static void loadTextures(){
		long timer = System.currentTimeMillis();
		TEXTURE_FOG = SpriteLoader.loadTexture("fog");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_NUM = SpriteLoader.loadTexture("num");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_BUTTONS = SpriteLoader.loadTexture("menu");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_BACKGROUND_FALL1 = SpriteLoader.loadTexture("backgrounds/fall1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_FOAM = SpriteLoader.loadTexture("Tile/Foam");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_FLOOR1 = SpriteLoader.loadTexture("Tile/floor1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_FLOOR2 = SpriteLoader.loadTexture("Tile/floor2");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_FLOOR3 = SpriteLoader.loadTexture("Tile/floor3");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_FLOOR4 = SpriteLoader.loadTexture("Tile/floor4");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_FLOOR5 = SpriteLoader.loadTexture("Tile/floor5");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_FLOOR6 = SpriteLoader.loadTexture("Tile/floor6");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_FLOOR7 = SpriteLoader.loadTexture("Tile/floor7");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_WALL1 = SpriteLoader.loadTexture("Tile/wall1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_PLAYER_LEG = SpriteLoader.loadTexture("Player/leg");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_PLAYER_BODY = SpriteLoader.loadTexture("Player/body");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_PLAYER_HEAD = SpriteLoader.loadTexture("Player/head");
		percent += TexWeight / numImg;
		renderBar();
		System.out.println("Texture loading took " + (System.currentTimeMillis() - timer) + " milliseconds");
	}
	
	public static void loadModel(){
		long timer = System.currentTimeMillis();
		MODEL_FOAM = ModelLoader.load("Tile/Foam");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_FLOOR1 = ModelLoader.load("Tile/floor1");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_WALL1 = ModelLoader.load("Tile/wall1");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_PLAYER_RIGHTLEG = ModelLoader.load("Player/RightLeg");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_PLAYER_LEFTLEG = ModelLoader.load("Player/LeftLeg");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_PLAYER_BODY = ModelLoader.load("Player/Body");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_PLAYER_HEAD = ModelLoader.load("Player/Head");
		percent += ModWeight / numOBJ;
		renderBar();
		System.out.println("Model loading took " + (System.currentTimeMillis() - timer) + " milliseconds");
	}
	
}
