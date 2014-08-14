package game.ui;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import core.Assets;
import core.Main;
import core.util.Shape;
import game.world.SceneGraph;

public class UI_HUD {
	
	public static Texture fog = Assets.TEXTURE_FOG;
	public static Texture num = Assets.TEXTURE_NUM;
	
	public void render(){
		Main.camera.setOrtho();
		//fog
		Shape.square(0, 0, Display.getWidth(), Display.getHeight(), fog);
		//score
		Shape.number(SceneGraph.player.score, 550, 550, 16, 22, num);
		//health bars
		SceneGraph.player.heart1.render(10, 580, SceneGraph.player.heartc == SceneGraph.player.heart1);
		SceneGraph.player.heart2.render(10, 550, SceneGraph.player.heartc == SceneGraph.player.heart2);
		SceneGraph.player.heart3.render(10, 520, SceneGraph.player.heartc == SceneGraph.player.heart3);
		//slots icons
		SceneGraph.player.heart1.slot1.renderUI(11 + 16, 576);
		SceneGraph.player.heart1.slot2.renderUI(29 + 16, 576);
		SceneGraph.player.heart1.slot3.renderUI(47 + 16, 576);
		SceneGraph.player.heart2.slot1.renderUI(11 + 16, 546);
		SceneGraph.player.heart2.slot2.renderUI(29 + 16, 546);
		SceneGraph.player.heart2.slot3.renderUI(47 + 16, 546);
		SceneGraph.player.heart3.slot1.renderUI(11 + 16, 516);
		SceneGraph.player.heart3.slot2.renderUI(29 + 16, 516);
		SceneGraph.player.heart3.slot3.renderUI(47 + 16, 516);
		//slot counter
		SceneGraph.player.heartc.slot3.renderCUI(567, 3);
		SceneGraph.player.heartc.slot2.renderCUI(567, 36);
		SceneGraph.player.heartc.slot1.renderCUI(567, 69);
		
		Main.camera.setPerspective();
	}

}
