package primitiveCollections;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.function.BiConsumer;

/** A primitive char implementation of a {@link Map}.<br>
 * Duplicate elements are not allowed.<br>
 * This class' purpose is to provide minor performance and memory usage improvements over an
 * {@code Map<Character, T>} by storing the Characters as type <code>char</code>
 * without converting them to Character.
 * @param <T> the data type of the values stored in this map
 * @see CharArrayList
 * @see CharBag
 *
 * @author TeamworkGuy2
 * @since 2015-1-18
 */
@javax.annotation.Generated("StringTemplate")
public class CharMapSorted<T> implements RandomAccess {
	protected volatile int mod;
	protected char[] keys;
	protected Object[] values;
	protected int size;


	/** Create an empty map
	 */
	public CharMapSorted() {
		this(16);
	}


	/** Create an empty map capable of holding specified number of items without needing to expand
	 * @param capacity the initial size of the map of items
	 */
	public CharMapSorted(int capacity) {
		this.keys = new char[capacity];
		this.values = new Object[capacity];
		this.size = 0;
	}


	@Override
	public Object clone() {
		return this.copy();
	}


	public CharMapSorted<T> copy() {
		CharMapSorted<T> newMap = new CharMapSorted<>(size);
		newMap.size = size;
		System.arraycopy(keys, 0, newMap.keys, 0, size);
		System.arraycopy(values, 0, newMap.values, 0, size);
		return newMap;
	}


	public T get(char key) {
		int index = Arrays.binarySearch(keys, 0, size, key);
		if(index > -1 && index < size) {
			@SuppressWarnings("unchecked")
			T value = (T)values[index];
			return value;
		}
		return null;
	}


	/** Get the key at the specified index
	 * @param index the index between zero and {@link #size()}-1 inclusive to retrieve
	 * @return the key found at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range [0, {@link #size()}-1]
	 */
	public char getKey(int index) {
		if(index < 0 || index >= size) { throw new ArrayIndexOutOfBoundsException(index); }
		return keys[index];
	}


	/** Get the integer at the specified index
	 * @param index the index between zero and {@link #size()}-1 inclusive to retrieve
	 * @return the integer found at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range [0, {@link #size()}-1]
	 */
	public T getValue(int index) {
		if(index < 0 || index >= size) { throw new ArrayIndexOutOfBoundsException(index); }
		@SuppressWarnings("unchecked")
		T value = (T)values[index];
		return value;
	}


	/** Get the index of the specified key in this sorted list if it exists
	 * @param key the key to search for in this list
	 * @return the index of the key if it is contained in this list, else return -1
	 */
	public int indexOf(char key) {
		int index = Arrays.binarySearch(keys, 0, size, key);
		return index > -1 ? index : -1;
	}


	/** Get the index of the specified value in this map if it exists
	 * @param value the value to search for in this map
	 * @return the index of the key if it is contained in this list, else return -1
	 */
	public int indexOfValue(T value) {
		if(value != null) {
			for(int i = 0; i < size; i++) {
				if(value.equals(values[i])) {
					return i;
				}
			}
		}
		else {
			for(int i = 0; i < size; i++) {
				if(values[i] == null) {
					return i;
				}
			}
		}
		return -1;
	}


	/** Check if the specified values is contained in this list of integers
	 * @param key the key to check for in this list
	 * @return true if the key was found in the list, false otherwise
	 */
	public boolean contains(char key) {
		int index = Arrays.binarySearch(keys, 0, size, key);
		return (index > -1 && index < size);
	}


	/** Check if the specified values is contained in this list of integers
	 * @param value the value to check for in this list
	 * @return true if the key was found in the list, false otherwise
	 */
	public boolean containsValue(T value) {
		return indexOfValue(value) > -1;
	}


	/** Remove a key-value entry from this map
	 * @param key the key to remove from this map
	 * @return the value removed, or null if the key was not found or the found value was null.
	 * To determine the difference, call {@link #contains(char)}
	 */
	public T remove(char key) {
		int index = indexOf(key);
		if(index > -1 && index < size) {
		@SuppressWarnings("unchecked")
			T value = (T)values[index];
			removeIndex(index);
			return value;
		}
		return null;
	}


