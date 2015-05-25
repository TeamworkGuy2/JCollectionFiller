package templates.generators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import templates.MapInfo;
import templates.PrimitiveListInfo;
import typeInfo.JavaPrimitives;
import codeTemplate.ClassTemplate;
import codeTemplate.PrimitiveClassTemplate;
import codeTemplate.PrimitiveTemplates;
import codeTemplate.TemplateUtil;

/**
 * @author TeamworkGuy2
 * @since 2014-12-20
 */
public class GeneratePrimitiveCollections {
	private static Class<?>[] types = { Character.TYPE, Integer.TYPE, Float.TYPE, Long.TYPE, Double.TYPE };

	private static <T extends PrimitiveClassTemplate> PrimitiveClassTemplate[] genTmpls(Function<Class<?>, T> supplier) {
		List<PrimitiveClassTemplate> tmplList = new ArrayList<>();
		for(Class<?> type : types) {
			tmplList.add(PrimitiveTemplates.inferPropertyNames(supplier.apply(type)));
		}
		PrimitiveClassTemplate[] tmpls = tmplList.toArray(new PrimitiveClassTemplate[tmplList.size()]);
		return tmpls;
	}


	public static final void generatePrimitiveListReadOnly() throws IOException {
		String pkgName = "primitiveCollections";

		TemplateUtil.renderTemplates("src/templates/PrimitiveListReadOnly.stg", "PrimitiveListReadOnly", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplate(type, "$Type$ListReadOnly", pkgName);
		}));
	}


	public static final void generatePrimitiveList() throws IOException {
		String pkgName = "primitiveCollections";

		TemplateUtil.renderTemplates("src/templates/PrimitiveList.stg", "PrimitiveList", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplateBuilder(type, new PrimitiveClassTemplate(), "$Type$List", pkgName).implement("$Type$ListReadOnly").getTemplate();
		}));
	}


	public static final void generatePrimitiveArrayList() throws IOException {
		String pkgName = "primitiveCollections";
		String iterWrapper = "$type$IteratorWrapper";
		String arrayListIter = "$type$ArrayListIterator";

		TemplateUtil.renderTemplates("src/templates/PrimitiveArrayList.stg", "PrimitiveArrayList", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplate(type, PrimitiveListInfo.iteratorTypes(type, iterWrapper, arrayListIter), "$Type$ArrayList", pkgName);
		}));
	}


	public static final void generatePrimitiveSortedList() throws IOException {
		String pkgName = "primitiveCollections";
		String iterWrapper = "$type$IteratorWrapper";
		String listSortedIter = "$type$ListSortedIterator";

		TemplateUtil.renderTemplates("src/templates/PrimitiveSortedList.stg", "PrimitiveSortedList", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplate(type, PrimitiveListInfo.iteratorTypes(type, iterWrapper, listSortedIter), "$Type$ListSorted", pkgName);
		}));
	}


	public static final void generatePrimitiveBag() throws IOException {
		String pkgName = "primitiveCollections";

		TemplateUtil.renderTemplates("src/templates/PrimitiveBag.stg", "PrimitiveBag", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplate(type, new PrimitiveListInfo(type), "$Type$Bag", pkgName);
		}));
	}


	public static final void generatePrimitiveViews() throws IOException {
		String pkgName = "primitiveCollections";

		TemplateUtil.renderTemplates("src/templates/PrimitiveArrayView.stg", "PrimitiveArrayView", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplate(type, new ArrayViewInfo(type, "o == objs[i]", true), "$Type$ArrayView", pkgName);
		}));
	}


	public static final void generatePrimitiveMapsReadOnly() throws IOException {
		String pkgName = "primitiveCollections";

		TemplateUtil.renderTemplates("src/templates/PrimitiveMapReadOnly.stg", "PrimitiveMapReadOnly", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplate(type, new MapInfo.MapType(type, "T", "T", ".equals", "null"), "$Type$MapReadOnly", pkgName);
		}));
	}


	public static final void generatePrimitiveMaps() throws IOException {
		String pkgName = "primitiveCollections";

		TemplateUtil.renderTemplates("src/templates/PrimitiveSortedMap.stg", "PrimitiveSortedMap", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplateBuilder(type, new MapInfo.MapType(type, "T", "T", ".equals", "null"), "$Type$MapSorted", pkgName).implement("$Type$MapReadOnly<T>").getTemplate();
		}));
	}


	public static final void generatePrimitiveViewHandles() throws IOException {
		String pkgName = "primitiveCollections";

		PrimitiveIteratorInfo charT = PrimitiveTemplates.newCharTemplate(new PrimitiveIteratorInfo(Character.TYPE, "CharArrayView"), "CharArrayViewHandle", pkgName);
		PrimitiveIteratorInfo intT = PrimitiveTemplates.newIntTemplate(new PrimitiveIteratorInfo(Integer.TYPE, "IntArrayView"), "IntArrayViewHandle", pkgName);
		PrimitiveIteratorInfo floatT = PrimitiveTemplates.newFloatTemplate(new PrimitiveIteratorInfo(Float.TYPE, "FloatArrayView"), "FloatArrayViewHandle", pkgName);
		PrimitiveIteratorInfo longT = PrimitiveTemplates.newLongTemplate(new PrimitiveIteratorInfo(Long.TYPE, "LongArrayView"), "LongArrayViewHandle", pkgName);
		PrimitiveIteratorInfo doubleT = PrimitiveTemplates.newDoubleTemplate(new PrimitiveIteratorInfo(Double.TYPE, "DoubleArrayView"), "DoubleArrayViewHandle", pkgName);

		TemplateUtil.renderTemplates("src/templates/PrimitiveArrayViewHandle.stg", "PrimitiveArrayViewHandle", charT, intT, floatT, longT, doubleT);
	}


	public static final void generatePrimitiveIteratorsReadOnly() throws IOException {
		String pkgName = "primitiveCollections";

		TemplateUtil.renderTemplates("src/templates/PrimitiveIteratorReadOnly.stg", "PrimitiveIteratorReadOnly", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplate(type, new PrimitiveClassTemplate(type), "$Type$IteratorReadOnly", pkgName);
		}));
	}


	public static final void generatePrimitiveIterators() throws IOException {
		String pkgName = "primitiveCollections";

		TemplateUtil.renderTemplates("src/templates/PrimitiveIterator.stg", "PrimitiveIterator", genTmpls((type) -> {
			return PrimitiveTemplates.newPrimitiveTemplateBuilder(type, new PrimitiveClassTemplate(type), "$Type$Iterator", pkgName).implement("$Type$IteratorReadOnly").getTemplate();
		}));
	}


	public static final void generatePrimitiveIteratorImpls() throws IOException {
		String pkgName = "primitiveCollections";
		List<PrimitiveIteratorInfo> infos = new ArrayList<>();
		Class<?>[] classTypes = { Character.TYPE, Integer.TYPE, Float.TYPE, Long.TYPE, Double.TYPE };

		for(Class<?> classType : classTypes) {
			infos.add(generatePrimitiveIteratorInfo(classType, "$type$[]", "length", "[", "]", true, "mod", "$type$ArrayIterator", "$Type$Iterator", pkgName));
		}

		for(Class<?> classType : classTypes) {
			infos.add(generatePrimitiveIteratorInfo(classType, "$Type$ArrayList", "size()", ".get(", ")", false, "col.mod", "$type$ArrayListIterator", "$Type$Iterator", pkgName));
		}

		for(Class<?> classType : classTypes) {
			infos.add(generatePrimitiveIteratorInfo(classType, "$Type$ListSorted", "size()", ".get(", ")", false, "col.mod", "$type$ListSortedIterator", "$Type$Iterator", pkgName));
		}

		TemplateUtil.renderTemplates("src/templates/PrimitiveIteratorImpls.stg", "PrimitiveIteratorImpls", infos.toArray(new PrimitiveIteratorInfo[infos.size()]));
	}


	public static final PrimitiveIteratorInfo generatePrimitiveIteratorInfo(Class<?> primitiveType, String collectionTypeTmpl,
			String sizeGetter, String getterStart, String getterEnd, boolean hasOwnMod, String modGetter, String classNameTmpl, String parentClassNameTmpl, String packageName) {
		PrimitiveIteratorInfo tmpl = PrimitiveTemplates.newPrimitiveTemplate(primitiveType, new PrimitiveIteratorInfo(), "", packageName);
		String typeNameUpper = tmpl.typeShortUpperCase;
		String typeNameLower = tmpl.typeShort;
		tmpl.className = classNameTmpl.replace("$type$", tmpl.typeShortUpperCase);
		tmpl.collectionType = collectionTypeTmpl.replace("$type$", typeNameLower).replace("$Type$", typeNameUpper);
		tmpl.implementClassNames = Arrays.asList(parentClassNameTmpl.replace("$type$", typeNameLower).replace("$Type$", typeNameUpper));
		tmpl.sizeGetter = sizeGetter;
		tmpl.getterStart = getterStart;
		tmpl.getterEnd = getterEnd;
		tmpl.modGetter = modGetter;
		tmpl.hasOwnMod = hasOwnMod;

		return tmpl;
	}


	public static final void generatePrimitiveIteratorWrappers() throws IOException {
		String pkgName = "primitiveCollections";
		WrapperTemplateInfo charT = PrimitiveTemplates.newCharTemplate(new WrapperTemplateInfo(Character.TYPE), "CharIteratorWrapper", pkgName);
		WrapperTemplateInfo intT = PrimitiveTemplates.newIntTemplate(new WrapperTemplateInfo(Integer.TYPE), "IntIteratorWrapper", pkgName);
		WrapperTemplateInfo floatT = PrimitiveTemplates.newFloatTemplate(new WrapperTemplateInfo(Float.TYPE), "FloatIteratorWrapper", pkgName);
		WrapperTemplateInfo longT = PrimitiveTemplates.newLongTemplate(new WrapperTemplateInfo(Long.TYPE), "LongIteratorWrapper", pkgName);
		WrapperTemplateInfo doubleT = PrimitiveTemplates.newDoubleTemplate(new WrapperTemplateInfo(Double.TYPE), "DoubleIteratorWrapper", pkgName);

		TemplateUtil.renderTemplates("src/templates/PrimitiveIteratorWrapper.stg", "PrimitiveIteratorWrapper", charT, intT, floatT, longT, doubleT);
	}


	public static final void generatePrimitiveCollections() throws IOException {
		generatePrimitiveListReadOnly();
		generatePrimitiveList();
		generatePrimitiveArrayList();
		generatePrimitiveSortedList();
		generatePrimitiveBag();
		generatePrimitiveViews();
		generatePrimitiveMapsReadOnly();
		generatePrimitiveMaps();
		generatePrimitiveViewHandles();
		generatePrimitiveIteratorsReadOnly();
		generatePrimitiveIterators();
		generatePrimitiveIteratorImpls();
		generatePrimitiveIteratorWrappers();
	}


	public static void main(String[] args) throws IOException {
		generatePrimitiveCollections();
	}


	/**
	 * @author TeamworkGuy2
	 * @since 2015-1-17
	 */
	public static class PrimitiveInfos extends ClassTemplate {
		public List<IteratorInfos> iteratorTypes;
	}


	public static class IteratorInfos extends ClassTemplate {
		@SuppressWarnings("hiding")
		public List<PrimitiveIteratorInfo> types;
	}


	public static class WrapperTemplateInfo extends PrimitiveClassTemplate {
		public String iteratorName;

		public WrapperTemplateInfo(Class<?> primitiveType) {
			super(primitiveType);
			this.iteratorName = this.typeShortUpperCase + "Iterator";
		}

	}


	public static class MapTmplInfo extends PrimitiveClassTemplate {
		public String valueType;
		public String defaultValueValue;

		public MapTmplInfo(Class<?> keyType, Class<?> valueType, String defaultValueValue) {
			super(keyType);
			this.valueType = JavaPrimitives.getByType(valueType).getJavaPrimitiveName();
			this.defaultValueValue = defaultValueValue;
		}

	}


	public static class ArrayViewInfo extends WrapperTemplateInfo {
		public String collectionType;
		public String iteratorImplName;
		public String iteratorWrapperName;
		public String compareValues;
		public boolean isPrimitive;

		public ArrayViewInfo(Class<?> primitiveType, String compareValues, boolean isPrimitive) {
			super(primitiveType);
			this.collectionType = this.typeShortUpperCase + "ArrayList";
			this.compareValues = compareValues;
			this.isPrimitive = isPrimitive;
			this.implementClassNames = Arrays.asList(this.typeShortUpperCase + "List");
			this.iteratorImplName = this.typeShortUpperCase + "ArrayIterator";
			this.iteratorWrapperName = this.typeShortUpperCase + "IteratorWrapper";
		}

	}


	/**
	 * @author TeamworkGuy2
	 * @since 2015-1-17
	 */
	public static class PrimitiveIteratorInfo extends PrimitiveClassTemplate {
		public String collectionType;
		public String sizeGetter;
		public String getterStart;
		public String getterEnd;
		public String modGetter;
		public boolean hasOwnMod;

		public PrimitiveIteratorInfo() {
		}

		public PrimitiveIteratorInfo(Class<?> primitiveType) {
			super(primitiveType);
		}

		public PrimitiveIteratorInfo(Class<?> primitiveType, String collectionType) {
			super(primitiveType);
			this.collectionType = collectionType;
		}

	}

}
