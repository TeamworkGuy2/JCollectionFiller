package primitiveCollections;

/** A float array {@link java.util.ListIterator} that supports {@link #previous} and {@link #set}.
 * {@link #add} and {@link #remove} are not supported
 * @author TeamworkGuy2
 * @since 2015-1-17
 */
@javax.annotation.Generated("StringTemplate")
public class FloatIteratorWrapper implements java.util.ListIterator<Float> {
	private FloatIterator iter;


	/** Create an iterator over a set of indices
	 * @param iter the primitive iterator to iterate over
	 */
	public FloatIteratorWrapper(FloatIterator iter) {
		this.iter = iter;
	}


	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}


	@Override
	public Float next() {
		return iter.next();
	}


	@Override
	public boolean hasPrevious() {
		return iter.hasPrevious();
	}


	@Override
	public Float previous() {
		return iter.previous();
	}


	@Override
	public int nextIndex() {
		return iter.nextIndex();
	}


	@Override
	public int previousIndex() {
		return iter.previousIndex();
	}


	@Override
	public void remove() {
		iter.remove();
	}


	@Override
	public void set(Float val) {
		iter.set(val);
	}


	@Override
	public void add(Float val) {
		iter.add(val);
	}

}
