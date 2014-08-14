package core;

import game.ui.MainMenu;
import game.ui.PauseMenu;
import game.ui.UI_HUD;
import game.world.SceneGraph;

/**
 * Copyright EndoPlasm Gaming ©2013 Edited 09/12/2013
 */

public class Game {

	public SceneGraph scene;

	public UI_HUD UI;

	public static MainMenu mainmenu;
	public static boolean inMenu = true;
	public static PauseMenu pausemenu;
	public static boolean pause = false;

	public Game() {
		// load assets
		Assets.load();
		// ui init
		UI = new UI_HUD();
		mainmenu = new MainMenu();
		pausemenu = new PauseMenu();
		// init SceneGraph
		scene = new SceneGraph();
		//SceneGraph.newGame();
	}

	public void update() {
		Main.mouse.update();
		if(!inMenu && !pause) updateScene();
		if(!inMenu && pause) pausemenu.Update();
		if(inMenu) mainmenu.Update();
	}

	public void updateScene() {
		SceneGraph.update();
	}

	public void render() {
		if(!inMenu) renderScene();
		if(!inMenu && pause) pausemenu.Render();
		if(inMenu) mainmenu.Render();
	}

	public void renderScene() {
		if(Main.lastFPS < 30) SceneGraph.map.particlelist.clear();
		Main.camera.useView();
		SceneGraph.render();
		UI.render();
	}
}
