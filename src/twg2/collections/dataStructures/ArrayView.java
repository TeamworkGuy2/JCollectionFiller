package twg2.collections.dataStructures;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import twg2.arrays.ArrayUtil;
import twg2.collections.interfaces.RandomAccessCollection;

/** An {@link List} backed by an array.<br>
 * All modify methods, such as {@link #add(Object) add()} and {@link #remove(Object) remove()}
 * throw {@link UnsupportedOperationException} except for {@link #set(int, Object) set()}
 * which maybe be enabled or disabled when an instance of this class is created.<br>
 * {@link ArrayViewHandle} provides a way to manage an {@code ArrayView} and replace the backing
 * array without creating a new {@code ArrayView}.
 * @see ArrayViewHandle
 * @author TeamworkGuy2
 * @since 2014-11-29
 */
public final class ArrayView<E> implements List<E>, RandomAccessCollection<E> {
	private Object[] objs;
	private int off;
	private int len;
	private volatile int mod;
	private final boolean allowSet;


	/** Create an empty array view
	 */
	public ArrayView() {
		this(null, 0, 0);
	}


	/** Create an empty array view
	 * @param allowSet true to allow {@link #set(int, Object) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public ArrayView(boolean allowSet) {
		this(null, 0, 0, false);
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 */
	public ArrayView(E[] objs) {
		this(objs, 0, objs.length, false);
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 * @param allowSet true to allow {@link #set(int, Object) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public ArrayView(E[] objs, boolean allowSet) {
		this(objs, 0, objs.length, allowSet);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 */
	public ArrayView(E[] objs, int offset, int length) {
		this(objs, offset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow {@link #set(int, Object) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public ArrayView(E[] objs, int offset, int length, boolean allowSet) {
		this.setArrayView(objs, offset, length);

		this.allowSet = allowSet;
	}


	// package-private
	/** This function should only be invoked from code in this package
	 */
	void setArrayView(E[] objs, int offset, int length) {
		this.mod++;
		this.objs = objs;
		this.off = offset;
		this.len = length;
	}


	@Override
	public E get(int index) {
		checkIndex(index);
		@SuppressWarnings("unchecked")
		E obj = (E)objs[off + index];
		return obj;
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
	public boolean contains(Object o) {
		return indexOf(o) > -1;
	}


	@Override
	public int indexOf(Object o) {
		for(int i = off, size = off + len; i < size; i++) {
			if(o != null ? o.equals(objs[i]) : objs[i] == null) {
				return i - off;
			}
		}
		return -1;
	}


	@Override
	public int lastIndexOf(Object o) {
		for(int i = off + len - 1; i >= off; i--) {
			if(o != null ? o.equals(objs[i]) : objs[i] == null) {
				return i - off;
			}
		}
		return -1;
	}


	@Override
	public Iterator<E> iterator() {
		return listIterator(0);
	}


	@Override
	public ListIterator<E> listIterator() {
		return listIterator(0);
	}


	@Override
	public ListIterator<E> listIterator(int idx) {
		return new ListIterator<E>() {
			private int modCached = mod;
			private int index = off + idx - 1;
			private int size = off + len;


			@Override
			public boolean hasNext() {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				return index < size - 1;
			}


			@Override
			public E next() {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				if(index >= size - 1) {
					throw new IllegalStateException("already at end of list");
				}
				index++;
				@SuppressWarnings("unchecked")
				E obj = (E)objs[index];
				return obj;
			}


			@Override
			public boolean hasPrevious() {
				return index > off;
			}


			@Override
			public E previous() {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				if(index <= off) {
					throw new IllegalStateException("already at beginning of list");
				}
				index--;
				@SuppressWarnings("unchecked")
				E obj = (E)objs[index];
				return obj;
			}


			@Override
			public int nextIndex() {
				return index + 1 - off;
			}


			@Override
			public int previousIndex() {
				return index - 1 - off;
			}


			@Override
			public void remove() {
				throw new UnsupportedOperationException("cannot modified immutable list iterator");
			}


			@Override
			public void set(E e) {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				ArrayView.this.set(index, e);
				this.modCached = mod;
			}


			@Override
			public void add(E e) {
				throw new UnsupportedOperationException("cannot modified immutable list iterator");
			}
		};
	}


	@Override
	public Object[] toArray() {
		return Arrays.copyOfRange(objs, off, off+len);
	}


	@Override
	public <T> T[] toArray(T[] a) {
		if(a.length < len) {
			@SuppressWarnings("unchecked")
			Class<T[]> type = (Class<T[]>)a.getClass();
			return (T[])Arrays.copyOfRange(objs, off, off+len, type);
		}
		System.arraycopy(objs, off, a, 0, len);
		if(a.length > len) {
			a[len] = null;
		}
		return a;
	}


	@Override
	public boolean containsAll(Collection<?> c) {
		for(Object obj : c) {
			if(!contains(obj)) {
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public void clear() {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public String toString() {
		String str = ArrayUtil.toString(objs, off, len);
		return str;
	}


	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public E set(int index, E element) {
		if(!allowSet) {
			throw new UnsupportedOperationException("cannot modified immutable view");
		}
		checkIndex(index);
		objs[off + index] = element;
		mod++;
		return null;
	}


	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException("cannot create sub list of view");
	}


	private final void checkIndex(int index) {
		if(index < 0 || index >= len) {
			throw new IndexOutOfBoundsException(index + " of view size " + len);
		}
	}

}
