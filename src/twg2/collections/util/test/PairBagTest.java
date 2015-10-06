package twg2.collections.util.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.tuple.Tuples;
import twg2.collections.util.dataStructures.PairBag;
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
		bag.add(Tuples.of("B", 2));
		bag.put("C", 3);
		bag.put(Tuples.of("D", 4));
		bag.putAll(new PairBag<>(Tuples.of("E", 5), Tuples.of("F", 6)));
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
		PairBag<String, Integer> bag = new PairBag<>(Arrays.asList(Tuples.of("a", 1), Tuples.of("b", 2), Tuples.of("c", 3)));
		List<String> expect = Arrays.asList("a", "b", "c");

		List<String> bagKeys = new ArrayList<>();
		for(Entry<String, Integer> entry : bag) {
			bagKeys.add(entry.getKey());
		}
		Assert.assertEquals(expect, bagKeys);
	}

}
