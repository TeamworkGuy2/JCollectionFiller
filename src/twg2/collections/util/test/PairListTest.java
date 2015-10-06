package twg2.collections.util.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.util.dataStructures.PairList;

/**
 * @author TeamworkGuy2
 * @since 2015-10-6
 */
public class PairListTest {

	@Test
	public void testIterator() {
		PairList<String, Integer> pairs = new PairList<>();
		pairs.put("a", 1);
		pairs.put("b", 2);
		pairs.put("c", 3);

		List<String> expect = Arrays.asList("a", "b", "c");

		List<String> pairKeys = new ArrayList<>();
		for(Entry<String, Integer> entry : pairs) {
			pairKeys.add(entry.getKey());
		}

		Assert.assertEquals(expect, pairKeys);
	}

}
