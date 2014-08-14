package game.world;

public class Map_Level extends Map{
	public int northStartx;
	public int westStartx;
	public int southStartx;
	public int eastStartx;
	public int northStarty;
	public int westStarty;
	public int southStarty;
	public int eastStarty;

	public Map_Level(int width, int height, String Name) {
		super(width, height, Name);

		northStarty = 1;
		eastStartx = width - 2;
		southStarty = height - 2;
		westStartx = 1;
	}

}
