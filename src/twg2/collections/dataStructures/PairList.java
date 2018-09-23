package twg2.collections.dataStructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import twg2.collections.interfaces.PairCollection;
import twg2.collections.interfaces.PairCollectionReadOnly;
import twg2.collections.util.ToStringUtil;

/** Map implementation which allows duplicate keys and values 
 * (HashMap and LinkedHashMap do not allow duplicate keys)
 * The insertion order is the iteration order.
 * Performance is similar to {@link ArrayList}.
 * This class provides a mixture of Map and List methods along with some custom methods, everything should 
 * be self explanatory.
 * This is basically a {@code List<Map.Entry<K, V>>} with the ability to store duplicate key-value pairs.
 */
public class PairList<K, V> implements PairCollection<K, V> {
	private List<K> keys; // List of Map keys
	private List<V> values; // List of Map values
	private List<K> keysIm; // Immutable copy of the keys
	private List<V> valuesIm; // Immutable copy of the values
	private volatile int mod;


	/** Create a PairList with a default size of 10.
	 */
	public PairList() {
		this.keys = new ArrayList<K>();
		this.values = new ArrayList<V>();
		this.keysIm = Collections.unmodifiableList(this.keys);
		this.valuesIm = Collections.unmodifiableList(this.values);
	}


	/** Create a PairList with the given size
	 * @param initialSize the initial size of this PairList
	 */
	public PairList(int initialSize) {
		this.keys = new ArrayList<K>(initialSize);
		this.values = new ArrayList<V>(initialSize);
		this.keysIm = Collections.unmodifiableList(this.keys);
		this.valuesIm = Collections.unmodifiableList(this.values);
	}


	/** Create a pair list from a {@link Map} of keys and values.
	 * Note: changes to the map are not reflected in this pair list
	 * @param keyValues the map of keys and values to put in this pair list
	 */
	public PairList(Map<? extends K, ? extends V> keyValues) {
		this(keyValues.entrySet());
	}


	/** Create a pair list from a {@link Map} of keys and values.
	 * Note: changes to the map are not reflected in this pair list
	 * @param keyValues the map of keys and values to put in this pair list
	 */
	public PairList(Collection<? extends Map.Entry<? extends K, ? extends V>> keyValues) {
		this(keyValues.size());
		for(Map.Entry<? extends K, ? extends V> entry : keyValues) {
			this.keys.add(entry.getKey());
			this.values.add(entry.getValue());
		}
	}


	@SafeVarargs
	public PairList(Map.Entry<? extends K, ? extends V>... entries) {
		this(entries.length);
		for(Map.Entry<? extends K, ? extends V> entry : entries) {
			this.keys.add(entry.getKey());
			this.values.add(entry.getValue());
		}
	}


	/** Create a pair list from a {@link Map} of keys and values.
	 * Note: changes to the map are not reflected in this pair list
	 * @param keyValues the map of keys and values to put in this pair list
	 */
	public PairList(Iterable<? extends Map.Entry<? extends K, ? extends V>> keyValues) {
		this();
		for(Map.Entry<? extends K, ? extends V> entry : keyValues) {
			this.keys.add(entry.getKey());
			this.values.add(entry.getValue());
		}
	}


	/** Create a pair list from two collections of keys and values.
	 * Both collections must be the same size.
	 * Note: changes to the collection are not reflected in this pair list
	 * @param keys the keys to put in this pair list
	 * @param values the values to put in this pair list
	 */
	public PairList(Collection<? extends K> keys, Collection<? extends V> values) {
		this(keys != null ? keys.size() : (values != null ? values.size() : 0));
		if(keys == null || values == null || keys.size() != values.size()) {
			throw new IllegalArgumentException("the number of keys (" + (keys != null ? keys.size() : "null") + ") " +
					"does not equal the number of values (" + (values != null ? values.size() : "null"));
		}
		Iterator<? extends K> keyIter = keys.iterator();
		Iterator<? extends V> valIter = values.iterator();
		while(keyIter.hasNext()) {
			K key = keyIter.next();
			V value = valIter.next();
			add(key, value);
		}
	}


	public PairList<K, V> copy() {
		PairList<K, V> copy = new PairList<>(this.keys, this.values);
		return copy;
	}


	/** get, returns the value matching the first occurrence of the specified key
	 * @param key key who's corresponding value is to be returned
	 * @return the value which matches the 'key' parameter, returns null if the key does not exist
	 */
	@Override
	public V get(K key) {
		if(keys.indexOf(key) < 0) {
			return null;
		}
		else {
			int idx = keys.indexOf(key);
			return values.get(idx);
		}
	}


	/** returns the key corresponding to the index given
	 * @param index the index of the key to be returned
	 * @return the key found at the specified index
	 */
	@Override
	public K getKey(int index) {
		if(index < 0 || index > this.size()-1) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		else {
			return keys.get(index);
		}
	}


	/** returns the value corresponding to the index given
	 * @param index the index of the value to be returned
	 * @return the value found at the specified index
	 */
	@Override
	public V getValue(int index) {
		if(index < 0 || index > this.size()-1) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		else {
			return values.get(index);
		}
	}


	/** returns the last key
	 * @return the last key in this pair list
	 */
	@Override
	public K getLastKey() {
		if(this.size() < 0) { throw new IndexOutOfBoundsException("0 of pair list size " + this.size()); }

		return keys.get(this.keys.size() - 1);
	}


	/** returns the last value
	 * @return the last value in this pair list
	 */
	@Override
	public V getLastValue() {
		if(this.size() < 0) { throw new IndexOutOfBoundsException("0 of pair list size " + this.size()); }

		return values.get(this.keys.size() - 1);
	}


