package twg2.collections.dataStructures;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import twg2.collections.interfaces.ListReadOnly;

/** Resizable-array implementation of the {@code List} interface.  Implements
 * all optional list operations, and permits all elements, including {@code null}.
 * In addition to implementing the {@code List} interface, this class provides
 * methods to manipulate the size of the array that is used internally to store
 * the list.
 * <p>The {@code size()}, {@code isEmpty()}, {@code get()}, {@code set()},
 * {@code iterator()}, and {@code listIterator()} operations run in constant
 * time.  The {@code add()} operation runs in <i>amortized constant time</i>,
 * that is, adding n elements requires O(n) time.  All of the other operations
 * run in linear time (roughly speaking).  The constant factor is low compared
 * to that for the {@code LinkedList} implementation.
 * <p>Each {@code BaseList} instance has a <i>capacity</i>.  The capacity is
 * the size of the backing array which is always at least as large as the list
 * size.  As elements are added to this list, its capacity grows automatically.
 * The details of the growth policy are not specified beyond the fact that
 * adding an element has constant amortized time cost.
 * <p>An application can increase the capacity of a {@code BaseList} instance
 * before adding multiple elements using the {@code ensureCapacity()} method.
 * This reduces the number of incremental reallocation required.
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a {@code BaseList} instance concurrently,
 * and at least one of the threads modifies the list structurally, it
 * <i>must</i> be synchronized externally.  (A structural modification is
 * any operation that adds or deletes elements, or explicitly resizes the
 * backing array; setting the value of an element is not a structural
 * modification).  This is typically accomplished by synchronizing on
 * some object that naturally encapsulates the list.
 * If no such object exists, the list should be "wrapped" using the
 * {@link Collections#synchronizedList Collections.synchronizedList}
 * method. This is best done at creation time, to prevent accidental
 * unsynchronized access to the list:<pre>
 *   List list = Collections.synchronizedList(new BaseList(...));</pre>
 * <p id="fail-fast">
 * The iterators returned by this class's {@link #iterator()} and {@link #listIterator(int)}
 * methods are <em>fail-fast</em>: if the list is structurally modified at any
 * time after the iterator is created, in any way except through the iterator's own
 * {@link ListIterator#remove() remove()} or {@link ListIterator#add(Object) add}
 * methods, the iterator will throw a {@link ConcurrentModificationException}.
 * Thus, in the face of concurrent modification, the iterator fails quickly and
 * cleanly, rather than risking arbitrary, non-deterministic behavior at an
 * undetermined time in the future.
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed as it is,
 * generally speaking, impossible to make any hard guarantees in the presence
 * of unsynchronized concurrent modification.  Fail-fast iterators throw
 * {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this exception
 * for its correctness:  <i>the fail-fast behavior of iterators should be used
 * only to detect bugs.</i>
 * 
 * Note: this is a copy of {@link java.util.ArrayList} which implements {@link ListReadOnly} and makes several important members 'protected' rather than 'private'
 * allowing for easier sub-classing.
 * @param <E> the type of elements in this list
 * @author TeamworkGuy2
 * @since 2018-09-22
 * @see Collection
 * @see List
 */
public class BaseList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable, ListReadOnly<E> {
	private static final long serialVersionUID = 91635238905654871L;

	/** Initial capacity of a super list
	 */
	protected static final int DEFAULT_CAPACITY = 10;

	/** The maximum size of array to allocate (unless necessary).
	 * Some VMs reserve some header words in an array.
	 * Attempts to allocate larger arrays may result in
	 * OutOfMemoryError: Requested array size exceeds VM limit
	 */
	protected static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	/** Default empty array instance
	 */
	protected static final Object[] EMPTY_ARY = new Object[0];

	/** Shared empty array instance used for default sized empty instances. We
	 * distinguish this from EMPTY_ARY to know how much to inflate when
	 * first element is added.
	 */
	protected static final Object[] DEFAULTCAPACITY_EMPTY_ARY = new Object[0];

	/** The array buffer in which the data elements are stored.
	 * The capacity of this BaseList is the length of this array buffer.
	 * An empty list with elementData == DEFAULTCAPACITY_EMPTY_ARY
	 * will be expanded to DEFAULT_CAPACITY when the first element is added.
	 */
	protected transient Object[] elements; // non-private to simplify nested class access

	/** The current size of this BaseList
	 * @serial
	 */
	protected int size;


	/** Constructs an empty list with the specified initial capacity.
	 * @param initialCapacity  the initial capacity of the list
	 * @throws IllegalArgumentException if the specified initial capacity is negative
	 */
	public BaseList(int initialCapacity) {
		if (initialCapacity > 0) {
			this.elements = new Object[initialCapacity];
		}
		else if (initialCapacity == 0) {
			this.elements = EMPTY_ARY;
		}
		else {
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		}
	}


	/** Constructs an empty list with an initial capacity of ten.
	 */
	public BaseList() {
		this.elements = DEFAULTCAPACITY_EMPTY_ARY;
	}


	/** Constructs a list containing the elements of the specified collection,
	 * in the order they are returned by the collection's iterator.
	 * @param c the collection whose elements are to be placed into this list
	 * @throws NullPointerException if the specified collection is null
	 */
	public BaseList(Collection<? extends E> c) {
		elements = c.toArray();
		if ((size = elements.length) != 0) {
			// defend against c.toArray (incorrectly) not returning Object[] (see e.g. https://bugs.openjdk.java.net/browse/JDK-6260652)
			if (elements.getClass() != Object[].class)
				elements = Arrays.copyOf(elements, size, Object[].class);
		}
		else {
			// replace with empty array.
			this.elements = EMPTY_ARY;
		}
	}


