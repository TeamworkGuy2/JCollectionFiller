package primitiveCollections;

/** An interface for class that wrap double arrays.  This interface provides
 * methods for getting values from the Double array.
 * @author TeamworkGuy2
 * @since 2015-5-24
 */
@javax.annotation.Generated("StringTemplate")
public interface DoubleListReadOnly extends java.util.RandomAccess {

	/** Create a copy of this double list
	 * @return a copy of this double list
	 */
	public DoubleListReadOnly copy();


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
