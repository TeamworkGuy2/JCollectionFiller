package twg2.collections.dataStructures;

/** Circular Array class for storing, adding, and removing data without needing to internally shift the entire array
 * when data is removed.<br/>
 * Thus add and remove operations both take approximately linear time.
 * Certain add operations that force the array to expand will take longer.<br/>
 * Because the array is circular, users must use {@link #get} to retrieve data, this keeps the user from having to
 * understand the circular array implementation.
 * @param <T> the type of object to store in this circular array
 * @author TeamworkGuy2
 * @since 2013-5-3
 */
public class CircularArray<T> {
	private static final int DEFAULT_SIZE = 64;
	private Object[] array;
	private int index;
	private int length;


	/** Create a circular array for storing, adding, and removing data.<br/>
	 * Constructs a circular array with a default size of 64 elements.
	 */
	public CircularArray() {
		this(DEFAULT_SIZE);
	}


	/** Create a circular array for storing, adding, and removing data.<br/>
	 * @param initialSize the initial size of the array
	 */
	public CircularArray(int initialSize) {
		this.array = new Object[initialSize];
		this.index = 0;
		this.length = 0;
	}


	/**
	 * @return the number of objects currently in this array
	 */
	public int size() {
		return this.length;
	}


	/** add the specified data to this circular array
	 * @param newData the data to add
	 * @param offset the index into the <code>newData</code> array at which to
	 * start adding data
	 * @param newLength the number of elements of data to add from
	 * the <code>newData</code> array
	 * @return the number of elements added to this circular array,
	 * or 0 if newData == null, or newLength < 1, or offset+newLength > newData.length
	 */
	public int add(T[] newData, int offset, int newLength) {
		if(newLength < 1 || newData == null || offset+newLength > newData.length) {
			return 0;
		}
		// If there is more data to add then available space in the array
		if(newLength > (array.length - length)) {
			// Double the array size
			expandArray(array.length);
		}

		// If the new data will wrapping around, add the new data and wrap around to fit
		if((index + length) < array.length && newLength > (array.length - (index + length))) {
			// The number of elements available up to the end of the array before needing to wrap around
			int tailLength = array.length - (index + length);
			// Copy data up to the end of the array
			System.arraycopy(newData, offset, array, index + length, tailLength);
			// Copy rest of data 'wrapped around' to the beginning of the array
			System.arraycopy(newData, offset+tailLength, array, 0, newLength-tailLength);
		}
		// Else if the data fits without wrapping around, add the new data
		else {
			System.arraycopy(newData, offset, array, (index + length) % array.length, newLength);
		}
		// Adjust the length
		length += newLength;
		// Return the number of elements successfully added
		return newLength;
	}


	/** add the specified object to the end of this circular array
	 * @param newData the data to add
	 * @return the number of elements added to this circular array,
	 * or 0 if newData == null, or newLength < 1, or offset+newLength > newData.length
	 */
	public int add(T newData) {
		int newLength = 1;
		// If there is more data to add then available space in the array
		if(newLength > (array.length - length)) {
			// Double the array size
			expandArray(array.length);
		}

		// If the new data will wrapping around, add the new data and wrap around to fit
		if((index + length) < array.length && newLength > (array.length - (index + length))) {
			// The number of elements available up to the end of the array before needing to wrap around
			int tailLength = array.length - (index + length);
			// Copy data up to the end of the array
			if(tailLength > 0) {
				array[index+length] = newData;
			}
			// Copy rest of data 'wrapped around' to the beginning of the array
			else {
				array[0] = newData;
			}
		}
		// Else if the data fits without wrapping around, add the new data
		else {
			array[(index + length) % array.length] = newData;
		}
		// Adjust the length
		length = length + newLength;
		// Return the number of elements successfully added
		return newLength;
	}


