package primitiveCollections;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.function.BiConsumer;

/** A primitive float implementation of a {@link Map}.<br>
 * Duplicate elements are not allowed.<br>
 * This class' purpose is to provide minor performance and memory usage improvements over an
 * {@code Map<Float, T>} by storing the Floats as type <code>float</code>
 * without converting them to Float.
 * @param <T> the data type of the values stored in this map
 * @see FloatArrayList
 * @see FloatBag
 *
 * @author TeamworkGuy2
 * @since 2015-1-18
 */
@javax.annotation.Generated("StringTemplate")
public class FloatMapSorted<T> implements FloatMapReadOnly<T>, RandomAccess {
	protected volatile int mod;
	protected float[] keys;
	protected Object[] values;
	protected int size;


	/** Create an empty map
	 */
	public FloatMapSorted() {
		this(16);
	}


	/** Create an empty map capable of holding specified number of items without needing to expand
	 * @param capacity the initial size of the map of items
	 */
	public FloatMapSorted(int capacity) {
		this.keys = new float[capacity];
		this.values = new Object[capacity];
		this.size = 0;
	}


	@Override
	public Object clone() {
		return this.copy();
	}


	@Override
	public FloatMapSorted<T> copy() {
		FloatMapSorted<T> newMap = new FloatMapSorted<>(size);
		newMap.size = size;
		System.arraycopy(keys, 0, newMap.keys, 0, size);
		System.arraycopy(values, 0, newMap.values, 0, size);
		return newMap;
	}


	@Override
	public T get(float key) {
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
	@Override
	public float getKey(int index) {
		if(index < 0 || index >= size) { throw new ArrayIndexOutOfBoundsException(index); }
		return keys[index];
	}


	/** Get the integer at the specified index
	 * @param index the index between zero and {@link #size()}-1 inclusive to retrieve
	 * @return the integer found at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range [0, {@link #size()}-1]
	 */
	@Override
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
	@Override
	public int indexOf(float key) {
		int index = Arrays.binarySearch(keys, 0, size, key);
		return index > -1 ? index : -1;
	}


	/** Get the index of the specified value in this map if it exists
	 * @param value the value to search for in this map
	 * @return the index of the key if it is contained in this list, else return -1
	 */
	@Override
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
	@Override
	public boolean contains(float key) {
		int index = Arrays.binarySearch(keys, 0, size, key);
		return (index > -1 && index < size);
	}


	/** Check if the specified values is contained in this list of integers
	 * @param value the value to check for in this list
	 * @return true if the key was found in the list, false otherwise
	 */
	@Override
	public boolean containsValue(T value) {
		return indexOfValue(value) > -1;
	}


	/** Remove a key-value entry from this map
	 * @param key the key to remove from this map
	 * @return the value removed, or null if the key was not found or the found value was null.
	 * To determine the difference, call {@link #contains(float)}
	 */
	public T remove(float key) {
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
	public float removeIndex(int index) {
		if(index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		// Shift all elements above the remove element to fill the empty index
		// Get the item to remove
		float key = keys[index];
		@SuppressWarnings({ "unchecked", "unused" })
		T value = (T)values[index];
		mod++;
		// Copy down the remaining upper half of the array if the item removed was not the last item in the array
		if(index < size - 1) {
			System.arraycopy(keys, index + 1, keys, index, size - index - 1);
			System.arraycopy(values, index + 1, values, index, size - index - 1);
		}
		keys[size - 1] = 0f;
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
	 * @see #put(float, Object, boolean)
	 */
	public T put(float key, T value) {
		return put(key, value, false);
	}


	/**
	 * @see #put(float, Object, boolean)
	 */
	public boolean putIfAbsent(float key, T value) {
		return put(key, value, true) == null;
	}


	/** Add the specified key-value pair to this map
	 * @param key the key to add to this map
	 * @param value the value to add to this map
	 * @param onlyIfAbsent true to only put the value if it is absent, false to overwrite existing values
	 * @return the previous value associated with the {@code key}, or null if the key did not exist in the map.
	 * To differentiate between a key with a null value and a new key, use {@link #contains(float)}
	 */
	public T put(float key, T value, boolean onlyIfAbsent) {
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
	public final void putAll(Map.Entry<Float, ? extends T>... entries) {
		if(size + entries.length > keys.length) {
			expandMap(size + entries.length);
		}

		for(Map.Entry<Float, ? extends T> entry : entries) {
			put(entry.getKey(), entry.getValue());
		}
	}


	public final void putAll(Map<Float, ? extends T> map) {
		for(Map.Entry<Float, ? extends T> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}


	public final void putAll(Collection<? extends Float> keys, Collection<? extends T> values) {
		putAll(keys, values, false, true);
	}


	public final void putAll(Collection<? extends Float> keys, Collection<? extends T> values,
			boolean allowDuplicateKeys, boolean requireEqualSize) {
		Iterator<? extends Float> keyIter = keys.iterator();
		Iterator<? extends T> valueIter = values.iterator();

		if(allowDuplicateKeys) {
			while(keyIter.hasNext() && valueIter.hasNext()) {
				Float key = keyIter.next();
				T value = valueIter.next();
				put(key, value);
			}
		}
		else {
			while(keyIter.hasNext() && valueIter.hasNext()) {
				Float key = keyIter.next();
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
			keys[i] = 0f;
			values[i] = null;
		}
		// Set the size to zero
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


	@Override
	public void forEach(BiConsumer<? super Float, ? super T> action) {
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
	public float[] toArray() {
		return toArray(new float[this.size], 0);
	}


	@Override
	public float[] toArray(float[] dst, int dstOffset) {
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
		float[] oldKeys = this.keys;
		Object[] oldValues = this.values;
		// Expand size by at least 1.5x + 1 to prevent small maps from constantly resizing
		int newSize = Math.max(totalSize, oldKeys.length + (oldKeys.length >>> 1) + 1);
		this.keys = new float[newSize];
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
	public static final <T> FloatMapSorted<T> of(Map.Entry<Float, T>... values) {
		FloatMapSorted<T> inst = new FloatMapSorted<>();
		inst.putAll(values);
		return inst;
	}

}
