package twg2.collections.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.dataStructures.BaseList;

/**
 * @author TeamworkGuy2
 * @since 2020-11-20
 */
public class BaseListIteratorTest {

	public static BaseList<String>.BaseListIterator getDefaultIter() {
		return (BaseList<String>.BaseListIterator)new BaseList<String>(Arrays.asList(
			"A",
			"B",
			"C",
			"D",
			"E"
		)).listIterator();
	}


	@Test
	public void testInitialState() {
		BaseList<String>.BaseListIterator iter = getDefaultIter();

		Assert.assertEquals(0, iter.nextIndex());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(-1, iter.previousIndex());
		Assert.assertFalse(iter.hasPrevious());
		Assert.assertEquals("A", iter.next());
		Assert.assertEquals("B", iter.next());

		Assert.assertEquals("B", iter.previous());
		Assert.assertTrue(iter.hasNext());
		Assert.assertTrue(iter.hasPrevious());
		Assert.assertEquals(0, iter.previousIndex());
		Assert.assertEquals(1, iter.nextIndex());

		Assert.assertEquals("B", iter.next());
		Assert.assertEquals("C", iter.next());
		int mark = iter.nextIndex();

		Assert.assertEquals("C", iter.previous());
		Assert.assertEquals("B", iter.previous());
		Assert.assertEquals("A", iter.previous());
		Assert.assertFalse(iter.hasPrevious());

		iter.reset(mark);
		Assert.assertEquals("D", iter.next());
		Assert.assertEquals("D", iter.previous());
		Assert.assertEquals(2, iter.previousIndex());
		Assert.assertEquals(3, iter.nextIndex());

		Assert.assertEquals("D", iter.next());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals("E", iter.next());
		Assert.assertFalse(iter.hasNext());
	}

}