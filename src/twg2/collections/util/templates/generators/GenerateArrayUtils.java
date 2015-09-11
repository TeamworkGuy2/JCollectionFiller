package twg2.collections.util.templates.generators;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import twg2.collections.util.templates.ArrayInfo;
import codeTemplate.primitiveTemplate.PrimitiveTemplates;
import codeTemplate.render.TemplateRenders;

/**
 * @author TeamworkGuy2
 * @since 2014-12-28
 */
public class GenerateArrayUtils {
	private static String tmplDir = "src/twg2/collections/util/templates/";
	private static ArrayInfo.ArrayType genericType = new ArrayInfo.ArrayType(true, "<T>", ".equals", false, "null");
	private static List<ArrayInfo.ArrayType> aryTypes;

	static {
		genericType.type = "T";

		aryTypes = Arrays.asList(
			PrimitiveTemplates.newBooleanTemplate(new ArrayInfo.ArrayType(false, null, " == ", false), null, null),
			PrimitiveTemplates.newByteTemplate(new ArrayInfo.ArrayType(false, null, " == ",	true), null, null),
			PrimitiveTemplates.newShortTemplate(new ArrayInfo.ArrayType(false, null, " == ", true), null, null),
			PrimitiveTemplates.newCharTemplate(new ArrayInfo.ArrayType(false, null, " == ", true), null, null),
			PrimitiveTemplates.newIntTemplate(new ArrayInfo.ArrayType(false, null, " == ", true), null, null),
			PrimitiveTemplates.newLongTemplate(new ArrayInfo.ArrayType(false, null, " == ", true), null, null),
			PrimitiveTemplates.newFloatTemplate(new ArrayInfo.ArrayType(false, null, " == ", true), null, null),
			PrimitiveTemplates.newDoubleTemplate(new ArrayInfo.ArrayType(false, null, " == ", true), null, null),
			genericType
		);
	}


	public static final void generateArrayUtil() throws IOException {
		ArrayInfo info = new ArrayInfo();
		info.className = "ArrayUtil";
		info.packageName = "twg2.collections.util.arrayUtils";

		info.types = aryTypes;

		TemplateRenders.renderClassTemplate(tmplDir + "TArrayUtil.stg", "TArrayUtil", info);
	}



	public static final void generateArrayManaged() throws IOException {
		ArrayInfo info = new ArrayInfo();
		info.className = "ArrayManaged";
		info.packageName = "twg2.collections.util.arrayUtils";

		info.types = aryTypes;

		TemplateRenders.renderClassTemplate(tmplDir + "TArrayManaged.stg", "TArrayManaged", info);
	}

	public static void main(String[] args) throws IOException {
		generateArrayUtil();
		generateArrayManaged();
	}

}
