package dataCollections;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/** Map implementation which allows duplicate keys and values 
 * ({@link HashMap} and {@link LinkedHashMap} do not allow duplicate keys).<br>
 * Implementations should have constant-time access performance
 * (i.e. O(1) for {@link #get(int)}, see {@link RandomAccessCollection})
 * @author TeamworkGuy2
 * @since 2015-4-5
 * @param <K> the data type of keys, the first value, in each pair of keys/values
 * @param <V> the data type of values, the second value, in each pair of keys/values
 */
public interface PairCollectionImmutable<K, V> extends RandomAccessCollection<K> {

	/** Check if this pair collection contains a specific key
	 * @param key Object to check for in this instance's list of keys
	 * @return true if this instance contains a key which equals the 'key' parameter
	 */
	public boolean containsKey(K key);


	/** Check if this pair collection contains a specific value
	 * @param value Object to check for in this instance's list of values
	 * @return true if this instance contains a value which equals the 'value' parameter
	 */
	public boolean containsValue(V value);


	/** get the value associated the first occurrence of the specified key
	 * @param key key who's corresponding value is to be returned
	 * @return the value which matches the 'key' parameter, returns null if the key does not exist
	 */
	public V get(K key);


	/** isEmpty
	 * @return true if this PairList instance has no key-value associates, returns false otherwise
	 */
	public boolean isEmpty();


	/** keyList, (Replaces the purpose of keySet)
	 * @return the List of keys from this map 
	 */
	public List<K> keyList();


	/** valueList
	 * @return the List of values from this map 
	 */
	public List<V> valueList();


	/** size
	 * @return the size of this PairList instance
	 */
	@Override
	public int size();


	/** values
	 * @return a Collection of this instance's values
	 */
	public Collection<V> values();


	@Override
	public String toString();

}