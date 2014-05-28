package core.util;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

/**
 * Copyright EndoPlasm Gaming ©2013 Edited 10/12/2013
 */
public class Shape {
	
	public static void square(float x, float y, float width, float height, Texture tex) {
		glPushMatrix();
		{
			glEnable(GL_TEXTURE_2D);
			glColor4f(1, 1, 1, 1);
			tex.bind();
			glTranslatef(x, y, 0f);
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);
				glTexCoord2f(0, 1);
				glVertex2f(0, height);
				glTexCoord2f(1, 1);
				glVertex2f(width, height);
				glTexCoord2f(1, 0);
				glVertex2f(width, 0);
			}
			glEnd();
			glDisable(GL_TEXTURE_2D);
		}
		glPopMatrix();
	}
	
	public static void square(float x, float y, float width, float height, float r, float g, float b, float a) {
		glPushMatrix();
		{
			glColor4f(r, g, b, a);
			glTranslatef(x, y, 0f);
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);
				glTexCoord2f(0, 1);
				glVertex2f(0, height);
				glTexCoord2f(1, 1);
				glVertex2f(width, height);
				glTexCoord2f(1, 0);
				glVertex2f(width, 0);
			}
			glEnd();
			glDisable(GL_TEXTURE_2D);
		}
		glPopMatrix();
	}

	public static void cube(float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a, Texture texture) {
		glPushMatrix();
		{
			glColor4f(r, g, b, a);
			texture.bind();
			glBegin(GL_QUADS);
			{
				// left face
				glTexCoord2f(0, 0);
				glVertex3f(x1, y1, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x1, y2, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x1, y2, z2);
				glTexCoord2f(1, 0);
				glVertex3f(x1, y1, z2);

				// right face
				glTexCoord2f(0, 0);
				glVertex3f(x2, y1, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x2, y2, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x2, y2, z2);
				glTexCoord2f(1, 0);
				glVertex3f(x2, y1, z2);

				// bottom face
				glTexCoord2f(0, 0);
				glVertex3f(x1, y1, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x2, y1, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x2, y1, z2);
				glTexCoord2f(1, 0);
				glVertex3f(x1, y1, z2);

				// top face
				glTexCoord2f(0, 0);
				glVertex3f(x1, y2, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x2, y2, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x2, y2, z2);
				glTexCoord2f(1, 0);
				glVertex3f(x1, y2, z2);

				// front face
				glTexCoord2f(0, 0);
				glVertex3f(x1, y1, z1);
				glTexCoord2f(0, 1);
				glVertex3f(x1, y2, z1);
				glTexCoord2f(1, 1);
				glVertex3f(x2, y2, z1);
				glTexCoord2f(1, 0);
				glVertex3f(x2, y1, z1);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void model(float x, float y, float z, float rx, float ry, float rz, Model model, Texture texture) {
		glPushMatrix();
		{
			glEnable(GL_TEXTURE_2D);
			glTranslatef(x, y, z);
			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);
			glColor4f(1,1,1,1);
			texture.bind();
			glBegin(GL_TRIANGLES);
			{
				for(Triangle tri: model.t){
					glTexCoord2f(model.vt.get(tri.v1.y - 1).x, model.vt.get(tri.v1.y - 1).y);
					glVertex3f(model.v.get(tri.v1.x - 1).x, model.v.get(tri.v1.x - 1).y, model.v.get(tri.v1.x - 1).z);
					
					glTexCoord2f(model.vt.get(tri.v2.y - 1).x, model.vt.get(tri.v2.y - 1).y);
					glVertex3f(model.v.get(tri.v2.x - 1).x, model.v.get(tri.v2.x - 1).y, model.v.get(tri.v2.x - 1).z);
					
					glTexCoord2f(model.vt.get(tri.v3.y - 1).x, model.vt.get(tri.v3.y - 1).y);
					glVertex3f(model.v.get(tri.v3.x - 1).x, model.v.get(tri.v3.x - 1).y, model.v.get(tri.v3.x - 1).z);
				}
			}
			glEnd();
			glDisable(GL_TEXTURE_2D);
		}
		glPopMatrix();
	}

	public static void cube(float x1, float y1, int z1, float x2, float y2, int z2, float r, float g, float b, float a) {
		glPushMatrix();
		{
			glColor4f(r, g, b, a);
			glBegin(GL_QUADS);
			{
				// left face
				glVertex3f(x1, y1, z1);
				glVertex3f(x1, y2, z1);
				glVertex3f(x1, y2, z2);
				glVertex3f(x1, y1, z2);

				// right face
				glVertex3f(x2, y1, z1);
				glVertex3f(x2, y2, z1);
				glVertex3f(x2, y2, z2);
				glVertex3f(x2, y1, z2);

				// bottom face
				glVertex3f(x1, y1, z1);
				glVertex3f(x2, y1, z1);
				glVertex3f(x2, y1, z2);
				glVertex3f(x1, y1, z2);

				// top face
				glVertex3f(x1, y2, z1);
				glVertex3f(x2, y2, z1);
				glVertex3f(x2, y2, z2);
				glVertex3f(x1, y2, z2);

				// front face
				glVertex3f(x1, y1, z1);
				glVertex3f(x1, y2, z1);
				glVertex3f(x2, y2, z1);
				glVertex3f(x2, y1, z1);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void cube(float x, float y, float z, float x1, float y1, int z1, float x2, float y2, int z2, float rx, float ry, float rz, float r, float g, float b, float a) {
		glPushMatrix();
		{
			glColor4f(r, g, b, a);
			glTranslatef(x, y, z);
			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);
			glRotatef(rz, 0, 0, 1);
			glBegin(GL_QUADS);
			{
				// left face
				glVertex3f(x1, y1, z1);
				glVertex3f(x1, y2, z1);
				glVertex3f(x1, y2, z2);
				glVertex3f(x1, y1, z2);

				// right face
				glVertex3f(x2, y1, z1);
				glVertex3f(x2, y2, z1);
				glVertex3f(x2, y2, z2);
				glVertex3f(x2, y1, z2);

				// bottom face
				glVertex3f(x1, y1, z1);
				glVertex3f(x2, y1, z1);
				glVertex3f(x2, y1, z2);
				glVertex3f(x1, y1, z2);

				// top face
				glVertex3f(x1, y2, z1);
				glVertex3f(x2, y2, z1);
				glVertex3f(x2, y2, z2);
				glVertex3f(x1, y2, z2);

				// front face
				glVertex3f(x1, y1, z1);
				glVertex3f(x1, y2, z1);
				glVertex3f(x2, y2, z1);
				glVertex3f(x2, y1, z1);
				
				// back face
				glVertex3f(x1, y1, z2);
				glVertex3f(x1, y2, z2);
				glVertex3f(x2, y2, z2);
				glVertex3f(x2, y1, z2);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void triangle4f(float x1, float y1, float x2, float y2, float x3, float y3, float z1, float z2){
		glPushMatrix();
		{
			glColor4f(0, 0, 0, 1);
			glBegin(GL_TRIANGLES);
			//top
			glVertex3f(x1, y1, z2);
			glVertex3f(x2, y2, z2);
			glVertex3f(x3, y3, z2);
			glEnd();
			glBegin(GL_QUADS);
			//side 2-1
			glVertex3f(x2, y2, z2);
			glVertex3f(x2, y2, z1);
			glVertex3f(x1, y1, z1);
			glVertex3f(x1, y1, z2);
			//side 2-3
			glVertex3f(x2, y2, z2);
			glVertex3f(x2, y2, z1);
			glVertex3f(x3, y3, z1);
			glVertex3f(x3, y3, z2);
			glEnd();
		}
		glPopMatrix();
	}
	
	public static void number(int num, float x, float y, float width, float height, Texture tex) {
		if(num == 0){
			Shape.square(x, y, width, height, 0, 22f / 256f, 16f / 256f, 0, tex);
		}
		if (num != 0) {for (int i = 0; Math.pow(10, i) <= num; i++) {
				int o = (int) (((num % Math.pow(10, i)) - (num % Math.pow(10, i + 1))) / (Math.pow(10, i)));
				o = Math.abs(o);
				Shape.square(x - i * width, y, width, height, o / 16f , 22f / 256f, (o + 1) / 16f, 0, tex);
			}
		}
	}

	public static void square(float x, float y, float width, float height, float u1, float v1, float u2, float v2, Texture tex) {
		glPushMatrix();
		{
			glEnable(GL_TEXTURE_2D);
			glColor4f(1, 1, 1, 1);
			tex.bind();
			glTranslatef(x, y, 0f);
			glBegin(GL_QUADS);
			{
				glTexCoord2f(u1, v1);
				glVertex2f(0, 0);
				glTexCoord2f(u1, v2);
				glVertex2f(0, height);
				glTexCoord2f(u2, v2);
				glVertex2f(width, height);
				glTexCoord2f(u2, v1);
				glVertex2f(width, 0);
			}
			glEnd();
			glDisable(GL_TEXTURE_2D);
		}
		glPopMatrix();
	}
}
