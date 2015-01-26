package test;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import primitiveCollections.IntArrayList;
import primitiveCollections.IntListSorted;
import dataCollections.Bag;
import dataCollections.IndexedMap;
import dataCollections.MultiBag;
import dataCollections.PairBag;
import dataCollections.PairList;

/**
 * @author TeamworkGuy2
 * @since 2014-1-0
 */
public class ListAndBagTest {
	private PairBag<String, int[]> intGroups = new PairBag<String, int[]>();
	private PairList<String, String> strStrs = new PairList<String, String>();
	private Bag<String> properties = new Bag<String>();
	private IntArrayList ints = new IntArrayList();


	@Test
	public void testListAndBag() {
		IntListSorted intsSorted = new IntListSorted();
		Random rand = new Random();
		intGroups.add("alpha", randomInts(rand, 10));
		intGroups.add("beta", randomInts(rand, 10));
		intGroups.add("gamma", randomInts(rand, 10));
		intGroups.add("delta", randomInts(rand, 10));

		IndexedMap<String, int[]> strIntsView = intGroups.getKeyValueView();
		StringBuilder strB = new StringBuilder();
		for(int i = 0, size = strIntsView.size(); i < size; i++) {
			strStrs.put(strIntsView.getKey(i), strB.append(strIntsView.getKey(i)).reverse().toString());
			strB.setLength(0);
			properties.add(strIntsView.getKey(i));
			int[] intAry = strIntsView.getValue(i);
			ints.addAll(intAry);
			intsSorted.addAll(intAry);
			Arrays.sort(intAry);
			Assert.assertArrayEquals(intsSorted.toArray(), intAry);
			ints.clear();
			intsSorted.clear();
		}
	}


	@Test
	public void testMultiBag() throws Exception {
		// Create three lists of size 1 each
		final int listCount = 3;
		int itemCount = 8;
		int offset = 10;
		MultiBag<Integer> ints = new MultiBag<Integer>(listCount, 1);

		Assert.assertTrue(ints.size() == 0);

		for(int i = 0; i < listCount; i++) {
			for(int ii = 0; ii < itemCount; ii++) {
				ints.add(i, offset+ii);
			}
			offset *= offset;
		}

		offset = 10;
		for(int i = 0; i < listCount; i++) {
			for(int ii = 0; ii < itemCount; ii++) {
				int val = ints.get(i, ii);
				Assert.assertTrue(val == offset + ii);
			}
			offset *= offset;
		}

		for(int ii = 0; ints.size(1) > 0; ) {
			ints.remove(1, ii);
		}

		Assert.assertTrue(ints.size() == itemCount*2);
	}


	private static int[] randomInts(final Random rand, final int count) {
		int[] val = new int[count];
		for(int i = 0; i < count; i++) {
			val[i] = (short)rand.nextInt();
		}
		return val;
	}

}