package twg2.collections.test;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.dataStructures.MultiBag;

/**
 * @author TeamworkGuy2
 * @since 2014-1-0
 */
public class MultiBagTest {

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

}
