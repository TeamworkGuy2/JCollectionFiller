package twg2.collections.builder;

import java.util.List;

import twg2.collections.tuple.Tuple2;

/**
 * @author TeamworkGuy2
 * @since 2016-2-4
 * @param <E> the type of elements 
 */
public class AddedRemoved<E> extends Tuple2<List<E>, List<E>> {

	public AddedRemoved(List<E> a, List<E> b) {
		super(a, b);
	}


	public List<E> getAdded() {
		return super.getKey();
	}


	public List<E> getRemoved() {
		return super.getValue();
	}

}