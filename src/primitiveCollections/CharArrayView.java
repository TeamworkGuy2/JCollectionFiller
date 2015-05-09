package primitiveCollections;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/** An {@link List} backed by an array.<br>
 * All modify methods, such as {@link #add(char) add()} and {@link #remove(int) remove()}
 * throw {@link UnsupportedOperationException} except for {@link #set(int, char) set()}
 * which maybe be enabled or disabled when an instance of this class is created.<br>
 * {@link CharArrayViewHandle} provides a way to manage an {@code ArrayView} and replace the backing
 * array without creating a new {@code ArrayView}.
 * @see CharArrayViewHandle
 * @author TeamworkGuy2
 * @since 2014-11-29
 */
public final class CharArrayView implements CharList, java.util.RandomAccess, Iterable<Character> {
	char[] objs;
	int off;
	int len;
	volatile int mod;
	private final boolean allowSet;


	/** Create an empty array view
	 */
	public CharArrayView() {
		this(null, 0, 0);
	}


	/** Create an empty array view
	 * @param allowSet true to allow {@link #set(int, char) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public CharArrayView(boolean allowSet) {
		this(null, 0, 0, false);
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 */
	public CharArrayView(char[] objs) {
		this(objs, 0, objs.length, false);
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 * @param allowSet true to allow {@link #set(int, char) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public CharArrayView(char[] objs, boolean allowSet) {
		this(objs, 0, objs.length, allowSet);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 */
	public CharArrayView(char[] objs, int offset, int length) {
		this(objs, offset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow {@link #set(int, char) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public CharArrayView(char[] objs, int offset, int length, boolean allowSet) {
		this.objs = objs;
		this.off = offset;
		this.len = length;
		this.allowSet = allowSet;
	}


	@Override
	public char get(int index) {
		if(off + index > len) {
			throw new IndexOutOfBoundsException();
		}
		char obj = objs[off + index];
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
	public boolean contains(char o) {
		return indexOf(o) > -1;
	}


	@Override
	public int indexOf(char o) {
		int modCached = mod;
		for(int i = off, size = off + len; i < size; i++) {
			if(o == objs[i]) {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				return i;
			}
		}
		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return -1;
	}


	public int lastIndexOf(char o) {
		int modCached = mod;
		for(int i = off + len - 1; i >= off; i--) {
			if(o == objs[i]) {
				if(modCached != mod) {
					throw new ConcurrentModificationException();
				}
				return i;
			}
		}
		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return -1;
	}


	@Override
	public Iterator<Character> iterator() {
		return listIterator(0);
	}


	public ListIterator<Character> listIterator() {
		return listIterator(0);
	}


	public ListIterator<Character> listIterator(int idx) {
		return new CharIteratorWrapper(new CharArrayIterator(objs, off, len, idx));
	}


	@Override
	public char[] toArray() {
		return Arrays.copyOfRange(objs, off, off+len);
	}


	@Override
	public char[] toArray(char[] dst, int dstOffset) {
		System.arraycopy(objs, off, dst, dstOffset, len);
		return dst;
	}


	public boolean containsAll(Collection<Character> c) {
		int modCached = mod;
		for(Character obj : c) {
			if(!contains(obj)) {
				return false;
			}
		}
		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return true;
	}


	@Override
	public void add(char e) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public boolean removeValue(char o) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public String toString() {
		int modCached = mod;
		StringBuilder strB = new StringBuilder();
		strB.append('[');
		if(len > 0) {
			int count = off + len - 1;
			for(int i = off; i < count; i++) {
				strB.append(objs[i]);
				strB.append(", ");
			}
			strB.append(objs[count]);
		}
		strB.append(']');

		if(modCached != mod) {
			throw new ConcurrentModificationException();
		}
		return strB.toString();
	}


	public char set(int index, char element) {
		if(!allowSet) {
			throw new UnsupportedOperationException("cannot modified immutable view");
		}
		if(index < 0 || index >= len) {
			throw new IndexOutOfBoundsException(index + " of view size " + len);
		}
		char oldVal = objs[off + index];
		objs[off + index] = element;
		mod++;
		return oldVal;
	}


	public void add(int index, char element) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


	@Override
	public void addAll(char... items) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


	@Override
	public void addAll(char[] items, int off, int len) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


	@Override
	public void addAll(CharList coll) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


	@Override
	public char remove(int index) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


		@Override
	public CharArrayList copy() {
		return new CharArrayList(toArray());
	}


	@Override
	public void clear() {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}

}
