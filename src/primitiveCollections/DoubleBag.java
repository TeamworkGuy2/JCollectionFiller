package primitiveCollections;

/** A primitive double implementation of an {@link java.util.ArrayList ArrayList}.<br>
 * The order of elements in the bag is not preserved when items are removed.<br>
 * This allows {@link #add(double) add()}, {@link #remove(int) remove()}, and {@link #get(int) get()}
 * operations to execute in O(1). Whereas {@link #contains(double) contains()} operations
 * are approximately O(n).<br>
 * This class' purpose is to provide minor performance and memory usage improvements over an
 * {@code ArrayList<Double>} by storing the Doubles as type <code>double</code>
 * without converting them to Double.
 * This simplifies comparison operations such as {@link #contains(double) contains()}
 * which can use {@code ==} instead of {@code .equals()}
 * @see DoubleArrayList
 * @see DoubleListSorted
 *
 * @author TeamworkGuy2
 * @since 2014-12-24
 */
@javax.annotation.Generated("StringTemplate")
public class DoubleBag extends DoubleArrayList implements DoubleList, java.util.RandomAccess {


	/** Create an unsorted group of items with a default size of 16
	 */
	public DoubleBag() {
		super();
	}


	/** Create an unsorted group of items with the specified size as the starting size
	 * @param capacity the initial size of the group of items
	 */
	public DoubleBag(int capacity) {
		super(capacity);
	}


	public DoubleBag(double[] values) {
		super(values, 0, values.length);
	}


	/** Create an unsorted group of items containing a copy of the specified values
	 * @param values the list of values to copy
	 * @param off the offset into {@code values} at which to start copying
	 * @param len the number of elements to copy from {@code values}
	 */
	public DoubleBag(double[] values, int off, int len) {
		super(values, off, len);
	}


	/** Create a new copy of a primitive list
	 * @param list the primitive list to copy
	 */
	public DoubleBag(DoubleList list) {
		super(list);
	}


	/** Remove the double at the specified index
	 * @param index the index between {@code [0, }{@link #size()}{@code -1} to remove
	 * @return the double found at the specified index
	 * @throws ArrayIndexOutOfBoundsException if the index is outside the range {@code [0, }{@link #size()}{@code -1]}
	 */
	@Override
	public double remove(int index) {
		if(index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		// Shift all elements above the remove element to fill the empty index
		else {
			// Get the item to remove
			double item = data[index];
			// move the last element in the array into this removed index
			if(index < size - 1) {
				data[index] = data[size - 1];
			}
			// Decrease the size because we removed one item
			size--;
			return item;
		}
	}


	/** Remove the specified value from this group
	 * @param item the value to remove
	 * @return true if the value was found and removed successfully, false otherwise
	 */
	@Override
	public boolean removeValue(double item) {
		// Search for the item to remove
		for(int i = 0; i < size; i++) {
			// If the item is found, remove it
			if(item == data[i]) {
				if(i < size - 1) {
					data[i] = data[size - 1];
				}
				size--;
				return true;
			}
		}
		return false;
	}

}
