package primitiveCollections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

/** A primitive double implementation of an {@link ArrayList}.<br/> 
 * The {@link #get(int) get()} and {@link #add(double) add()} operations are O(1).
 * The {@link #remove(int) remove()}, {@link #removeValue(double) remove()}, and
 * {@link #contains(double) contains()} operations are approximately O(n).<br>
 * This class' purpose is to provide minor performance and memory usage improvements over an
 * {@code ArrayList<Double>} by storing the Doubles as type <code>double</code>
 * without converting them to Double.
 * This simplifies comparison operations such as {@link #contains(double) contains()}
 * which can use {@code ==} instead of {@code .equals()}
 * @see DoubleBag
 * @see DoubleListSorted
 *
 * @author TeamworkGuy2
 * @since 2013-1-20
 */
@javax.annotation.Generated("StringTemplate")
public class DoubleArrayList implements DoubleList, RandomAccess, Iterable<Double> {
	protected volatile int mod;
	protected double[] data;
	protected int size;


	/** Create an unsorted group of items with a default size of 16
	 */
	public DoubleArrayList() {
		this(16);
	}


	/** Create an unsorted group of items with the specified size as the starting size
	 * @param capacity the initial size of the group of items
	 */
	public DoubleArrayList(int capacity) {
		this.data = new double[capacity];
		this.size = 0;
	}


	public DoubleArrayList(double[] values) {
		this(values, 0, values.length);
	}


	/** Create an unsorted group of items containing a copy of the specified values
	 * @param values the list of values to copy
	 * @param off the offset into {@code values} at which to start copying
	 * @param len the number of elements to copy from {@code values}
	 */
	public DoubleArrayList(double[] values, int off, int len) {
		if(values == null || len < 0 || off + len > values.length) {
			throw new IllegalArgumentException("offset (" + off + ") + length (" + len + ") must specify a valid range within"
					+ " values array (length: " + (values != null ? values.length : "null array") + ")");
		}
		this.data = new double[len];
		System.arraycopy(values, off, this.data, 0, len);
		this.size = len;
	}


	@Override
	public DoubleArrayList copy() {
		DoubleArrayList newList = new DoubleArrayList(data.length);
		newList.size = size;
		System.arraycopy(data, 0, newList.data, 0, size);
		return newList;
	}


	/** Get the double at the specified index
	 * @param index the index between {@code [0, }{@link #size()}{@code -1} to retrieve
	 * @return the double found at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range {@code [0, }{@link #size()}{@code -1]}
	 */
	@Override
	public double get(int index) {
		if(index < 0 || index >= size) { throw new ArrayIndexOutOfBoundsException(index); }
		return data[index];
	}


	/** Check if the specified values is contained in this list of Doubles
	 * @param value the value to check for in this list
	 * @return true if the value was found in the list, false otherwise
	 */
	@Override
	public boolean contains(double value) {
		return indexOf(value) > -1;
	}


	/** Find the first occurring index of the specified value in this list
	 * @param value the value to search for in this list
	 * @return an index between {@code [0, }{@link #size()}{@code -1]} if the value is
	 * found, or -1 if the value cannot be found
	 */
	@Override
	public int indexOf(double value) {
		// Search for the item to remove
		for(int i = 0; i < size; i++) {
			// If the item is found, return true
			if(value == data[i]) {
				return i;
			}
		}
		// Else if the item is not found, return false
		return -1;
	}


	/** Find the first occurring index of the specified value in this list,
	 * starting at the specified offset
	 * @param value the value to search for in this list
	 * @param fromIndex an index within the range {@code [0, }{@link #size()}{@code -1]}
	 * at which to start searching for the {@code value}, inclusive
	 * @return an index between {@code [0, }{@link #size()}{@code -1]} if the value is
	 * found, or -1 if the value cannot be found
	 */
	public int indexOf(double value, int fromIndex) {
		// Search for the item to remove
		for(int i = fromIndex; i < size; i++) {
			// If the item is found, return true
			if(value == data[i]) {
				return i;
			}
		}
		// Else if the item is not found, return false
		return -1;
	}


	/** Find the last occurring index of the specified value in this list
	 * @param value the value to search for in this list
	 * @return an index between {@code [0, }{@link #size()}{@code -1]} if the value is
	 * found, or -1 if the value cannot be found
	 */
	public int lastIndexOf(double value) {
		// Search for the item to remove
		for(int i = size - 1; i > -1; i--) {
			// If the item is found, return true
			if(value == data[i]) {
				return i;
			}
		}
		// Else if the item is not found, return false
		return -1;
	}


