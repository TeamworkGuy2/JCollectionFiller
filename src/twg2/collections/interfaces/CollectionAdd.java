package twg2.collections.interfaces;

import java.util.Collection;

/** Methods for adding elements to a collection
 * @author TeamworkGuy2
 * @since 2015-9-9
 */
public interface CollectionAdd<E> {

	/**
	 * @param elem the element to add to this collection
	 * @return true if the element was added successfully. This return value is used by
	 * collections that do not allow duplicate values, false is only returned if the collection
	 * already contains {@code elem} and does not allow duplicate elements.
	 * @see Collection#add(Object)
	 */
	public boolean add(E elem);


	/**
	 * @param elems the collection of elements to add to this collection
	 * @return true if all of the elements were removed successfully. This return value is used by
	 * collections that do not allow duplicate values, false is only returned if the collection
	 * already contains {@code elem} and does not allow duplicate elements.
	 * @see Collection#addAll(Collection)
	 */
	public boolean addAll(Collection<? extends E> elems);

}
