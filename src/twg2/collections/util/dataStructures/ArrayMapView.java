package twg2.collections.util.dataStructures;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/** An {@link Map} backed by key and values arrays.<br>
 * {@link ArrayMapViewHandle} provides a way to manage an {@code ArrayMapView} and replace the backing
 * array without creating a new {@code ArrayMapView}.
 * @param <K> type of keys in this array map view
 * @param <V> type of values in this array map view
 * @see ArrayMapViewHandle
 * @author TeamworkGuy2
 * @since 2014-11-29
 */
public final class ArrayMapView<K, V> implements PairCollectionImmutable<K, V>, Iterable<Map.Entry<K, V>> {
	private Object[] keys;
	private Object[] values;
	private int keysOff;
	private int valuesOff;
	private int len;
	private volatile int mod;
	private final boolean allowSet;
	private ArrayViewHandle<K> keysView;
	private ArrayViewHandle<V> valuesView;


	/** Create an empty array view
	 */
	public ArrayMapView() {
		this(null, 0, null, 0, 0);
	}


	/** Create an empty array view
	 * @param allowSet true to allow {@link #set(int, java.util.Map.Entry) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public ArrayMapView(boolean allowSet) {
		this(null, 0, null, 0, 0, false);
	}


	/** Create an array view of an entire array
	 * @param keys the array of keys to create a view of
	 * @param values the array of values to create a view of
	 */
	public ArrayMapView(K[] keys, V[] values) {
		this(keys, 0, values, 0, keys.length, false);
	}


	/** Create an array view of an entire array
	 * @see #ArrayMapView(Object[], int, Object[], int, int, boolean)
	 */
	public ArrayMapView(K[] keys, V[] values, boolean allowSet) {
		this(keys, 0, values, 0, keys.length, allowSet);
	}


