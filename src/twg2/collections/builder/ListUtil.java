package twg2.collections.builder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.function.Function;
import java.util.function.Predicate;

/** Utility methods for dealing with {@link List lists}.
 * For example, check if a list contains no duplicate values, get the union between
 * two lists, etc.
 * @author TeamworkGuy2
 * @since 2014-11-9
 */
public final class ListUtil {


	private ListUtil() { throw new AssertionError("cannot instantiate static class ListUtil"); }


	/** Check whether a list contains unique values as defined by {@link Object#equals(Object)}
	 * @param list the list of values to check
	 * @return true if the values in the list form a unique set, false otherwise
	 */
	public static final <E> boolean isUnique(List<E> list) {
		if(list instanceof RandomAccess) {
			int size = list.size();
			for(int i = size - 1; i > -1; i--) {
				E itemI = list.get(i);
				for(int ii = size - 1; ii > -1; ii--) {
					if(i != ii && itemI != null ? itemI.equals(list.get(ii)) : list.get(ii) == null) {
						return false;
					}
				}
			}
			return true;
		}
		else {
			int i = 0;
			for(E itemI : list) {
				int ii = 0;
				for(E itemII : list) {
					if(i != ii && itemI != null ? itemI.equals(itemII) : itemII == null) {
						return false;
					}
					ii++;
				}
				i++;
			}
		}
		return true;
	}


	/** Check whether a sub-list contains unique values as defined by {@link Object#equals(Object)}
	 * @param list the list of values to check
	 * @param off the offset index into {@code list} at which to start comparing values
	 * @param len the number of values to compare, starting at index {@code off} in the {@code list} of values
	 * @return true if the sub-list of values form a unique set, false otherwise
	 */
	public static final <E> boolean isUnique(List<E> list, int off, int len) {
		for(int i = off, size = off + len; i < size; i++) {
			E itemI = list.get(i);
			for(int ii = off, size2 = off + len; ii < size2; ii++) {
				if(i != ii && itemI != null ? itemI.equals(list.get(ii)) : list.get(ii) == null) {
					return false;
				}
			}
		}
		return true;
	}


	/** Creates a shallow copy of the set of keys in a map with a given condition
	 * @return the set of keys from the map provided or a new empty list of the map was empty
	 * @see AddCondition
	 */
	public static final <K, V> List<K> copyOfKeys(Map<K, V> map, AddCondition condition) {
		List<K> list = new ArrayList<K>();
		if(map != null) {
			ListAdd.addCollectionToList(map.keySet(), list, condition);
		}
		return list;
	}



	/** Creates a shallow copy of the set of keys in a map with a given condition
	 * @return the set of keys from the map provided or a new empty list of the map was empty
	 * @see AddCondition
	 */
	public static final <K, V> List<V> copyOfValues(Map<K, V> map, AddCondition condition) {
		List<V> list = new ArrayList<V>();
		if(map != null) {
			ListAdd.addCollectionToList(map.values(), list, condition);
		}
		return list;
	}


	/** Create an array of values from a {@link Collection} based on the
	 * class type of the first element in the array
	 * @param coll the collection of values to convert to an array
	 * @return a newly constructed array of all the values from the collection
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] toArray(Collection<E> coll) {
		E[] ary = null;
		if(coll instanceof List && coll instanceof RandomAccess) {
			List<E> list = (List<E>)coll;
			int size = coll.size();
			if(size > 0) {
				ary = (E[])Array.newInstance(list.get(0).getClass(), coll.size());
			}
			for(int i = 0; i < size; i++) {
				ary[i] = list.get(i);
			}
		}
		else {
			int i = 0;
			for(E elem : coll) {
				if(i == 0) {
					ary = (E[])Array.newInstance(elem.getClass(), coll.size());
				}
				ary[i] = elem;
				i++;
			}
		}
		return ary;
	}


	/** Transforms an iterator of values into a new {@link ArrayList}
	 * @see #map(Iterator, Function, Collection)
	 */
	public static final <E, R> List<R> map(Iterator<? extends E> valueIter, Function<E, R> transformer) {
		return map(valueIter, transformer, new ArrayList<>());
	}


	/** Map the values from an iterator to a destination collection using a a mapping function
	 * @param valueIter the input collection to convert
	 * @param transformer the function to transform the input list values
	 * @param dst the destination list to store the transformed values in
	 * @return the input {@code dst} list filled with the transformed values
	 */
	public static final <E, R, S extends Collection<R>> S map(Iterator<? extends E> valueIter, Function<E, R> transformer, S dst) {
		while(valueIter.hasNext()) {
			E val = valueIter.next();
			R obj = transformer.apply(val);
			dst.add(obj);
		}
		return dst;
	}


