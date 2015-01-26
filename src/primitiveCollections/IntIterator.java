package primitiveCollections;

/** A primitive int {@link java.util.ListIterator}
 * @author TeamworkGuy2
 * @since 2015-1-17
 */
@javax.annotation.Generated("StringTemplate")
public interface IntIterator {


	public boolean hasNext();


	public int next();


	public boolean hasPrevious();


	public int previous();


	public int nextIndex();


	public int previousIndex();


	public void remove();


	public void set(int val);


	public void add(int e);

}
