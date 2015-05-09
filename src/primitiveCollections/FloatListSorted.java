package primitiveCollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.RandomAccess;

/** A primitive float implementation of an {@link ArrayList}.<br>
 * This differs from {@link FloatArrayList} by maintaining the natural
 * sort order of the elements in this collection.  Which is why some operations like
 * {@code add(int index, float value)} are not available as they would break the sorted order of this collection.
 * Duplicate elements are allowed.<br>
 * The {@link #get(int) get()} and {@link #add(float) add()} operations are O(1).
 * The {@link #remove(int) remove()} and {@link #removeValue(float) remove(T)} operations are approximately O(n).
 * The {@link #contains(float) contains()} method provides O(log2 n) performance.<br/> 
 * This class' purpose is to provide minor performance and memory usage improvements over an
 * {@code ArrayList<Float>} by storing the Floats as type <code>float</code>
 * without converting them to Float.
 * @see FloatArrayList
 * @see FloatBag
 *
 * @author TeamworkGuy2
 * @since 2014-4-17
 */
@javax.annotation.Generated("StringTemplate")
public class FloatListSorted implements FloatList, RandomAccess, Iterable<Float> {
	protected volatile int mod;
	protected float[] data;
	protected int size;


	/** Create a sorted group of items with a default size of 16
	 */
	public FloatListSorted() {
		this(16);
	}


	/** Create an unsorted group of items with the specified size as the starting size
	 * @param capacity the initial size of the group of items
	 */
	public FloatListSorted(int capacity) {
		this.data = new float[capacity];
		this.size = 0;
	}


	@Override
	public FloatListSorted copy() {
		FloatListSorted newList = new FloatListSorted(data.length);
		newList.size = size;
		System.arraycopy(data, 0, newList.data, 0, size);
		return newList;
	}


	/** Get the integer at the specified index
	 * @param index the index between zero and {@link #size()}-1 inclusive to retrieve
	 * @return the integer found at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range [0, {@link #size()}-1]
	 */
	@Override
	public float get(int index) {
		if(index < 0 || index >= size) { throw new ArrayIndexOutOfBoundsException(index); }
		return data[index];
	}


	/** Get the index of the specified value in this sorted list if it exists
	 * @param value the value to search for in this list
	 * @return the index of the value if it ins contained in this list, else return -1
	 */
	@Override
	public int indexOf(float value) {
		int index = Arrays.binarySearch(data, 0, size, value);
		return index > -1 ? index : -1;
	}


	/** Check if the specified values is contained in this list of integers
	 * @param value the value to check for in this list
	 * @return true if the value was found in the list, false otherwise
	 */
	@Override
	public boolean contains(float value) {
		// Search for the item to remove
		int index = Arrays.binarySearch(data, 0, size, value);
		return (index > -1 && index < size);
	}


	/** Remove the integer at the specified index
	 * @param index the index between zero and {@link #size()}-1 inclusive to remove
	 * @return the integer found at the specified index
	 */
	@Override
	public float remove(int index) {
		if(index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		// Shift all elements above the remove element to fill the empty index
		// Get the item to remove
		float item = data[index];
		// Copy down the remaining upper half of the array if the item removed was not the last item in the array
		if(index < size - 1) {
			System.arraycopy(data, index + 1, data, index, size - index - 1);
		}
		mod++;
		// Decrease the size because we removed one item
		size--;
		return item;
	}


	/** Remove the specified value from this group
	 * @param value the value to remove
	 * @return true if the value was found and removed successfully, false otherwise
	 */
	@Override
	public boolean removeValue(float value) {
		// Search for the item to remove
		int index = Arrays.binarySearch(data, 0, size, value);
		if(index > -1 && index < size) {
			mod++;
			System.arraycopy(data, index + 1, data, index, size - index - 1);
			size--;
			return true;
		}
		return false;
	}


	/** Add the specified item to this group of elements
	 * @param item the item to add to this group of elements
	 */
	@Override
	public void add(float item) {
		// If the list is to small, expand it
		if(size == data.length) {
			expandList();
		}
		mod++;
		// Add the new item
		int index = Arrays.binarySearch(data, 0, size, item);
		if(index < 0) { index = -index - 1; }
		if(index > size) { index = size; }
		System.arraycopy(data, index, data, index + 1, size - index);
		data[index] = item;
		size++;
	}


	@Override
	@SafeVarargs
	public final void addAll(float... items) {
		addAll(items, 0, items.length);
	}


	@Override
	public void addAll(float[] items, int off, int len) {
		mod++;
		for(int i = off, size = off + len; i < size; i++) {
			add(items[i]);
		}
	}


	@Override
	public void addAll(FloatList coll) {
		mod++;
		int collSize = coll.size();
		// trick: copy the other (unsorted) collection's items into this collection's sorted data array above the existing item indices
		// this works because add() only expands this data array if it is too small (which we handle before hand) and never copies (and overwrites) data up by more than 1 index when inserting items
		if(size + collSize > data.length) {
			expandList(size + collSize);
		}
		int off = this.size;
		float[] itemsAry = this.data;

		coll.toArray(itemsAry, off);

		for(int i = off, size = off + collSize; i < size; i++) {
			add(itemsAry[i]);
		}
	}


	/** Clear the group of elements
	 */
	@Override
	public void clear() {
		mod++;
		// Clear list to null
		for(int i = 0; i < size; i++) {
			data[i] = 0f;
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
	public float[] toArray() {
		return toArray(new float[this.size], 0);
	}


	@Override
	public float[] toArray(float[] dst, int dstOffset) {
		System.arraycopy(this.data, 0, dst, dstOffset, this.size);
		return dst;
	}


	@Override
	public FloatIteratorWrapper iterator() {
		return new FloatIteratorWrapper(new FloatListSortedIterator(this));
	}


	public FloatListSortedIterator iteratorPrimitive() {
		return new FloatListSortedIterator(this);
	}


	private final void expandList() {
		// Expand the size by 1.5x
		int len = this.data.length;
		expandList(len + (len >>> 1));
	}


	private final void expandList(int totalSize) {
		float[] oldData = this.data;
		// Expand the size by the number of new elements + 4
		// +4 rather than +1 to prevent constantly expanding a small list
		this.data = new float[totalSize + 4];
		System.arraycopy(oldData, 0, this.data, 0, size);
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(64);
		builder.append('[');
		if(size > 0) {
			int sizeTemp = size - 1;
			for(int i = 0; i < sizeTemp; i++) {
				builder.append(data[i]);
				builder.append(", ");
			}
			builder.append(data[sizeTemp]);
		}
		builder.append(']');

		return builder.toString();
	}


	public float sum() {
		return sum(this);
	}


	public float average() {
		return average(this);
	}


	public float max() {
		return max(this);
	}


	public float min() {
		return min(this);
	}


	@SafeVarargs
	public static final FloatListSorted of(float... values) {
		FloatListSorted inst = new FloatListSorted();
		inst.addAll(values);
		return inst;
	}


	public static final float sum(FloatListSorted list) {
		float[] values = list.data;
		float sum = 0;
		for(int i = 0, size = list.size; i < size; i++) {
			sum += values[i];
		}
		return sum;
	}


	public static final float average(FloatListSorted list) {
		return (float)sum(list) / list.size;
	}


	public static final float max(FloatListSorted list) {
		if(list.size > 0) {
			return list.data[list.size() - 1];
		}
		return 0f;
	}


	public static final float min(FloatListSorted list) {
		if(list.size > 0) {
			return list.data[0];
		}
		return 0f;
	}

}
