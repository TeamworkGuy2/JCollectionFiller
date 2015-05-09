package primitiveCollections;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/** An {@link List} backed by an array.<br>
 * All modify methods, such as {@link #add(float) add()} and {@link #remove(int) remove()}
 * throw {@link UnsupportedOperationException} except for {@link #set(int, float) set()}
 * which maybe be enabled or disabled when an instance of this class is created.<br>
 * {@link FloatArrayViewHandle} provides a way to manage an {@code ArrayView} and replace the backing
 * array without creating a new {@code ArrayView}.
 * @see FloatArrayViewHandle
 * @author TeamworkGuy2
 * @since 2014-11-29
 */
public final class FloatArrayView implements FloatList, java.util.RandomAccess, Iterable<Float> {
	float[] objs;
	int off;
	int len;
	volatile int mod;
	private final boolean allowSet;


	/** Create an empty array view
	 */
	public FloatArrayView() {
		this(null, 0, 0);
	}


	/** Create an empty array view
	 * @param allowSet true to allow {@link #set(int, float) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public FloatArrayView(boolean allowSet) {
		this(null, 0, 0, false);
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 */
	public FloatArrayView(float[] objs) {
		this(objs, 0, objs.length, false);
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 * @param allowSet true to allow {@link #set(int, float) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public FloatArrayView(float[] objs, boolean allowSet) {
		this(objs, 0, objs.length, allowSet);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 */
	public FloatArrayView(float[] objs, int offset, int length) {
		this(objs, offset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow {@link #set(int, float) set()} to be called,
	 * false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public FloatArrayView(float[] objs, int offset, int length, boolean allowSet) {
		this.objs = objs;
		this.off = offset;
		this.len = length;
		this.allowSet = allowSet;
	}


	@Override
	public float get(int index) {
		if(off + index > len) {
			throw new IndexOutOfBoundsException();
		}
		float obj = objs[off + index];
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
	public boolean contains(float o) {
		return indexOf(o) > -1;
	}


	@Override
	public int indexOf(float o) {
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


	public int lastIndexOf(float o) {
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
	public Iterator<Float> iterator() {
		return listIterator(0);
	}


	public ListIterator<Float> listIterator() {
		return listIterator(0);
	}


	public ListIterator<Float> listIterator(int idx) {
		return new FloatIteratorWrapper(new FloatArrayIterator(objs, off, len, idx));
	}


	@Override
	public float[] toArray() {
		return Arrays.copyOfRange(objs, off, off+len);
	}


	@Override
	public float[] toArray(float[] dst, int dstOffset) {
		System.arraycopy(objs, off, dst, dstOffset, len);
		return dst;
	}


	public boolean containsAll(Collection<Float> c) {
		int modCached = mod;
		for(Float obj : c) {
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
	public void add(float e) {
		throw new UnsupportedOperationException("cannot modified immutable view");
	}


	@Override
	public boolean removeValue(float o) {
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


	public float set(int index, float element) {
		if(!allowSet) {
			throw new UnsupportedOperationException("cannot modified immutable view");
		}
		if(index < 0 || index >= len) {
			throw new IndexOutOfBoundsException(index + " of view size " + len);
		}
		float oldVal = objs[off + index];
		objs[off + index] = element;
		mod++;
		return oldVal;
	}


	public void add(int index, float element) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


	@Override
	public void addAll(float... items) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


	@Override
	public void addAll(float[] items, int off, int len) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


	@Override
	public void addAll(FloatList coll) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


	@Override
	public float remove(int index) {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}


		@Override
	public FloatArrayList copy() {
		return new FloatArrayList(toArray());
	}


	@Override
	public void clear() {
		throw new UnsupportedOperationException("cannot modify immutable view");
	}

}
