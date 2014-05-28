package game.ui;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import core.Assets;
import core.Main;
import core.util.Shape;
import game.world.SceneGraph;

public class UI_Main {
	
	public static Texture fog = Assets.TEXTURE_FOG;
	public static Texture num = Assets.TEXTURE_NUM;
	
	public void render(){
		Main.camera.setOrtho();
		Shape.square(0, 0, Display.getWidth(), Display.getHeight(), fog);
		Shape.number(SceneGraph.player.score, 550, 550, 16, 22, num);
		SceneGraph.player.heart1.render(10, 580, SceneGraph.player.heartc == SceneGraph.player.heart1);
		SceneGraph.player.heart2.render(10, 550, SceneGraph.player.heartc == SceneGraph.player.heart2);
		SceneGraph.player.heart3.render(10, 520, SceneGraph.player.heartc == SceneGraph.player.heart3);
		Main.camera.setPerspective();
	}

}
