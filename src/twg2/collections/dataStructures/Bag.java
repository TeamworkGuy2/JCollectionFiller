package twg2.collections.dataStructures;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import twg2.arrays.ArrayManager;
import twg2.arrays.ArrayUtil;
import twg2.collections.interfaces.CollectionRemove;
import twg2.collections.interfaces.ModifiableCollection;
import twg2.collections.interfaces.Sized;

/** Bag, a collection similar to an {@link ArrayList} that does not preserve the insertion order of items once items are removed. 
 * All operations are O(1), except when the internal storage mechanism is full and {@link #remove(Object) remove(T)}
 * or {@link #add(Object) add(T)} is called, requiring the internal storage to be expanded.<br>
 * Note: the insertion order is preserved if {@code remove()} is not called. If the bag is filled by
 * calling {@code add()} or {@code addAll()} and emptied using one of the {@code clear*()} methods, then insertion order is preserved.<br>
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
	private BagListView listView;


	/** Creates an unsorted collection with a default size of 10
	 */
	public Bag() {
		this(10);
	}


	/** Create an unsorted collection with the specified initial size
	 * @param capacity the initial size of this collection
	 */
	public Bag(int capacity) {
		this.data = new Object[capacity];
		this.size = 0;
	}


	/** Create an unsorted collection with the specified initial size and the specified array type
	 * @param capacity the initial size of this collection
	 */
	public Bag(Class<?> componentType, int capacity) {
		this.data = (Object[]) Array.newInstance(componentType, capacity);
		this.size = 0;
	}


	public Bag(Collection<T> coll) {
		this.data = new Object[coll.size()];
		this.size = 0;
		this.addAll(coll);
	}


	public Bag(T[] vals) {
		this(vals, 0, vals.length);
	}


	public Bag(T[] vals, int off, int len) {
		this.data = new Object[len];
		this.addAll(vals, off, len);
	}


	@SuppressWarnings("unchecked")
	public Bag<T> copy() {
		Bag<T> copy;
		Class<?> componentType = this.data.getClass().getComponentType();

		if(componentType != Object.class) {
			copy = new Bag<>(componentType, this.size);
			copy.addAll((T[])this.data, 0, this.size);
		}
		else {
			copy = new Bag<>((T[])this.data, 0, this.size);
		}

		return copy;
	}


	/** Get the element at the specified index from this collection
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


	/** Remove the element at the specified index from this collection
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


	/** Remove the specified element from this bag
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
	public boolean removeAll(Iterable<? extends T> elems) {
		boolean res = true;
		for(T elem : elems) {
			res &= remove(elem);
		}
		return res;
	}


	@Override
	public T set(int i, T item) {
		if(i >= size) {
			throw new IndexOutOfBoundsException(i + " of [0, " + size + "]");
		}
		@SuppressWarnings("unchecked")
		T oldItem = (T)data[i];
		data[i] = item;
		action++;
		return oldItem;
	}


	/** Add the specified item to this bag
	 * @param item the item to add to this bag
	 */
	@Override
	public boolean add(T item) {
		// If the bag is to small, expand it
		if(size + 1 > data.length) {
			this.data = ArrayManager.expand(data);
		}
		action++;
		// Add the new item
		data[size] = item;
		size++;
		return true;
	}


	/** Add the specified bag of items to this bag
	 * @param items the items to add to this bag
	 */
	public void addAll(Bag<? extends T> items) {
		if(items == null) {
			return;
		}

		int itemCount = items.size();
		if(size + itemCount > data.length) {
			this.data = ArrayManager.expand(data, size + itemCount);
		}
		action++;
		System.arraycopy(items.data, 0, this.data, this.size, itemCount);
		size += itemCount;
	}


	/** Add the specified item to this bag
	 * @param items the items to add to this bag
	 */
	@Override
	public boolean addAll(Iterable<? extends T> items) {
		if(items instanceof Collection) {
			addAll(items, ((Collection<?>)items).size());
		}
		else if(items instanceof Sized) {
			addAll(items, ((Sized)items).size());
		}
		else {
			for(T item : items) {
				add(item);
			}
		}
		return true;
	}


	/** Utility method for adding an iterable with a known size to this bag
	 * @param items an iterator of items to add to this bag
	 * @param iteratorSize the number of items in the {@code items} iterator
	 */
	public void addAll(Iterable<? extends T> items, int iteratorSize) {
		if(items == null) {
			return;
		}

		if(size + iteratorSize > data.length) {
			this.data = ArrayManager.expand(data, size + iteratorSize);
		}
		action++;
		for(T item : items) {
			data[size] = item;
			size++;
		}
	}


	/**
	 * @see #addAll(Object[], int, int)
	 */
	public void addAll(T[] items) {
		this.addAll(items, 0, items.length);
	}


	/** Add an array of items to this collection
	 * @param items the array of items to add to this bag
	 * @param off the {@code items} offset
	 * @param len the number of {@code items} to copy into this collection starting at {@code off}
	 */
	public void addAll(T[] items, int off, int len) {
		if(items == null) {
			return;
		}

		if(size + len > data.length) {
			this.data = ArrayManager.expand(data, size + len);
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


	/** Clear this collection. Once complete {@link #size()} returns 0 and
	 * all internal element references have been set to null.
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


	/** Clear this collection and add the specific elements.
	 * Is slightly more efficient than calling {@link #clear()} and {@link #addAll(Iterable)}
	 * since some assumptions and shortcuts can be made.
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
			this.data = ArrayManager.expand(data, itemsCount);
		}

		for(int i = 0; i < itemsCount; i++) {
			data[i] = items.get(i);
		}
	}


	/** Clear this collection and add the specific elements.
	 * Is slightly more efficient than calling {@link #clear()} and {@link #addAll(Object[])}
	 * since some assumptions and shortcuts can be made.
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


	/** Get the current size of this collection
	 * @return the number of elements in this collection
	 */
	@Override
	public int size() {
		return size;
	}


	/** Is this collection of elements empty
	 * @return true if this collection contains no elements (i.e. {@link #size()} == 0), false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	/** Create an iterator over this collection
	 * @return a new iterator over this collection
	 */
	@Override
	public Iterator<T> iterator() {
		return new BagIterator();
	}


	/** Returns an array containing all of the elements in this collection.
	 * @return a new {@code Object[]} array of length {@link #size()},
	 * containing a copy of the contents of this bag.
	 */
	public Object[] toArray() {
		Object[] objs = new Object[this.size];
		System.arraycopy(this.data, 0, objs, 0, this.size);
		return objs;
	}


	/** Returns an array containing all of the elements in this collection.
	 * The array size is equal to {@link #size()}.
	 * The array type matches the {@code dst} array type.
	 * @param dst the destination array to copy this collection's contents into.
	 * If the array is not larger enough, a large array of the same type is created and return.
	 * @return An array containing a copy of the contents of this collection.
	 * The {@code dst} array is returned if its length is equal to or greater then {@link #size()},
	 * else a new array of the same type as {@code dst} is returned.
	 */
	public T[] toArray(T[] dst) {
		if(dst.length < this.size) {
			@SuppressWarnings("unchecked")
			T[] copy = (T[])Arrays.copyOf(this.data, this.size, dst.getClass());
			return copy;
		}
		System.arraycopy(this.data, 0, dst, 0, this.size);
		return dst;
	}


	public List<T> listView() {
		return listView != null ? listView : (listView = new BagListView());
	}


	/** Internal lock checking mechanism which counts the number of modifications to this object.
	 * If the count differs between two calls then this object has been modified between the
	 * two calls that returned the two different values.
	 * @return the number of actions (add, set, remove, clear) carried out by
	 * this bag since it was created.
	 */
	public int getActionCount() {
		return action;
	}


	/** Warning: This function is available for performance reasons, it is highly recommended to use {@link #get(int)} or {@link #iterator()}.<br>
	 * Note: the return value may change between calls and references to the return value should only be held in contexts where complete control over parent collection modification can be ensured.
	 * @return the underlying array used by this collection, current implementations store data start at index 0 through {@link #size()} - 1
	 */
	public Object[] getRawArray() {
		return this.data;
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
