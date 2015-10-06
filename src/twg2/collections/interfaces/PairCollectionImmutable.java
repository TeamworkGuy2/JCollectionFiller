package twg2.collections.interfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.RandomAccess;

/** Map implementation which allows duplicate keys and values 
 * ({@link HashMap} and {@link LinkedHashMap} do not allow duplicate keys).<br>
 * Implementations should have constant-time access performance (i.e. O(1) for {@link #getKey(int)} and {@link #getValue(int)})
 * @author TeamworkGuy2
 * @since 2015-4-5
 * @param <K> the data type of keys, the first value, in each pair of keys/values
 * @param <V> the data type of values, the second value, in each pair of keys/values
 */
public interface PairCollectionImmutable<K, V> extends RandomAccess, Sized, Iterable<Entry<K, V>> {

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


	/**
	 * @param index the index of the key to retrieve, range [0, {@link #size()} - 1]
	 * @return the key at the given index in this pair collection
	 */
	public K getKey(int index);


	/**
	 * @param index the index of the value to retrieve, range [0, {@link #size()} - 1]
	 * @return the value at the given index in this pair collection
	 */
	public V getValue(int index);


	/**
	 * @return the last key in the pair collection
	 */
	public default K getLastKey() {
		return this.getKey(this.size() - 1);
	}


	/**
	 * @return the last value in the pair collection
	 */
	public default V getLastValue() {
		return this.getValue(this.size() - 1);
	}


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
	public Iterator<Entry<K, V>> iterator();


	@Override
	public String toString();

}