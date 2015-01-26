package primitiveCollections;

/** A primitive char {@link java.util.ListIterator}
 * @author TeamworkGuy2
 * @since 2015-1-17
 */
@javax.annotation.Generated("StringTemplate")
public interface CharIterator {


	public boolean hasNext();


	public char next();


	public boolean hasPrevious();


	public char previous();


	public int nextIndex();


	public int previousIndex();


	public void remove();


	public void set(char val);


	public void add(char e);

}
