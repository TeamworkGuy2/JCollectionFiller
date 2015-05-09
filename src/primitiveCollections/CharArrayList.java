package primitiveCollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

/** A primitive char implementation of an {@link ArrayList}.<br/> 
 * The {@link #get(int) get()} and {@link #add(char) add()} operations are O(1).
 * The {@link #remove(int) remove()}, {@link #removeValue(char) remove()}, and
 * {@link #contains(char) contains()} operations are approximately O(n).<br>
 * This class' purpose is to provide minor performance and memory usage improvements over an
 * {@code ArrayList<Character>} by storing the Characters as type <code>char</code>
 * without converting them to Character.
 * This simplifies comparison operations such as {@link #contains(char) contains()}
 * which can use {@code ==} instead of {@code .equals()}
 * @see CharBag
 * @see CharListSorted
 *
 * @author TeamworkGuy2
 * @since 2013-1-20
 */
@javax.annotation.Generated("StringTemplate")
public class CharArrayList implements CharList, RandomAccess, Iterable<Character> {
	protected volatile int mod;
	protected char[] data;
	protected int size;


	/** Create an unsorted group of items with a default size of 16
	 */
	public CharArrayList() {
		this(16);
	}


	/** Create an unsorted group of items with the specified size as the starting size
	 * @param capacity the initial size of the group of items
	 */
	public CharArrayList(int capacity) {
		this.data = new char[capacity];
		this.size = 0;
	}


	public CharArrayList(char[] values) {
		this(values, 0, values.length);
	}


	/** Create an unsorted group of items containing a copy of the specified values
	 * @param values the list of values to copy
	 * @param off the offset into {@code values} at which to start copying
	 * @param len the number of elements to copy from {@code values}
	 */
	public CharArrayList(char[] values, int off, int len) {
		if(values == null || len < 0 || off + len > values.length) {
			throw new IllegalArgumentException("offset (" + off + ") + length (" + len + ") must specify a valid range within"
					+ " values array (length: " + (values != null ? values.length : "null array") + ")");
		}
		this.data = new char[len];
		System.arraycopy(values, off, this.data, 0, len);
		this.size = len;
	}


	/** Create a new copy of a primitive list
	 * @param list the primitive list to copy
	 */
	public CharArrayList(CharList list) {
		this(list.size());

		final int size = list.size();
		for(int i = 0; i < size; i++) {
			this.data[i] = list.get(i);
		}
		this.size = size;
	}


	@Override
	public CharArrayList copy() {
		CharArrayList newList = new CharArrayList(data.length);
		newList.size = size;
		System.arraycopy(data, 0, newList.data, 0, size);
		return newList;
	}


	/** Get the char at the specified index
	 * @param index the index between {@code [0, }{@link #size()}{@code -1} to retrieve
	 * @return the char found at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range {@code [0, }{@link #size()}{@code -1]}
	 */
	@Override
	public char get(int index) {
		if(index < 0 || index >= size) { throw new ArrayIndexOutOfBoundsException(index); }
		return data[index];
	}


	/** Check if the specified values is contained in this list of Characters
	 * @param value the value to check for in this list
	 * @return true if the value was found in the list, false otherwise
	 */
	@Override
	public boolean contains(char value) {
		return indexOf(value) > -1;
	}


