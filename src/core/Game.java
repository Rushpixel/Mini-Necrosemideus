package core;

import game.ui.MainMenu;
import game.ui.PauseMenu;
import game.ui.UI_Main;
import game.world.SceneGraph;

/**
 * Copyright EndoPlasm Gaming ©2013 Edited 09/12/2013
 */

public class Game {

	public SceneGraph scene;

	public UI_Main UI;

	public static MainMenu mainmenu;
	public static boolean inMenu = true;
	public static PauseMenu pausemenu;
	public static boolean pause = false;

	public Game() {
		// load assets
		Assets.load();
		// ui init
		UI = new UI_Main();
		mainmenu = new MainMenu();
		pausemenu = new PauseMenu();
		// init SceneGraph
		scene = new SceneGraph();
		//SceneGraph.newGame();
	}

	public void update() {
		if(!inMenu && !pause) updateScene();
		if(!inMenu && pause) pausemenu.Update();
		if(inMenu) mainmenu.Update();
	}

	public void updateScene() {
		Main.mouse.update();
		SceneGraph.update();
	}

	public void render() {
		if(!inMenu) renderScene();
		if(!inMenu && pause) pausemenu.Render();
		if(inMenu) mainmenu.Render();
	}

	public void renderScene() {
		Main.camera.useView();
		SceneGraph.render();
		UI.render();
	}
}
