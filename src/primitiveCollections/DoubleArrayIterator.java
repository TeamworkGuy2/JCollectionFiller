package primitiveCollections;

/** A double array {@link java.util.ListIterator} that supports {@link #previous} and {@link #set}.
 * {@link #add} and {@link #remove} are not supported
 * @author TeamworkGuy2
 * @since 2015-1-17
 */
@javax.annotation.Generated("StringTemplate")
public class DoubleArrayIterator implements DoubleIterator {
	private int mod = 0;
	private volatile int modCached = 0;
	private double[] col;
	private int off;
	private int size;
	private int index;


	public DoubleArrayIterator(double[] col, int offStartIndex) {
		this(col, 0, col.length, offStartIndex);
	}


	/** Create an iterator over a set of indices
	 * @param col the array of values to iterate over
	 * @param off the minimum index
	 * @param len maximum iterator index is {@code len + off} 
	 * @param offStartIndex the index to begin the iterator at
	 * false to thrown an {@link UnsupportedOperationException}
	 */
	public DoubleArrayIterator(double[] col, int off, int len, int offStartIndex) {
		this.col = col;
		this.off = off;
		this.size = off + len;
		this.index = off + offStartIndex - 1; // start at the previous index since next() is called get the first item
	}


	@Override
	public boolean hasNext() {
		checkMod();
		return index < size - 1;
	}


	@Override
	public double next() {
		checkMod();
		if(index >= size - 1) {
			throw new IllegalStateException("already at end of list");
		}
		index++;
		double val = (double)col[index];
		return val;
	}


	@Override
	public boolean hasPrevious() {
		return index > off;
	}


	@Override
	public double previous() {
		checkMod();
		if(index <= off) {
			throw new IllegalStateException("already at beginning of list");
		}
		index--;
		double val = (double)col[index];
		return val;
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
	public void set(double val) {
		throw new UnsupportedOperationException("cannot modified immutable list iterator");
	}


	@Override
	public void add(double e) {
		throw new UnsupportedOperationException("cannot modified immutable list iterator");
	}


	protected final void checkMod() {
		if(modCached != mod) {
			throw new java.util.ConcurrentModificationException();
		}
	}


	protected final void updateMod() {
		modCached = mod;
	}


	public void markModified() {
		mod++;
	}

}
