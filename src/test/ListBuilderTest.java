package test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import collectionUtils.ListBuilder;

/**
 * @author TeamworkGuy2
 * @since 2015-7-12
 */
public class ListBuilderTest {

	@Test
	public void testCopyDeep() {
		List<List<Object>> deepList = Arrays.asList(
				Arrays.asList("A", "B", "C"),
				Arrays.asList("str1"),
				Arrays.asList(Arrays.asList("01", "02"), "3"),
				Arrays.asList(Arrays.asList(Arrays.asList("<", ">"), Arrays.asList("=")), "3")
			);

		List<List<Object>> copiedList = ListBuilder.copyDeep(deepList);
		Assert.assertEquals(deepList, copiedList);
	}

}
