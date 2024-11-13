package enums;

import java.awt.Image;

import res.Resource;

public enum Symbol {
	X(Resource.getImage("X.png")),
	O(Resource.getImage("O.png"));
	
	public Image img;
	private Symbol(Image img) {
		this.img = img;
	}
}
