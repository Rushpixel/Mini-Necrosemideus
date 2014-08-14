package core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import core.util.Audio;
import core.util.Model;
import core.util.ModelLoader;
import core.util.Shape;
import core.util.SpriteLoader;
import game.world.ColorSwitch;
import game.world.Map;
import game.world.Map_Level;

public class Assets {

	public static Map[] StaticMapsFull;
	public static Map_Level[] StaticMapsCatacombs;
	public static Map MAP_HUBMAIN;

	public static int numImg = 30;
	public static Texture TEXTURE_FOG;
	public static Texture TEXTURE_NUM;
	public static Texture TEXTURE_BUTTONS;
	public static Texture TEXTURE_BACKGROUND_FALL1;
	public static Texture TEXTURE_FLOOR1;
	public static Texture TEXTURE_FLOOR2;
	public static Texture TEXTURE_FLOOR3;
	public static Texture TEXTURE_FLOOR4;
	public static Texture TEXTURE_FLOOR5;
	public static Texture TEXTURE_FLOOR6;
	public static Texture TEXTURE_FLOOR7;
	public static Texture TEXTURE_GRASS1;
	public static Texture TEXTURE_PATH1;
	public static Texture TEXTURE_PATH2;
	public static Texture TEXTURE_WALL1;
	public static Texture TEXTURE_SLOT_RAPID;
	public static Texture TEXTURE_SLOT_SPLIT;
	public static Texture TEXTURE_SLOT_IMPACT;
	public static Texture TEXTURE_SLOT_RANGE;
	public static Texture TEXTURE_SLOT_VAMPIRE;
	public static Texture TEXTURE_PLAYER_LEG;
	public static Texture TEXTURE_PLAYER_BODY;
	public static Texture TEXTURE_PLAYER_HEAD;
	public static Texture TEXTURE_SPECTRE_LEGRIGHT1;
	public static Texture TEXTURE_SPECTRE_LEGLEFT1;
	public static Texture TEXTURE_SPECTRE_BODY1;
	public static Texture TEXTURE_SPECTRE_HEAD1;
	public static Texture TEXTURE_SPECTRE2_BODY1;
	public static Texture TEXTURE_SPECTRE3_BODY1;
	public static Texture TEXTURE_SPECTRE3_HEAD1;

	public static int numOBJ = 13;
	public static Model MODEL_FLOOR1;
	public static Model MODEL_WALL1;
	public static Model MODEL_PLAYER_RIGHTLEG;
	public static Model MODEL_PLAYER_LEFTLEG;
	public static Model MODEL_PLAYER_BODY;
	public static Model MODEL_PLAYER_HEAD;
	public static Model MODEL_SPECTRE_HEAD1;
	public static Model MODEL_SPECTRE_BODY1;
	public static Model MODEL_SPECTRE_LEGLEFT1;
	public static Model MODEL_SPECTRE_LEGRIGHT1;
	public static Model MODEL_SPECTRE2_BODY1;
	public static Model MODEL_SPECTRE3_BODY1;
	public static Model MODEL_SPECTRE3_HEAD1;

	public static int numWAV = 9;
	public static String AUDIO_SHOT1 = "shot1.wav";
	public static String AUDIO_STEP1 = "step1.wav";
	public static String AUDIO_EXPLOSION1 = "explosion1.wav";
	public static String AUDIO_HIT1 = "hit1.wav";
	public static String AUDIO_HIT2 = "hit2.wav";
	public static String AUDIO_PICKUP1 = "pickup1.wav";
	public static String AUDIO_SCREAM1 = "scream1.wav";
	public static String AUDIO_COIN1 = "coin1.wav";
	public static String AUDIO_DEATH = "death.wav";

	public static float MapWeight = 0.2f;
	public static float TexWeight = 0.4f;
	public static float ModWeight = 0.3f;
	public static float WAVWeight = 0.1f;
	public static float percent = 0;

