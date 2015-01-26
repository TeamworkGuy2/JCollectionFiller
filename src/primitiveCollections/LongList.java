package primitiveCollections;

/** An interface for class that wrap long arrays.  This interface provides
 * methods for getting, setting and removing values from the Long array.
 * @author TeamworkGuy2
 * @since 2013-1-20
 */
@javax.annotation.Generated("StringTemplate")
public interface LongList extends java.util.RandomAccess {

	/** Create a copy of this long list
	 * @return a copy of this long list
	 */
	public LongList copy();


	/** Get the long at the specified index
	 * @param index the index within the range {@code [0, }{@link #size()}{@code -1]} inclusive to retrieve
	 * @return the long found at the specified index
	 */
	public long get(int index);


	/** Check if the specified values is contained in this list of longs
	 * @param value the value to check for in this list
	 * @return true if the value was found in the list, false otherwise
	 */
	public boolean contains(long value);


	/** Find the first occurring index of the specified long in this list
	 * @param value the value to search for in this list
	 * @return an index between {@code [0, }{@link #size()}{@code -1]} if the value
	 * is found, or -1 if the value cannot be found
	 */
	public int indexOf(long value);


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


	/** Add the specified item to this list of elements
	 * @param item the item to add to this list of elements
	 */
	public boolean add(long item);


	/** Clear the list of elements
	 */
	public void clear();


	/** Get the current size of this list of elements
	 * @return the size of this list of elements
	 */
	public int size();


	/** Is this list of elements empty
	 * @return true if this list of elements is empty, false otherwise
	 */
	public boolean isEmpty();


	public long[] toArray();


	public long[] toArray(long[] dst, int dstOffset);


	public String toString();

}
