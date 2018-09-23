package twg2.collections.dataStructures;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

import twg2.collections.interfaces.ListReadOnly;
import twg2.collections.interfaces.MapIndexed;
import twg2.collections.interfaces.PairCollection;
import twg2.collections.interfaces.PairCollectionReadOnly;
import twg2.collections.interfaces.RandomAccessCollection;
import twg2.collections.util.ToStringUtil;

/** Bag, a collection similar to a cross between an {@link List} and a
 * {@link Map} that maps each key to a value but does not preserve the
 * insertion order of items. 
 * All operations are O(1), except {@link #remove(Object) remove(T)} and {@link #add(Object, Object) add(K, V)} when
 * the internal storage mechanism is full and must be expanded.
 * @param <K> the type of keys that can be stored in this collection
 * @param <V> the type of values that can be stored in this collection
 * @see Bag
 * @author TeamworkGuy2
 * @since 2013-1-20
 */
public class PairBag<K, V> implements PairCollection<K, V>, MapIndexed<K, V>, Iterable<Map.Entry<K, V>> {
	private Object[] keys;
	private Object[] values;
	/** The highest currently empty index to insert new items into,
	 * also equivalent to the zero based size of this bag */
	private int size;
	/** Used by iterators to ensure that the list has not been modified while iterating */
	private volatile int action;
	private BagMapKeyView keyView;
	private BagMapValueView valueView;
	private MapIndexed<K, V> keyValueView;


	/** Creates an unsorted group of items with a default size of 16
	 */
	public PairBag() {
		this(16);
	}


	/** Create an unsorted group of items with the specified size as the starting size
	 * @param capacity the initial size of the group of items
	 */
	public PairBag(int capacity) {
		this.keys = new Object[capacity];
		this.values = new Object[capacity];
		this.size = 0;
	}


	/** Create a pair list from a {@link Map} of keys and values.
	 * Note: changes to the map are not reflected in this pair list
	 * @param keyValues the map of keys and values to put in this pair list
	 */
	public PairBag(Map<? extends K, ? extends V> keyValues) {
		this(keyValues.entrySet());
	}


	public PairBag(Collection<? extends Map.Entry<? extends K, ? extends V>> keyValues) {
		this(keyValues.size());
		for(Map.Entry<? extends K, ? extends V> entry : keyValues) {
			this.add(entry);
		}
	}


	@SafeVarargs
	public PairBag(Map.Entry<K, V>...entries) {
		this(entries.length);
		for(Map.Entry<K, V> entry : entries) {
			this.add(entry);
		}
	}


	/** Create a pair list from a {@link Map} of keys and values.
	 * Note: changes to the map are not reflected in this pair list
	 * @param keyValues the map of keys and values to put in this pair list
	 */
	public PairBag(Iterable<? extends Map.Entry<? extends K, ? extends V>> keyValues) {
		this();
		for(Map.Entry<? extends K, ? extends V> entry : keyValues) {
			this.add(entry);
		}
	}


