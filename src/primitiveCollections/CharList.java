package primitiveCollections;

/** An interface for class that wrap char arrays.  This interface provides
 * methods for getting, setting and removing values from the Character array.
 * @author TeamworkGuy2
 * @since 2013-1-20
 */
@javax.annotation.Generated("StringTemplate")
public interface CharList extends java.util.RandomAccess {

	/** Create a copy of this char list
	 * @return a copy of this char list
	 */
	public CharList copy();


	/** Get the char at the specified index
	 * @param index the index within the range {@code [0, }{@link #size()}{@code -1]} inclusive to retrieve
	 * @return the char found at the specified index
	 */
	public char get(int index);


	/** Check if the specified values is contained in this list of chars
	 * @param value the value to check for in this list
	 * @return true if the value was found in the list, false otherwise
	 */
	public boolean contains(char value);


	/** Find the first occurring index of the specified char in this list
	 * @param value the value to search for in this list
	 * @return an index between {@code [0, }{@link #size()}{@code -1]} if the value
	 * is found, or -1 if the value cannot be found
	 */
	public int indexOf(char value);


	/** Remove the char at the specified index
	 * @param index the index within the range {@code [0, }{@link #size()}{@code -1]}
	 * inclusive to remove
	 * @return the char found at the specified index
	 */
	public char remove(int index);


	/** Remove the specified value from this list
	 * @param item the value to remove
	 * @return true if the value was found and removed successfully, false otherwise
	 */
	public boolean removeValue(char item);


	/** Add the specified item to this collection
	 * @param item the item to add to this collection
	 */
	public void add(char item);


	/** Add an array of items to this collection
	 * @param items the array of items to add to this collection
	 */
	public void addAll(char... items);


	/** Add the specified array of items to this collection
	 * @param items the array of items to add to this collection
	 * @param off the {@code items} offset at which to start adding items to this collection
	 * @param len the number of items to add from {@code items} into this collection
	 */
	public void addAll(char[] items, int off, int len);


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


	public char[] toArray();


	public char[] toArray(char[] dst, int dstOffset);


	@Override
	public String toString();

}
