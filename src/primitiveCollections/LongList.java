package primitiveCollections;

/** An interface for class that wrap long arrays.  This interface provides
 * methods for getting, setting and removing values from the Long array.
 * @author TeamworkGuy2
 * @since 2013-1-20
 */
@javax.annotation.Generated("StringTemplate")
public interface LongList extends java.util.RandomAccess, LongListReadOnly {

	@Override
	public LongList copy();


	/** Remove the long at the specified index
	 * @param index the index within the range {@code [0, }{@link #size()}{@code -1]}
	 * inclusive to remove
	 * @return the long found at the specified index
	 */
	public long remove(int index);


	/** Remove the specified value from this list
	 * @param item the value to remove
	 * @return true if the value was found and removed successfully, false otherwise
	 */
	public boolean removeValue(long item);


	/** Add the specified item to this collection
	 * @param item the item to add to this collection
	 */
	public void add(long item);


	/** Add an array of items to this collection
	 * @param items the array of items to add to this collection
	 */
	public void addAll(long... items);


	/** Add an array of items to this collection
	 * @param coll the collection of items to add to this collection
	 */
	public void addAll(LongList coll);


	/** Add the specified array of items to this collection
	 * @param items the array of items to add to this collection
	 * @param off the {@code items} offset at which to start adding items to this collection
	 * @param len the number of items to add from {@code items} into this collection
	 */
	public void addAll(long[] items, int off, int len);


	/** Clear the list of elements
	 */
	public void clear();

}
