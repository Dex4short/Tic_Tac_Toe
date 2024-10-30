package extras;

public class Timing {

	public Timing() {
		
	}
	public void sleep(long mills) {
		try {
			Thread.sleep(mills);//pause for a second
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
