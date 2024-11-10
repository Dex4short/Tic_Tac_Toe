package interfaces;

public interface Action {
	public default void action() {
		onAction();
	}
	public void onAction();
}
