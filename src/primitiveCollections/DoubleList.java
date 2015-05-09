package primitiveCollections;

/** An interface for class that wrap double arrays.  This interface provides
 * methods for getting, setting and removing values from the Double array.
 * @author TeamworkGuy2
 * @since 2013-1-20
 */
@javax.annotation.Generated("StringTemplate")
public interface DoubleList extends java.util.RandomAccess {

	/** Create a copy of this double list
	 * @return a copy of this double list
	 */
	public DoubleList copy();


	/** Get the double at the specified index
	 * @param index the index within the range {@code [0, }{@link #size()}{@code -1]} inclusive to retrieve
	 * @return the double found at the specified index
	 */
	public double get(int index);


	/** Check if the specified values is contained in this list of doubles
	 * @param value the value to check for in this list
	 * @return true if the value was found in the list, false otherwise
	 */
	public boolean contains(double value);


	/** Find the first occurring index of the specified double in this list
	 * @param value the value to search for in this list
	 * @return an index between {@code [0, }{@link #size()}{@code -1]} if the value
	 * is found, or -1 if the value cannot be found
	 */
	public int indexOf(double value);


	/** Remove the double at the specified index
	 * @param index the index within the range {@code [0, }{@link #size()}{@code -1]}
	 * inclusive to remove
	 * @return the double found at the specified index
	 */
	public double remove(int index);


	/** Remove the specified value from this list
	 * @param item the value to remove
	 * @return true if the value was found and removed successfully, false otherwise
	 */
	public boolean removeValue(double item);


	/** Add the specified item to this collection
	 * @param item the item to add to this collection
	 */
	public void add(double item);


	/** Add an array of items to this collection
	 * @param items the array of items to add to this collection
	 */
	public void addAll(double... items);


	/** Add an array of items to this collection
	 * @param coll the collection of items to add to this collection
	 */
	public void addAll(DoubleList coll);


	/** Add the specified array of items to this collection
	 * @param items the array of items to add to this collection
	 * @param off the {@code items} offset at which to start adding items to this collection
	 * @param len the number of items to add from {@code items} into this collection
	 */
	public void addAll(double[] items, int off, int len);


	/** Clear the list of elements
	 */
	public void clear();


	/** Get the current size of this collection
	 * @return the size of this collection
	 */
	public int size();


	/** Check if this collection is empty
	 * @return true if this has zero elements, false otherwise
	 */
	public boolean isEmpty();


	public double[] toArray();


	public double[] toArray(double[] dst, int dstOffset);


	@Override
	public String toString();

}
