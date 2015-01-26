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


	/** Add the specified item to this list of elements
	 * @param item the item to add to this list of elements
	 */
	public boolean add(double item);


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


	public double[] toArray();


	public double[] toArray(double[] dst, int dstOffset);


	public String toString();

}
