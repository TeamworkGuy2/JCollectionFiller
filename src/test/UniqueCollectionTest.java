package test;

import org.junit.Assert;
import org.junit.Test;

import primitiveCollections.IntArrayList;
import uniqueCollection.UniqueIdArray;
import uniqueCollection.UniqueIdGenerator;
import uniqueCollection.UniqueIdMap;

/**
 * @author TeamworkGuy2
 * @since 2014-12-17
 */
public final class UniqueCollectionTest {


	@Test
	public void testUniqueIdArray() {
		UniqueIdArray unique = new UniqueIdArray();
		IntArrayList ids = new IntArrayList();
		int count = 5;
		for(int i = 0; i < count; i++) {
			int id = unique.createId();
			Assert.assertTrue(id > -1);
			ids.add(id);
		}

		Assert.assertTrue(unique.size() == count);
		Assert.assertTrue(ids.toArray().length == count);

		for(int i = 0; i < count; i++) {
			Assert.assertTrue(unique.deleteId(i));
		}

		Assert.assertTrue(unique.size() == 0);
	}


	@Test
	public void testUniqueIdGenerator() {
		UniqueIdGenerator unique = new UniqueIdGenerator();
		IntArrayList ids = new IntArrayList();
		int count = 5;
		for(int i = 0; i < count; i++) {
			int id = unique.createId();
			Assert.assertTrue(id > -1);
			ids.add(id);
		}

		Assert.assertTrue(unique.size() == count);
		Assert.assertTrue(ids.toArray().length == count);

		for(int i = 0; i < count; i++) {
			Assert.assertTrue(unique.deleteId(i));
		}

		Assert.assertTrue(unique.size() == 0);
	}


	@Test
	public void testUniqueIdMap() {
		UniqueIdMap<String> unique = new UniqueIdMap<>();
		IntArrayList ids = new IntArrayList();
		String[] values = { "thum", "val", "are", "cal", "wev" };
		int count = 5;
		for(int i = 0; i < count; i++) {
			int id = unique.createId(values[i]);
			Assert.assertTrue(id > -1);
			ids.add(id);
		}

		Assert.assertTrue(unique.size() == count);
		Assert.assertTrue(ids.toArray().length == count);

		for(int i = 0; i < count; i++) {
			Assert.assertTrue(unique.deleteId(i).equals(values[i]));
		}

		Assert.assertTrue(unique.size() == 0);
	}

}
