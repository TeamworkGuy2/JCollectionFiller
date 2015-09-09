package twg2.collections.interfaces;

import java.util.Collection;

/** Methods for removing elements from a collection.
 * @author TeamworkGuy2
 * @since 2015-9-9
 */
public interface CollectionRemove<E> {

	/**
	 * @param elem the element to add to this collection
	 * @return true if the element was removed successfully
	 * @see Collection#remove(Object)
	 */
	public boolean remove(E elem);


	/**
	 * @param elems the collection of elements to add to this collection
	 * @return true if all of the elements were removed successfully
	 * @see Collection#removeAll(Collection)
	 */
	public boolean removeAll(Collection<? extends E> elems);


	/** Remove all elements from a collection
	 */
	public void clear();

}
