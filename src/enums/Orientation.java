package enums;

public enum Orientation {
	Horizontal(-1,0,1,0), Vertical(0,-1,0,1), LeftDiagonal(-1,-1,1,1), RightDiagonal(1,-1,-1,1);
	
	public int x1,y1,x2,y2;
	private Orientation(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
}
