package extras;

public class Metrics {

	private Metrics() {
		//disable creating new instance
	}
	public static double rectLength(double w, double h) {
		return Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
	}
	public static double rectScale(double w, double h) {
		return rectLength(w, h) / 100d;
	}
}
