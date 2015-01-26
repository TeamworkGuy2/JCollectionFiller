package dataCollections;

import java.util.Map;

/** A modifiable {@link IndexedMap} where key-value pairs can be removed.
 * @param <K> the type of keys stored in this map
 * @param <V> the type of values stored in this map
 * @see IndexedMap
 * @author TeamworkGuy2
 * @since 2014-4-12
 */
public interface ModifiableIndexedMap<K, V> extends IndexedMap<K, V> {

	/** Remove the key-value pair at the specified index. Only the key is returned.
	 * Subsequent items after the inserted item are shifted one index lower (toward 0).
	 * @param index the index of the item to remove from this map.
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @return the key of the key-value pair removed from the specified index
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public K removeKey(int index);


	/** Remove the key-value pair at the specified index. Only the value is returned.
	 * Subsequent items after the inserted item are shifted one index lower (toward 0).
	 * @param index the index of the item to remove from this map.
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @return the value of the key-value pair removed from the specified index
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public V removeValue(int index);


	/** Remove the key-value pair at the specified index.
	 * Does not return the remove key-value pair.
	 * @param index the index of the item to remove from this map.
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 */
	public void remove(int index);


	/** Remove the key-value pair at the specified index.
	 * @param index the index of the item to remove from this map.
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @return the key-value pair removed from the specified index
	 */
	public Map.Entry<K, V> removeEntry(int index);


	/** Set the key-value pair at the specified index.
	 * Does not return the old key-value pair
	 * @param index the index to store the item in.
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @param key the key to store at the specified index
	 * @param value the value to store at the specified index
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public void set(int index, K key, V value);


	/** Set the key-value pair at the specified index
	 * @param index the index to store the item in.
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @param key the key to store at the specified index
	 * @param value the value to store at the specified index
	 * @return the previous key-value pair at the specified index or null if the index
	 * correspond to a null key-value pair
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public Map.Entry<K, V> setEntry(int index, K key, V value);


	/** Add a key-value pair to this map. The index of the inserted key-value
	 * pair is completely dependent on the implementation.
	 * @param key the key to store at the specified index
	 * @param value the value to store at the specified index
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public void add(K key, V value);


	/** Add a key-value pair to this map. Subsequent items after the inserted item
	 * are shifted one index higher (toward +infinity).
	 * @param index the index at which to insert a new item.
	 * Valid index range {@code [0, }{@link #size()}{@code ]}
	 * @param key the key to store at the specified index
	 * @param value the value to store at the specified index
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	//public void add(int index, K key, V value);


	/** Remove all of the items in this map.
	 * Once this call returns {@link #size()} should return 0.
	 */
	public void clear();

}

