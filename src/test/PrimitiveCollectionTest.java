package test;

import org.junit.Assert;
import org.junit.Test;

import primitiveCollections.CharArrayList;
import primitiveCollections.CharBag;
import primitiveCollections.CharListSorted;
import primitiveCollections.FloatArrayList;
import primitiveCollections.FloatListSorted;
import primitiveCollections.FloatBag;
import primitiveCollections.IntArrayList;
import primitiveCollections.IntBag;
import primitiveCollections.IntListSorted;

/**
 * @author TeamworkGuy2
 * @since 2014-12-26
 */
public class PrimitiveCollectionTest {


	@Test
	public void primitiveArrayListTest() {
		{
			CharArrayList cList = new CharArrayList();
			char[] chs = new char[] { 0, 5, 22, 105 };
			char[] chs2 = new char[] { 1, 5, 3, 2 };
			cList.addAll(chs);
			for(char ch : chs) {
				Assert.assertTrue(cList.contains(ch));
				cList.removeValue(ch);
			}
			Assert.assertTrue(cList.size() == 0);
			cList.add(0, chs2[0]);
			cList.add(1, chs2[1]);
			cList.add(2, chs2[2]);
			cList.set(1, chs2[3]);
			Assert.assertTrue(cList.indexOf(chs2[2]) == 2);
			Assert.assertTrue(cList.get(1) == chs2[3]);
		}

		{
			IntArrayList iList = new IntArrayList();
			int[] ins = new int[] { 0, 5, 22, 105 };
			int[] ins2 = new int[] { 1, 5, 3, 2 };
			iList.addAll(ins);
			for(int in : ins) {
				Assert.assertTrue(iList.contains(in));
				iList.removeValue(in);
			}
			Assert.assertTrue(iList.size() == 0);
			iList.add(0, ins2[0]);
			iList.add(1, ins2[1]);
			iList.add(2, ins2[2]);
			iList.set(1, ins2[3]);
			Assert.assertTrue(iList.indexOf(ins2[2]) == 2);
			Assert.assertTrue(iList.get(1) == ins2[3]);
		}

		{
			FloatArrayList fList = new FloatArrayList();
			float[] fls = new float[] { 0, 5, 22, 105 };
			float[] fls2 = new float[] { 1, 5, 3, 2 };
			fList.addAll(fls);
			for(float fl : fls) {
				Assert.assertTrue(fList.contains(fl));
				fList.removeValue(fl);
			}
			Assert.assertTrue(fList.size() == 0);
			fList.add(0, fls2[0]);
			fList.add(1, fls2[1]);
			fList.add(2, fls2[2]);
			fList.set(1, fls2[3]);
			Assert.assertTrue(fList.indexOf(fls2[2]) == 2);
			Assert.assertTrue(fList.get(1) == fls2[3]);
		}
	}


	@Test
	public void primitiveListSortedTest() {
		{
			CharListSorted cList = new CharListSorted();
			char[] chs = new char[] { 0, 5, 22, 105 };
			cList.addAll(chs);
			Assert.assertTrue(cList.indexOf(chs[2]) == 2);
			for(char ch : chs) {
				Assert.assertTrue(cList.contains(ch));
				cList.removeValue(ch);
			}
			Assert.assertTrue(cList.size() == 0);
		}

		{
			IntListSorted iList = new IntListSorted();
			int[] ins = new int[] { 0, 5, 22, 105 };
			iList.addAll(ins);
			Assert.assertTrue(iList.indexOf(ins[2]) == 2);
			for(int in : ins) {
				Assert.assertTrue(iList.contains(in));
				iList.removeValue(in);
			}
			Assert.assertTrue(iList.size() == 0);
		}

		{
			FloatListSorted fList = new FloatListSorted();
			float[] fls = new float[] { 0, 5, 22, 105 };
			fList.addAll(fls);
			Assert.assertTrue(fList.indexOf(fls[2]) == 2);
			for(float fl : fls) {
				Assert.assertTrue(fList.contains(fl));
				fList.removeValue(fl);
			}
			Assert.assertTrue(fList.size() == 0);
		}
	}


