package twg2.collections.interfaces;

import java.util.Iterator;
import java.util.RandomAccess;

/** An interface to provide generic access to a group/collection/set of objects
 * that has a size and can be randomly accessed.
 * @param <T> the type of item stored in this collection
 * @see RandomAccess
 * @author TeamworkGuy2
 * @since 2013-12-26
 */
public interface RandomAccessCollection<T> extends Sized, RandomAccess, Iterable<T> {


	/**
	 * @param index the index of the item to retrieve from the collection.
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @return the item at the specified index
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public T get(int index);


	/**
	 * @return the last element in the collection
	 */
	public default T getLast() {
		return this.get(this.size() - 1);
	}


	@Override
	Iterator<T> iterator();


	@Override
	public String toString();

}
