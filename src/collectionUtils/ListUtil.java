package collectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

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
		for(int i = off, size = off+len; i < size; i++) {
			E itemI = list.get(i);
			for(int ii = off, size2 = off+len; ii < size2; ii++) {
				if(i != ii && itemI != null ? itemI.equals(list.get(ii)) : list.get(ii) == null) {
					return false;
				}
			}
		}
		return true;
	}


	@SafeVarargs
	public static final <E> List<E> ofMutable(E... elements) {
		List<E> list = new ArrayList<>();
		if(elements != null) {
			Collections.addAll(list, elements);
			for(E elem : elements) {
				list.add(elem);
			}
		}
		return list;
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

}
