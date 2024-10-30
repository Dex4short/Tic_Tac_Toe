package enums;

public enum GridType {
	Grid_3x3(3, 3), Grid_6x6(6, 6), Grid_9x9(9, 9);
	
	public int row, col;
	private GridType(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
