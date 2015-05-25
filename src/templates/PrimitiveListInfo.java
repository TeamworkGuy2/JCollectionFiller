package templates;

import java.util.ArrayList;
import java.util.List;

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
		this.implementClassNames = new ArrayList<>();
		this.implementClassNames.add(this.typeShortUpperCase + "List");
	}


	public static PrimitiveListInfo iteratorTypes(Class<?> primitiveType, String iterator, String iteratorPrimitive) {
		PrimitiveListInfo info = new PrimitiveListInfo(primitiveType);
		info.iteratorName = iterator.replace("$type$", info.typeShortUpperCase);
		info.iteratorPrimitiveName = iteratorPrimitive.replace("$type$", info.typeShortUpperCase);
		return info;
	}


	public static final <T extends PrimitiveClassTemplate> T implementClasses(T classInfo, List<String> implementsClasses) {
		classInfo.implementClassNames = new ArrayList<>(implementsClasses);
		return classInfo;
	}

}
