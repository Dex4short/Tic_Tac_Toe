package enums;

public enum GridType {
	Grid_3x3(3, 3), Grid_4x4(4, 4), Grid_5x5(5, 5);
	
	public int row, col;
	private GridType(int row, int col) {
		this.row = row;
		this.col = col;
	}
	@Override
	public String toString() {
		return row + "x" + col; 
	}
}