	/** Trims the capacity of this {@code BaseList} instance to be the
	 * list's current size.  An application can use this operation to minimize
	 * the storage of an {@code BaseList} instance.
	 */
	public void trimToSize() {
		modCount++;
		if (size < elements.length) {
			elements = (size == 0 ? EMPTY_ARY : Arrays.copyOf(elements, size));
		}
	}


	/** Increases the capacity of this {@code BaseList} instance, if
	 * necessary, to ensure that it can hold at least the number of elements
	 * specified by the minimum capacity argument.
	 * @param minCapacity the desired minimum capacity
	 */
	public void ensureCapacity(int minCapacity) {
		if (minCapacity > elements.length && !(elements == DEFAULTCAPACITY_EMPTY_ARY && minCapacity <= DEFAULT_CAPACITY)) {
			modCount++;
			grow(minCapacity);
		}
	}


	/** Increases the capacity to ensure that it can hold at least the
	 * number of elements specified by the minimum capacity argument.
	 * @param minCapacity the desired minimum capacity
	 * @throws OutOfMemoryError if minCapacity is less than zero
	 */
	protected Object[] grow(int minCapacity) {
		return elements = Arrays.copyOf(elements, newCapacity(minCapacity));
	}


	/** Returns a capacity at least as large as the given minimum capacity.
	 * Returns the current capacity increased by 50% if that suffices.
	 * Will not return a capacity greater than MAX_ARRAY_SIZE unless the
	 * given minimum capacity is greater than MAX_ARRAY_SIZE.
	 * @param minCapacity the desired minimum capacity
	 * @throws OutOfMemoryError if minCapacity is less than zero
	 */
	protected int newCapacity(int minCapacity) {
		// overflow check
		int oldCapacity = elements.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity <= 0) {
			if (elements == DEFAULTCAPACITY_EMPTY_ARY)
				return Math.max(DEFAULT_CAPACITY, minCapacity);
			if (minCapacity < 0) // overflow
				throw new OutOfMemoryError();
			return minCapacity;
		}
		return (newCapacity - MAX_ARRAY_SIZE <= 0)
			? newCapacity
			: hugeCapacity(minCapacity);
	}


	protected static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE)
			? Integer.MAX_VALUE
			: MAX_ARRAY_SIZE;
	}


	/** Returns the number of elements in this list.
	 * @return the number of elements in this list
	 */
	@Override
	public int size() {
		return size;
	}


	/** Returns {@code true} if this list contains no elements.
	 * @return {@code true} if this list contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	/** Returns {@code true} if this list contains the specified element.
	 * More formally, returns {@code true} if and only if this list contains
	 * at least one element {@code e} such that
	 * {@code Objects.equals(o, e)}.
	 * @param o element whose presence in this list is to be tested
	 * @return {@code true} if this list contains the specified element
	 */
	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}


	/** Returns the index of the first occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * More formally, returns the lowest index {@code i} such that
	 * {@code Objects.equals(o, get(i))},
	 * or -1 if there is no such index.
	 */
	@Override
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++)
				if (elements[i] == null)
					return i;
		}
		else {
			for (int i = 0; i < size; i++)
				if (o.equals(elements[i]))
					return i;
		}
		return -1;
	}


	/** Returns the index of the last occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * More formally, returns the highest index {@code i} such that
	 * {@code Objects.equals(o, get(i))},
	 * or -1 if there is no such index.
	 */
	@Override
	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i = size-1; i >= 0; i--)
				if (elements[i]==null)
					return i;
		}
		else {
			for (int i = size-1; i >= 0; i--)
				if (o.equals(elements[i]))
					return i;
		}
		return -1;
	}


	/** Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element).
	 * <p>The returned array will be "safe" in that no references to it are
	 * maintained by this list.  (This method allocates a new array).
	 * The caller is thus free to modify the returned array.
	 * <p>This method acts as bridge between array-based and collection-based APIs.
	 * @return an array containing all of the elements in this list in proper sequence
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}


	/** Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element); the runtime type of the returned
	 * array is that of the specified array.  If the list fits in the
	 * specified array, it is returned therein.  Otherwise, a new array is
	 * allocated with the runtime type of the specified array and the size of
	 * this list.
	 * <p>If the list fits in the specified array with room to spare
	 * (i.e., the array has more elements than the list), the element in
	 * the array immediately following the end of the collection is set to
	 * {@code null}.  (This is useful in determining the length of the
	 * list <i>only</i> if the caller knows that the list does not contain
	 * any null elements.)
	 * @param a the array into which the elements of the list are to
	 *		  be stored, if it is big enough; otherwise, a new array of the
	 *		  same runtime type is allocated for this purpose.
	 * @return an array containing the elements of the list
	 * @throws ArrayStoreException if the runtime type of the specified array
	 *		 is not a supertype of the runtime type of every element in
	 *		 this list
	 * @throws NullPointerException if the specified array is null
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if (a.length < size)
			// Make a new array of a's runtime type, but my contents:
			return (T[]) Arrays.copyOf(elements, size, a.getClass());
		System.arraycopy(elements, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}


	// Positional Access Operations

	@SuppressWarnings("unchecked")
	final E elementData(int index) {
		return (E) elements[index];
	}


	@SuppressWarnings("unchecked")
	static final <E> E elementAt(Object[] es, int index) {
		return (E) es[index];
	}


	/** Returns the element at the specified position in this list.
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public E get(int index) {
		Objects.checkIndex(index, size);
		return elementData(index);
	}


	/** Replaces the element at the specified position in this list with
	 * the specified element.
	 * @param index index of the element to replace
	 * @param element element to be stored at the specified position
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public E set(int index, E element) {
		Objects.checkIndex(index, size);
		E oldValue = elementData(index);
		elements[index] = element;
		return oldValue;
	}


	/** Appends the specified element to the end of this list.
	 * @param e element to be appended to this list
	 * @return {@code true} (as specified by {@link Collection#add})
	 */
	@Override
	public boolean add(E e) {
		modCount++;
		add(e, elements, size);
		return true;
	}


	/** This helper method split out from add(E) to keep method
	 * bytecode size under 35 (the -XX:MaxInlineSize default value),
	 * which helps when add(E) is called in a C1-compiled loop.
	 */
	protected void add(E e, Object[] elementData, int s) {
		if (s == elementData.length)
			elementData = grow(size + 1);
		elementData[s] = e;
		size = s + 1;
	}


	/** Inserts the specified element at the specified position in this
	 * list. Shifts the element currently at that position (if any) and
	 * any subsequent elements to the right (adds one to their indices).
	 * @param index index at which the specified element is to be inserted
	 * @param element element to be inserted
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		modCount++;
		final int s;
		Object[] elementData;
		if ((s = size) == (elementData = this.elements).length)
			elementData = grow(size + 1);
		System.arraycopy(elementData, index,
						 elementData, index + 1,
						 s - index);
		elementData[index] = element;
		size = s + 1;
	}


	/** Removes the element at the specified position in this list.
	 * Shifts any subsequent elements to the left (subtracts one from their indices).
	 * @param index the index of the element to be removed
	 * @return the element that was removed from the list
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public E remove(int index) {
		Objects.checkIndex(index, size);
		final Object[] es = elements;

		@SuppressWarnings("unchecked")
		E oldValue = (E) es[index];
		fastRemove(es, index);

		return oldValue;
	}


	/** Removes the first occurrence of the specified element from this list,
	 * if it is present.  If the list does not contain the element, it is
	 * unchanged.  More formally, removes the element with the lowest index
	 * {@code i} such that {@code Objects.equals(o, get(i))}
	 * (if such an element exists).  Returns {@code true} if this list
	 * contained the specified element (or equivalently, if this list
	 * changed as a result of the call).
	 * @param o element to be removed from this list, if present
	 * @return {@code true} if this list contained the specified element
	 */
	@Override
	public boolean remove(Object o) {
		final Object[] es = elements;
		final int size = this.size;
		int i = 0;
		found: {
			if (o == null) {
				for (; i < size; i++)
					if (es[i] == null)
						break found;
			}
			else {
				for (; i < size; i++)
					if (o.equals(es[i]))
						break found;
			}
			return false;
		}
		fastRemove(es, i);
		return true;
	}


	public boolean removeRef(Object o) {
		final Object[] es = elements;
		int i = 0;
		found: {
			for (int size = this.size; i < size; i++)
				if (es[i] == o)
					break found;
			return false;
		}
		fastRemove(es, i);
		return true;
	}


	/** Protected remove method that skips bounds checking and does not
	 * return the value removed.
	 */
	protected void fastRemove(Object[] es, int i) {
		modCount++;
		final int newSize;
		if ((newSize = size - 1) > i)
			System.arraycopy(es, i + 1, es, i, newSize - i);
		es[size = newSize] = null;
	}


	/** Removes all of the elements from this list. The list will
	 * be empty after this call returns.
	 */
	@Override
	public void clear() {
		modCount++;
		final Object[] es = elements;
		for (int to = size, i = size = 0; i < to; i++)
			es[i] = null;
	}


	/** Appends all of the elements in the specified collection to the end of
	 * this list, in the order that they are returned by the specified collection's
	 * Iterator.  The behavior of this operation is
	 * undefined if the specified collection is modified while the operation
	 * is in progress.  (This implies that the behavior of this call is
	 * undefined if the specified collection is this list, and this
	 * list is nonempty.)
	 * @param c collection containing elements to be added to this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		Object[] a = c.toArray();
		modCount++;
		int numNew = a.length;
		if (numNew == 0)
			return false;
		Object[] elementData;
		final int s;
		if (numNew > (elementData = this.elements).length - (s = size))
			elementData = grow(s + numNew);
		System.arraycopy(a, 0, elementData, s, numNew);
		size = s + numNew;
		return true;
	}


	/** Inserts all of the elements in the specified collection into this
	 * list, starting at the specified position.  Shifts the element
	 * currently at that position (if any) and any subsequent elements to
	 * the right (increases their indices).  The new elements will appear
	 * in the list in the order that they are returned by the
	 * specified collection's iterator.
	 * @param index index at which to insert the first element from the
	 *		  specified collection
	 * @param c collection containing elements to be added to this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 * @throws NullPointerException if the specified collection is null
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		rangeCheckForAdd(index);

		Object[] a = c.toArray();
		modCount++;
		int numNew = a.length;
		if (numNew == 0)
			return false;
		Object[] elementData;
		final int s;
		if (numNew > (elementData = this.elements).length - (s = size))
			elementData = grow(s + numNew);

		int numMoved = s - index;
		if (numMoved > 0)
			System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
		System.arraycopy(a, 0, elementData, index, numNew);
		size = s + numNew;
		return true;
	}


	/** Removes from this list all of the elements whose index is between
	 * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
	 * Shifts any succeeding elements to the left (reduces their index).
	 * This call shortens the list by {@code (toIndex - fromIndex)} elements.
	 * (If {@code toIndex==fromIndex}, this operation has no effect).
	 * @throws IndexOutOfBoundsException if {@code fromIndex} or
	 *   {@code toIndex} is out of range
	 *   ({@code fromIndex < 0 ||
	 *   toIndex > size() ||
	 *   toIndex < fromIndex})
	 */
	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		if (fromIndex > toIndex) {
			throw new IndexOutOfBoundsException(outOfBoundsMsg(fromIndex, toIndex));
		}
		modCount++;
		shiftTailOverGap(elements, fromIndex, toIndex);
	}


	/** Erases the gap from lo to hi, by sliding down following elements.
	 */
	protected void shiftTailOverGap(Object[] es, int lo, int hi) {
		System.arraycopy(es, hi, es, lo, size - hi);
		for (int to = size, i = (size -= hi - lo); i < to; i++)
			es[i] = null;
	}


	/** Removes from this list all of its elements that are contained in the
	 * specified collection.
	 * @param c collection containing elements to be removed from this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws ClassCastException if the class of an element of this list
	 *		 is incompatible with the specified collection
	 * (<a href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException if this list contains a null element and the
	 *		 specified collection does not permit null elements
	 * (<a href="Collection.html#optional-restrictions">optional</a>),
	 *		 or if the specified collection is null
	 * @see Collection#contains(Object)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return batchRemove(c, false, 0, size);
	}


	/** Retains only the elements in this list that are contained in the
	 * specified collection.  In other words, removes from this list all
	 * of its elements that are not contained in the specified collection.
	 * @param c collection containing elements to be retained in this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws ClassCastException if the class of an element of this list
	 *		 is incompatible with the specified collection
	 * (<a href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException if this list contains a null element and the
	 *		 specified collection does not permit null elements
	 * (<a href="Collection.html#optional-restrictions">optional</a>),
	 *		 or if the specified collection is null
	 * @see Collection#contains(Object)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return batchRemove(c, true, 0, size);
	}


	boolean batchRemove(Collection<?> c, boolean complement, int from, int end) {
		Objects.requireNonNull(c);
		final Object[] es = elements;
		int r;
		// Optimize for initial run of survivors
		for (r = from;; r++) {
			if (r == end)
				return false;
			if (c.contains(es[r]) != complement)
				break;
		}
		int w = r++;
		try {
			for (Object e; r < end; r++)
				if (c.contains(e = es[r]) == complement)
					es[w++] = e;
		}
		catch (Throwable ex) {
			// Preserve behavioral compatibility with AbstractCollection, even if c.contains() throws.
			System.arraycopy(es, r, es, w, end - r);
			w += end - r;
			throw ex;
		} finally {
			modCount += end - w;
			shiftTailOverGap(es, w, end);
		}
		return true;
	}


	/** Returns a shallow copy of this {@code BaseList} instance.  (The
	 * elements themselves are not copied).
	 * @return a clone of this {@code BaseList} instance
	 */
	@Override
	public Object clone() {
		try {
			BaseList<?> v = (BaseList<?>) super.clone();
			v.elements = Arrays.copyOf(elements, size);
			v.modCount = 0;
			return v;
		}
		catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError(e);
		}
	}


	/** Saves the state of the {@code BaseList} instance to a stream (that is, serializes it).
	 * @param s the stream
	 * @throws java.io.IOException if an I/O error occurs
	 * @serialData The length of the array backing the {@code BaseList}
	 * instance is emitted (int), followed by all of its elements
	 * (each an {@code Object}) in the proper order.
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		// Write out element count, and any hidden stuff
		int expectedModCount = modCount;
		s.defaultWriteObject();

		// Write out size as capacity for behavioral compatibility with clone()
		s.writeInt(size);

		// Write out all elements in the proper order.
		for (int i=0; i<size; i++) {
			s.writeObject(elements[i]);
		}

		if (modCount != expectedModCount) {
			throw new ConcurrentModificationException();
		}
	}


	/** Reconstitutes the {@code BaseList} instance from a stream (that is, deserializes it).
	 * @param s the stream
	 * @throws ClassNotFoundException if the class of a serialized object could not be found
	 * @throws java.io.IOException if an I/O error occurs
	 */
	private void readObject(java.io.ObjectInputStream s)
		throws java.io.IOException, ClassNotFoundException {

		// Read size, and any hidden stuff
		s.defaultReadObject();

		// Read capacity (ignored)
		s.readInt();

		if (size > 0) {
			// like clone(), allocate array based upon size not capacity
			Object[] elems = new Object[size];

			// Read in all elements in the proper order.
			for (int i = 0; i < size; i++) {
				elems[i] = s.readObject();
			}

			elements = elems;
		}
		else if (size == 0) {
			elements = EMPTY_ARY;
		}
		else {
			throw new java.io.InvalidObjectException("Invalid size: " + size);
		}
	}


	/**
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	public void forEach(Consumer<? super E> action) {
		Objects.requireNonNull(action);
		final int expectedModCount = modCount;
		final Object[] es = elements;
		for (int i = 0, size = this.size; modCount == expectedModCount && i < size; i++)
			action.accept(elementAt(es, i));
		if (modCount != expectedModCount)
			throw new ConcurrentModificationException();
	}


	/**
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return removeIf(filter, 0, size);
	}


	/** Removes all elements satisfying the given predicate, from index
	 * i (inclusive) to index end (exclusive).
	 */
	boolean removeIf(Predicate<? super E> filter, int i, final int end) {
		Objects.requireNonNull(filter);
		int expectedModCount = modCount;
		final Object[] es = elements;
		// Optimize for initial run of survivors
		for (; i < end && !filter.test(elementAt(es, i)); i++)
			;
		// Tolerate predicates that reentrantly access the collection for
		// read (but writers still get CME), so traverse once to find
		// elements to delete, a second pass to physically expunge.
		if (i < end) {
			final int beg = i;
			final long[] deathRow = nBits(end - beg);
			deathRow[0] = 1L;   // set bit 0
			for (i = beg + 1; i < end; i++)
				if (filter.test(elementAt(es, i)))
					setBit(deathRow, i - beg);
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			expectedModCount++;
			modCount++;
			int w = beg;
			for (i = beg; i < end; i++)
				if (isClear(deathRow, i - beg))
					es[w++] = es[i];
			shiftTailOverGap(es, w, end);
			return true;
		}
		else {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			return false;
		}
	}


	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		Objects.requireNonNull(operator);
		final int expectedModCount = modCount;
		final Object[] es = elements;
		final int size = this.size;
		for (int i = 0; modCount == expectedModCount && i < size; i++)
			es[i] = operator.apply(elementAt(es, i));
		if (modCount != expectedModCount)
			throw new ConcurrentModificationException();
		modCount++;
	}


	@Override
	@SuppressWarnings("unchecked")
	public void sort(Comparator<? super E> c) {
		final int expectedModCount = modCount;
		Arrays.sort((E[])elements, 0, size, c);
		if (modCount != expectedModCount)
			throw new ConcurrentModificationException();
		modCount++;
	}


	/** Returns a list iterator over the elements in this list (in proper
	 * sequence), starting at the specified position in the list.
	 * The specified index indicates the first element that would be
	 * returned by an initial call to {@link ListIterator#next next}.
	 * An initial call to {@link ListIterator#previous previous} would
	 * return the element with the specified index minus one.
	 * <p>The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		rangeCheckForAdd(index);
		return new BaseListIterator(index);
	}


	/** Returns a list iterator over the elements in this list (in proper sequence).
	 * <p>The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
	 * @see #listIterator(int)
	 */
	@Override
	public ListIterator<E> listIterator() {
		return new BaseListIterator(0);
	}


	/** Returns an iterator over the elements in this list in proper sequence.
	 * <p>The returned iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
	 * @return an iterator over the elements in this list in proper sequence
	 */
	@Override
	public Iterator<E> iterator() {
		return new BaseIterator();
	}


	/** Returns a view of the portion of this list between the specified
	 * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
	 * {@code fromIndex} and {@code toIndex} are equal, the returned list is
	 * empty.)  The returned list is backed by this list, so non-structural
	 * changes in the returned list are reflected in this list, and vice-versa.
	 * The returned list supports all of the optional list operations.
	 *
	 * <p>This method eliminates the need for explicit range operations (of
	 * the sort that commonly exist for arrays).  Any operation that expects
	 * a list can be used as a range operation by passing a subList view
	 * instead of a whole list.  For example, the following idiom
	 * removes a range of elements from a list:
	 * <pre>
	 *      list.subList(from, to).clear();
	 * </pre>
	 * Similar idioms may be constructed for {@link #indexOf(Object)} and
	 * {@link #lastIndexOf(Object)}, and all of the algorithms in the
	 * {@link Collections} class can be applied to a subList.
	 *
	 * <p>The semantics of the list returned by this method become undefined if
	 * the backing list (i.e., this list) is <i>structurally modified</i> in
	 * any way other than via the returned list.  (Structural modifications are
	 * those that change the size of this list, or otherwise perturb it in such
	 * a fashion that iterations in progress may yield incorrect results.)
	 *
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		subListRangeCheck(fromIndex, toIndex, size);
		return new SubList<>(this, fromIndex, toIndex);
	}


	/** Creates a <em><a href="Spliterator.html#binding">late-binding</a></em>
	 * and <em>fail-fast</em> {@link Spliterator} over the elements in this list.
	 * <p>The {@code Spliterator} reports {@link Spliterator#SIZED},
	 * {@link Spliterator#SUBSIZED}, and {@link Spliterator#ORDERED}.
	 * Overriding implementations should document the reporting of additional
	 * characteristic values.
	 * @return a {@code Spliterator} over the elements in this list
	 */
	@Override
	public Spliterator<E> spliterator() {
		return new BaseListSpliterator(0, -1, 0);
	}


	void checkInvariants() {
		// assert size >= 0;
		// assert size == elementData.length || elementData[size] == null;
	}


	/** A version of rangeCheck used by add and addAll.
	 */
	protected void rangeCheckForAdd(int index) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}


	/** Constructs an IndexOutOfBoundsException detail message.
	 * Of the many possible refactorings of the error handling code,
	 * this "outlining" performs best with both server and client VMs.
	 */
	protected String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}


	/** A version used in checking (fromIndex > toIndex) condition
	 */
	protected static String outOfBoundsMsg(int fromIndex, int toIndex) {
		return "From Index: " + fromIndex + " > To Index: " + toIndex;
	}


	static void subListRangeCheck(int fromIndex, int toIndex, int size) {
		if (fromIndex < 0)
			throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
		if (toIndex > size)
			throw new IndexOutOfBoundsException("toIndex = " + toIndex);
		if (fromIndex > toIndex)
			throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
	}


	private static long[] nBits(int n) {
		return new long[((n - 1) >> 6) + 1];
	}


	private static void setBit(long[] bits, int i) {
		bits[i >> 6] |= 1L << i;
	}


	private static boolean isClear(long[] bits, int i) {
		return (bits[i >> 6] & (1L << i)) == 0;
	}


	/** An optimized version of AbstractList.Itr
	 */
	public class BaseIterator implements Iterator<E> {
		protected int cursor; // index of next element to return
		protected int lastRet = -1; // index of last element returned; -1 if no such
		protected int expectedModCount = modCount;

		// prevent creating a synthetic constructor
		BaseIterator() {}


		@Override
		public boolean hasNext() {
			return cursor != size;
		}


		@Override
		@SuppressWarnings("unchecked")
		public E next() {
			checkModCount();
			int i = cursor;
			if (i >= size)
				throw new NoSuchElementException();
			Object[] elementData = BaseList.this.elements;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i + 1;
			return (E) elementData[lastRet = i];
		}


		@Override
		public void remove() {
			if (lastRet < 0)
				throw new IllegalStateException();
			checkModCount();

			try {
				BaseList.this.remove(lastRet);
				cursor = lastRet;
				lastRet = -1;
				expectedModCount = modCount;
			}
			catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}


		@Override
		public void forEachRemaining(Consumer<? super E> action) {
			Objects.requireNonNull(action);
			final int size = BaseList.this.size;
			int i = cursor;
			if (i < size) {
				final Object[] es = elements;
				if (i >= es.length)
					throw new ConcurrentModificationException();
				for (; i < size && modCount == expectedModCount; i++)
					action.accept(elementAt(es, i));
				// update once at end to reduce heap write traffic
				cursor = i;
				lastRet = i - 1;
				checkModCount();
			}
		}


		final void checkModCount() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}


	/** An optimized version of AbstractList.ListItr
	 */
	public class BaseListIterator extends BaseIterator implements ListIterator<E> {

		BaseListIterator(int index) {
			super();
			cursor = index;
		}


		@Override
		public boolean hasPrevious() {
			return cursor != 0;
		}


		@Override
		public int nextIndex() {
			return cursor;
		}


		@Override
		public int previousIndex() {
			return cursor - 1;
		}


		@Override
		@SuppressWarnings("unchecked")
		public E previous() {
			checkModCount();
			int i = cursor - 1;
			if (i < 0)
				throw new NoSuchElementException();
			Object[] elementData = BaseList.this.elements;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i;
			return (E) elementData[lastRet = i];
		}


		@Override
		public void set(E e) {
			if (lastRet < 0)
				throw new IllegalStateException();
			checkModCount();

			try {
				BaseList.this.set(lastRet, e);
			}
			catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}


		@Override
		public void add(E e) {
			checkModCount();

			try {
				int i = cursor;
				BaseList.this.add(i, e);
				cursor = i + 1;
				lastRet = -1;
				expectedModCount = modCount;
			}
			catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}


		// custom: WARNING: use at your own risk - allow iterator position to be reset; e.g. for reuse in critical performance situations
		public void reset(int mark) {
			checkModCount();
			if(mark < 0 || mark > size) {
				throw new IndexOutOfBoundsException(mark + " of [0, " + size + "]");
			}
			cursor = mark;
			lastRet = -1;
		}

	}


	public static class SubList<E> extends AbstractList<E> implements RandomAccess, ListReadOnly<E> {
		protected final BaseList<E> root;
		protected final SubList<E> parent;
		protected final int offset;
		protected int size;


		/** Constructs a sublist of an arbitrary BaseList.
		 */
		public SubList(BaseList<E> root, int fromIndex, int toIndex) {
			this.root = root;
			this.parent = null;
			this.offset = fromIndex;
			this.size = toIndex - fromIndex;
			this.modCount = root.modCount;
		}


		/** Constructs a sublist of another SubList.
		 */
		protected SubList(SubList<E> parent, int fromIndex, int toIndex) {
			this.root = parent.root;
			this.parent = parent;
			this.offset = parent.offset + fromIndex;
			this.size = toIndex - fromIndex;
			this.modCount = root.modCount;
		}


		@Override
		public E set(int index, E element) {
			Objects.checkIndex(index, size);
			checkModCount();
			E oldValue = root.elementData(offset + index);
			root.elements[offset + index] = element;
			return oldValue;
		}


		@Override
		public E get(int index) {
			Objects.checkIndex(index, size);
			checkModCount();
			return root.elementData(offset + index);
		}


		@Override
		public int size() {
			checkModCount();
			return size;
		}


		@Override
		public void add(int index, E element) {
			rangeCheckForAdd(index);
			checkModCount();
			root.add(offset + index, element);
			updateSizeAndModCount(1);
		}


		@Override
		public E remove(int index) {
			Objects.checkIndex(index, size);
			checkModCount();
			E result = root.remove(offset + index);
			updateSizeAndModCount(-1);
			return result;
		}


		@Override
		protected void removeRange(int fromIndex, int toIndex) {
			checkModCount();
			root.removeRange(offset + fromIndex, offset + toIndex);
			updateSizeAndModCount(fromIndex - toIndex);
		}


		@Override
		public boolean addAll(Collection<? extends E> c) {
			return addAll(this.size, c);
		}


		@Override
		public boolean addAll(int index, Collection<? extends E> c) {
			rangeCheckForAdd(index);
			int cSize = c.size();
			if (cSize==0)
				return false;
			checkModCount();
			root.addAll(offset + index, c);
			updateSizeAndModCount(cSize);
			return true;
		}


		@Override
		public boolean removeAll(Collection<?> c) {
			return batchRemove(c, false);
		}


		@Override
		public boolean retainAll(Collection<?> c) {
			return batchRemove(c, true);
		}


		protected boolean batchRemove(Collection<?> c, boolean complement) {
			checkModCount();
			int oldSize = root.size;
			boolean modified =
				root.batchRemove(c, complement, offset, offset + size);
			if (modified)
				updateSizeAndModCount(root.size - oldSize);
			return modified;
		}


		@Override
		public boolean removeIf(Predicate<? super E> filter) {
			checkModCount();
			int oldSize = root.size;
			boolean modified = root.removeIf(filter, offset, offset + size);
			if (modified)
				updateSizeAndModCount(root.size - oldSize);
			return modified;
		}


		@Override
		public Iterator<E> iterator() {
			return listIterator();
		}


		@Override
		public ListIterator<E> listIterator(int index) {
			checkModCount();
			rangeCheckForAdd(index);

			return new ListIterator<E>() {
				int cursor = index;
				int lastRet = -1;
				int expectedModCount = root.modCount;

				@Override
				public boolean hasNext() {
					return cursor != SubList.this.size;
				}

				@Override
				@SuppressWarnings("unchecked")
				public E next() {
					checkModCount();
					int i = cursor;
					if (i >= SubList.this.size)
						throw new NoSuchElementException();
					Object[] elementData = root.elements;
					if (offset + i >= elementData.length)
						throw new ConcurrentModificationException();
					cursor = i + 1;
					return (E) elementData[offset + (lastRet = i)];
				}

				@Override
				public boolean hasPrevious() {
					return cursor != 0;
				}

				@Override
				@SuppressWarnings("unchecked")
				public E previous() {
					checkModCount();
					int i = cursor - 1;
					if (i < 0)
						throw new NoSuchElementException();
					Object[] elementData = root.elements;
					if (offset + i >= elementData.length)
						throw new ConcurrentModificationException();
					cursor = i;
					return (E) elementData[offset + (lastRet = i)];
				}

				@Override
				public void forEachRemaining(Consumer<? super E> action) {
					Objects.requireNonNull(action);
					final int size = SubList.this.size;
					int i = cursor;
					if (i < size) {
						final Object[] es = root.elements;
						if (offset + i >= es.length)
							throw new ConcurrentModificationException();
						for (; i < size && modCount == expectedModCount; i++)
							action.accept(elementAt(es, offset + i));
						// update once at end to reduce heap write traffic
						cursor = i;
						lastRet = i - 1;
						checkModCount();
					}
				}

				@Override
				public int nextIndex() {
					return cursor;
				}

				@Override
				public int previousIndex() {
					return cursor - 1;
				}

				@Override
				public void remove() {
					if (lastRet < 0)
						throw new IllegalStateException();
					checkModCount();

					try {
						SubList.this.remove(lastRet);
						cursor = lastRet;
						lastRet = -1;
						expectedModCount = root.modCount;
					}
					catch (IndexOutOfBoundsException ex) {
						throw new ConcurrentModificationException();
					}
				}

				@Override
				public void set(E e) {
					if (lastRet < 0)
						throw new IllegalStateException();
					checkModCount();

					try {
						root.set(offset + lastRet, e);
					}
					catch (IndexOutOfBoundsException ex) {
						throw new ConcurrentModificationException();
					}
				}

				@Override
				public void add(E e) {
					checkModCount();

					try {
						int i = cursor;
						SubList.this.add(i, e);
						cursor = i + 1;
						lastRet = -1;
						expectedModCount = root.modCount;
					}
					catch (IndexOutOfBoundsException ex) {
						throw new ConcurrentModificationException();
					}
				}

				final void checkModCount() {
					if (root.modCount != expectedModCount)
						throw new ConcurrentModificationException();
				}
			};
		}


		@Override
		public List<E> subList(int fromIndex, int toIndex) {
			subListRangeCheck(fromIndex, toIndex, size);
			return new SubList<>(this, fromIndex, toIndex);
		}


		protected void rangeCheckForAdd(int index) {
			if (index < 0 || index > this.size)
				throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		}


		protected String outOfBoundsMsg(int index) {
			return "Index: " + index + ", Size: " + this.size;
		}


		protected void checkModCount() {
			if (root.modCount != modCount)
				throw new ConcurrentModificationException();
		}


		protected void updateSizeAndModCount(int sizeChange) {
			SubList<E> slist = this;
			do {
				slist.size += sizeChange;
				slist.modCount = root.modCount;
				slist = slist.parent;
			} while (slist != null);
		}


		@Override
		public Spliterator<E> spliterator() {
			checkModCount();

			// BaseListSpliterator not used here due to late-binding
			return new Spliterator<E>() {
				private int index = offset; // current index, modified on advance/split
				private int fence = -1; // -1 until used; then one past last index
				private int expectedModCount; // initialized when fence set

				private int getFence() {
					// initialize fence to size on first use
					int hi = fence; // (a specialized variant appears in method forEach)
					if (hi < 0) {
						expectedModCount = modCount;
						hi = fence = offset + size;
					}
					return hi;
				}

				@Override
				public BaseList<E>.BaseListSpliterator trySplit() {
					int hi = getFence();
					int lo = index;
					int mid = (lo + hi) >>> 1;
					// BaseListSpliterator can be used here as the source is already bound
					// divide range in half unless too small
					return (lo >= mid) ? null : root.new BaseListSpliterator(lo, index = mid, expectedModCount);
				}

				@Override
				public boolean tryAdvance(Consumer<? super E> action) {
					Objects.requireNonNull(action);
					int hi = getFence(), i = index;
					if (i < hi) {
						index = i + 1;
						@SuppressWarnings("unchecked")
						E e = (E)root.elements[i];
						action.accept(e);
						if (root.modCount != expectedModCount)
							throw new ConcurrentModificationException();
						return true;
					}
					return false;
				}

				@Override
				public void forEachRemaining(Consumer<? super E> action) {
					Objects.requireNonNull(action);
					int i, hi, mc; // hoist accesses and checks from loop
					BaseList<E> lst = root;
					Object[] a;
					if ((a = lst.elements) != null) {
						if ((hi = fence) < 0) {
							mc = modCount;
							hi = offset + size;
						}
						else {
							mc = expectedModCount;
						}

						if ((i = index) >= 0 && (index = hi) <= a.length) {
							for (; i < hi; ++i) {
								@SuppressWarnings("unchecked")
								E e = (E) a[i];
								action.accept(e);
							}
							if (lst.modCount == mc)
								return;
						}
					}
					throw new ConcurrentModificationException();
				}

				@Override
				public long estimateSize() {
					return getFence() - index;
				}

				@Override
				public int characteristics() {
					return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
				}
			};
		}
	}


	/** Index-based split-by-two, lazily initialized Spliterator
	 */
	final class BaseListSpliterator implements Spliterator<E> {

		/*
		 * If BaseList was immutable (no add, remove, set), spliterators
		 * could be implement with Arrays.spliterator. Modification counters
		 * (mod counts) are used to detect changes during traversal. Mod counts
		 * are not guaranteed to detect concurrent changes, and may produce
		 * inter-thread false positives, but detect enough problems to
		 * be a good trade off between performance and correctness in practice.
		 * This is implemented by (1) lazily initializing 'fence' and
		 * 'expectedModCount' until the latest point that we need to track the
		 * state we are checking against. (This doesn't apply to SubLists, that
		 * create spliterators with current non-lazy values).
		 * (2) Only a single ConcurrentModificationException check is performed
		 * at the end of forEach (the most performance-sensitive method).
		 * When using forEach (as opposed to iterators), we can normally only
		 * detect interference after actions, not before. Further
		 * ConcurrentModificationException-triggering checks apply to all
		 * other possible violations of assumptions for example null or too-small
		 * elementData array given its size(), that could only have occurred
		 * due to interference.  This allows the inner loop of forEach to run
		 * without any further checks, and simplifies lambda-resolution.
		 * While this does entail a number of checks, note that in the common
		 * case of list.stream().forEach(a), no checks or other computation
		 * occur anywhere other than inside forEach itself.  The other
		 * less-often-used methods cannot take advantage of most of
		 * these streamlinings.
		 */

		private int index; // current index, modified on advance/split
		private int fence; // -1 until used; then one past last index
		private int expectedModCount; // initialized when fence set

		/** Creates new spliterator covering the given range.
		 */
		BaseListSpliterator(int origin, int fence, int expectedModCount) {
			this.index = origin;
			this.fence = fence;
			this.expectedModCount = expectedModCount;
		}

		private int getFence() {
			// initialize fence to size on first use
			int hi = fence; // (a specialized variant appears in method forEach)
			if (hi < 0) {
				expectedModCount = modCount;
				hi = fence = size;
			}
			return hi;
		}

		@Override
		public BaseListSpliterator trySplit() {
			int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
			// divide range in half unless too small
			return (lo >= mid) ? null : new BaseListSpliterator(lo, index = mid, expectedModCount);
		}

		@Override
		public boolean tryAdvance(Consumer<? super E> action) {
			if (action == null)
				throw new NullPointerException();
			int hi = getFence(), i = index;
			if (i < hi) {
				index = i + 1;
				@SuppressWarnings("unchecked")
				E e = (E)elements[i];
				action.accept(e);
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
				return true;
			}
			return false;
		}

		@Override
		public void forEachRemaining(Consumer<? super E> action) {
			int i, hi, mc; // hoist accesses and checks from loop
			Object[] a;
			if (action == null)
				throw new NullPointerException();
			if ((a = elements) != null) {
				if ((hi = fence) < 0) {
					mc = modCount;
					hi = size;
				}
				else {
					mc = expectedModCount;
				}

				if ((i = index) >= 0 && (index = hi) <= a.length) {
					for (; i < hi; ++i) {
						@SuppressWarnings("unchecked")
						E e = (E) a[i];
						action.accept(e);
					}
					if (modCount == mc)
						return;
				}
			}
			throw new ConcurrentModificationException();
		}

		@Override
		public long estimateSize() {
			return getFence() - index;
		}

		@Override
		public int characteristics() {
			return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
		}
	}

}
