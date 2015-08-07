package twg2.collections.util.templates.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import codeTemplate.ClassTemplate;
import codeTemplate.TemplateRender;

/**
 * @author TeamworkGuy2
 * @since 2015-8-6
 */
public class GenerateTuples {

	public static class TupleInfo extends ClassTemplate {
		public List<Integer> types = new ArrayList<>();
	}


	public static class TuplesInfo extends ClassTemplate {
		public List<List<Integer>> types = new ArrayList<>();
	}




	public static void generateTuples() {
		int start = 2;
		int count = 3;

		generateTuplesUtil(start, count, "Tuples");

		IntStream.range(start, start + count).forEach((i) -> {
			generateTuple(i, "Tuple" + i);
		});
	}


	public static void generateTuplesUtil(int startIndex, int count, String name) {
		TuplesInfo info = new TuplesInfo();
		info.className = name;
		info.packageName = "tuple";

		info.types = Arrays.asList(IntStream.range(startIndex, startIndex + count).mapToObj((i) -> Arrays.asList(IntStream.range(0, i).boxed().toArray((s) -> new Integer[s]))).toArray((s) -> new List[s]));

		TemplateRender.renderClassTemplate("src/twg2/collections/util/templates/TTuples.stg", "TTuples", info);
	}


	public static void generateTuple(int count, String name) {
		TupleInfo info = new TupleInfo();
		info.className = name;
		info.packageName = "tuple";

		if(count == 2) {
			info.implementClassNames = Arrays.asList("java.util.Map.Entry<A0, A1>");
		}

		info.types = Arrays.asList(IntStream.range(0, count).boxed().toArray((s) -> new Integer[s]));

		TemplateRender.renderClassTemplate("src/twg2/collections/util/templates/TTuple.stg", "TTuple", info);
	}


	public static void main(String[] args) {
		generateTuples();
	}

}
