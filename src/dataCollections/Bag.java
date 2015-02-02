package dataCollections;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

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
	private int action;


	/** Creates an unsorted group of items with a default size of 16
	 */
	public Bag() {
		this(16);
	}


	/** Create an unsorted group of items with the specified size as the starting size
	 * @param capacity the initial size of the group of items
	 */
	public Bag(int capacity) {
		this.data = new Object[capacity];
		this.size = 0;
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
	public void add(T item) {
		// If the bag is to small, expand it
		if(size >= data.length) {
			expandArray();
		}
		action++;
		// Add the new item
		data[size] = item;
		size++;
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
	public boolean isEmpty() {
		return size == 0;
	}


	@Override
	public Iterator<T> iterator() {
		return new BagIterator();
	}


	@Override
	public String toString() {
		StringBuilder strB = new StringBuilder();
		strB.append('[');
		if(size > 0) {
			int count = size - 1;
			for(int i = 0; i < count; i++) {
				strB.append(data[i]);
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
