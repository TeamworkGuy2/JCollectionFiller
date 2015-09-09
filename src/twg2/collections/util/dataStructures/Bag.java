package twg2.collections.util.dataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import twg2.collections.interfaces.ModifiableCollection;
import twg2.collections.util.arrayUtils.ArrayUtil;

/** Bag, a collection similar to an {@link ArrayList} that does not preserve the insertion order of items. 
 * All operations are O(1), except {@link #remove(Object) remove(T)} and {@link #add(Object) add(T)} when
 * the internal storage mechanism is full and must be expanded.
 * Original idea from a post on
 * <a href="http://www.java-gaming.org/topics/the-bag-fast-object-collection/24203/view.html">java-gaming.org</a>.
 * This structure is useful for
 * <a href="http://www.java-gaming.org/topics/avoiding-looping-through-everything/24587/30/view.html">groups of unsorted items<a/>.
 * @param <T> the type of items that can be inserted
 * @author TeamworkGuy2
 * @since 2013-1-20
 */
public class Bag<T> implements ModifiableCollection<T>, Iterable<T> {
	private Object[] data;
	/** The highest currently empty index to insert new items into,
	 * also equivalent to the zero based size of this bag */
	private int size;
	/** Used by iterators to ensure that the list has not been modified while iterating */
	private volatile int action;


	/** Creates an unsorted collection with a default size of 16
	 */
	public Bag() {
		this(16);
	}


	/** Create an unsorted collection with the specified size as the starting size
	 * @param capacity the initial size of this collection
	 */
	public Bag(int capacity) {
		this.data = new Object[capacity];
		this.size = 0;
	}


	public Bag(Collection<T> coll) {
		this.data = new Object[coll.size()];
		this.size = 0;
		this.addAll(coll);
	}


	public Bag(T[] vals, int off, int len) {
		this.data = new Object[len];
		this.addAll(vals, off, len);
	}