	/** remove the specified length number of objects from the beginning of this array
	 * @param removeLength the number of elements to remove
	 * @return the number of elements successfully removed
	 */
	public int remove(int removeLength) {
		if(removeLength < 1) {
			return 0;
		}

		// If the number of elements to remove does not exceed the number of elements in this array, remove
		// the specified number of elements from the array
		if(removeLength < length) {
			// Adjust array start index and length, we do not clear the array because there is no need to
			// Access is restricted to set values between index and index+length, so later additions will overwrite the values
			index = (index + removeLength) % array.length;
			length = length - removeLength;
			return removeLength;
		}
		// Else the number of elements removed exceeds the length of the array, remove all of the elements in the array
		else {
			index = (index + length) % array.length;
			int oldLength = length;
			length = 0;
			return oldLength;
		}
	}


	/** get objects from the beginning of this array and put them in the specified destination array.<br/>
	 * The data remains in this array until {@link #remove} is called.
	 * @param destArray the array to put the data in
	 * @param offset the offset into the destination array to begin copying newLength number of elements
	 * @param newLength the number of elements to transfer into the buffer
	 * @return the number of elements copied to the buffer successfully, 0 if the buffer is null, or if newLength < 1,
	 * or if buf.remaining() < newLength
	 */
	public int get(T[] destArray, int offset, int newLength) {
		if(newLength < 1 || destArray == null || destArray.length-offset < newLength) {
			return 0;
		}
		// If the number of elements requested is more than the number of elements currently in the array,
		// only copy the number of elements currently in the array
		if(newLength > length) {
			newLength = length;
		}

		int remaining = 0;
		// Check if the data length requested wraps around to the beginning of the array and calculate the number
		// of elements up to the end of the array or the number of elements requested, whichever is shorter
		if((index + newLength) % array.length != index + newLength) {
			remaining = (array.length - index);
		}
		else {
			remaining = newLength;
		}
		// Copy data up to the end of the array
		System.arraycopy(array, index, destArray, offset, remaining);
		// If there is any more data to copy, wrap around and copy from the beginning of the array
		if(newLength - remaining != 0) {
			System.arraycopy(array, 0, destArray, offset+remaining, newLength-remaining);
		}
		return newLength;
	}


	/** expand the array by adding the specified number of elements
	 * @param expandSize the number of elements to add to the array size
	 */
	private void expandArray(int expandSize) {
		if(expandSize < 1) {
			return;
		}
		// Save current array reference, and create new array size
		Object[] temp = array;
		this.array = new Object[temp.length + expandSize]; // same as [this.array.length + expandSize]

		// If there is any 'wrap around' data, copy data up to the end of the array and copy the wrap around data
		if(index+length > temp.length) {
			// Copy everything up to the end of the old array
			System.arraycopy(temp, index, array, index, temp.length - index);

			// Calculate the remaining number of elements that need copying
			int remaining = (length - (temp.length - index));
			// Determine if all of the wrap around elements can fit in the remaining space at the end of the
			// expanded array
			int tailSize = 0;
			if(remaining < expandSize) { tailSize = remaining; }
			else { tailSize = expandSize; }

			// Copy the wrap around elements into the expanded array up to the end of the expanded array
			System.arraycopy(temp, 0, array, temp.length, tailSize);
			// Wrap around and copy any remaining wrap around elements
			if(remaining > tailSize) {
				System.arraycopy(temp, tailSize, array, 0, remaining-tailSize);
			}
		}
		// Else none of the data 'wraps around', so everything can be copied at once
		else {
			System.arraycopy(temp, index, array, index, length);
		}
	}


	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("index: " + index + ", length: " + length + ", (size: " + array.length + "), data: [");
		for(int i = 0; i < length-1; i++) {
			b.append(array[(index + i) % array.length] + ", ");
		}
		if(this.length > 0) {
			b.append(array[(index+length-1) % array.length] + "]");
		}
		else {
			b.append("]");
		}
		return b.toString();
	}

}