	/** Transforms a list of values into a new {@link ArrayList}
	 * @see #map(Iterable, Function, Collection)
	 */
	public static final <E, R> List<R> map(Iterable<? extends E> list, Function<E, R> transformer) {
		return map(list, transformer, new ArrayList<>());
	}


	/** Convert a list using a mapping function and store the resulting values
	 * in a given destination list
	 * @param coll the input collection to convert
	 * @param transformer the function to transform the input list values
	 * @param dst the destination list to store the transformed values in
	 * @return the input {@code dst} list filled with the transformed values
	 */
	public static final <E, R, S extends Collection<R>> S map(Iterable<? extends E> coll, Function<E, R> transformer, S dst) {
		if(coll instanceof List && coll instanceof RandomAccess) {
			@SuppressWarnings("unchecked")
			List<E> collList = (List<E>)coll;
			for(int i = 0, size = collList.size(); i < size; i++) {
				R elem = transformer.apply(collList.get(i));
				dst.add(elem);
			}
		}
		else {
			for(E elem : coll) {
				R obj = transformer.apply(elem);
				dst.add(obj);
			}
		}
		return dst;
	}


	@SafeVarargs
	public static final <E, R> Collection<R> map(Function<E, R> transformer, E... values) {
		return map(transformer, new ArrayList<>(), values);
	}


	/** Map an array of values to a destination list
	 * @param transformer the function to transform the input list values
	 * @param dst the destination collection to store the transformed values in
	 * @param values the array of input values to map to result values
	 * @return the input {@code dst} collection filled with the transformed values
	 */
	@SafeVarargs
	public static final <E, R, S extends Collection<R>> S map(Function<E, R> transformer, S dst, E... values) {
		for(E elem : values) {
			R obj = transformer.apply(elem);
			dst.add(obj);
		}
		return dst;
	}


	/** Filters a list of values into a new {@link ArrayList}
	 * @see #filter(Collection, Predicate, Collection)
	 */
	public static final <E> List<E> filter(Collection<? extends E> list, Predicate<E> filter) {
		return filter(list, filter, new ArrayList<>());
	}


	/** Filter a list using a filter function and store the resulting values
	 * in a given destination list
	 * @param coll the input collection to filter
	 * @param filter the function to filter the input list values
	 * @param dst the destination list to store the values in that pass the filter test
	 * @return the input {@code dst} list filled with the filtered values
	 */
	public static final <E, S extends Collection<E>> S filter(Collection<? extends E> coll, Predicate<E> filter, S dst) {
		if(coll instanceof List && coll instanceof RandomAccess) {
			@SuppressWarnings("unchecked")
			List<E> collList = (List<E>)coll;
			for(int i = 0, size = collList.size(); i < size; i++) {
				E elem = collList.get(i);
				if(filter.test(elem)) {
					dst.add(elem);
				}
			}
		}
		else {
			for(E elem : coll) {
				if(filter.test(elem)) {
					dst.add(elem);
				}
			}
		}
		return dst;
	}


	/** Filers and transforms a list of values into a new {@link ArrayList}
	 * @see #filterMap(Collection, Predicate, Function, Collection)
	 */
	public static final <E, R> List<R> filterMap(Collection<? extends E> list, Predicate<E> filter, Function<E, R> transformer) {
		return map(list, transformer, new ArrayList<>());
	}


	/** Filter and transform a list using a filter and mapping function and store the
	 * resulting values in a given destination list
	 * @param coll the input collection to filter and map
	 * @param filter the function to filter the {@code coll} input values
	 * @param transformer the function to transform the input list values
	 * @param dst the destination list to store the transformed values in
	 * @return the input {@code dst} list filled with the transformed values
	 */
	public static final <E, R, S extends Collection<R>> S filterMap(Collection<? extends E> coll, Predicate<E> filter, Function<E, R> transformer, S dst) {
		if(coll instanceof List && coll instanceof RandomAccess) {
			@SuppressWarnings("unchecked")
			List<E> collList = (List<E>)coll;
			for(int i = 0, size = collList.size(); i < size; i++) {
				E elem = collList.get(i);
				if(filter.test(elem)) {
					R obj = transformer.apply(elem);
					dst.add(obj);
				}
			}
		}
		else {
			for(E elem : coll) {
				if(filter.test(elem)) {
					R obj = transformer.apply(elem);
					dst.add(obj);
				}
			}
		}
		return dst;
	}

}
