package twg2.collections.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.dataStructures.Bag;

/**
 * @author TeamworkGuy2
 * @since 2015-2-9
 */
public class BagTest {

	@Test
	public void bagTest() {
		String[] strs = new String[] { "Alpha", "Beta", "Gamma" };
		String[] strsAlt = new String[] { "W", "X", "Y", "Z" };
		Bag<String> bag1 = new Bag<>();
		Bag<String> bag2 = new Bag<>(Arrays.asList(strs));

		bag1.addAll(Arrays.asList(strs));
		Assert.assertEquals("bag1 not equal to bag2", bag1, bag2);

		bag1.clearAndAddAll(Arrays.asList(strsAlt));

		for(int i = bag2.size() - 1; i > -1; i--) {
			bag2.remove(i);
		}

		Assert.assertEquals(0, bag2.size());

		for(int i = 0, size= strsAlt.length; i < size; i++) {
			bag2.add(strsAlt[i]);
		}

		Assert.assertEquals("bag1 not equal to bag2", bag1, bag2);
		Assert.assertEquals("X", bag2.get(1));
		Assert.assertEquals(2, bag2.indexOf("Y"));
		Assert.assertEquals(0, bag2.lastIndexOf("W"));
		Assert.assertTrue(bag2.contains("Z"));

		bag2.clear();
		bag2.addAll(strsAlt, 1, strsAlt.length - 1);
		bag2.add(strsAlt[0]);
		bag2.set(0, "A");
		Assert.assertArrayEquals(new Bag<>(new String[] { "-", "A", "Y", "Z", "W" }, 1, 4).toArray(new String[0]), bag2.toArray(new String[0]));
	}


	@Test
	public void copyTest() {
		Bag<String> bag1 = new Bag<>(Arrays.asList("Aa", "Bb", "Cc", "Dd", "Ee"));
		Bag<String> bag2 = bag1.copy();
		bag1.set(1, "B1");

		Assert.assertNotEquals(bag1.getRawArray(), bag2.getRawArray());
		Assert.assertEquals("B1", bag1.get(1));
		Assert.assertEquals("Bb", bag2.get(1));
	}


	@Test
	public void iterator() {
		String[] strs = new String[] { "Aa", "Bb", "Cc", "Dd", "Ee" };
		Bag<String> bag = new Bag<>(strs);
		List<String> res = new ArrayList<String>();

		bag.forEach((s) -> res.add(s));

		Assert.assertArrayEquals(strs, res.toArray(new String[0]));
	}


	@Test
	public void remove() {
		Bag<String> bag = new Bag<>(new String[] { "Aa", "Bb", "Cc" });

		boolean res = false;

		res = bag.remove("Cc");
		Assert.assertTrue(res);

		res = bag.remove("Zzz");
		Assert.assertFalse(res);

		res = bag.remove(null);
		Assert.assertFalse(res);

		Assert.assertFalse(bag.contains("Cc"));
		Assert.assertEquals(2, bag.size());
	}

}
