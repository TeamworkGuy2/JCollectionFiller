package twg2.collections.interfaces;

/** A modifiable {@link RandomAccessCollection} where elements can be removed.
 * @param <T> the type of item stored in this collection
 * @see RandomAccessCollection
 * @author TeamworkGuy2
 * @since 2014-4-12
 */
public interface ModifiableCollection<T> extends RandomAccessCollection<T>, CollectionAdd<T>, CollectionRemove<T> {

	/** Remove the item at the specified index. Subsequent items after the inserted item
	 * are shifted one index lower (toward 0).
	 * @param index the index of the item to remove from this collection.
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @return the item removed at the specified index
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public T remove(int index);


	/** Set the item at the specified index
	 * @param index the index to store the item in
	 * Valid index range {@code [0, }{@link #size()}{@code -1]}
	 * @param item the item to store in one of this collection's current indices.
	 * @return the previous item at the specified index or null if the index
	 * correspond to a null item
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	public T set(int index, T item);


	/** Add an item to this collection. The index of the inserted item
	 * is completely dependent on the implementation.
	 * @param item the new item to add to this collection
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	@Override
	public boolean add(T item);


	/** Add an item to this collection. Subsequent items after the inserted item
	 * are shifted one index higher (toward +infinity).
	 * @param index the index at which to insert a new item.
	 * Valid index range {@code [0, }{@link #size()}{@code ]}
	 * @param item the new item to insert into this collection
	 * @throws IndexOutOfBoundsException if the index is not within the specified range
	 */
	//public void add(int index, T item);


	/** Remove all of the items in this collection.
	 * Once this call returns {@link #size()} should return 0.
	 */
	@Override
	public void clear();

}
