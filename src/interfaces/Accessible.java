package interfaces;

public interface Accessible<T> {

	public default T access() {
		return onAccess();
	}
	public abstract T onAccess();
}
