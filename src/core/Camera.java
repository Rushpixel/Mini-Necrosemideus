package core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.opengl.Display;

import core.util.MathUtil;

/**
 * Copyright EndoPlasm Gaming ©2013 Edited 09/12/2013
 */
public class Camera {

	public float x;
	public float y;
	public float z;
	public float rx;
	public float ry;
	public float rz;

	public float FoV;
	public float aspect;
	public float near;
	public float far;

	public Camera() {
		x = 0;
		y = 0;
		z = 600;
		rx = 0;
		ry = 0;
		rz = 0;

		this.FoV = 45;
		this.aspect = 1f / 1f;
		this.near = 20f;
		this.far = 2000;

		initProjection();
	}

	private void initProjection() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		gluPerspective(FoV, aspect, near, far);

		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_DEPTH_TEST);
		// glEnable(GL_TEXTURE_2D);
		// glEnable(GL_CULL_FACE);
		// glCullFace(GL_BACK);

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	public void setPerspective() {
		glEnable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();

	}

	public void setOrtho() {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();

		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);

		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();

		glLoadIdentity();

		glDisable(GL_DEPTH_TEST);
	}

	public void useView() {
		// glTranslatef(-x, -y, -z);
		// glRotatef(rx, 1, 0, 0);
		// glRotatef(ry, 0, 1, 0);
		// glRotatef(rz, 0, 0, 1);
		gluLookAt(x, y, z, x, y, 0, MathUtil.getXSpeed(rz, 1), MathUtil.getYSpeed(rz, 1), 0);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}

}
