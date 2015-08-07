package twg2.collections.util.test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.util.AddCondition;
import twg2.collections.util.ListAdd;
import twg2.collections.util.ListUtil;
import twg2.collections.util.MapBuilder;
import checks.CheckTask;


/**
 * @author TeamworkGuy2
 * @since 2014-12-16
 */
public final class BuilderTests {


	@Test
	public void testListAddUtil() {
		ArrayList<String> strs = new ArrayList<>();

		// add non-null items
		ListAdd.addArrayToList(new String[] { "a", "ab", "abc", null }, strs, AddCondition.NO_NULL);
		Assert.assertEquals("invalid number of strings", strs.size(), 3);

		// add a subset of an array, also throw errors if null
		CheckTask.assertException(() -> {
			ListAdd.addArrayToList(new String[] { "b", null, "c" }, 1, 1, strs, true, false, false, true);
		});

		// add items with a duplicate at the end and throw errors if contains, and catch error
		CheckTask.assertException(() -> {
			ListAdd.addListToList(Arrays.asList("c", "d", "e", "a"), strs, AddCondition.ERROR_CONTAINS);
		});
		Assert.assertTrue("lists not equal", Arrays.asList("a", "ab", "abc", "c", "d", "e").equals(strs));

		// add items with duplicates, but don't allow duplicates and check for duplicates
		ListAdd.addCollectionToList(new HashSet<>(Arrays.asList("e", "f")), strs, AddCondition.NO_NULL_OR_CONTAINS);
		Assert.assertTrue("list should be unique", ListUtil.isUnique(strs));

		// add a duplicate item and check for duplicates
		ListAdd.addCollectionToList(new HashSet<>(Arrays.asList("f")), strs, AddCondition.ADD_ALL);
		Assert.assertTrue("list should have duplicate", !ListUtil.isUnique(strs));
	}


	@Test
	public void testMapBuilder() {
		@SuppressWarnings("unchecked")
		Map.Entry<String, String>[] list1 = new Map.Entry[] {
				new AbstractMap.SimpleImmutableEntry<>("__a", "alpha"),
				new AbstractMap.SimpleImmutableEntry<>("__b", "beta"),
				new AbstractMap.SimpleImmutableEntry<>("__c", "charlie"), // conflicting
				new AbstractMap.SimpleImmutableEntry<>("__c", "gamma") // conflicting
		};

		@SuppressWarnings("unchecked")
		Map.Entry<String, String>[] expect1 = new Map.Entry[] {
				new AbstractMap.SimpleImmutableEntry<>("__a", "alpha"),
				new AbstractMap.SimpleImmutableEntry<>("__b", "beta"),
				new AbstractMap.SimpleImmutableEntry<>("__c", "gamma") // last conflict overwrites previous
		};

		Map<String, String> map = MapBuilder.of(Arrays.asList(list1).iterator());
		List<Map.Entry<String, String>> listA = new ArrayList<>(map.entrySet());
		List<Map.Entry<String, String>> listB = Arrays.asList(expect1);
		// sort the lists since one came from a map
		Comparator<Map.Entry<String, String>> comparator = (a, b) -> a.getKey().compareTo(b.getKey());
		Collections.sort(listA, comparator);
		Collections.sort(listB, comparator);
		Assert.assertEquals(listA, listB);
	}

}
