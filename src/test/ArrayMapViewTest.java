package test;

import java.util.AbstractMap;
import java.util.ListIterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import checks.Check;
import dataCollections.ArrayMapView;
import dataCollections.ArrayMapViewHandle;

/** Unit tests for {@link ArrayMapView}
 * @author TeamworkGuy2
 * @since 2015-4-5
 */
public class ArrayMapViewTest {


	@Test
	public void arrayMapObjectViewTest() {
		String[] keys = new String[] { "A1", "B1", "C1", "D1", "E1", "F1" };
		Integer[] values = new Integer[] { 1, 1, 2, 3, 5, 7 };
		String[] viewKeyAry = new String[] { "C1", "D1", "E1" };
		Integer[] viewValAry = new Integer[] { 2, 3, 5 };
		int keysOff = 2;
		int valsOff = 2;
		int len = 3;

		Check.assertArrayLengths(keys, values);
		Check.assertArrayLengths(viewKeyAry, viewValAry);

		ArrayMapViewHandle<String, Integer> viewHdl = new ArrayMapViewHandle<>(keys, keysOff, values, valsOff, len);
		ArrayMapView<String, Integer> view = viewHdl.getArrayView();

		// test array view length
		Assert.assertTrue("invalid length " + view.size() + " expected " + len, view.size() == len);

		// test of array view values
		for(int loopI = 0; loopI < len; loopI++) {
			int i = 0;
			for(Map.Entry<String, Integer> entry : view) {
				Assert.assertTrue("key mismatch, found '" + entry + "' expected '" + keys[keysOff + i] + "=" + values[valsOff + i] + "'", entry.getKey().equals(keys[keysOff + i]));
				Assert.assertTrue("value mismatch, found '" + entry + "' expected '" + keys[keysOff + i] + "=" + values[valsOff + i] + "'", entry.getValue().equals(values[valsOff + i]));
				i++;
			}

			Assert.assertArrayEquals(viewKeyAry, view.keyList().toArray());
			Assert.assertArrayEquals(viewValAry, view.valueList().toArray());
		}

		// test of setArrayView()
		keys = new String[] { "A1", "B1", "C1", "F1" };
		values = new Integer[] { 1, 1, 2, 7 };
		viewKeyAry = new String[] { "B1", "C1", "F1" };
		viewValAry = new Integer[] { 1, 2, 7 };
		keysOff = 1;
		valsOff = 1;
		len = 3;

		Check.assertArrayLengths(keys, values);
		Check.assertArrayLengths(viewKeyAry, viewValAry);

		viewHdl.setArrayView(keys, keysOff, values, valsOff, len);

		for(int loopI = 0; loopI < len; loopI++) {
			int i = 0;
			for(Map.Entry<String, Integer> entry : view) {
				Assert.assertTrue("key mismatch, found '" + entry + "' expected '" + keys[keysOff + i] + "=" + values[valsOff + i] + "'", entry.getKey().equals(keys[keysOff + i]));
				Assert.assertTrue("value mismatch, found '" + entry + "' expected '" + keys[keysOff + i] + "=" + values[valsOff + i] + "'", entry.getValue().equals(values[valsOff + i]));
				i++;
			}
			Assert.assertArrayEquals(viewKeyAry, view.keyList().toArray());
			Assert.assertArrayEquals(viewValAry, view.valueList().toArray());
		}

		// test the array view list iterator (and by inheritance, iterator)
		ListIterator<Map.Entry<String, Integer>> itr = view.listIterator(0);
		for(int i = 0; itr.hasNext(); i++) {
			Map.Entry<String, Integer> entry = itr.next();
			Assert.assertTrue("index unexpected: " + itr.nextIndex() + ", expected: " + i, i == itr.nextIndex() - 1);
			Assert.assertTrue(i + ": (" + entry + ") != (" + viewKeyAry[i] + "=" + viewValAry[i] + ")", entry.getKey() == viewKeyAry[i] && entry.getValue() == viewValAry[i]);
			Assert.assertTrue("invalid last index " + i + ", expected " + (viewKeyAry.length - 1),
					(!itr.hasNext() && i == viewKeyAry.length - 1) || itr.hasNext());
		}
	}


	@Test
	public void arrayObjectViewModifyTest() {
		String[] keys = new String[] { "alpha", "beta", "gamma" };
		Integer[] values = new Integer[] { 1, 1, 3 };

		Check.assertArrayLengths(keys, values);

		// TODO test set() on ArrayView with allowSet = true
		ArrayMapViewHandle<String, Integer> viewHdl = new ArrayMapViewHandle<>(keys, 0, values, 0, keys.length, true);
		ArrayMapView<String, Integer> view = viewHdl.getArrayView();
		Map.Entry<String, Integer> insertVal = new AbstractMap.SimpleImmutableEntry<>("delta", 2);
		view.set(2, insertVal);
		keys[2] = insertVal.getKey();
		values[2] = insertVal.getValue();
		Assert.assertTrue(insertVal.equals(view.getEntry(2)));
	}

}
