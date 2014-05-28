package core.util;

public class Node {
	
	public Node parent;
	public int H, F;
	public int G = 0;
	public int x, y;
	public boolean valid;
	
	public Node(int x, int y, boolean valid){
		this.x = x;
		this.y = y;
		this.valid = valid;
	}
	
	public void calcH(Node Goal){
		//Manhattan heuristics
		H = (int)MathUtil.distance(x, y, Goal.x, Goal.y);
	}
	
	public void calcF(){
		F = (G) + (H * 2);
	}
	
	public void render(float xo, float yo, float r, float b, float g){
		Shape.cube(x * 32 + xo, y * 32 + yo, 64, -4, -4, -2, 4, 4, 2, 0, 0, 0, r, g, b, 1);
		if(parent != null){
			Shape.cube(x * 32 + xo, y * 32 + yo, 64, -2, -0, -1, 2, 10, 1, 0, 0, MathUtil.direction(x * 32 + xo, y * 32 + yo, parent.x * 32 + xo, parent.y * 32 + yo) + 90, r, g, b, 1);
		}
	}

}
