package core.util;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Copyright EndoPlasm Gaming ©2013
 * Edited 09/12/2013
 */
public class SpriteLoader {
	
	public static Texture loadTexture(String key){
		Texture texture = null;
		try {
			 texture = TextureLoader.getTexture(".png", new FileInputStream(new File("Resources/Textures/" + key + ".png")));
			 texture.setTextureFilter(GL_NEAREST);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture;
	}
	
	public static BufferedImage loadImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
