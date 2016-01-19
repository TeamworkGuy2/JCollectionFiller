package twg2.collections.dataStructures;

/** An object that acts as an array that contains multiple internal arrays of differing sizes.
 * @param <T> the type of data to store in the arrays
 * @author TeamworkGuy2
 * @since 2014-1-1
 */
public class MultiBag<T> {
	private static final int DEFAULT_SIZE = 16;
	private int arrayCount;
	private int sizeTotal;
	private int[] offsets;
	/** the number of elements each list can hold before needing to be expanded */
	private int[] lengths;
	/** the current size of each list */
	private int[] sizes;
	private Object[] arrays;


	/** Create an object containing a single array with a default size of 16
	 */
	public MultiBag() {
		this(1, DEFAULT_SIZE);
	}


	/** Create an object containing multiple arrays each with the default size of 16
	 * @param listCount the number of lists to create in this object
	 */
	public MultiBag(final int listCount) {
		this(listCount, DEFAULT_SIZE);
	}


	/** Create an object containing multiple arrays each with the the specified size
	 * @param listCount the number of lists to create in this object
	 * @param listSizes the initial capacity of all of the lists
	 */
	public MultiBag(final int listCount, final int listSizes) {
		final int size = listCount * listSizes;
		arrayCount = listCount;
		arrays = new Object[size];
		sizes = new int[size];
		lengths = new int[size];
		offsets = new int[size];

		int offsetSize = 0;
		for(int i = 0; i < listCount; i++) {
			lengths[i] = listSizes;
			offsets[i] = offsetSize;
			offsetSize += listSizes;
		}
	}


	/** Create an unsorted group of items with the specified size as the starting size
	 * @param listCount the number of lists to create in this object
	 * @param listSizes a list of initial capacities of each of the lists,
	 * this array's length should must be equal to or greater than {@code listCount}
	 */
	public MultiBag(final int listCount, final int[] listSizes) {
		int total = 0;
		final int size = listSizes.length;
		for(int i = 0; i < size; i++) {
			total += listSizes[i];
		}
		arrayCount = listCount;
		arrays = new Object[total];
		sizes = new int[total];
		lengths = new int[total];
		offsets = new int[total];

		int offsetSize = 0;
		for(int i = 0; i < listCount; i++) {
			lengths[i] = listSizes[i];
			offsets[i] = offsetSize;
			offsetSize += listSizes[i];
		}
	}


	/** Get the element at the specified index from the specified array
	 * @param listId the index of the list within this object to retrieve the specified index from
	 * @param index the index between [0, {@link #size(int)}-1] inclusive of this array to retrieve
	 * @return the element found at the specified array's index
	 */
	@SuppressWarnings("unchecked")
	public T get(int listId, int index) {
		final int offset = offsets[listId];
		index = offset + index;
		if(index >= offset + sizes[listId]) { throw new IndexOutOfBoundsException(); }
		return (T)arrays[index];
	}


	/** Remove the element at the specified index from the specified array
	 * @param listId the index of the list within this object to remove the specified index from
	 * @param index the index between [0, {@link #size()}-1] inclusive of this array to remove
	 * @return the element found at the specified array's index
	 */
	@SuppressWarnings("unchecked")
	public T remove(int listId, int index) {
		final int offset = offsets[listId];
		if(offset+index >= offset + sizes[listId]) { throw new IndexOutOfBoundsException(); }
		final int size = offset + sizes[listId];
		// Get the item to remove
		Object item = arrays[offset+index];
		// Replace the item to remove with the last element from our array
		arrays[offset+index] = arrays[size-1];
		// Set the last element to null
		arrays[size-1] = null;
		// Decrease the size because we removed one item
		sizes[listId]--;
		sizeTotal--;
		return (T)item;
	}


	/** Remove the specified object from this group of objects
	 * @param listId the index of the list within this object to remove the specified index from
	 * @param item the object to remove based on item.equals(get(i)) if item
	 * is not null, or get(i)==null if item is null, where i is [0, size()-1]
	 * @return true if the element was removed successfully, false otherwise
	 */
	public boolean remove(int listId, T item) {
		// Search for the item to remove
		if(item != null) {
			int afterLastIndex = offsets[listId] + sizes[listId];
			for(int i = offsets[listId]; i < afterLastIndex; i++) {
				// If the item is found, remove it
				if(item.equals(arrays[i])) {
					remove(listId, i);
					return true;
				}
			}
		}
		// Else if the item is null, search for a null item in our list
		else {
			int afterLastIndex = offsets[listId] + sizes[listId];
			for(int i = offsets[listId]; i < afterLastIndex; i++) {
				// If the item is found, remove it
				if(arrays[i] == null) {
					remove(listId, i);
					return true;
				}
			}
		}
		return false;
	}


	/** Add the specified item to this group of elements
	 * @param listId the index of the list within this object to remove the specified index from
	 * @param item the item to add to this group of elements
	 */
	public boolean add(int listId, T item) {
		// If the bag is to small, expand it
		if(sizes[listId] >= lengths[listId]) {
			expandArray(listId);
		}
		// Add the new item
		arrays[offsets[listId] + sizes[listId]] = item;
		sizes[listId]++;
		sizeTotal++;
		return true;
	}


	/** Clear all of this object's lists
	 */
	public void clear() {
		// Clear each internal list to null
		int size = 0;
		for(int a = 0; a < arrayCount; a++) {
			size = offsets[a] + sizes[a];
			for(int i = offsets[a]; i < size; i++) {
				arrays[i] = null;
			}
			sizes[a] = 0;
		}
		sizeTotal = 0;
	}


	/** Clear a specific list within this object
	 * @param listId the index of the list within this object to remove the specified index from
	 */
	public void clear(int listId) {
		int size = offsets[listId] + sizes[listId];
		for(int i = offsets[listId]; i < size; i++) {
			arrays[listId] = null;
		}
		sizeTotal -= sizes[listId];
		sizes[listId] = 0;
	}


	/** Get the current size of this group of elements
	 * @param listId the index of the list within this object to remove the specified index from
	 * @return the size of this group of elements
	 */
	public int size(int listId) {
		return sizes[listId];
	}


	/**
	 * @return the combined size of all of this object's arrays
	 */
	public int size() {
		return sizeTotal;
	}


	/**
	 * @return the number of internal list in this object
	 */
	public int listCount() {
		return arrayCount;
	}


	/** Is this group of elements empty
	 * @param listId the index of the list within this object to remove the specified index from
	 * @return true if this group of elements is empty, false otherwise
	 */
	public boolean isEmpty(int listId) {
		return sizes[listId] == 0;
	}


	/**
	 * @param listId the index of the list within this object to remove the specified index from
	 */
	private final void expandArray(int listId) {
		Object[] oldData = this.arrays;
		// Expand array size 1.5x + 4, +4 instead of +1 to prevent small bags from constantly needed to resize
		final int sizeIncrease = (lengths[listId] >>> 1) + 4;
		this.arrays = new Object[oldData.length + sizeIncrease];
		final int remainingIndex = offsets[listId] + lengths[listId];
		lengths[listId] += sizeIncrease;
		System.arraycopy(oldData, 0, this.arrays, 0, offsets[listId] + sizes[listId]);
		System.arraycopy(oldData, remainingIndex, this.arrays, remainingIndex + sizeIncrease,
				(offsets[arrayCount - 1] + sizes[arrayCount - 1]) - remainingIndex);

		for(int i = listId+1; i < arrayCount; i++) {
			offsets[i] += sizeIncrease;
		}
	}

}
