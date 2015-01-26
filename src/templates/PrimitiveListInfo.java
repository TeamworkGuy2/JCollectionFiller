package templates;

import java.util.Arrays;

import codeTemplate.PrimitiveClassTemplate;

/**
 * @author TeamworkGuy2
 * @since 2014-12-24
 */
public class PrimitiveListInfo extends PrimitiveClassTemplate {
	/** the class name of the iterator class this primitive list uses */
	public String iteratorName;
	/** the class name of the primitive iterator class this primitive list uses */
	public String iteratorPrimitiveName;


	public PrimitiveListInfo(Class<?> primitiveType) {
		super(primitiveType);
		this.extendClassName = this.typeShortUpperCase + "ArrayList";
		this.implementClassNames = Arrays.asList(this.typeShortUpperCase + "List");
	}


	public static PrimitiveListInfo iteratorTypes(Class<?> primitiveType, String iterator, String iteratorPrimitive) {
		PrimitiveListInfo info = new PrimitiveListInfo(primitiveType);
		info.iteratorName = iterator.replace("$type$", info.typeShortUpperCase);
		info.iteratorPrimitiveName = iteratorPrimitive.replace("$type$", info.typeShortUpperCase);
		return info;
	}

}
