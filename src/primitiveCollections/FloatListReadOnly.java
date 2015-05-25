package primitiveCollections;

/** An interface for class that wrap float arrays.  This interface provides
 * methods for getting values from the Float array.
 * @author TeamworkGuy2
 * @since 2015-5-24
 */
@javax.annotation.Generated("StringTemplate")
public interface FloatListReadOnly extends java.util.RandomAccess {

	/** Create a copy of this float list
	 * @return a copy of this float list
	 */
	public FloatListReadOnly copy();


	/** Get the float at the specified index
	 * @param index the index within the range {@code [0, }{@link #size()}{@code -1]} inclusive to retrieve
	 * @return the float found at the specified index
	 */
	public float get(int index);


	/** Check if the specified values is contained in this list of floats
	 * @param value the value to check for in this list
	 * @return true if the value was found in the list, false otherwise
	 */
	public boolean contains(float value);


	/** Find the first occurring index of the specified float in this list
	 * @param value the value to search for in this list
	 * @return an index between {@code [0, }{@link #size()}{@code -1]} if the value
	 * is found, or -1 if the value cannot be found
	 */
	public int indexOf(float value);


	/** Get the current size of this collection
	 * @return the size of this collection
	 */
	public int size();


	/** Check if this collection is empty
	 * @return true if this has zero elements, false otherwise
	 */
	public boolean isEmpty();


	public float[] toArray();


	public float[] toArray(float[] dst, int dstOffset);


	@Override
	public String toString();

}