	@Test
	public void primitiveBagTest() {
		{
			CharBag cBag = new CharBag();
			char[] chs = new char[] { 0, 5, 22, 105 };
			char[] chs2 = new char[] { 1, 5, 3, 2 };
			cBag.addAll(chs);
			for(char ch : chs) {
				Assert.assertTrue(cBag.contains(ch));
				cBag.removeValue(ch);
			}
			Assert.assertTrue(cBag.size() == 0);
			cBag.add(0, chs2[0]);
			cBag.add(1, chs2[1]);
			cBag.add(2, chs2[2]);
			cBag.set(1, chs2[3]);
			Assert.assertTrue(cBag.indexOf(chs2[2]) == 2);
			Assert.assertTrue(cBag.get(1) == chs2[3]);
		}

		{
			IntBag iBag = new IntBag();
			int[] ins = new int[] { 0, 5, 22, 105 };
			int[] ins2 = new int[] { 1, 5, 3, 2 };
			iBag.addAll(ins);
			for(int in : ins) {
				Assert.assertTrue(iBag.contains(in));
				iBag.removeValue(in);
			}
			Assert.assertTrue(iBag.size() == 0);
			iBag.add(0, ins2[0]);
			iBag.add(1, ins2[1]);
			iBag.add(2, ins2[2]);
			iBag.set(1, ins2[3]);
			Assert.assertTrue(iBag.indexOf(ins2[2]) == 2);
			Assert.assertTrue(iBag.get(1) == ins2[3]);
		}

		{
			FloatBag fBag = new FloatBag();
			float[] fls = new float[] { 0, 5, 22, 105 };
			float[] fls2 = new float[] { 1, 5, 3, 2 };
			fBag.addAll(fls);
			for(float fl : fls) {
				Assert.assertTrue(fBag.contains(fl));
				fBag.removeValue(fl);
			}
			Assert.assertTrue(fBag.size() == 0);
			fBag.add(0, fls2[0]);
			fBag.add(1, fls2[1]);
			fBag.add(2, fls2[2]);
			fBag.set(1, fls2[3]);
			Assert.assertTrue(fBag.indexOf(fls2[2]) == 2);
			Assert.assertTrue(fBag.get(1) == fls2[3]);
		}
	}


	@Test
	public void testIntList() {
		testIntListDefault(new IntArrayList(8));
		testIntListSorted(new IntListSorted(8));
	}


	public void testIntListDefault(IntArrayList list) {
		int[] items = new int[] {5, 9, 12, 8, 4, 3, 2, 1, 6};

		list.add(items[0]);
		list.add(items[1]);
		list.add(items[2]);
		Assert.assertTrue(list.remove(1) == 9);
		Assert.assertTrue(list.remove(1) == 12);
		Assert.assertTrue(list.removeValue(5) == true);
		Assert.assertTrue(list.size() == 0);

		list.add(items[3]);
		list.add(items[4]);
		list.add(items[5]);
		int temp = list.get(list.size()-1);
		list.add(0, 567);
		list.add(0, 765);
		Assert.assertTrue(list.get(0) == 765 && list.get(1) == 567 && temp == list.get(list.size() - 1));

		list.remove(1);
		list.remove(0);
		Assert.assertTrue(list.removeValue(items[3]) == true);
		Assert.assertTrue(list.removeValue(items[4]) == true);
		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(list.get(0) == 3);

		list.add(items[6]);
		list.add(items[7]);
		list.add(items[8]);
		Assert.assertTrue(list.removeValue(1) == true);

		list.clear();
		list.addAll(items, 0, items.length);
		Assert.assertTrue(list.size() == items.length);
	}


	public void testIntListSorted(IntListSorted list) {
		int[] items = new int[] {5, 9, 12, 3, 4, 3, 2, 1, 6};

		list.add(items[0]);
		list.add(items[1]);
		list.add(items[2]);
		Assert.assertTrue(list.remove(1) == 9);
		Assert.assertTrue(list.remove(1) == 12);
		Assert.assertTrue(list.removeValue(5) == true);
		Assert.assertTrue(list.size() == 0);

		list.add(items[3]);
		list.add(items[4]);
		list.add(items[5]);
		Assert.assertTrue(list.removeValue(items[3]) == true);
		Assert.assertTrue(list.removeValue(items[4]) == true);
		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(list.get(0) == 3);

		list.add(items[6]);
		list.add(items[7]);
		list.add(items[8]);
		Assert.assertTrue(list.removeValue(1) == true);
	}

}
