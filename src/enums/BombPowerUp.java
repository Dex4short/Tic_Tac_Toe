package enums;

import java.awt.Image;
import java.awt.Point;

import res.Resource;

public enum BombPowerUp {
	BigBang(Resource.getImage("bomb_card_all_direction.png")){
		@Override
		public Point[] explosionPoints() {
			return new Point[] {
					new Point(row-1, col-1), new Point(row-1, col), new Point(row-1, col+1),
					new Point(row  , col-1), new Point(row  , col), new Point(row  , col+1),
					new Point(row+1, col-1), new Point(row+1, col), new Point(row+1, col+1)
			};
		}
	},
	SinglePoint(Resource.getImage("bomb_card_single_point.png")){
		@Override
		public Point[] explosionPoints() {
			return new Point[] {
					new Point(row, col)
			};
		}
	},
	Horizon(Resource.getImage("bomb_card_horizontal.png")){
		@Override
		public Point[] explosionPoints() {
			return new Point[] {
					new Point(row  , col-1), new Point(row  , col), new Point(row  , col+1)
			};
		}
	},
	Vertica(Resource.getImage("bomb_card_vertical.png")){
		@Override
		public Point[] explosionPoints() {
			return new Point[] {
					new Point(row-1, col),
					new Point(row  , col), 
					new Point(row+1, col)
			};
		}
	}, 
	Cross(Resource.getImage("bomb_card_cross.png")){
		@Override
		public Point[] explosionPoints() {
			return new Point[] {
											 new Point(row-1, col),
					new Point(row  , col-1), new Point(row  , col), new Point(row  , col+1),
											 new Point(row+1, col)
			};
		}
	},
	EX(Resource.getImage("bomb_card_ex.png")){
		@Override
		public Point[] explosionPoints() {
			return new Point[] {
					new Point(row-1, col-1), 						new Point(row-1, col+1),
											 new Point(row  , col),
					new Point(row+1, col-1), 						new Point(row+1, col+1)
			};
		}
	},
	DiagonalLeft(Resource.getImage("bomb_card_diagonal_left.png")){
		@Override
		public Point[] explosionPoints() {
			return new Point[] {
																	new Point(row-1, col+1),
											 new Point(row  , col),
					new Point(row+1, col-1)
			};
		}
	},
	DiagonalRight(Resource.getImage("bomb_card_diagonal_right.png")){
		@Override
		public Point[] explosionPoints() {
			return new Point[] {
					new Point(row-1, col-1),
											 new Point(row  , col),
											 						new Point(row+1, col+1)
			};
		}
	};
	
	public Image img;
	public int row, col;
	private BombPowerUp(Image img) {
		this.img = img;
		row = -1;
		col = -1;
	}
	public void setTarget(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public abstract Point[] explosionPoints();
}
