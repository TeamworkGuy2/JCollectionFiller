package primitiveCollections;

/** A char array {@link java.util.ListIterator} that supports {@link #previous} and {@link #set}.
 * {@link #add} and {@link #remove} are not supported
 * @author TeamworkGuy2
 * @since 2015-1-17
 */
@javax.annotation.Generated("StringTemplate")
public class CharIteratorWrapper implements java.util.ListIterator<Character> {
	private CharIterator iter;


	/** Create an iterator over a set of indices
	 * @param iter the primitive iterator to iterate over
	 */
	public CharIteratorWrapper(CharIterator iter) {
		this.iter = iter;
	}


	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}


	@Override
	public Character next() {
		return iter.next();
	}


	@Override
	public boolean hasPrevious() {
		return iter.hasPrevious();
	}


	@Override
	public Character previous() {
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
	public void set(Character val) {
		iter.set(val);
	}


	@Override
	public void add(Character val) {
		iter.add(val);
	}

}
