package dataCollections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/** Map implementation which allows duplicate keys and values 
 * (HashMap and LinkedHashMap do not allow duplicate keys)
 * The insertion order is the iteration order.
 * Performance is similar to {@link ArrayList}.
 * This class provides a mixture of Map and List methods along with some custom methods, everything should 
 * be self explanatory.
 * This is basically a {@code List<Map.Entry<K, V>>} with the ability to store duplicate key-value pairs.
 */
public interface PairCollection<K, V> extends RandomAccessCollection<K> {

	/** clear, removes all key-value pairs from this instance
	 */
	public void clear();


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


	/** put
	 * Always returns null because duplicate keys are allowed so all key-value pair passed to this method
	 * are added
	 * @param key key to add to this PairList instance
	 * @param value value to add to this PairList instance
	 */
	public V put(K key, V value);


	public void add(K key, V value);


	public V put(Map.Entry<K, V> keyValue);


	public void add(Map.Entry<K, V> keyValue);


	/** putAll
	 * Adds all of the pairs in the mapPairs parameter to this PairList instance
	 * @param mapPairs map to add to this PairList instance
	 */
	public void putAll(Map<? extends K, ? extends V> mapPairs);


	/** putAll
	 * Adds all of the pairs in the listPairs to this PairList instance
	 * @param listPairs pairList to add to this pairList
	 */
	public void putAll(PairCollection<? extends K, ? extends V> listPairs);


	/** remove
	 * @param key key to remove along with it's corresponding value
	 * @return the previous value associated with the deleted key
	 */
	public V remove(K key);


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
