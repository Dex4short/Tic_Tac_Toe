package interfaces;

public interface Updatable {

	public default void update(int update_code) {
		onUpdate(update_code);
	}
	public void onUpdate(int update_code);
}
