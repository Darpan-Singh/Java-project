package common;

public class Location {
	public Location(int x, int y) {
		loc_x = x;
		loc_y = y;
	}
	
	public int getX() { return loc_x;}
	public int getY() { return loc_y;}
	
	public void setLoc(int x, int y) {
		loc_x = x;
		loc_y = y;
	}
	
	private int loc_x, loc_y;
}
