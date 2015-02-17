package templates.generators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public static final void generatePrimitiveList() throws IOException {
		String pkgName = "primitiveCollections";

		PrimitiveClassTemplate charT = PrimitiveTemplates.newCharTemplate(new PrimitiveClassTemplate(), "CharList", pkgName);
		PrimitiveClassTemplate intT = PrimitiveTemplates.newIntTemplate(new PrimitiveClassTemplate(), "IntList", pkgName);
		PrimitiveClassTemplate floatT = PrimitiveTemplates.newFloatTemplate(new PrimitiveClassTemplate(), "FloatList", pkgName);
		PrimitiveClassTemplate longT = PrimitiveTemplates.newLongTemplate(new PrimitiveClassTemplate(), "LongList", pkgName);
		PrimitiveClassTemplate doubleT = PrimitiveTemplates.newDoubleTemplate(new PrimitiveClassTemplate(), "DoubleList", pkgName);

		TemplateUtil.renderTemplates("src/templates/PrimitiveList.stg", "PrimitiveList", charT, intT, floatT, longT, doubleT);
	}


	public static final void generatePrimitiveArrayList() throws IOException {
		String pkgName = "primitiveCollections";
		String iterWrapper = "$type$IteratorWrapper";
		String arrayListIter = "$type$ArrayListIterator";

		PrimitiveListInfo charT = PrimitiveTemplates.newCharTemplate(PrimitiveListInfo.iteratorTypes(Character.TYPE, iterWrapper, arrayListIter), "CharArrayList", pkgName);
		PrimitiveListInfo intT = PrimitiveTemplates.newIntTemplate(PrimitiveListInfo.iteratorTypes(Integer.TYPE, iterWrapper, arrayListIter), "IntArrayList", pkgName);
		PrimitiveListInfo floatT = PrimitiveTemplates.newFloatTemplate(PrimitiveListInfo.iteratorTypes(Float.TYPE, iterWrapper, arrayListIter), "FloatArrayList", pkgName);
		PrimitiveListInfo longT = PrimitiveTemplates.newLongTemplate(PrimitiveListInfo.iteratorTypes(Long.TYPE, iterWrapper, arrayListIter), "LongArrayList", pkgName);
		PrimitiveListInfo doubleT = PrimitiveTemplates.newDoubleTemplate(PrimitiveListInfo.iteratorTypes(Double.TYPE, iterWrapper, arrayListIter), "DoubleArrayList", pkgName);

		TemplateUtil.renderTemplates("src/templates/PrimitiveArrayList.stg", "PrimitiveArrayList", charT, intT, floatT, longT, doubleT);
	}


	public static final void generatePrimitiveSortedList() throws IOException {
		String pkgName = "primitiveCollections";
		String iterWrapper = "$type$IteratorWrapper";
		String listSortedIter = "$type$ListSortedIterator";

		PrimitiveListInfo charT = PrimitiveTemplates.newCharTemplate(PrimitiveListInfo.iteratorTypes(Character.TYPE, iterWrapper, listSortedIter), "CharListSorted", pkgName);
		PrimitiveListInfo intT = PrimitiveTemplates.newIntTemplate(PrimitiveListInfo.iteratorTypes(Integer.TYPE, iterWrapper, listSortedIter), "IntListSorted", pkgName);
		PrimitiveListInfo floatT = PrimitiveTemplates.newFloatTemplate(PrimitiveListInfo.iteratorTypes(Float.TYPE, iterWrapper, listSortedIter), "FloatListSorted", pkgName);
		PrimitiveListInfo longT = PrimitiveTemplates.newLongTemplate(PrimitiveListInfo.iteratorTypes(Long.TYPE, iterWrapper, listSortedIter), "LongListSorted", pkgName);
		PrimitiveListInfo doubleT = PrimitiveTemplates.newDoubleTemplate(PrimitiveListInfo.iteratorTypes(Double.TYPE, iterWrapper, listSortedIter), "DoubleListSorted", pkgName);

		TemplateUtil.renderTemplates("src/templates/PrimitiveSortedList.stg", "PrimitiveSortedList", charT, intT, floatT, longT, doubleT);
	}


	public static final void generatePrimitiveBag() throws IOException {
		String pkgName = "primitiveCollections";

		PrimitiveListInfo charT = PrimitiveTemplates.newCharTemplate(new PrimitiveListInfo(Character.TYPE), "CharBag", pkgName);
		PrimitiveListInfo intT = PrimitiveTemplates.newIntTemplate(new PrimitiveListInfo(Integer.TYPE), "IntBag", pkgName);
		PrimitiveListInfo floatT = PrimitiveTemplates.newFloatTemplate(new PrimitiveListInfo(Float.TYPE), "FloatBag", pkgName);
		PrimitiveListInfo longT = PrimitiveTemplates.newLongTemplate(new PrimitiveListInfo(Long.TYPE), "LongBag", pkgName);
		PrimitiveListInfo doubleT = PrimitiveTemplates.newDoubleTemplate(new PrimitiveListInfo(Double.TYPE), "DoubleBag", pkgName);

		TemplateUtil.renderTemplates("src/templates/PrimitiveBag.stg", "PrimitiveBag", charT, intT, floatT, longT, doubleT);
	}


	public static final void generatePrimitiveViews() throws IOException {
		String pkgName = "primitiveCollections";

		ArrayViewInfo charT = PrimitiveTemplates.newCharTemplate(new ArrayViewInfo(Character.TYPE, "o == objs[i]", true), "CharArrayView", pkgName);
		ArrayViewInfo intT = PrimitiveTemplates.newIntTemplate(new ArrayViewInfo(Integer.TYPE, "o == objs[i]", true), "IntArrayView", pkgName);
		ArrayViewInfo floatT = PrimitiveTemplates.newFloatTemplate(new ArrayViewInfo(Float.TYPE, "o == objs[i]", true), "FloatArrayView", pkgName);
		ArrayViewInfo longT = PrimitiveTemplates.newLongTemplate(new ArrayViewInfo(Long.TYPE, "o == objs[i]", true), "LongArrayView", pkgName);
		ArrayViewInfo doubleT = PrimitiveTemplates.newDoubleTemplate(new ArrayViewInfo(Double.TYPE, "o == objs[i]", true), "DoubleArrayView", pkgName);

		TemplateUtil.renderTemplates("src/templates/PrimitiveArrayView.stg", "PrimitiveArrayView", charT, intT, floatT, longT, doubleT);
	}


	public static final void generatePrimitiveMaps() throws IOException {
		String pkgName = "primitiveCollections";

		MapInfo.MapType charT = PrimitiveTemplates.newCharTemplate(new MapInfo.MapType(Character.TYPE, "T", "T", ".equals", "null"), "CharMapSorted", pkgName);
		MapInfo.MapType intT = PrimitiveTemplates.newIntTemplate(new MapInfo.MapType(Integer.TYPE, "T", "T", ".equals", "null"), "IntMapSorted", pkgName);
		MapInfo.MapType floatT = PrimitiveTemplates.newFloatTemplate(new MapInfo.MapType(Float.TYPE, "T", "T", ".equals", "null"), "FloatMapSorted", pkgName);
		MapInfo.MapType longT = PrimitiveTemplates.newLongTemplate(new MapInfo.MapType(Long.TYPE, "T", "T", ".equals", "null"), "LongMapSorted", pkgName);
		MapInfo.MapType doubleT = PrimitiveTemplates.newDoubleTemplate(new MapInfo.MapType(Double.TYPE, "T", "T", ".equals", "null"), "DoubleMapSorted", pkgName);

		TemplateUtil.renderTemplates("src/templates/PrimitiveSortedMap.stg", "PrimitiveSortedMap", charT, intT, floatT, longT, doubleT);
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


	public static final void generatePrimitiveIterators() throws IOException {
		String pkgName = "primitiveCollections";

		PrimitiveListInfo charT = PrimitiveTemplates.newCharTemplate(new PrimitiveListInfo(Character.TYPE), "CharIterator", pkgName);
		PrimitiveListInfo intT = PrimitiveTemplates.newIntTemplate(new PrimitiveListInfo(Integer.TYPE), "IntIterator", pkgName);
		PrimitiveListInfo floatT = PrimitiveTemplates.newFloatTemplate(new PrimitiveListInfo(Float.TYPE), "FloatIterator", pkgName);
		PrimitiveListInfo longT = PrimitiveTemplates.newLongTemplate(new PrimitiveListInfo(Long.TYPE), "LongIterator", pkgName);
		PrimitiveListInfo doubleT = PrimitiveTemplates.newDoubleTemplate(new PrimitiveListInfo(Double.TYPE), "DoubleIterator", pkgName);

		TemplateUtil.renderTemplates("src/templates/PrimitiveIterator.stg", "PrimitiveIterator", charT, intT, floatT, longT, doubleT);
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
		generatePrimitiveList();
		generatePrimitiveArrayList();
		generatePrimitiveSortedList();
		generatePrimitiveBag();
		generatePrimitiveViews();
		generatePrimitiveMaps();
		generatePrimitiveViewHandles();
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
