package extras;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class Shapes {

	public static Shape glyph(String txt, Graphics2D g2d) {
		FontRenderContext frc = g2d.getFontRenderContext();
		GlyphVector glyph_vector = g2d.getFont().createGlyphVector(frc, txt);
		return glyph_vector.getOutline();
	}
}
