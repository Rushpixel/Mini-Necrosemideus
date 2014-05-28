package core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import core.util.GameMouse;
import static org.lwjgl.opengl.GL11.*;

/**
 * Copyright EndoPlasm Gaming ©2013 Edited 08/12/2013
 */
public class Main {

	public Game game;

	public static int UPS = 60;
	public static boolean VSync = true;
	public static boolean AStarDraw = false;
	public static boolean ShadowDraw = true;

	public static Camera camera;
	public static GameMouse mouse;

	public static void main(String[] args) {
		Main window = new Main();
		window.start();
	}

	public void start() {

		createDisplay();

		initOpenGL();

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

	public void end() {
		Display.destroy();
		System.exit(0);
	}
}