	/** indexOf, returns the index of the specified key
	 * @param key the key who's index is to be returned
	 * @return the index where the specified key was found, or -1 if the key cannot be found
	 */
	public int indexOf(K key) {
		int index = keys.indexOf(key);
		if(index > 0) {
			return index;
		}
		else {
			return -1;
		}
	}


	/** containsKey
	 * @param key Object to check for in this instance's list of keys
	 * @return true if this instance contains a key which equals the 'key' parameter
	 */
	@Override
	public boolean containsKey(K key) {
		if(keys.contains(key)) {
			return true;
		}
		return false;
	}


	/** containsValue
	 * @param value Object to check for in this instance's list of values
	 * @return true if this instance contains a value which equals the 'value' parameter
	 */
	@Override
	public boolean containsValue(V value) {
		if(values.contains(value)) {
			return true;
		}
		return false;
	}


	/** isEmpty
	 * @return true if this PairList instance has no key-value associates, returns false otherwise
	 */
	@Override
	public boolean isEmpty() {
		if(keys.size() == 0) {
			return true;
		}
		return false;
	}


	/** keyList, (Replaces the purpose of keySet)
	 * @return the List of keys from this map 
	 */
	@Override
	public List<K> keyList() {
		return this.keysIm;
	}


	/** valueList
	 * @return the List of values from this map 
	 */
	@Override
	public List<V> valueList() {
		return this.valuesIm;
	}


	/** put
	 * Always returns null because duplicate keys are allowed so all key-value pair passed to this method
	 * are added
	 * @param key key to add to this PairList instance
	 * @param value value to add to this PairList instance
	 */
	@Override
	public V put(K key, V value) {
		int index = keys.indexOf(key);
		if(index > -1) {
			V val = values.get(index);
			mod++;
			keys.set(index, key);
			values.set(index, value);
			return val;
		}
		else {
			add(key, value);
			return null;
		}
	}


	@Override
	public void add(K key, V value) {
		mod++;
		keys.add(key);
		values.add(value);
	}


	@Override
	public V put(Map.Entry<? extends K, ? extends V> keyValue) {
		put(keyValue.getKey(), keyValue.getValue());
		return null;
	}


	@Override
	public void add(Map.Entry<? extends K, ? extends V> keyValue) {
		add(keyValue.getKey(), keyValue.getValue());
	}
	


	/** putAll
	 * Adds all of the pairs in the mapPairs parameter to this PairList instance
	 * @param mapPairs map to add to this PairList instance
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> mapPairs) {
		Set<? extends K> keySet = mapPairs.keySet();
		mod++;

		for(K key : keySet) {
			keys.add(key);
		}

		Collection<? extends V> valueCollection = mapPairs.values();
		for(V value : valueCollection) {
			values.add(value);
		}
	}


	/** putAll
	 * Adds all of the pairs in the listPairs to this PairList instance
	 * @param listPairs pairList to add to this pairList
	 */
	@Override
	public void putAll(PairCollectionReadOnly<? extends K, ? extends V> listPairs) {
		mod++;
		keys.addAll(listPairs.keyList());
		values.addAll(listPairs.values());
	}


	/** remove
	 * @param key key to remove along with it's corresponding value
	 * @return the previous value associated with the deleted key
	 */
	@Override
	public V remove(Object key) {
		int index = keys.indexOf(key);
		if(index > -1) {
			V removedValue = values.get(index); // Temp value we are about to remove, used as return value
			removeIndex(index);
			return removedValue;
		}
		return null;
	}


	public void removeIndex(int index) {
		mod++;
		values.remove(index);
		keys.remove(index);
	}


	/** clear, removes all key-value pairs from this instance
	 */
	@Override
	public void clear() {
		mod++;
		keys.clear();
		values.clear();
	}


	/** size
	 * @return the size of this PairList instance
	 */
	@Override
	public int size() {
		return keys.size();
	}


	/** values
	 * @return a Collection of this instance's values
	 */
	@Override
	public Collection<V> values() {
		return valuesIm;
	}


	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new PairListIterator<>(this);
	}


	@Override
	public String toString() {
		StringBuilder builder = ToStringUtil.toStringKeyValuePairs(this.keys, this.values, this.keys.size(), null);
		return builder.toString();
	}


	/**
	 * @author TeamworkGuy2
	 * @since 2015-10-5
	 */
	public static class PairListIterator<K, V> implements Iterator<Entry<K, V>> {
		private final PairList<K, V> list;
		private int index;
		private PairListEntry<K, V> entry;
		private final int expectedMod;


		public PairListIterator(PairList<K, V> list) {
			this.list = list;
			this.index = -1; // so that first call to next() works and 'index' always matches current iterator position
			this.entry = new PairListEntry<>();
			this.expectedMod = list.mod;
		}


		@Override
		public boolean hasNext() {
			if(expectedMod != list.mod) {
				throw new ConcurrentModificationException("pair list change while iterating");
			}
			return index + 1 < list.size();
		}

		@Override
		public Entry<K, V> next() {
			if(expectedMod != list.mod) {
				throw new ConcurrentModificationException("pair list change while iterating");
			}
			index++;
			K nextKey = list.getKey(index);
			V nextVal = list.getValue(index);
			entry.key = nextKey;
			entry.value = nextVal;
			return entry;
		}
		
	}


	static class PairListEntry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;


		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			throw new UnsupportedOperationException("Entry.setValue()");
		}
	}

}
