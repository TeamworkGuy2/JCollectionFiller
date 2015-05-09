package templates;

import java.util.ArrayList;
import java.util.List;

import codeTemplate.ClassTemplate;
import codeTemplate.PrimitiveClassTemplate;

public class MapInfo extends ClassTemplate {
	public List<MapType> types;




	public static class MapType extends PrimitiveClassTemplate {
		public String valueTypeSignature;
		public String valueType;
		public String checkEquality;
		public String defaultValueValue;


		/**
		 * @param keyType the Map's key data type class, for example {@code Integer.TYPE} or
		 * {@code String}
		 * @param genericSignature a generic signature string like {@code <T>} or
		 * {@code <E extends CharSequence>}
		 * @param genericTypeName the name of the {@code genericSignature} type, for example, if the
		 * {@code genericSignature = "E extends CharSequence"}, the {@code enericTypeName} would be {@code "E"}
		 * @param checkEquality the type of equality check to use when comparing two values
		 * of this type, for example {@code "=="} or {@code ".equals"}
		 * @param defaultValueValue the default value of the generic type
		 */
		public MapType(Class<?> keyType, String genericSignature, String genericTypeName, String checkEquality, String defaultValueValue) {
			super(keyType);
			this.classTypeParameterDefinitions = new ArrayList<String>();
			this.classTypeParameterDefinitions.add(genericSignature);

			this.classTypeParameterNames = new ArrayList<String>();
			this.classTypeParameterNames.add(genericTypeName);

			this.checkEquality = checkEquality;
			this.defaultValueValue = defaultValueValue;
			this.valueTypeSignature = genericSignature;
			this.valueType = genericTypeName;
		}
	}

}