	/** Remove the integer at the specified index
	 * @param index the index between zero and {@link #size()}-1 inclusive to remove
	 * @return the integer found at the specified index
	 */
	public char removeIndex(int index) {
		if(index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		// Shift all elements above the remove element to fill the empty index
		// Get the item to remove
		char key = keys[index];
		@SuppressWarnings({ "unchecked", "unused" })
		T value = (T)values[index];
		mod++;
		// Copy down the remaining upper half of the array if the item removed was not the last item in the array
		if(index < size - 1) {
			System.arraycopy(keys, index + 1, keys, index, size - index - 1);
			System.arraycopy(values, index + 1, values, index, size - index - 1);
		}
		keys[size - 1] = (char)0;
		values[size - 1] = null;
		// Decrease the size because we removed one item
		size--;
		return key;
	}


	/** Remove the specified value from this group
	 * @param value the value to remove
	 * @return true if the value was found and removed successfully, false otherwise
	 */
	public boolean removeValue(T value) {
		// Search for the item to remove
		int index = indexOfValue(value);
		if(index > -1 && index < size) {
			removeIndex(index);
			return true;
		}
		return false;
	}


	/**
	 * @see #put(char, Object, boolean)
	 */
	public T put(char key, T value) {
		return put(key, value, false);
	}


	/**
	 * @see #put(char, Object, boolean)
	 */
	public boolean putIfAbsent(char key, T value) {
		return put(key, value, true) == null;
	}


	/** Add the specified key-value pair to this map
	 * @param key the key to add to this map
	 * @param value the value to add to this map
	 * @param onlyIfAbsent true to only put the value if it is absent, false to overwrite existing values
	 * @return the previous value associated with the {@code key}, or null if the key did not exist in the map.
	 * To differentiate between a key with a null value and a new key, use {@link #contains(char)}
	 */
	public T put(char key, T value, boolean onlyIfAbsent) {
		// If the list is to small, expand it
		if(size + 1 > keys.length) {
			expandMap();
		}

		// Add the new item
		int index = Arrays.binarySearch(keys, 0, size, key);
		// key already exists in map, overwrite it
		if(index > -1 && index < size) {
			@SuppressWarnings("unchecked")
			T oldValue = (T)values[index];
			if(onlyIfAbsent) {
				return oldValue;
			}
			mod++;
			keys[index] = key; // keys are already equal via previous Arrays.binarySearch(), allow new key
			values[index] = value;
			return oldValue;
		}

		mod++;
		if(index < 0) {
			index = -index - 1;
			System.arraycopy(keys, index, keys, index + 1, size - index);
			System.arraycopy(values, index, values, index + 1, size - index);
		}
		if(index > size) {
			index = size;
		}
		keys[index] = key;
		values[index] = value;
		size++;
		return null;
	}


	@SafeVarargs
	public final void putAll(Map.Entry<Character, ? extends T>... entries) {
		if(size + entries.length > keys.length) {
			expandMap(size + entries.length);
		}

		for(Map.Entry<Character, ? extends T> entry : entries) {
			put(entry.getKey(), entry.getValue());
		}
	}


	public final void putAll(Map<Character, ? extends T> map) {
		for(Map.Entry<Character, ? extends T> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}


	public final void putAll(Collection<? extends Character> keys, Collection<? extends T> values) {
		putAll(keys, values, false, true);
	}


	public final void putAll(Collection<? extends Character> keys, Collection<? extends T> values,
			boolean allowDuplicateKeys, boolean requireEqualSize) {
		Iterator<? extends Character> keyIter = keys.iterator();
		Iterator<? extends T> valueIter = values.iterator();

		if(allowDuplicateKeys) {
			while(keyIter.hasNext() && valueIter.hasNext()) {
				Character key = keyIter.next();
				T value = valueIter.next();
				put(key, value);
			}
		}
		else {
			while(keyIter.hasNext() && valueIter.hasNext()) {
				Character key = keyIter.next();
				T value = valueIter.next();
				if(!putIfAbsent(key, value)) {
					throw new IllegalArgumentException("duplicate key when adding collection to map");
				}
			}
		}

		if(requireEqualSize && (keyIter.hasNext() || valueIter.hasNext())) {
			throw new IllegalStateException("putAll(Collection, Collection) lengths were not equal");
		}
	}


	/** Clear the group of elements
	 */
	public void clear() {
		mod++;
		// Clear list to null
		for(int i = 0; i < size; i++) {
			keys[i] = (char)0;
			values[i] = null;
		}
		// Set the size to zero
		size = 0;
	}


	/** Get the current size of this group of elements
	 * @return the size of this group of elements
	 */
	public int size() {
		return size;
	}


	/** Is this group of elements empty
	 * @return true if this group of elements is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}


	public void forEach(BiConsumer<? super Character, ? super T> action) {
		int cachedMod = this.mod;
		for(int i = 0; i < this.size; i++) {
			@SuppressWarnings("unchecked")
			T val = (T) values[i];
			action.accept(keys[i], val);
		}
		if(cachedMod != this.mod) {
			throw new ConcurrentModificationException();
		}
	}


	/*@Override
	public char[] toArray() {
		return toArray(new char[this.size], 0);
	}


	@Override
	public char[] toArray(char[] dst, int dstOffset) {
		System.arraycopy(this.data, 0, dst, dstOffset, this.size);
		return dst;
	}


	@Override
	public  iterator() {
		return new (new (this));
	}


	public  iteratorPrimitive() {
		return new (this);
	}*/


	private final void expandMap() {
		// Increase the size by 1.5x + 4, +4 in case the size is 0 or 1,
		// +4 rather than +1 to prevent constantly expanding a small list
		int newSize = keys.length + (keys.length >>> 1) + 1;
		expandMap(newSize);
	}


	private final void expandMap(int totalSize) {
		char[] oldKeys = this.keys;
		Object[] oldValues = this.values;
		// Expand size by at least 1.5x + 1 to prevent small maps from constantly resizing
		int newSize = Math.max(totalSize, oldKeys.length + (oldKeys.length >>> 1) + 1);
		this.keys = new char[newSize];
		this.values = new Object[newSize];
		System.arraycopy(oldKeys, 0, this.keys, 0, size);
		System.arraycopy(oldValues, 0, this.values, 0, size);
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(64);
		builder.append('[');
		if(size > 0) {
			int sizeTemp = size - 1;
			for(int i = 0; i < sizeTemp; i++) {
				builder.append(keys[i]);
				builder.append('=');
				builder.append(values[i]);
				builder.append(", ");
			}
			builder.append(keys[sizeTemp]);
			builder.append('=');
			builder.append(values[sizeTemp]);
		}
		builder.append(']');

		return builder.toString();
	}


	@SafeVarargs
	public static final <T> CharMapSorted<T> of(Map.Entry<Character, T>... values) {
		CharMapSorted<T> inst = new CharMapSorted<>();
		inst.putAll(values);
		return inst;
	}

}