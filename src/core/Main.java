package core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import core.util.Audio;
import core.util.GameMouse;
import static org.lwjgl.opengl.GL11.*;

/**
 * Copyright EndoPlasm Gaming ©2013 Edited 08/12/2013
 */
public class Main {

	public Game game;
	
	public static Audio audio;

	public static int UPS = 60;
	public static boolean VSync = true;
	public static boolean AStarDraw = false;
	public static boolean ShadowDraw = true;
	public static boolean mouseMenu = false;
	
	public static float lastFPS = 0;

	public static Camera camera;
	public static GameMouse mouse;

	public static void main(String[] args) {
		Main window = new Main();
		window.start();
	}

	public void start() {

		createDisplay();

		initOpenGL();
		
		initAudio();

		initGame();

		gameLoop();

		end();

	}

	public void createDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(600, 600));
			Display.setVSyncEnabled(VSync);
			Display.setResizable(false);
			//Display.create(new PixelFormat(0, 8, 0, 1));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void initOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glMatrixMode(GL_MODELVIEW);

		glClearColor(0, 0, 0, 0);

		glDisable(GL_DEPTH_TEST);
		
		glEnable(GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		 //glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		camera = new Camera();
		mouse = new GameMouse();
	}
	
	public void initAudio(){
		audio = new Audio();
	}

	public void initGame() {
		game = new Game();
	}

	public void gameLoop() {

		double differ = 0L;
		double lastTime = System.nanoTime();
		final double ns = 1000000000.0 / UPS;

		int fcounter = 0;
		int ucounter = 0;
		long count_time = System.currentTimeMillis();

		while (!Display.isCloseRequested()) {

			// Deal with the game
			long now = System.nanoTime();
			differ += (now - lastTime) / ns;
			lastTime = now;

			for (; differ >= 1.0d; differ--) {
				update();
				ucounter++;
			}
			render();
			fcounter++;

			if (System.currentTimeMillis() - 1000 > count_time) {
				Display.setTitle("Necromemideus FPS: " + fcounter + " UPS: " + ucounter);
				ucounter = 0;
				lastFPS = fcounter;
				fcounter = 0;
				count_time = System.currentTimeMillis();
			}

		}
	}

	public void update() {
		game.update();
	}

	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		game.render();
		
		Display.update();
	}
	
	public static void printRAM(){
		int kb = 1024;
        
        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();
        
        System.out.println();
        System.out.println("HEAP DATA for JVM");
         
        //Print used memory
        System.out.println("Used Memory: "
            + (runtime.totalMemory() - runtime.freeMemory()) / kb + "KB");
 
        //Print free memory
        System.out.println("Free Memory: "
            + runtime.freeMemory() / kb + "KB");
         
        //Print total available memory
        System.out.println("Total Memory: " + runtime.totalMemory() / kb + "KB");
 
        //Print Maximum available memory
        System.out.println("Max Memory: " + runtime.maxMemory() / kb + "KB");
        
        System.out.println();
	}

	public void end() {
		Display.destroy();
		Audio.soundsystem.cleanup();
		System.exit(0);
	}
}
