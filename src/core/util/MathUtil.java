package core.util;

import java.awt.geom.Line2D;

/**
 * Copyright EndoPlasm Gaming ©2013 Edited 08/12/2013
 */
public class MathUtil {

	public static float distance(float x1, float y1, float x2, float y2) {
		float x = x1 - x2;
		float y = y1 - y2;
		return (float) Math.sqrt((x * x) + (y * y));
	}

	public static float direction(float x1, float y1, float x2, float y2) {
		double dx = x1 - x2;
		double dy = y2 - y1;

		double inRads = Math.atan2(dy, dx);

		if (inRads < 0)
			inRads = Math.abs(inRads);
		else
			inRads = 2 * Math.PI - inRads;
		return (float) Math.toDegrees(inRads);
	}

	public static float getXSpeed(float rotation, float dist) {
		return (float) Math.cos(Math.toRadians(rotation)) * dist;
	}

	public static float getYSpeed(float rotation, float dist) {
		return (float) Math.sin(Math.toRadians(rotation)) * dist;
	}

	public static boolean doLinesIntersect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		Line2D line1 = new Line2D.Float(x1, y1, x2, y2);
		Line2D line2 = new Line2D.Float(x3, y3, x4, y4);
		boolean result = line2.intersectsLine(line1);
		return result;
	}

	// TODO: Owned by Java-Gaming.org, give credit
	public static Vertex2f getLineLineIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double det1And2 = det(x1, y1, x2, y2);
		double det3And4 = det(x3, y3, x4, y4);
		double x1LessX2 = x1 - x2;
		double y1LessY2 = y1 - y2;
		double x3LessX4 = x3 - x4;
		double y3LessY4 = y3 - y4;
		double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
		if (det1Less2And3Less4 == 0) {
			// the denominator is zero so the lines are parallel and there's
			// either no solution (or multiple solutions if the lines overlap)
			// so return null.
			// System.out.println("Lines are parallel");
			return null;
		}
		double x = (det(det1And2, x1LessX2, det3And4, x3LessX4) / det1Less2And3Less4);
		double y = (det(det1And2, y1LessY2, det3And4, y3LessY4) / det1Less2And3Less4);
		return new Vertex2f((float) x, (float) y);
	}

	protected static double det(double a, double b, double c, double d) {
		return a * d - b * c;
	}

	public static float min(float[] values) {
		float smallest = values[0];
		for (float f : values) {
			if (f < smallest) smallest = f;
		}
		return smallest;
	}

	public static float max(float[] values) {
		float largest = values[0];
		for (float f : values) {
			if (f > largest) largest = f;
		}
		return largest;
	}

	public static Vertex2f rotateMatrix2D(Vertex2f v1, float rotation){
		double rot = Math.toRadians(rotation);
		double sin = Math.sin(rot);
		double cos = Math.cos(rot);
		Vertex2f v2 = new Vertex2f(0,0);
		v2.x = (float) ((v1.x * sin) + (v1.y * cos));
		v2.y = (float) ((v1.x * -cos) + (v1.y * sin));
		return v2;
	}

	public static Vertex2f shiftMatrix2D(Vertex2f v1, float x, float y){
		Vertex2f v2 = new Vertex2f(v1.x + x, v1.y + y);
		return v2;
	}
}
