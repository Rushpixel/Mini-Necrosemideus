package core.util;

public class Rectangle {
	public Vertex2f UL;
	public Vertex2f UR;
	public Vertex2f DL;
	public Vertex2f DR;
	public float axis1x;
	public float axis1y;
	public float axis2x;
	public float axis2y;

	public Rectangle(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		UL = new Vertex2f(x1, y1);
		UR = new Vertex2f(x2, y2);
		DL = new Vertex2f(x3, y3);
		DR = new Vertex2f(x4, y4);
		calcAxis();
	}

	public void calcAxis() {
		axis1x = UR.x - UL.x;
		axis1y = UR.y - UL.y;
		axis2x = UR.x - DR.x;
		axis2y = UR.y - DR.y;
	}

	public boolean doesCollide(Rectangle other) {
		float range = MathUtil.distance(UL.x, UL.y, DR.x, DR.y);
		float Orange = MathUtil.distance(other.UL.x, other.UL.y, other.DR.x, other.DR.y);
		if(MathUtil.distance(other.UL.x, other.UL.y, UL.x, UL.y) > range + Orange) return false;
		if (!Possible(axis1x, axis1y, UL, UR, DL, DR, other.UL, other.UR, other.DL, other.DR)) return false;
		if (!Possible(axis2x, axis2y, UL, UR, DL, DR, other.UL, other.UR, other.DL, other.DR)) return false;
		if (!Possible(other.axis1x, other.axis1y, UL, UR, DL, DR, other.UL, other.UR, other.DL, other.DR)) return false;
		if (!Possible(other.axis2x, other.axis2y, UL, UR, DL, DR, other.UL, other.UR, other.DL, other.DR)) return false;
		return true;
	}

	public boolean Possible(float axisx, float axisy, Vertex2f AUL, Vertex2f AUR, Vertex2f ADL, Vertex2f ADR, Vertex2f BUL, Vertex2f BUR, Vertex2f BDL, Vertex2f BDR) {
		float pAUL = dotProduct(axisx, axisy, project(axisx, axisy, AUL));
		float pAUR = dotProduct(axisx, axisy, project(axisx, axisy, AUR));
		float pADL = dotProduct(axisx, axisy, project(axisx, axisy, ADL));
		float pADR = dotProduct(axisx, axisy, project(axisx, axisy, ADR));

		float pBUL = dotProduct(axisx, axisy, project(axisx, axisy, BUL));
		float pBUR = dotProduct(axisx, axisy, project(axisx, axisy, BUR));
		float pBDL = dotProduct(axisx, axisy, project(axisx, axisy, BDL));
		float pBDR = dotProduct(axisx, axisy, project(axisx, axisy, BDR));

		float minA = MathUtil.min(new float[] { pAUL, pAUR, pADL, pADR });
		float maxA = MathUtil.max(new float[] { pAUL, pAUR, pADL, pADR });

		float minB = MathUtil.min(new float[] { pBUL, pBUR, pBDL, pBDR });
		float maxB = MathUtil.max(new float[] { pBUL, pBUR, pBDL, pBDR });

		if (minA > maxB) return false;
		if (minB > maxA) return false;
		return true;
	}

	public float dotProduct(float axisx, float axisy, Vertex2f point) {
		return (axisx * point.x) + (axisy * point.y);
	}

	public Vertex2f project(float axisx, float axisy, Vertex2f point) {
		float j = (float) (Math.pow(axisx, 2) + Math.pow(axisy, 2));
		float i = ((point.x * axisx + point.y * axisy) / j);
		Vertex2f result = new Vertex2f(i * axisx, i * axisy);
		return result;
	}
	
	public Vertex2f getMin(Vertex2f axis, Vertex2f[] list){
		float nearestDot = dotProduct(axis.x, axis.y, list[0]);
		Vertex2f nearest = list[0];
		for(Vertex2f v: list){
			float dot = dotProduct(axis.x, axis.y, v);
			if(dot < nearestDot){
				nearestDot = dot;
				nearest = v;
			}
		}
		return nearest;
	}
	
	public Vertex2f getMinDist(Vertex2f[] list){
		float nearestDist = MathUtil.distance(0, 0, list[0].x, list[0].y);
		Vertex2f nearest = list[0];
		for(Vertex2f v: list){
			float dist = MathUtil.distance(0, 0, v.x, v.y);
			if(dist < nearestDist){
				nearestDist = dist;
				nearest = v;
			}
		}
		return nearest;
	}
	
	public Vertex2f getMaxDist(Vertex2f[] list){
		float furthestDist = MathUtil.distance(0, 0, list[0].x, list[0].y);
		Vertex2f furthest = list[0];
		for(Vertex2f v: list){
			float dist = MathUtil.distance(0, 0, v.x, v.y);
			if(dist > furthestDist){
				furthestDist = dist;
				furthest = v;
			}
		}
		return furthest;
	}
	
	public Vertex2f getMax(Vertex2f axis, Vertex2f[] list){
		float furthestDot = dotProduct(axis.x, axis.y, list[0]);
		Vertex2f furthest = list[0];
		for(Vertex2f v: list){
			float dot = dotProduct(axis.x, axis.y, v);
			if(dot > furthestDot){
				furthestDot = dot;
				furthest = v;
			}
		}
		return furthest;
	}

	public Vertex2f moveToContact(Rectangle other) {
		
		Vertex2f Ar1 = distAxis(new Vertex2f(axis1x,axis1y), other);
		Vertex2f Ar2 = distAxis(new Vertex2f(axis2x,axis2y), other);
		Vertex2f Br1 = distAxis(new Vertex2f(other.axis1x,other.axis1y), other);
		Vertex2f Br2 = distAxis(new Vertex2f(other.axis2x,other.axis2y), other);
		
		return getMinDist(new Vertex2f[]{Ar1, Ar2, Br1, Br2});
	}
	
	public Vertex2f distAxis(Vertex2f axis, Rectangle other){
		Vertex2f pAUL = project(axis.x, axis.y, UL);
		Vertex2f pAUR = project(axis.x, axis.y, UR);
		Vertex2f pADL = project(axis.x, axis.y, DL);
		Vertex2f pADR = project(axis.x, axis.y, DR);
		Vertex2f minA = getMin(axis, new Vertex2f[]{pAUL, pAUR, pADL, pADR});
		Vertex2f maxA = getMax(axis, new Vertex2f[]{pAUL, pAUR, pADL, pADR});
		
		Vertex2f pBUL = project(axis.x, axis.y, other.UL);
		Vertex2f pBUR = project(axis.x, axis.y, other.UR);
		Vertex2f pBDL = project(axis.x, axis.y, other.DL);
		Vertex2f pBDR = project(axis.x, axis.y, other.DR);
		Vertex2f minB = getMin(axis, new Vertex2f[]{pBUL, pBUR, pBDL, pBDR});
		Vertex2f maxB = getMax(axis, new Vertex2f[]{pBUL, pBUR, pBDL, pBDR});
		
		Vertex2f r1 = subtractPoints(axis, minA, maxB);
		Vertex2f r2 = subtractPoints(axis, maxA, minB);
		
		if(MathUtil.distance(0, 0, r1.x, r1.y) < MathUtil.distance(0, 0, r2.x, r2.y)){
			return r1;
		} else{
			return r2;
		}
	}	
	
	public Vertex2f subtractPoints(Vertex2f axis, Vertex2f A, Vertex2f B){
		return new Vertex2f(A.x - B.x, A.y - B.y);
	}
}