	/** Find the first occurring index of the specified value in this list
	 * @param value the value to search for in this list
	 * @return an index between {@code [0, }{@link #size()}{@code -1]} if the value is
	 * found, or -1 if the value cannot be found
	 */
	@Override
	public int indexOf(char value) {
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
	public int indexOf(char value, int fromIndex) {
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
	public int lastIndexOf(char value) {
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


	/** Remove the char at the specified index
	 * @param index the index between {@code [0, }{@link #size()}{@code -1} to remove
	 * @return the char found at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range {@code [0, }{@link #size()}{@code -1]}
	 */
	@Override
	public char remove(int index) {
		if(index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		mod++;
		// Shift all elements above the remove element to fill the empty index
		// Get the item to remove
		char item = data[index];
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
	public boolean removeValue(char item) {
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
	public void add(char item) {
		mod++;
		// If the list is too small, expand it
		if(size >= data.length) {
			expandList();
		}
		// Add the new item
		data[size] = item;
		size++;
	}


	/** Add the specified item to this list of elements
	 * @param index the index at which to insert the value
	 * @param value the value to add to this list of elements
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range {@code [0, }{@link #size()}{@code ]}
	 */
	public void add(int index, char value) {
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
	}


	@Override
	@SafeVarargs
	public final void addAll(char... items) {
		addAll(items, 0, items.length);
	}


	/** Add the specified sub array of items to this group of elements
	 * @param items the array of items
	 * @param off the offset into {@code off} at which to start adding items
	 * into this group of elements
	 * @param len the number of elements, starting at index {@code off}, to
	 * add to this group of elements from {@code items}
	 */
	@Override
	public void addAll(char[] items, int off, int len) {
		if(len < 0) {
			throw new IllegalArgumentException("number of elements to add must not be negative (" + len + ")");
		}
		mod++;
		// If the list is too small, expand it 
		// -1 because if data.length=2 and size=1, and len=1, we could fit an
		// element without expanding the list, but 1+1 >= 2 is true, so instead 1+1-1 >= 2
		// keeps the list from expanding unnecessarily
		if(size + len > data.length) {
			expandList(size + len);
		}
		// Add the new items
		for(int i = 0; i < len; i++) {
			data[size + i] = items[i];
		}
		size += len;
	}


	@Override
	public void addAll(CharList coll) {
		mod++;
		int collSize = coll.size();
		if(size + collSize > data.length) {
			expandList(size + collSize);
		} 
		coll.toArray(data, size);
		this.size += collSize;
	}


	/** Add the specified sub array of items to this group of elements
	 * @param items the array of items
	 * @return true if the items are added successfully, false otherwise
	 */
	public boolean addAll(Collection<? extends Character> items) {
		int len = items.size();
		// If the list is too small, expand it 
		// -1 because if data.length=2 and size=1, and len=1, we could fit an
		// element without expanding the list, but 1+1 >= 2 is true, so instead 1+1-1 >= 2
		// keeps the list from expanding unnecessarily
		if(size + len > data.length) {
			expandList(size + len);
		}
		mod++;
		// Add the new items
		if(items instanceof List && items instanceof RandomAccess) {
			List<? extends Character> itemsList = (List<? extends Character>)items;
			for(int i = 0; i < len; i++) {
				data[size + i] = itemsList.get(i);
			}
		}
		else {
			int i = 0;
			for(Character item : items) {
				data[size + i] = item;
				i++;
			}
		}
		size += len;
		return true;
	}


	/** Set the char at the specified index
	 * @param index the index to store the item in
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @param value the char to store at the specified index in this list.
	 * @return the previous char at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is not within the specified range
	 */
	public char set(int index, char value) {
		if(index < 0 || index >= size) { throw new ArrayIndexOutOfBoundsException(index); }
		mod++;
		char oldValue = data[index];
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
			data[i] = (char)0;
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
	public char[] toArray() {
		return toArray(new char[this.size], 0);
	}


	@Override
	public char[] toArray(char[] dst, int dstOffset) {
		System.arraycopy(this.data, 0, dst, dstOffset, this.size);
		return dst;
	}


	@Override
	public CharIteratorWrapper iterator() {
		return new CharIteratorWrapper(new CharArrayListIterator(this));
	}


	public CharArrayListIterator iteratorPrimitive() {
		return new CharArrayListIterator(this);
	}


	private final void expandList() {
		// Expand the size by 1.5x
		int len = this.data.length;
		expandList(len + (len >>> 1));
	}


	private final void expandList(int totalSize) {
		char[] oldData = this.data;
		// Expand the size by the number of new elements + 4
		// +4 rather than +1 to prevent constantly expanding a small list
		this.data = new char[totalSize + 4];
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


	public char sum() {
		return sum(this);
	}


	public float average() {
		return average(this);
	}


	public char max() {
		return max(this);
	}


	public char min() {
		return min(this);
	}


	@SafeVarargs
	public static final CharArrayList of(char... values) {
		CharArrayList inst = new CharArrayList();
		inst.addAll(values);
		return inst;
	}


	public static final CharArrayList newListOfDefaultValues(int length) {
		CharArrayList inst = new CharArrayList(length);
		inst.size = length;
		return inst;
	}


	public static final CharArrayList newListOfDefaultValues(int length, char defaultVal) {
		CharArrayList inst = new CharArrayList(length);
		inst.size = length;
		Arrays.fill(inst.data, 0, length, defaultVal);
		return inst;
	}


	public static final char sum(CharArrayList list) {
		char[] values = list.data;
		char sum = 0;
		for(int i = 0, size = list.size; i < size; i++) {
			sum += values[i];
		}
		return sum;
	}


	public static final float average(CharArrayList list) {
		return (float)sum(list) / list.size;
	}


	public static final char max(CharArrayList list) {
		int size = list.size;
		if(size < 1) {
			return (char)0;
		}
		char[] values = list.data;
		char max = Character.MIN_VALUE;
		char val = 0;
		for(int i = 0; i < size; i++) {
			if(max < (val = values[i])) {
				max = val;
			}
		}
		return max;
	}


	public static final char min(CharArrayList list) {
		int size = list.size;
		if(size < 1) {
			return (char)0;
		}
		char[] values = list.data;
		char min = Character.MAX_VALUE;
		char val = 0;
		for(int i = 0; i < size; i++) {
			if(min > (val = values[i])) {
				min = val;
			}
		}
		return min;
	}

}
