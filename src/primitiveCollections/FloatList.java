package primitiveCollections;

/** An interface for class that wrap float arrays.  This interface provides
 * methods for getting, setting and removing values from the Float array.
 * @author TeamworkGuy2
 * @since 2013-1-20
 */
@javax.annotation.Generated("StringTemplate")
public interface FloatList extends java.util.RandomAccess, FloatListReadOnly {

	@Override
	public FloatList copy();


	/** Remove the float at the specified index
	 * @param index the index within the range {@code [0, }{@link #size()}{@code -1]}
	 * inclusive to remove
	 * @return the float found at the specified index
	 */
	public float remove(int index);


	/** Remove the specified value from this list
	 * @param item the value to remove
	 * @return true if the value was found and removed successfully, false otherwise
	 */
	public boolean removeValue(float item);


	/** Add the specified item to this collection
	 * @param item the item to add to this collection
	 */
	public void add(float item);


	/** Add an array of items to this collection
	 * @param items the array of items to add to this collection
	 */
	public void addAll(float... items);


	/** Add an array of items to this collection
	 * @param coll the collection of items to add to this collection
	 */
	public void addAll(FloatList coll);


	/** Add the specified array of items to this collection
	 * @param items the array of items to add to this collection
	 * @param off the {@code items} offset at which to start adding items to this collection
	 * @param len the number of items to add from {@code items} into this collection
	 */
	public void addAll(float[] items, int off, int len);


	/** Clear the list of elements
	 */
	public void clear();

}
