package twg2.collections.interfaces;

import java.util.ArrayList;
import java.util.Map;

/** Map implementation which allows duplicate keys and values 
 * (HashMap and LinkedHashMap do not allow duplicate keys)
 * The insertion order is the iteration order.
 * Performance is similar to {@link ArrayList}.
 * This class provides a mixture of Map and List methods along with some custom methods, everything should 
 * be self explanatory.
 * This is basically a {@code List<Map.Entry<K, V>>} with the ability to store duplicate key-value pairs.
 */
public interface PairCollection<K, V> extends PairCollectionImmutable<K, V> {

	/** clear, removes all key-value pairs from this instance
	 */
	public void clear();


	/** put
	 * Always returns null because duplicate keys are allowed so all key-value pair passed to this method
	 * are added
	 * @param key key to add to this PairList instance
	 * @param value value to add to this PairList instance
	 */
	public V put(K key, V value);


	public void add(K key, V value);


	public V put(Map.Entry<? extends K, ? extends V> keyValue);


	public void add(Map.Entry<? extends K, ? extends V> keyValue);


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

}
