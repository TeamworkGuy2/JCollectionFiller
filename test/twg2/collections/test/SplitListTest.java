package twg2.collections.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import twg2.collections.dataStructures.SplitList;

/**
 * @author TeamworkGuy2
 * @since 2016-2-14
 */
public class SplitListTest {

	@Test
	public void fixedBlockCount() {
		List<String> strs = null;

		strs = Arrays.asList();
		Assert.assertEquals(list(list(), list(), list()), SplitList.fixedBlockCount(3, strs));

		strs = Arrays.asList("A");
		Assert.assertEquals(list(list(), list(), list("A")), SplitList.fixedBlockCount(3, strs));

		strs = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
		Assert.assertEquals(list(list("A", "B"), list("C", "D"), list("E", "F", "G")), SplitList.fixedBlockCount(3, strs));

		strs = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
		Assert.assertEquals(list(list("A", "B", "C"), list("D", "E", "F"), list("G", "H")), SplitList.fixedBlockCount(3, strs));

		strs = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I");
		Assert.assertEquals(list(list("A", "B", "C"), list("D", "E", "F"), list("G", "H", "I")), SplitList.fixedBlockCount(3, strs));
	}


	@Test
	public void fixedBlockSize() {
		List<String> strs = null;

		strs = Arrays.asList();
		Assert.assertEquals(list(), SplitList.fixedBlockSize(3, strs));

		strs = Arrays.asList("A");
		Assert.assertEquals(list(list("A")), SplitList.fixedBlockSize(3, strs));

		strs = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
		Assert.assertEquals(list(list("A", "B", "C"), list("D", "E", "F"), list("G")), SplitList.fixedBlockSize(3, strs));

		strs = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
		Assert.assertEquals(list(list("A", "B", "C"), list("D", "E", "F"), list("G", "H")), SplitList.fixedBlockSize(3, strs));

		strs = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I");
		Assert.assertEquals(list(list("A", "B", "C"), list("D", "E", "F"), list("G", "H", "I")), SplitList.fixedBlockSize(3, strs));
	}


	@SafeVarargs
	private static final <T> List<T> list(T... ts) {
		return Arrays.asList(ts);
	}

}