	/** Remove the double at the specified index
	 * @param index the index between {@code [0, }{@link #size()}{@code -1} to remove
	 * @return the double found at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range {@code [0, }{@link #size()}{@code -1]}
	 */
	@Override
	public double remove(int index) {
		if(index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		mod++;
		// Shift all elements above the remove element to fill the empty index
		// Get the item to remove
		double item = data[index];
		// Copy down the remaining upper half of the array if the item removed was not the last item in the array
		if(index < size - 1) {
			System.arraycopy(data, index + 1, data, index, size - index - 1);
		}
		// Decrease the size because we removed one item
		size--;
		return item;
	}


	/** Remove the specified value from this group
	 * @param item the value to remove
	 * @return true if the value was found and removed successfully, false otherwise
	 */
	@Override
	public boolean removeValue(double item) {
		// Search for the item to remove
		for(int i = 0; i < size; i++) {
			// If the item is found, remove it
			if(item == data[i]) {
				mod++;
				if(i < size - 1) {
					System.arraycopy(data, i + 1, data, i, size - i - 1);
				}
				size--;
				return true;
			}
		}
		return false;
	}


	/** Add the specified item to this group of elements
	 * @param item the item to add to this group of elements
	 */
	@Override
	public boolean add(double item) {
		mod++;
		// If the list is too small, expand it
		if(size >= data.length) {
			expandList();
		}
		// Add the new item
		data[size] = item;
		size++;
		return true;
	}


	/** Add the specified item to this list of elements
	 * @param index the index at which to insert the value
	 * @param value the value to add to this list of elements
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range {@code [0, }{@link #size()}{@code ]}
	 */
	public boolean add(int index, double value) {
		if(index < 0 || index > size) { throw new ArrayIndexOutOfBoundsException(index); }
		mod++;
		// If the list is too small, expand it
		if(size >= data.length) {
			expandList();
		}
		System.arraycopy(data, index, data, index+1, size-index);
		// Add the new item
		data[index] = value;
		size++;
		return true;
	}


	public boolean addAll(double[] items) {
		return addAll(items, 0, items.length);
	}


	/** Add the specified sub array of items to this group of elements
	 * @param items the array of items
	 * @param off the offset into {@code off} at which to start adding items
	 * into this group of elements
	 * @param len the number of elements, starting at index {@code off}, to
	 * add to this group of elements from {@code items}
	 * @return true if the items are added successfully, false otherwise
	 */
	public boolean addAll(double[] items, int off, int len) {
		if(len < 0) {
			throw new IllegalArgumentException("number of elements to add must not be negative (" + len + ")");
		}
		mod++;
		// If the list is too small, expand it 
		// -1 because if data.length=2 and size=1, and len=1, we could fit an
		// element without expanding the list, but 1+1 >= 2 is true, so instead 1+1-1 >= 2
		// keeps the list from expanding unnecessarily
		if(size+len-1 >= data.length) {
			expandList(len);
		}
		// Add the new items
		for(int i = 0; i < len; i++) {
			data[size + i] = items[i];
		}
		size += len;
		return true;
	}


	/** Add the specified sub array of items to this group of elements
	 * @param items the array of items
	 * @return true if the items are added successfully, false otherwise
	 */
	public boolean addAll(Collection<? extends Double> items) {
		int len = items.size();
		// If the list is too small, expand it 
		// -1 because if data.length=2 and size=1, and len=1, we could fit an
		// element without expanding the list, but 1+1 >= 2 is true, so instead 1+1-1 >= 2
		// keeps the list from expanding unnecessarily
		if(size+len-1 >= data.length) {
			expandList(len);
		}
		mod++;
		// Add the new items
		if(items instanceof List && items instanceof RandomAccess) {
			List<? extends Double> itemsList = (List<? extends Double>)items;
			for(int i = 0; i < len; i++) {
				data[size + i] = itemsList.get(i);
			}
		}
		else {
			int i = 0;
			for(Double item : items) {
				data[size + i] = item;
				i++;
			}
		}
		size += len;
		return true;
	}


	/** Set the double at the specified index
	 * @param index the index to store the item in
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @param value the double to store at the specified index in this list.
	 * @return the previous double at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is not within the specified range
	 */
	public double set(int index, double value) {
		if(index < 0 || index >= size) { throw new ArrayIndexOutOfBoundsException(index); }
		mod++;
		double oldValue = data[index];
		data[index] = value;
		return oldValue;
	}


	/** Clear the group of elements
	 */
	@Override
	public void clear() {
		mod++;
		// Clear list to null
		for(int i = 0; i < size; i++) {
			data[i] = 0;
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


	@Override
	public double[] toArray() {
		return toArray(new double[this.size], 0);
	}


	@Override
	public double[] toArray(double[] dst, int dstOffset) {
		System.arraycopy(this.data, 0, dst, dstOffset, this.size);
		return dst;
	}


	@Override
	public DoubleIteratorWrapper iterator() {
		return new DoubleIteratorWrapper(new DoubleArrayListIterator(this));
	}


	public DoubleArrayListIterator iteratorPrimitive() {
		return new DoubleArrayListIterator(this);
	}


	private final void expandList() {
		// Increase the size by 1.5x + 4, +4 in case the size is 0 or 1,
		// +4 rather than +1 to prevent constantly expanding a small list
		expandList((this.data.length >>> 1));
	}


	private final void expandList(int increaseCount) {
		double[] oldData = this.data;
		// Increase the size by the number of new elements + 4
		// +4 rather than +1 to prevent constantly expanding a small list
		this.data = new double[oldData.length + increaseCount + 4];
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


	public double sum() {
		return sum(this);
	}


	public double average() {
		return average(this);
	}


	public double max() {
		return max(this);
	}


	public double min() {
		return min(this);
	}


	@SafeVarargs
	public static final DoubleArrayList of(double... values) {
		DoubleArrayList inst = new DoubleArrayList();
		inst.addAll(values);
		return inst;
	}


	public static final double sum(DoubleArrayList list) {
		double[] values = list.data;
		double sum = 0;
		for(int i = 0, size = list.size; i < size; i++) {
			sum += values[i];
		}
		return sum;
	}


	public static final double average(DoubleArrayList list) {
		return (double)sum(list) / list.size;
	}


	public static final double max(DoubleArrayList list) {
		int size = list.size;
		if(size < 1) {
			return 0;
		}
		double[] values = list.data;
		double max = Double.MIN_VALUE;
		double val = 0;
		for(int i = 0; i < size; i++) {
			if(max < (val = values[i])) {
				max = val;
			}
		}
		return max;
	}


	public static final double min(DoubleArrayList list) {
		int size = list.size;
		if(size < 1) {
			return 0;
		}
		double[] values = list.data;
		double min = Double.MAX_VALUE;
		double val = 0;
		for(int i = 0; i < size; i++) {
			if(min > (val = values[i])) {
				min = val;
			}
		}
		return min;
	}

}
