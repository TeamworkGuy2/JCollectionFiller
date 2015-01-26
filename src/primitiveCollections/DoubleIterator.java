package primitiveCollections;

/** A primitive double {@link java.util.ListIterator}
 * @author TeamworkGuy2
 * @since 2015-1-17
 */
@javax.annotation.Generated("StringTemplate")
public interface DoubleIterator {


	public boolean hasNext();


	public double next();


	public boolean hasPrevious();


	public double previous();


	public int nextIndex();


	public int previousIndex();


	public void remove();


	public void set(double val);


	public void add(double e);

}
