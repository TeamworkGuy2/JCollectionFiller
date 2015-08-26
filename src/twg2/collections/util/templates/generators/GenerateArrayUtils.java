package twg2.collections.util.templates.generators;

import java.io.IOException;
import java.util.Arrays;

import twg2.collections.util.templates.ArrayInfo;
import codeTemplate.primitiveTemplate.PrimitiveTemplates;
import codeTemplate.render.TemplateRenders;

/**
 * @author TeamworkGuy2
 * @since 2014-12-28
 */
public class GenerateArrayUtils {


	public static final void generateArrayUtil() throws IOException {
		ArrayInfo info = new ArrayInfo();
		info.className = "ArrayUtil";
		info.packageName = "twg2.collections.util.arrayUtils";

		ArrayInfo.ArrayType genericType = new ArrayInfo.ArrayType(true, "<T>", ".equals", false, "null");
		genericType.type = "T";

		info.types = Arrays.asList(
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

		TemplateRenders.renderClassTemplate("src/twg2/collections/util/templates/TArrayUtil.stg", "TArrayUtil", info);
	}


	public static void main(String[] args) throws IOException {
		generateArrayUtil();
	}

}
