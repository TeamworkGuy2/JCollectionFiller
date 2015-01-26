package primitiveCollections;

/** A primitive long {@link java.util.ListIterator}
 * @author TeamworkGuy2
 * @since 2015-1-17
 */
@javax.annotation.Generated("StringTemplate")
public interface LongIterator {


	public boolean hasNext();


	public long next();


	public boolean hasPrevious();


	public long previous();


	public int nextIndex();


	public int previousIndex();


	public void remove();


	public void set(long val);


	public void add(long e);

}