	/** Create a pair list from two collections of keys and values.
	 * Both collections must be the same size.
	 * Note: changes to the collection are not reflected in this pair list
	 * @param keys the keys to put in this pair list
	 * @param values the values to put in this pair list
	 */
	public PairBag(Collection<? extends K> keys, Collection<? extends V> values) {
		this();
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


	public PairBag<K, V> copy() {
		PairBag<K, V> copy = new PairBag<>();

		copy.keys = new Object[this.keys.length];
		System.arraycopy(this.keys, 0, copy.keys, 0, this.keys.length);

		copy.values = new Object[this.values.length];
		System.arraycopy(this.values, 0, copy.values, 0, this.values.length);

		copy.size = this.size;
		//copy.action = this.action;
		return copy;
	}


	/** Bad lock checking mechanism that allows someone to compare a past and
	 * current action count.  If two count values returned by this method
	 * differ than this object has been modified between the two calls
	 * that returned the two different values.
	 * @return the number of actions (add, remove, clear) carried out by
	 * this bag since it was created.
	 */
	public int getActionCount() {
		return action;
	}


	@Override
	public V get(K key) {
		return getValue(getKeyIndex(key));
	}


	/** Get the key at the specified index from this group of key value pairs
	 * @param index the index between {@code [0, }{@link #size()}{@code -1]}
	 * inclusive to retrieve
	 * @return the key found at the specified index
	 */
	@Override
	public K getKey(int index) {
		if(index < 0 || index >= size) { throw new IndexOutOfBoundsException("" + index); }
		@SuppressWarnings("unchecked")
		K key = (K)keys[index];
		return key;
	}


	/** Get the value at the specified index from this group of key value pairs
	 * @param index the index within the range {@code [0, }{@link #size()}{@code -1]}
	 * inclusive to retrieve
	 * @return the value found at the specified index
	 */
	@Override
	public V getValue(int index) {
		if(index < 0 || index >= size) { throw new IndexOutOfBoundsException("" + index); }
		@SuppressWarnings("unchecked")
		V value = (V)values[index];
		return value;
	}


	@Override
	public K getLastKey() {
		if(this.size < 1) { throw new IndexOutOfBoundsException("0 of pair bag size " + this.size); }
		@SuppressWarnings("unchecked")
		K key = (K)keys[this.size - 1];
		return key;
	}


	@Override
	public V getLastValue() {
		if(this.size < 1) { throw new IndexOutOfBoundsException("0 of pair bag size " + this.size); }
		@SuppressWarnings("unchecked")
		V value = (V)values[this.size - 1];
		return value;
	}


	/** Get the index of the specified key
	 * @param key the key to search for in this group of objects
	 * @return the index of the key value pair if it is found, or -1 if it is not found
	 */
	public int getKeyIndex(K key) {
		return getKeyObjectIndex(key);
	}


	/** Get the index of the first occurence of the specified value
	 * @param value the value to search for in this group of objects
	 * @return the index of the first key value pair containing a matching value, or -1 if it is not found
	 */
	public int getValueIndex(V value) {
		return getValueObjectIndex(value);
	}


	/** Get the index of the specified key
	 * @param key the key to search for in this group of objects
	 * @return the index of the key value pair if it is found, or -1 if it is not found
	 */
	public int getKeyObjectIndex(Object key) {
		// Search for the item to remove
		if(key != null) {
			for(int i = 0; i < size; i++) {
				// If the item is found, return it's index
				if(key.equals(keys[i])) {
					return i;
				}
			}
		}
		// Else if the item is null, search for a null item in our list
		else {
			for(int i = 0; i < size; i++) {
				// If a null item is found, return it's index
				if(keys[i] == null) {
					return i;
				}
			}
		}
		return -1;
	}


	/** Get the index of the first occurence of the specified value
	 * @param value the value to search for in this group of objects
	 * @return the index of the first key value pair containing a matching value, or -1 if it is not found
	 */
	public int getValueObjectIndex(Object value) {
		// Search for the item to remove
		if(value != null) {
			for(int i = 0; i < size; i++) {
				// If the item is found, return it's index
				if(value.equals(values[i])) {
					return i;
				}
			}
		}
		// Else if the item is null, search for a null item in our list
		else {
			for(int i = 0; i < size; i++) {
				// If a null item is found, return it's index
				if(values[i] == null) {
					return i;
				}
			}
		}
		return -1;
	}


	@Override
	public boolean containsKey(K key) {
		return getKeyIndex(key) > -1;
	}


	@Override
	public boolean containsValue(V value) {
		return getValueIndex(value) > -1;
	}


	public boolean containsKeyObject(Object key) {
		return getKeyObjectIndex(key) > -1;
	}


	public boolean containsValueObject(Object value) {
		return getValueObjectIndex(value) > -1;
	}


	public void setKeyValue(int index, K key, V value) {
		action++;
		keys[index] = key;
		values[index] = value;
	}


	@Override
	public V put(K key, V value) {
		int index = getKeyIndex(key);
		if(index > -1) {
			V v = getValue(index);
			setKeyValue(index, key, value);
			return v;
		}
		else {
			add(key, value);
			return null;
		}
	}


	/** Add the specified key value pair to this group of elements
	 * @param key the key to add to this group of elements
	 * @param value the value to associate with the key being added
	 */
	@Override
	public void add(K key, V value) {
		action++;
		// If the bag is to small, expand it
		if(size >= keys.length) {
			expandBag();
		}
		// Add the new item
		keys[size] = key;
		values[size] = value;
		size++;
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


	@Override
	public void putAll(Map<? extends K, ? extends V> mapPairs) {
		for(Map.Entry<? extends K, ? extends V> entry : mapPairs.entrySet()) {
			add(entry.getKey(), entry.getValue());
		}
	}


	@Override
	public void putAll(PairCollectionReadOnly<? extends K, ? extends V> listPairs) {
		List<? extends V> values = listPairs.valueList();
		// avoid creating iterator if the list is random access
		if(values instanceof RandomAccess) {
			for(int i = 0, size = listPairs.size(); i < size; i++) {
				add(listPairs.getKey(i), values.get(i));
			}
		}
		else {
			int i = 0;
			for(V v : values) {
				add(listPairs.getKey(i), v);
				i++;
			}
		}
	}


	/** Remove the key value pair at the specified index from this group of elements
	 * @param index the index between zero and {@link #size()}-1 inclusive to remove
	 * @return the key removed from the specified index
	 */
	public K remove(int index) {
		if(index < 0 || index >= size) { throw new IndexOutOfBoundsException("" + index); }
		action++;
		// Get the item to remove
		@SuppressWarnings("unchecked")
		K key = (K)keys[index];
		// Replace the item to remove with the last element from our array
		keys[index] = keys[size - 1];
		values[index] = values[size - 1];
		// Set the last element to null
		keys[size - 1] = null;
		values[size - 1] = null;
		// Item removed - decrease size, action occurred
		size--;
		return key;
	}


	/** Remove the key value pair at the specified index from this group of elements
	 * @param index the index between zero and {@link #size()}-1 inclusive to remove
	 * @return the key-value pair removed from the specified index
	 */
	public Map.Entry<K, V> removeEntry(int index) {
		if(index < 0 || index >= size) { throw new IndexOutOfBoundsException("" + index); }
		action++;
		// Get the item to remove
		@SuppressWarnings("unchecked")
		K key = (K) keys[index];
		@SuppressWarnings("unchecked")
		V value = (V) values[index];
		// Replace the item to remove with the last element from our array
		keys[index] = keys[size - 1];
		values[index] = values[size - 1];
		// Set the last element to null
		keys[size - 1] = null;
		values[size - 1] = null;
		// Item removed - decrease size, action occurred
		size--;
		return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
	}


	/** Remove the specified key and its corresponding value from this group of objects
	 * @param key the key to remove based on item.equals(get(i)) if item
	 * is not null, or get(i)==null if item is null, where i is [0, size()-1]
	 * @return the value associated with the specified key, or null if the key was not found
	 */
	@Override
	public V remove(K key) {
		// Search for the item to remove
		if(key != null) {
			for(int i = 0; i < size; i++) {
				// If the item is found, remove it
				if(key.equals(keys[i])) {
					action++;
					@SuppressWarnings("unchecked")
					V value = (V)values[i];
					keys[i] = keys[size - 1];
					values[i] = values[size - 1];
					keys[size - 1] = null;
					values[size - 1] = null;
					size--;
					return value;
				}
			}
		}
		// Else if the item is null, search for a null item in our list
		else {
			for(int i = 0; i < size; i++) {
				// If the item is found, remove it
				if(keys[i] == null) {
					action++;
					@SuppressWarnings("unchecked")
					V value = (V)values[i];
					keys[i] = keys[size - 1];
					values[i] = values[size - 1];
					keys[size - 1] = null;
					values[size - 1] = null;
					size--;
					return value;
				}
			}
		}
		return null;
	}


	/** Clear the group of elements
	 */
	@Override
	public void clear() {
		action++;
		// Clear list to null
		for(int i = 0; i < size; i++) {
			keys[i] = null;
			values[i] = null;
		}
		// Set the size back to the beginning of the array
		size = 0;
	}


	/** Get the current size of this group of elements
	 * @return the size of this group of elements
	 */
	@Override
	public int size() {
		return size;
	}


	/** Is this group of elements empty
	 * @return true if this group of elements is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	/** Useless key iterator for this group of key value pairs.
	 * Do not store references to the returned key-value entries.
	 * @return an iterator that iterates over this groups' collection
	 * of key value pairs.
	 */
	@Override
	public Iterator<Map.Entry<K, V>> iterator() {
		return new BagMapIterator();
	}


	private BagMapKeyView getKeyViewPrivate() {
		return keyView == null ? (keyView = new BagMapKeyView()) : keyView;
	}


	private BagMapValueView getValueViewPrivate() {
		return valueView == null ? (valueView = new BagMapValueView()) : valueView;
	}


	private MapIndexed<K, V> getKeyValueViewPrivate() {
		return keyValueView == null ? (keyValueView = new BagMapKeyValueView()) : keyValueView;
	}


	@Override
	public List<K> keyList() {
		return getKeyViewPrivate();
	}


	@Override
	public List<V> valueList() {
		return getValueViewPrivate();
	}


	@Override
	public Collection<V> values() {
		return getValueViewPrivate();
	}


	public RandomAccessCollection<K> getKeyView() {
		return getKeyViewPrivate();
	}


	public RandomAccessCollection<V> getValueView() {
		return getValueViewPrivate();
	}


	public MapIndexed<K, V> getKeyValueView() {
		return getKeyValueViewPrivate();
	}


	public Collection<Map.Entry<K, V>> entryCollection() {
		return new AbstractCollection<Map.Entry<K,V>>() {

			@Override
			public Iterator<Entry<K, V>> iterator() {
				return new BagMapIterator();
			}

			@Override
			public int size() {
				return PairBag.this.size;
			}
		};
	}


	private final void expandBag() {
		Object[] oldKeys = this.keys;
		Object[] oldValues = this.values;
		if(oldKeys.length != oldValues.length) {
			throw new AssertionError("key and value array lengths do not match");
		}
		// Expand array size 1.5x + 4, +4 instead of +1 to prevent small bags from constantly needed to resize
		this.keys = new Object[oldKeys.length + (oldKeys.length >>> 1) + 4];
		this.values = new Object[oldValues.length + (oldValues.length >>> 1) + 4];
		System.arraycopy(oldKeys, 0, this.keys, 0, oldKeys.length);
		System.arraycopy(oldValues, 0, this.values, 0, oldValues.length);
	}


	@Override
	public String toString() {
		StringBuilder builder = ToStringUtil.toStringKeyValuePairs(this.keys, this.values, this.size, null);
		return builder.toString();
	}


	/** An Iterator for this class
	 * The iterator itself acts as the returned entry
	 */
	private class BagMapIterator implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
		private int expectedActions;
		private int currentIndex;

		public BagMapIterator() {
			expectedActions = PairBag.this.action;
		}

		@Override
		public K getKey() {
			checkIndex();
			@SuppressWarnings("unchecked")
			K key = (K)keys[currentIndex-1];
			return key;
		}

		@Override
		public V getValue() {
			checkIndex();
			@SuppressWarnings("unchecked")
			V value = (V)values[currentIndex-1];
			return value;
		}

		@Override
		public V setValue(V value) { throw new UnsupportedOperationException("Immutable value"); }

		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		@Override
		public Map.Entry<K, V> next() {
			checkModification();
			currentIndex++;
			return this;
		}

		@Override
		public void remove() {
			checkModification();
			expectedActions++; // Since remove() increments the action count, keep the two in sync
			PairBag.this.remove(currentIndex);
			// Rewind so that next() call returns same index, which now contains new item since removed item indices
			// are replaced with items from the end of the group
			currentIndex--;
		}

		private void checkModification() {
			if(expectedActions != PairBag.this.action) {
				throw new ConcurrentModificationException("Bag was modified while iterating");
			}
		}

		private void checkIndex() {
			if(currentIndex < 1) { throw new IllegalStateException("Iterator not started, call hasNext() or next()"); }
			if(currentIndex > size) { throw new NoSuchElementException(); }
		}
	}


	private class BagMapKeyView extends AbstractList<K> implements ListReadOnly<K> {

		@Override
		public int size() {
			return PairBag.this.size;
		}


		@Override
		public K get(int index) {
			return PairBag.this.getKey(index);
		}


		@Override
		public int indexOf(Object o) {
			return PairBag.this.getKeyObjectIndex(o);
		}


		@Override
		public boolean isEmpty() {
			return PairBag.this.isEmpty();
		}


		@Override
		public boolean contains(Object o) {
			return PairBag.this.containsKeyObject(o);
		}


		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}


		@Override
		public Object[] toArray() {
			return Arrays.copyOf(PairBag.this.keys, PairBag.this.size);
		}


		@Override
		@SuppressWarnings("unchecked")
		public <T> T[] toArray(T[] a) {
			if(a.length < PairBag.this.size) {
				return (T[]) Arrays.copyOf(PairBag.this.keys, PairBag.this.size, a.getClass());
			}
			System.arraycopy(PairBag.this.keys, 0, a, 0, PairBag.this.size);
			if(a.length > PairBag.this.size) {
				a[PairBag.this.size] = null;
			}
			return a;
		}

	}


