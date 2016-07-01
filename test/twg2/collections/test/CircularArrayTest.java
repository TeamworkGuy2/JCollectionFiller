package twg2.collections.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.dataStructures.CircularArray;

public class CircularArrayTest {

	@Test
	public void testCircularArray() {
		CircularArray<String> array = new CircularArray<String>(5);
		array.add("A");
		array.add(new String[] {"B", "C", "D", "E"}, 0, 4);
		array.remove(3);
		array.add("F");
		array.remove(4);
		String[] ary = new String[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new String[0]);

		array.add(new String[] {"G", "H"}, 0, 2);
		ary = new String[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new String[] { "G", "H" });

		array.remove(3);
		array.add("I");
		array.remove(6);
		array.add("J");
		array.add(new String[] {"K", "L", "M"}, 0, 3);
		array.add(null, 0, 1);
		ary = new String[array.size()];
		array.get(ary, 0, ary.length);
		Assert.assertArrayEquals(ary, new String[] { "J", "K", "L", "M" });
	}

}
