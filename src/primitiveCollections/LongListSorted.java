package primitiveCollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.RandomAccess;

/** A primitive long implementation of an {@link ArrayList}.<br>
 * This differs from {@link LongArrayList} by maintaining the natural
 * sort order of the elements in this collection.  Which is why some operations like
 * {@code add(int index, long value)} are not available as they would break the sorted order of this collection.
 * Duplicate elements are allowed.<br>
 * The {@link #get(int) get()} and {@link #add(long) add()} operations are O(1).
 * The {@link #remove(int) remove()} and {@link #removeValue(long) remove(T)} operations are approximately O(n).
 * The {@link #contains(long) contains()} method provides O(log2 n) performance.<br/> 
 * This class' purpose is to provide minor performance and memory usage improvements over an
 * {@code ArrayList<Long>} by storing the Longs as type <code>long</code>
 * without converting them to Long.
 * @see LongArrayList
 * @see LongBag
 *
 * @author TeamworkGuy2
 * @since 2014-4-17
 */
@javax.annotation.Generated("StringTemplate")
public class LongListSorted implements LongList, RandomAccess, Iterable<Long> {
	protected int mod;
	protected long[] data;
	protected int size;


	/** Create a sorted group of items with a default size of 16
	 */
	public LongListSorted() {
		this(16);
	}


	/** Create an unsorted group of items with the specified size as the starting size
	 * @param capacity the initial size of the group of items
	 */
	public LongListSorted(int capacity) {
		this.data = new long[capacity];
		this.size = 0;
	}


	@Override
	public LongListSorted copy() {
		LongListSorted newList = new LongListSorted(data.length);
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
	public long get(int index) {
		if(index < 0 || index >= size) { throw new ArrayIndexOutOfBoundsException(index); }
		return data[index];
	}


	/** Get the index of the specified value in this sorted list if it exists
	 * @param value the value to search for in this list
	 * @return the index of the value if it ins contained in this list, else return -1
	 */
	@Override
	public int indexOf(long value) {
		int index = Arrays.binarySearch(data, 0, size, value);
		return index > -1 ? index : -1;
	}


	/** Check if the specified values is contained in this list of integers
	 * @param value the value to check for in this list
	 * @return true if the value was found in the list, false otherwise
	 */
	@Override
	public boolean contains(long value) {
		// Search for the item to remove
		int index = Arrays.binarySearch(data, 0, size, value);
		return (index > -1 && index < size);
	}


	/** Remove the integer at the specified index
	 * @param index the index between zero and {@link #size()}-1 inclusive to remove
	 * @return the integer found at the specified index
	 */
	@Override
	public long remove(int index) {
		if(index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		// Shift all elements above the remove element to fill the empty index
		// Get the item to remove
		long item = data[index];
		// Copy down the remaining upper half of the array if the item removed was not the last item in the array
		if(index < size - 1) {
			System.arraycopy(data, index + 1, data, index, size - index - 1);
		}
		// Decrease the size because we removed one item
		size--;
		mod++;
		return item;
	}


	/** Remove the specified value from this group
	 * @param item the value to remove
	 * @return true if the value was found and removed successfully, false otherwise
	 */
	@Override
	public boolean removeValue(long value) {
		// Search for the item to remove
		int index = Arrays.binarySearch(data, 0, size, value);
		if(index > -1 && index < size) {
			System.arraycopy(data, index + 1, data, index, size - index - 1);
			size--;
			mod++;
			return true;
		}
		return false;
	}


	/** Add the specified item to this group of elements
	 * @param item the item to add to this group of elements
	 */
	@Override
	public boolean add(long item) {
		// If the list is to small, expand it
		if(size == data.length) {
			expandList();
		}
		// Add the new item
		int index = Arrays.binarySearch(data, 0, size, item);
		if(index < 0) { index = -index - 1; }
		if(index > size) { index = size; }
		System.arraycopy(data, index, data, index + 1, size - index);
		data[index] = item;
		size++;
		mod++;
		return true;
	}


	public boolean addAll(long[] items) {
		return addAll(items, 0, items.length);
	}


	public boolean addAll(long[] items, int off, int len) {
		boolean res = true;
		for(int i = off, size = off + len; i < size; i++) {
			res &= add(items[i]);
		}
		mod++;
		return res;
	}


	/** Clear the group of elements
	 */
	@Override
	public void clear() {
		// Clear list to null
		for(int i = 0; i < size; i++) {
			data[i] = 0L;
		}
		// Set the size to zero
		size = 0;
		mod++;
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
	public long[] toArray() {
		return toArray(new long[this.size], 0);
	}


	@Override
	public long[] toArray(long[] dst, int dstOffset) {
		System.arraycopy(this.data, 0, dst, dstOffset, this.size);
		return dst;
	}


	@Override
	public LongIteratorWrapper iterator() {
		return new LongIteratorWrapper(new LongListSortedIterator(this));
	}


	public LongListSortedIterator iteratorPrimitive() {
		return new LongListSortedIterator(this);
	}


	private final void expandList() {
		long[] oldData = this.data;
		// Increase the size by 1.5x + 4, +4 in case the size is 0 or 1,
		// +4 rather than +1 to prevent constantly expanding a small list
		this.data = new long[oldData.length + (oldData.length >>> 1) + 1];
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


	public long sum() {
		return sum(this);
	}


	public double average() {
		return average(this);
	}


	public long max() {
		return max(this);
	}


	public long min() {
		return min(this);
	}


	@SafeVarargs
	public static final LongListSorted of(long... values) {
		LongListSorted inst = new LongListSorted();
		inst.addAll(values);
		return inst;
	}


	public static final long sum(LongListSorted list) {
		long[] values = list.data;
		long sum = 0;
		for(int i = 0, size = list.size; i < size; i++) {
			sum += values[i];
		}
		return sum;
	}


	public static final float average(LongListSorted list) {
		return (float)sum(list) / list.size;
	}


	public static final long max(LongListSorted list) {
		if(list.size > 0) {
			return list.data[list.size() - 1];
		}
		return 0L;
	}


	public static final long min(LongListSorted list) {
		if(list.size > 0) {
			return list.data[0];
		}
		return 0L;
	}

}
