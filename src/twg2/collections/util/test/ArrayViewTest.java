package twg2.collections.util.test;

import java.util.ListIterator;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.util.dataStructures.ArrayView;
import twg2.collections.util.dataStructures.ArrayViewHandle;

/** Unit tests for {@link ArrayView}
 * @author TeamworkGuy2
 * @since 2014-12-17
 */
public class ArrayViewTest {


	@Test
	public void arrayObjectViewTest() {
		String[] objs = new String[] { "A1", "B1", "C1", "D1", "E1", "F1" };
		String[] viewAry = new String[] { "C1", "D1", "E1" };
		int off = 2;
		int len = 3;
		ArrayViewHandle<String> viewHdl = new ArrayViewHandle<>(objs, off, len);
		ArrayView<String> view = viewHdl.getArrayView();

		// test array view length
		Assert.assertTrue("invalid length " + view.size() + " expected " + len, view.size() == len);

		// test of array view values
		for(int loopI = 0; loopI < len; loopI++) {
			int i = 0;
			for(String str : view) {
				Assert.assertTrue("found '" + str + "' expected '" + objs[off + i] + "'", str.equals(objs[off + i]));
				Assert.assertTrue("found '" + str + "' expected '" + view.get(i) + "'", str.equals(view.get(i)));
				i++;
			}
			Assert.assertArrayEquals(viewAry, view.toArray(new String[len]));
		}

		// test of setArrayView()
		objs = new String[] { "A1", "B1", "C1", "F1" };
		viewAry = new String[] { "B1", "C1", "F1" };
		off = 1;
		len = 3;
		viewHdl.setArrayView(objs, off, len);

		for(int loopI = 0; loopI < len; loopI++) {
			int i = 0;
			for(String str : view) {
				Assert.assertTrue("found '" + str + "' expected '" + objs[off + i] + "'", str.equals(objs[off + i]));
				i++;
			}
			Assert.assertArrayEquals(viewAry, view.toArray(new String[len]));
		}

		// test the array view list iterator (and by inheritance, iterator)
		ListIterator<String> itr = view.listIterator(0);
		for(int i = 0; itr.hasNext(); i++) {
			String str = itr.next();
			Assert.assertTrue("index unexpected: " + itr.nextIndex() + ", expected: " + i, i == itr.nextIndex() - 1);
			Assert.assertTrue(i + ": " + str + " != " + viewAry[i], str == viewAry[i]);
			Assert.assertTrue("invalid last index " + i + ", expected " + (viewAry.length - 1),
					(!itr.hasNext() && i == viewAry.length - 1) || itr.hasNext());
		}
	}


	@Test
	public void arrayObjectViewModifyTest() {
		String[] vals = new String[] { "alpha", "beta", "gamma" };
		// TODO test set() on ArrayView with allowSet = true
		ArrayViewHandle<String> viewHdl = new ArrayViewHandle<String>(vals, 0, vals.length, true);
		ArrayView<String> view = viewHdl.getArrayView();
		String insertVal = "delta";
		view.set(2, insertVal);
		vals[2] = insertVal;
		Assert.assertTrue(view.get(2) == insertVal);
	}

}