	public static void load() {
		Main.printRAM();
		Long t0 = System.currentTimeMillis();
		System.out.println("Assets to Load: " + numImg + " textures, " + numOBJ + " .OBJs, " + numWAV + " audio files,");

		renderBar();
		loadMaps();
		loadTextures();
		loadModel();
		loadWAV();

		long ta = System.currentTimeMillis();
		System.out.println("Total loading took " + (ta - t0) + " milliseconds");
		System.out.println();
		// Main.printRAM();
	}

	public static void renderBar() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		Main.camera.setOrtho();

		Shape.square(50, 310, 500, 10, 0.5f, 0.5f, 0.5f, 1f);
		for (int i = 0; i < percent * 25f; i++) {
			Shape.square(50 + i * 20, 305, 19, 20, 1, 1, 1, 1f);
		}

		Main.camera.setPerspective();

		Display.update();
	}

	public static void loadMaps() {
		long timer = System.currentTimeMillis();
		File file = new File("Resources/Maps/");
		File[] files = file.listFiles();
		List<Map_Level> tempcatacombs = new ArrayList<Map_Level>();
		for (File f : files) {
			String name = f.getName();
			String[] breakdown = name.split("_");
			System.out.println("Loading map " + name);
			String prefix = breakdown[0];
			if (prefix.equals("Catacomb")) {
				tempcatacombs.add(loadCatacomb(f));
			} else if(prefix.equals("Hub")){
				if(name.equals("Hub_Main.png")){
					MAP_HUBMAIN = loadMap(f);
				}
			}else{
				System.out.println("map loaded with unknown or invalid prefix, prefix: " + prefix);
			}
			percent += MapWeight / files.length;
			renderBar();
		}
		
		// catacombs
		StaticMapsCatacombs = new Map_Level[tempcatacombs.size()];
		for(int i = 0; i < StaticMapsCatacombs.length; i++){
			StaticMapsCatacombs[i] = tempcatacombs.get(i);
		}
		System.out.println("Map loading took " + (System.currentTimeMillis() - timer) + " milliseconds");
	}

	public static Map getMap(String name) {
		for(Map m: StaticMapsFull){
			if(name.equals(m.Name)) return m;
		}
		System.err.println("Map with name " + name + " not found");
		return null;
	}

	public static Map_Level loadCatacomb(File file) {
			BufferedImage image = SpriteLoader.loadImage(file);
			Map_Level output = new Map_Level(image.getWidth(), image.getHeight(), file.getName());
			int[] map = new int[output.width * output.height];
			image.getRGB(0, 0, image.getWidth(), image.getWidth(), map, 0, image.getWidth());
			for (int j = 0; j < map.length; j++) {
				if (map[j] == ColorSwitch.START && j / output.height == 1) output.northStartx = j % output.width;
				if (map[j] == ColorSwitch.START && j % output.width == output.width - 2) output.eastStarty = j / output.height;
				if (map[j] == ColorSwitch.START && j / output.height == output.height - 2) output.southStartx = j % output.width;
				if (map[j] == ColorSwitch.START && j % output.width == 1) output.westStarty = j / output.height;
				output.Map[j % output.width][j / output.height] = ColorSwitch.getTile(map[j], j % output.width, j / output.height, output);
			}
		return output;
	}
	
	public static Map loadMap(File file) {
		BufferedImage image = SpriteLoader.loadImage(file);
		Map output = new Map_Level(image.getWidth(), image.getHeight(), file.getName());
		int[] map = new int[output.width * output.height];
		image.getRGB(0, 0, image.getWidth(), image.getWidth(), map, 0, image.getWidth());
		for (int j = 0; j < map.length; j++) {
			output.Map[j % output.width][j / output.height] = ColorSwitch.getTile(map[j], j % output.width, j / output.height, output);
		}
	return output;
}

	public static void loadTextures() {
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
		TEXTURE_GRASS1 = SpriteLoader.loadTexture("Tile/grass1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_PATH1 = SpriteLoader.loadTexture("Tile/path1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_PATH2 = SpriteLoader.loadTexture("Tile/path2");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_WALL1 = SpriteLoader.loadTexture("Tile/wall1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SLOT_RAPID = SpriteLoader.loadTexture("Slot/Slot_Rapid");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SLOT_SPLIT = SpriteLoader.loadTexture("Slot/Slot_Split");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SLOT_IMPACT = SpriteLoader.loadTexture("Slot/Slot_Impact");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SLOT_RANGE = SpriteLoader.loadTexture("Slot/Slot_Range");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SLOT_VAMPIRE = SpriteLoader.loadTexture("Slot/Slot_Vampire");
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
		TEXTURE_SPECTRE_HEAD1 = SpriteLoader.loadTexture("Spectre/Head1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SPECTRE_BODY1 = SpriteLoader.loadTexture("Spectre/Body1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SPECTRE_LEGLEFT1 = SpriteLoader.loadTexture("Spectre/LeftLeg1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SPECTRE_LEGRIGHT1 = SpriteLoader.loadTexture("Spectre/RightLeg1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SPECTRE2_BODY1 = SpriteLoader.loadTexture("Spectre2/body1");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SPECTRE3_HEAD1 = SpriteLoader.loadTexture("Spectre3/Head");
		percent += TexWeight / numImg;
		renderBar();
		TEXTURE_SPECTRE3_BODY1 = SpriteLoader.loadTexture("Spectre3/body");
		percent += TexWeight / numImg;
		renderBar();
		System.out.println("Texture loading took " + (System.currentTimeMillis() - timer) + " milliseconds");
	}

	public static void loadModel() {
		long timer = System.currentTimeMillis();
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
		MODEL_SPECTRE_HEAD1 = ModelLoader.load("Spectre/Head1");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_SPECTRE_BODY1 = ModelLoader.load("Spectre/Body1");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_SPECTRE_LEGLEFT1 = ModelLoader.load("Spectre/LeftLeg1");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_SPECTRE_LEGRIGHT1 = ModelLoader.load("Spectre/RightLeg1");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_SPECTRE2_BODY1 = ModelLoader.load("Spectre2/Body1");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_SPECTRE3_HEAD1 = ModelLoader.load("Spectre3/Head1");
		percent += ModWeight / numOBJ;
		renderBar();
		MODEL_SPECTRE3_BODY1 = ModelLoader.load("Spectre3/Body1");
		percent += ModWeight / numOBJ;
		renderBar();
		System.out.println("Model loading took " + (System.currentTimeMillis() - timer) + " milliseconds");
	}

	public static void loadWAV() {
		long timer = System.currentTimeMillis();
		Audio.loadSound(AUDIO_SHOT1);
		percent += WAVWeight / numWAV;
		renderBar();
		Audio.loadSound(AUDIO_STEP1);
		percent += WAVWeight / numWAV;
		renderBar();
		Audio.loadSound(AUDIO_EXPLOSION1);
		percent += WAVWeight / numWAV;
		renderBar();
		Audio.loadSound(AUDIO_HIT1);
		percent += WAVWeight / numWAV;
		renderBar();
		Audio.loadSound(AUDIO_EXPLOSION1);
		percent += WAVWeight / numWAV;
		renderBar();
		Audio.loadSound(AUDIO_PICKUP1);
		percent += WAVWeight / numWAV;
		renderBar();
		Audio.loadSound(AUDIO_SCREAM1);
		percent += WAVWeight / numWAV;
		renderBar();
		Audio.loadSound(AUDIO_COIN1);
		percent += WAVWeight / numWAV;
		renderBar();
		Audio.loadSound(AUDIO_DEATH);
		percent += WAVWeight / numWAV;
		renderBar();
		System.out.println("Audio loading took " + (System.currentTimeMillis() - timer) + " milliseconds");
	}

}
