package twg2.collections.util.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.tuple.Tuples;
import twg2.collections.util.MapBuilder;
import twg2.collections.util.dataStructures.SortedPairList;

/**
 * @author TeamworkGuy2
 * @since 2015-8-17
 */
public class SortedPairListTest {

	@Test
	public void testSortedPairList() {
		SortedPairList<String, Integer> pairs = SortedPairList.newStringPairList();
		pairs.add("red", 1);
		pairs.add("green", 2);

		pairs.remove("red");
		pairs.add("blue", 3);

		Assert.assertEquals(Arrays.asList("blue", "green"), pairs.keyList());

		pairs.removeIndex(0);

		Assert.assertEquals(Arrays.asList("green"), pairs.keyList());

		pairs.putAll(MapBuilder.of(Tuples.of("A", 65), Tuples.of("B", 66), Tuples.of("C", 67)));

		Assert.assertEquals(Arrays.asList("A", "B", "C", "green"), pairs.keyList());

		Assert.assertTrue(pairs.containsKey("B"));
		Assert.assertTrue(pairs.indexOf("B") == 1);
		Assert.assertEquals("green", pairs.getLastKey());

		pairs.clear();

		Assert.assertTrue(pairs.isEmpty());
	}


	@Test
	public void testSortedPairListIterator() {
		SortedPairList<String, Integer> pairs = SortedPairList.newStringPairList();
		pairs.add("red", 1);
		pairs.add("green", 2);
		pairs.add("blue", 3);

		List<String> expect = Arrays.asList("blue", "green", "red");

		List<String> pairKeys = new ArrayList<>();
		for(Entry<String, Integer> entry : pairs) {
			pairKeys.add(entry.getKey());
		}

		Assert.assertEquals(expect, pairs.keyList());
	}

}