	/** Create an array view of a sub-portion of an array
	 * @see #ArrayMapView(Object[], int, Object[], int, int, boolean)
	 */
	public ArrayMapView(K[] keys, int keysOffset, V[] values, int valuesOffset, int length) {
		this(keys, keysOffset, values, valuesOffset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param keys the array of keys to create a view of
	 * @param keysOffset the offset into {@code keys} of the array view's {@code 0th} key index
	 * @param values the array of values to create a view of
	 * @param valuesOffset the offset into {@code values} of the array view's {@code 0th} value index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow {@link #set(int, java.util.Map.Entry) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public ArrayMapView(K[] keys, int keysOffset, V[] values, int valuesOffset, int length, boolean allowSet) {
		if(keys == null || values == null || length > keys.length || length > values.length) {
			throw new IllegalArgumentException("keys length (" + (keys != null ? keys.length : "null") + ") is not equal to values length (" + (values != null ? values.length : "null") + ")");
		}

		this.allowSet = allowSet;
		this.keysView = new ArrayViewHandle<K>(keys, keysOffset, length);
		this.valuesView = new ArrayViewHandle<V>(values, valuesOffset, length);

		this.setArrayView(keys, keysOffset, values, valuesOffset, length);
	}


	// package-private
	/** This function should only be invoked from code in this package
	 */
	void setArrayView(K[] keys, int keysOffset, V[] values, int valuesOffset, int length) {
		this.mod++;
		this.keys = keys;
		this.keysOff= keysOffset;
		this.values = values;
		this.valuesOff = valuesOffset;
		this.len = length;
		this.keysView.setArrayView(keys, keysOffset, length);
		this.valuesView.setArrayView(values, valuesOffset, length);
	}


	@Override
	public int size() {
		return len;
	}


	@Override
	public boolean isEmpty() {
		return len == 0;
	}


	@Override
	public Iterator<Map.Entry<K, V>> iterator() {
		return listIterator(0);
	}


	public ListIterator<Map.Entry<K, V>> listIterator() {
		return listIterator(0);
	}


	public ListIterator<Map.Entry<K, V>> listIterator(int idx) {
		return new ListIterator<Map.Entry<K, V>>() {
			private int modCached = mod;
			private int index = idx - 1;
			private int size = len;


			@Override
			public boolean hasNext() {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				return index < size - 1;
			}


			@Override
			public Map.Entry<K, V> next() {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				if(index >= size - 1) {
					throw new IllegalStateException("already at end of list");
				}
				index++;
				@SuppressWarnings("unchecked")
				Map.Entry<K, V> entry = new AbstractMap.SimpleImmutableEntry<>((K)keys[keysOff + index], (V)values[valuesOff + index]);
				return entry;
			}


			@Override
			public boolean hasPrevious() {
				return index > 0;
			}


			@Override
			public Map.Entry<K, V> previous() {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				if(index <= 0) {
					throw new IllegalStateException("already at beginning of list");
				}
				index--;
				@SuppressWarnings("unchecked")
				Map.Entry<K, V> entry = new AbstractMap.SimpleImmutableEntry<>((K)keys[keysOff + index], (V)values[valuesOff + index]);
				return entry;
			}


			@Override
			public int nextIndex() {
				return index + 1;
			}


			@Override
			public int previousIndex() {
				return index - 1;
			}


			@Override
			public void remove() {
				throw new UnsupportedOperationException("cannot modified immutable list iterator");
			}


			@Override
			public void set(Map.Entry<K, V> e) {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				ArrayMapView.this.set(index, e);
				this.modCached = mod;
			}


			@Override
			public void add(Map.Entry<K, V> e) {
				throw new UnsupportedOperationException("cannot modified immutable list iterator");
			}
		};
	}


	@Override
	public String toString() {
		int modCached = mod;
		StringBuilder strB = new StringBuilder();
		strB.append('[');
		if(len > 0) {
			int count = len - 1;
			int keyOff = keysOff;
			int valOff = valuesOff;
			for(int i = 0; i < count; i++) {
				strB.append(keys[keyOff + i]);
				strB.append("=");
				strB.append(values[valOff + i]);
				strB.append(", ");
			}
			strB.append(keys[keyOff + count]);
			strB.append("=");
			strB.append(values[valOff + count]);
		}
		strB.append(']');

		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return strB.toString();
	}


	@Override
	public boolean containsKey(Object key) {
		boolean res = indexOf(key) > -1;
		return res;
	}


	@Override
	public boolean containsValue(Object value) {
		@SuppressWarnings("unchecked")
		boolean res = indexOfValue((V)value) > -1;
		return res;
	}


	@Override
	public V get(Object key) {
		int index = indexOf(key);
		@SuppressWarnings("unchecked")
		V val = (V)values[valuesOff + index];
		return val;
	}


	@Override
	public Collection<V> values() {
		return valuesView.getArrayView();
	}


	@Override
	public K get(int index) {
		@SuppressWarnings("unchecked")
		K key = (K)keys[keysOff + index];
		return key;
	}


	@Override
	public List<K> keyList() {
		return keysView.getArrayView();
	}


	@Override
	public List<V> valueList() {
		return valuesView.getArrayView();
	}


	public Map.Entry<K, V> getEntry(int index) {
		@SuppressWarnings("unchecked")
		Map.Entry<K, V> entry = new AbstractMap.SimpleImmutableEntry<>((K)keys[keysOff + index], (V)values[valuesOff + index]);
		return entry;
	}


	public V getValue(int index) {
		@SuppressWarnings("unchecked")
		V val = (V)values[valuesOff + index];
		return val;
	}


	public V set(int index, Map.Entry<K, V> element) {
		if(!allowSet) {
			throw new UnsupportedOperationException("cannot modified immutable view");
		}
		if(index < 0 || index >= len) {
			throw new IndexOutOfBoundsException(index + " of view size " + len);
		}
		keys[keysOff + index] = element.getKey();
		values[valuesOff + index] = element.getValue();
		mod++;
		return null;
	}


	public boolean containsAllKeys(Collection<? extends K> k) {
		int modCached = mod;
		for(K key : k) {
			if(!containsKey(key)) {
				return false;
			}
		}
		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return true;
	}


	public int indexOf(Object o) {
		int modCached = mod;
		for(int i = keysOff, size = keysOff + len; i < size; i++) {
			if(o != null ? o.equals(keys[i]) : keys[i] == null) {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				return i;
			}
		}
		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return -1;
	}


	public int lastIndexOf(Object o) {
		int modCached = mod;
		for(int i = keysOff + len - 1; i >= keysOff; i--) {
			if(o != null ? o.equals(keys[i]) : keys[i] == null) {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				return i;
			}
		}
		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return -1;
	}



	public int indexOfValue(V o) {
		int modCached = mod;
		for(int i = valuesOff, size = valuesOff + len; i < size; i++) {
			if(o != null ? o.equals(values[i]) : values[i] == null) {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				return i;
			}
		}
		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return -1;
	}


	public int lastIndexOfValue(V o) {
		int modCached = mod;
		for(int i = valuesOff + len - 1; i >= valuesOff; i--) {
			if(o != null ? o.equals(values[i]) : values[i] == null) {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				return i;
			}
		}
		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return -1;
	}


	public Map.Entry<K, V>[] toArray() {
		@SuppressWarnings("unchecked")
		Map.Entry<K, V>[] keyValAry = (Map.Entry<K, V>[])new Map.Entry[len];
		for(int i = 0, size = len; i < size; i++) {
			@SuppressWarnings("unchecked")
			Map.Entry<K, V> entry = new AbstractMap.SimpleImmutableEntry<>((K)keys[keysOff + i], (V)values[valuesOff + i]);
			keyValAry[i] = entry;
		}

		return Arrays.copyOfRange(keyValAry, 0, len);
	}


	public Map.Entry<K, V>[] toArray(Map.Entry<K, V>[] a) {
		@SuppressWarnings("unchecked")
		Map.Entry<K, V>[] keyValAry = (Map.Entry<K, V>[])new Map.Entry[len];
		for(int i = 0, size = len; i < size; i++) {
			@SuppressWarnings("unchecked")
			Map.Entry<K, V> entry = new AbstractMap.SimpleImmutableEntry<>((K)keys[keysOff + i], (V)values[valuesOff + i]);
			keyValAry[i] = entry;
		}

		if(a.length < len) {
			@SuppressWarnings("unchecked")
			Class<Map.Entry<K, V>[]> type = (Class<Map.Entry<K, V>[]>)a.getClass();
			return (Map.Entry<K, V>[])Arrays.copyOfRange(keyValAry, 0, len, type);
		}
		System.arraycopy(keyValAry, 0, a, 0, len);
		if(a.length > len) {
			a[len] = null;
		}
		return a;
	}

}