	private class BagMapValueView extends AbstractList<V> implements ListReadOnly<V> {

		@Override
		public int size() {
			Arrays.asList(new String[0]);
			return PairBag.this.size;
		}


		@Override
		public V get(int index) {
			return PairBag.this.getValue(index);
		}


		@Override
		public int indexOf(Object o) {
			return PairBag.this.getValueObjectIndex(o);
		}


		@Override
		public boolean isEmpty() {
			return PairBag.this.isEmpty();
		}


		@Override
		public boolean contains(Object o) {
			return PairBag.this.containsValueObject(o);
		}


		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}


		@Override
		public Object[] toArray() {
			return Arrays.copyOf(PairBag.this.keys, PairBag.this.size);
		}


		@Override
		@SuppressWarnings("unchecked")
		public <T> T[] toArray(T[] a) {
			if(a.length < PairBag.this.size) {
				return (T[]) Arrays.copyOf(PairBag.this.keys, PairBag.this.size, a.getClass());
			}
			System.arraycopy(PairBag.this.keys, 0, a, 0, PairBag.this.size);
			if(a.length > PairBag.this.size) {
				a[PairBag.this.size] = null;
			}
			return a;
		}

	}


	public class BagMapKeyValueView implements MapIndexed<K, V> {
		@Override
		public int size() {
			return PairBag.this.size;
		}

		@Override
		public K getKey(int index) {
			return PairBag.this.getKey(index);
		}

		@Override
		public V getValue(int index) {
			return PairBag.this.getValue(index);
		}
	}

}
