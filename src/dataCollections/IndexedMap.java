package dataCollections;

import java.util.RandomAccess;

/** An interface to provide generic access to a group of key-value pairs of objects
 * that has a size and can be randomly accessed.
 * @param <K> the type of keys stored in this map
 * @param <V> the type of values stored in this map
 * @see RandomAccess
 * @author TeamworkGuy2
 * @since 2014-3-3
 */
public interface IndexedMap<K, V> extends RandomAccess {

	/**
	 * @return the size of this collection of objects
	 */
	public int size();


	/**
	 * @param index the index of the key to retrieve from the collection,
	 * valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @return the key at the specified index
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public K getKey(int index);


	/**
	 * @param index the index of the value to retrieve from the collection,
	 * valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @return the value at the specified index
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public V getValue(int index);

}
