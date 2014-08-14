package core.util;

import java.awt.AWTException;
import java.awt.Robot;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GameMouse {

	Robot robot;

	public static float hMov = 0;
	public static float vMov = 0;

	public GameMouse() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		Mouse.setGrabbed(true);
	}

	public void hideMouse() {
		Cursor emptyCursor;
		try {
			emptyCursor = new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null);
			Mouse.setNativeCursor(emptyCursor);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (Display.isActive()) {
			if (!Mouse.isGrabbed()){
				try {
					Display.makeCurrent();
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				robot.mouseMove(Display.getWidth() / 2 + Display.getX(), Display.getHeight() / 2 + Display.getY());
				Mouse.setGrabbed(true);
			}
		} else if (Mouse.isGrabbed()) {
			Mouse.setGrabbed(false);
		}

		if (Mouse.isGrabbed()) {
			hMov = (Display.getWidth() / 2 - Mouse.getX());
			vMov = (Display.getHeight() / 2 - Mouse.getY());
			Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
		}
	}

}
