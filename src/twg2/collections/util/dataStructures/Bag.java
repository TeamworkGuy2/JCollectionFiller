package twg2.collections.util.dataStructures;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import twg2.arrays.ArrayManaged;
import twg2.arrays.ArrayUtil;
import twg2.collections.interfaces.CollectionRemove;
import twg2.collections.interfaces.ModifiableCollection;

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
public class Bag<T> implements ModifiableCollection<T>, CollectionRemove<T>, Iterable<T> {
	private Object[] data;
	/** The highest currently empty index to insert new items into,
	 * also equivalent to the zero based size of this bag */
	private int size;
	/** Used by iterators to ensure that the list has not been modified while iterating */
	private volatile int action;
	private BagListView listView = new BagListView();


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
		if(index >= size) { throw new IndexOutOfBoundsException(index + " of [0, " + size + "]"); }
		@SuppressWarnings("unchecked")
		T item = (T)data[index];
		return item;
	}


	@Override
	public T getLast() {
		@SuppressWarnings("unchecked")
		T item = (T)data[size - 1]; // let java throw out of bounds exception if bag is empty and index is -1
		return item;
	}


	/** Remove the element at the specified index from this group of elements
	 * @param index the index between zero and {@link #size()}-1 inclusive to remove
	 * @return the element found at the specified index
	 */
	@Override
	public T remove(int index) {
		if(index >= size) { throw new IndexOutOfBoundsException(index + " of [0, " + size + "]"); }
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
	@Override
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
	public boolean removeAll(Collection<? extends T> elems) {
		boolean res = true;
		for(T elem : elems) {
			res &= remove(elem);
		}
		return res;
	}


	@Override
	public T set(int i, T item) {
		if(i >= size) { throw new IndexOutOfBoundsException(i + " of [0, " + size + "]"); }
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
			this.data = ArrayManaged.expandArray(data);
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
			this.data = ArrayManaged.expandArray(data, size + itemCount);
		}
		action++;
		System.arraycopy(items.data, 0, this.data, this.size, itemCount);
		size += itemCount;
	}


	/** Add the specified item to this group of elements
	 * @param items the items to add to this group of elements
	 */
	@Override
	public boolean addAll(Collection<? extends T> items) {
		addAll(items, items.size());
		return true;
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
			this.data = ArrayManaged.expandArray(data, size + iteratorSize);
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
			this.data = ArrayManaged.expandArray(data, size + len);
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
		return indexOf(value, 0) > -1;
	}


	/** Find the first occurring index of the specified value in this list
	 * @param value the value to search for in this list
	 * @return an index between {@code [0, }{@link #size()} {@code - 1]} if the value is
	 * found, or -1 if the value cannot be found
	 */
	public int indexOf(T value) {
		return indexOf(value, 0);
	}


	/** Find the first occurring index of the specified value in this list,
	 * starting at the specified offset
	 * @param value the value to search for in this list
	 * @param fromIndex shrinks the search range to {@code [fromIndex, }{@link #size()} {@code - 1]}
	 * @return an index between {@code [fromIndex, }{@link #size()} {@code - 1]} if the value is
	 * found, or -1 if the value cannot be found
	 */
	public int indexOf(T value, int fromIndex) {
		// Search for the item to remove
		if(value != null) {
			return ArrayUtil.indexOf(this.data, fromIndex, size - fromIndex, value);
		}
		else {
			return ArrayUtil.indexOfRef(this.data, fromIndex, size - fromIndex, value);
		}
	}


	/** @see #lastIndexOf(Object, int)
	 */
	public int lastIndexOf(T value) {
		return lastIndexOf(value, 0);
	}


	/** Find the last occurring index of the specified value in this list
	 * @param value the value to search for in this list
	 * @param fromIndex shrinks the search range to {@code [0, }{@link #size()} {@code - fromIndex - 1]}
	 * @return an index between {@code [0, }{@link #size()} {@code - fromIndex - 1]} if the value is
	 * found, or -1 if the value cannot be found
	 */
	public int lastIndexOf(T value, int fromIndex) {
		// Search for the item to remove
		if(value != null) {
			return ArrayUtil.lastIndexOf(this.data, 0, size - fromIndex, value);
		}
		else {
			return ArrayUtil.lastIndexOfRef(this.data, 0, size - fromIndex, value);
		}
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
		// Set the size back to empty
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
			this.data = ArrayManaged.expandArray(data, itemsCount);
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
		// Set the size back to empty
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


	public List<T> listView() {
		return listView;
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
		return ArrayUtil.toString(data, 0, size);
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




	/** A view of this Bag as a list, performance may not be a good as this Bag as not all methods are implemented (i.e. uses {@link AbstractList})
	 * @author TeamworkGuy2
	 * @since 2015-10-6
	 */
	protected class BagListView extends AbstractList<T> {

		@Override
		public T get(int index) {
			return Bag.this.get(index);
		}

		@SuppressWarnings("unchecked")
		@Override
		public int indexOf(Object o) {
			return Bag.this.indexOf((T)o);
		}

		@SuppressWarnings("unchecked")
		@Override
		public int lastIndexOf(Object o) {
			return Bag.this.lastIndexOf((T)o);
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean contains(Object o) {
			return Bag.this.contains((T)o);
		}

		@Override
		public int size() {
			return Bag.this.size;
		}

		@Override
		public void clear() {
			Bag.this.clear();
		}

		@Override
		public Iterator<T> iterator() {
			return Bag.this.iterator();
		}

	}

}