	public Bag<T> copy() {
		@SuppressWarnings("unchecked")
		Bag<T> copy = new Bag<>((T[])this.data, 0, this.size);
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


	/** Get the element at the specified index from this group of elements
	 * @param index the index between zero and {@link #size()}-1 inclusive to retrieve
	 * @return the element found at the specified index
	 */
	@Override
	public T get(int index) {
		if(index >= size) { throw new IndexOutOfBoundsException(); }
		@SuppressWarnings("unchecked")
		T item = (T)data[index];
		return item;
	}


	@Override
	public T getLast() {
		@SuppressWarnings("unchecked")
		T item = (T)data[size - 1];
		return item;
	}


	/** Remove the element at the specified index from this group of elements
	 * @param index the index between zero and {@link #size()}-1 inclusive to remove
	 * @return the element found at the specified index
	 */
	@Override
	public T remove(int index) {
		if(index >= size) { throw new IndexOutOfBoundsException(); }
		action++;
		// Get the item to remove
		@SuppressWarnings("unchecked")
		T item = (T)data[index];
		// Replace the item to remove with the last element from our array
		data[index] = data[size - 1];
		// Set the last element to null
		data[size-1] = null;
		// Decrease the size because we removed one item
		size--;
		return item;
	}


	/** Remove the specified object from this group of objects
	 * @param item the object to remove based on item.equals(get(i)) if item
	 * is not null, or get(i)==null if item is null, where i is [0, size()-1]
	 * @return true if the element was removed successfully, false otherwise
	 */
	public boolean remove(T item) {
		// Search for the item to remove
		if(item != null) {
			for(int i = 0; i < size; i++) {
				// If the item is found, remove it
				if(item.equals(data[i])) {
					remove(i);
					return true;
				}
			}
		}
		// Else if the item is null, search for a null item in our list
		else {
			for(int i = 0; i < size; i++) {
				// If the item is found, remove it
				if(data[i] == null) {
					remove(i);
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public T set(int i, T item) {
		if(i >= size) { throw new IndexOutOfBoundsException(); }
		@SuppressWarnings("unchecked")
		T oldItem = (T)data[i];
		data[i] = item;
		action++;
		return oldItem;
	}


	/** Add the specified item to this group of elements
	 * @param item the item to add to this group of elements
	 */
	@Override
	public boolean add(T item) {
		// If the bag is to small, expand it
		if(size + 1 > data.length) {
			expandArray();
		}
		action++;
		// Add the new item
		data[size] = item;
		size++;
		return true;
	}


	/** Add the specified bag of items to this group of elements
	 * @param items the items to add to this group of elements
	 */
	public void addAll(Bag<? extends T> items) {
		if(items == null) {
			return;
		}

		int itemCount = items.size();
		if(size + itemCount > data.length) {
			expandArray(size + itemCount);
		}
		action++;
		System.arraycopy(items.data, 0, this.data, this.size, itemCount);
		size += itemCount;
	}


	/** Add the specified item to this group of elements
	 * @param items the items to add to this group of elements
	 */
	public void addAll(Collection<? extends T> items) {
		addAll(items, items.size());
	}


	/** Utility method for adding know size iterable group of elements to this group of elements (e.g. a collection)
	 * @param items an iterator of items to add to this group of elements
	 * @param iteratorSize the number of items in the iterator
	 */
	public void addAll(Iterable<? extends T> items, int iteratorSize) {
		if(items == null) {
			return;
		}

		if(size + iteratorSize > data.length) {
			expandArray(size + iteratorSize);
		}
		action++;
		for(T item : items) {
			data[size] = item;
			size++;
		}
	}


	public void addAll(T[] items) {
		this.addAll(items, 0, items.length);
	}


	/** Add an array of items to this collection
	 * @param items the array of items to add to this group of elements
	 * @param off the {@code items} offset
	 * @param len the number of {@code items} to copy into this collection starting at {@code off}
	 */
	public void addAll(T[] items, int off, int len) {
		if(items == null) {
			return;
		}

		if(size + len > data.length) {
			expandArray(size + len);
		}
		action++;
		System.arraycopy(items, off, this.data, size, len);
		size += len;
	}



	/** Check if the specified values is contained in this list of $var.objectType$s
	 * @param value the value to check for in this list
	 * @return true if the value was found in the list, false otherwise
	 */
	public boolean contains(T value) {
		return indexOf(value) > -1;
	}


	/** Find the first occurring index of the specified value in this list
	 * @param value the value to search for in this list
	 * @return an index between {@code [0, }{@link #size()}{@code -1]} if the value is
	 * found, or -1 if the value cannot be found
	 */
	public int indexOf(T value) {
		// Search for the item to remove
		for(int i = 0; i < size; i++) {
			// If the item is found, return true
			if((value == null && data[i] == null) || value.equals(data[i])) {
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
	public int indexOf(T value, int fromIndex) {
		// Search for the item to remove
		for(int i = fromIndex; i < size; i++) {
			// If the item is found, return true
			if((value == null && data[i] == null) || value.equals(data[i])) {
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
	public int lastIndexOf(T value) {
		// Search for the item to remove
		for(int i = size - 1; i > -1; i--) {
			// If the item is found, return true
			if((value == null && data[i] == null) || value.equals(data[i])) {
				return i;
			}
		}
		// Else if the item is not found, return false
		return -1;
	}


	/** Clear the group of elements
	 */
	@Override
	public void clear() {
		action++;
		// Clear list to null
		for(int i = 0; i < size; i++) {
			data[i] = null;
		}
		// Set the size back to the beginning of the array
		size = 0;
	}


	/** Clear the group of elements and add the specific elements
	 */
	public void clearAndAddAll(List<T> items) {
		if(items == null) {
			clear();
			return;
		}
		action++;

		int itemsCount = items.size();
		// Clear elements past the last index that will be occupied by the new group of items
		for(int i = itemsCount; i < size; i++) {
			data[i] = null;
		}
		// Set the size back to the beginning of the array
		size = itemsCount;

		if(itemsCount > data.length) {
			expandArray(itemsCount);
		}

		for(int i = 0; i < itemsCount; i++) {
			data[i] = items.get(i);
		}
	}


	/** Clear the group of elements and add the specific elements
	 */
	public void clearAndAddAll(T[] items) {
		if(items == null) {
			clear();
			return;
		}
		action++;

		int itemsCount = items.length;
		// Clear elements past the last index that will be occupied by the new group of items
		for(int i = itemsCount; i < size; i++) {
			data[i] = null;
		}
		// Set the size back to the beginning of the array
		size = 0;

		this.addAll(items, 0, itemsCount);
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
	public Iterator<T> iterator() {
		return new BagIterator();
	}


	public Object[] toArray() {
		Object[] objs = new Object[this.size];
		System.arraycopy(this.data, 0, objs, 0, this.size);
		return objs;
	}


	public Object[] toArray(T[] dst) {
		if(dst.length < this.size) {
			@SuppressWarnings("unchecked")
			T[] copy = (T[])Arrays.copyOf(this.data, this.size, dst.getClass());
			return copy;
		}
		System.arraycopy(this.data, 0, dst, 0, this.size);
		return dst;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + size;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Bag)) {
			return false;
		}

		@SuppressWarnings("unchecked")
		Bag<T> other = (Bag<T>)obj;
		if (size != other.size) {
			return false;
		}
		if (!ArrayUtil.equals(this.data, 0, other.data, 0, this.size)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		StringBuilder strB = new StringBuilder();
		strB.append('[');
		if(size > 0) {
			int count = size - 1;
			for(int i = 0; i < count; i++) {
				strB.append(data[i]);
				strB.append(", ");
			}
			strB.append(data[count]);
		}
		strB.append(']');
		return strB.toString();
	}


	private final void expandArray() {
		Object[] oldData = this.data;
		// Expand array size 1.5x + 4, +4 instead of +1 to prevent small bags from constantly needed to resize
		this.data = new Object[oldData.length + (oldData.length >>> 1) + 4];
		System.arraycopy(oldData, 0, this.data, 0, oldData.length);
	}


	private final void expandArray(int totalSize) {
		Object[] oldData = this.data;
		// Expand array size 1.5x + 4, +4 instead of +1 to prevent small bags from constantly needed to resize
		int newSize = Math.max(totalSize, oldData.length + (oldData.length >>> 1) + 4);
		this.data = new Object[newSize];
		System.arraycopy(oldData, 0, this.data, 0, oldData.length);
	}


	/** An Iterator for this class
	 */
	private class BagIterator implements Iterator<T> {
		private int expectedActions;
		private int currentIndex;


		public BagIterator() {
			super();
			expectedActions = Bag.this.action;
		}


		@Override
		public boolean hasNext() {
			checkMod();
			return currentIndex < size;
		}


		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			checkMod();
			currentIndex++;
			return (T)data[currentIndex-1];
		}


		protected final void checkMod() {
			if(expectedActions != Bag.this.action) {
				throw new ConcurrentModificationException("Bag was modified while iterating");
			}
		}


		@Override
		public void remove() {
			checkMod();
			expectedActions++; // Since remove() increments the action count, keep the two in sync
			Bag.this.remove(currentIndex);
			// Rewind so that next() call returns same index, which now contains new item since removed item indices
			// are replaced with items from the end of the group
			currentIndex--;
		}
	}

}
