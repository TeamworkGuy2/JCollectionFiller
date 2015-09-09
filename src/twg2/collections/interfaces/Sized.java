package twg2.collections.interfaces;

/**
 * @author TeamworkGuy2
 * @since 2015-9-9
 */
public interface Sized {

	/**
	 * @return the size of this collection of objects
	 */
	public int size();


	/**
	 * @return true if {@link #size()} {@code == 0}, false otherwise
	 */
	public default boolean isEmpty() {
		return size() == 0;
	}

}
