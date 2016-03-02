package twg2.collections.test;

import java.util.Arrays;

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
		for(int i = 0, size= strsAlt.length; i < size; i++) {
			bag2.add(strsAlt[i]);
		}

		Assert.assertEquals("bag1 not equal to bag2", bag1, bag2);
	}

}
