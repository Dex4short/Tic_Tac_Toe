package enums;

public enum Direction {
	North(0,-1), NorthEast(1,-1), East(1,0), SouthEast(1,1), South(0,1), SouthWest(-1,1), West(-1,0), NorthWest(-1,-1);
	
	public int x_iterate, y_iterate;
	private Direction(int x_iterate, int y_iterate) {
		this.x_iterate = x_iterate;
		this.y_iterate = y_iterate;
	}
	
}
