package twg2.collections.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-4-18
 */
public class ListBuilder {


	@SafeVarargs
	public static <E> List<E> of(E... elements) {
		return newImmutable(elements);
	}
	

	public static <E> List<E> of(Iterable<E> iter) {
		return newImmutable(iter);
	}


	public static <E> List<E> of(Iterator<E> iter) {
		return newImmutable(iter);
	}


	@SafeVarargs
	public static <E> List<E> newImmutable(E... elements) {
		return Collections.unmodifiableList(newMutable(elements));
	}
	

	public static <E> List<E> newImmutable(Iterable<E> iter) {
		return Collections.unmodifiableList(newMutable(iter));
	}


	public static <E> List<E> newImmutable(Iterator<E> iter) {
		return Collections.unmodifiableList(newMutable(iter));
	}


	@SafeVarargs
	public static <E> List<E> newMutable(E... elements) {
		ArrayList<E> list = new ArrayList<>();
		for(E elem : elements) {
			list.add(elem);
		}
		return list;
	}


	public static <E> List<E> newMutable(Iterable<E> iter) {
		ArrayList<E> list = new ArrayList<>();
		for(E elem : iter) {
			list.add(elem);
		}
		return list;
	}


	public static <E> List<E> newMutable(Iterator<E> iter) {
		ArrayList<E> list = new ArrayList<>();
		while(iter.hasNext()) {
			E elem = iter.next();
			list.add(elem);
		}
		return list;
	}


	public static <T> List<T> copyDeep(Collection<T> lists) {
		List<T> dst = new ArrayList<>();

		copyDeepTo(lists, dst);

		return dst;
	}


	public static <T> void copyDeepTo(Collection<T> lists, Collection<T> dst) {
		for(T elem : lists) {
			if(elem instanceof Collection) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				T subList = (T)copyDeep((List)elem);
				dst.add(subList);
			}
			else {
				dst.add(elem);
			}
		}
	}

}
