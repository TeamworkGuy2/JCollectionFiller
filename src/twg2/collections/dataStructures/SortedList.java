package twg2.collections.dataStructures;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2016-1-2
 */
public class SortedList {


	/** Add a value to a list at an index which preserves the sorted order of the list
	 * @param list
	 * @param key
	 * @param comparator
	 */
	public static final <K> int addItem(List<K> list, K key, Comparator<K> comparator) {
		int index = calcInsertIndex(list, key, comparator);
		int listSize = list.size();
		if(index >= listSize) {
			list.add(key);
			return listSize;
		}
		else {
			list.add(index, key);
			return index;
		}
	}


	/** Add a value to a list at an index which preserves the sorted order of the list
	 * @param list
	 * @param key
	 */
	public static final <K extends Comparable<K>> int addItem(List<K> list, K key) {
		int index = calcInsertIndex(list, key);
		int listSize = list.size();
		if(index >= listSize) {
			list.add(key);
			return listSize;
		}
		else {
			list.add(index, key);
			return index;
		}
	}


	/** Calculate the index at which a value should be insert into a list of sorted values
	 * @param list
	 * @param key
	 * @param comparator
	 * @return the index at which to insert the value
	 */
	public static final <K> int calcInsertIndex(List<K> list, K key, Comparator<K> comparator) {
		int insertIndex = Collections.binarySearch(list, key, comparator);
		if(insertIndex > -1) {
			insertIndex++;
		}
		else {
			insertIndex = -insertIndex - 1;
		}
		return insertIndex;
	}


	/** Calculate the index at which a value should be insert into a list of sorted values
	 * @param list an existing sorted list of items
	 * @param key the key to search for
	 * @return the index at which to insert the value
	 */
	public static final <K> int calcInsertIndex(List<? extends Comparable<? super K>> list, K key) {
		int insertIndex = Collections.binarySearch(list, key);
		if(insertIndex > -1) {
			insertIndex++;
		}
		else {
			insertIndex = -insertIndex - 1;
		}
		return insertIndex;
	}

}
