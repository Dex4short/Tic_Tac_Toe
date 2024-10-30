package extras;

public class Metrics {

	private Metrics() {
		//disable creating new instance
	}
	public static int rectLength(int w, int h) {
		return (int)Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
	}
}
