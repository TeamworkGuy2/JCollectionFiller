package twg2.collections.test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.dataStructures.PairBag;
import checks.CheckCollections;

/**
 * @author TeamworkGuy2
 * @since 2015-8-22
 */
public class PairBagTest {

	@Test
	public void testPairBag() {
		PairBag<String, Integer> bag = new PairBag<>();
		testPut(bag);
		testRemove(bag);
		testSet(bag);
		testGet(bag);
	}


	public void testGet(PairBag<String, Integer> bag) {
		Assert.assertTrue(bag.containsKey("E"));
		CheckCollections.assertLooseEquals(bag.keyList(), Arrays.asList("A", "C", "E", "end"));

		Assert.assertEquals("end", bag.getKey(bag.getValueIndex(123)));
	}


	public void testPut(PairBag<String, Integer> bag) {
		bag.add("A", 1);
		bag.add(entry("B", 2));
		bag.put("C", 3);
		bag.put(entry("D", 4));
		bag.putAll(new PairBag<>(entry("E", 5), entry("F", 6)));
	}


	public void testRemove(PairBag<String, Integer> bag) {
		String rmvKey = bag.remove(1);
		Assert.assertEquals("B", rmvKey);
		Integer rmvValue = bag.remove("D");
		Assert.assertEquals(Integer.valueOf(4), rmvValue);
	}


	public void testSet(PairBag<String, Integer> bag) {
		int idx = bag.getKeyIndex("F");
		Assert.assertTrue(idx > -1);
		bag.setKeyValue(idx, "end", 123);
	}


	@Test
	public void testIterator() {
		PairBag<String, Integer> bag = new PairBag<>(Arrays.asList(entry("a", 1), entry("b", 2), entry("c", 3)));
		List<String> expect = Arrays.asList("a", "b", "c");

		List<String> bagKeys = new ArrayList<>();
		for(Entry<String, Integer> entry : bag) {
			bagKeys.add(entry.getKey());
		}
		Assert.assertEquals(expect, bagKeys);
	}


	private static <K, V> Entry<K, V> entry(K key, V value) {
		return new AbstractMap.SimpleImmutableEntry<>(key, value);
	}

}
