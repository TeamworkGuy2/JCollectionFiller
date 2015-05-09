package templates;

import java.util.List;

import codeTemplate.ClassTemplate;
import codeTemplate.PrimitiveClassTemplate;

/**
 * @author TeamworkGuy2
 * @since 2014-12-28
 */
public class ArrayInfo extends ClassTemplate {
	public List<ArrayType> types;




	/**
	 * @author TeamworkGuy2
	 * @since 2014-12-28
	 */
	public static class ArrayType extends PrimitiveClassTemplate {
		public boolean isGeneric;
		public String genericSignature;
		public String checkEquality;


		public ArrayType(boolean isGeneric, String genericSignature, String checkEquality, boolean doAggregate) {
			this.isGeneric = isGeneric;
			this.genericSignature = genericSignature;
			this.checkEquality = checkEquality;
			this.isAggregatable = doAggregate;
		}


		/**
		 * @param isGeneric true if the type is a generic type
		 * @param defaultValue the default value for this data type
		 * @param genericSignature a generic signature string like {@code <T>} or
		 * {@code <E extends CharSequence>}
		 * @param checkEquality the type of equality check to use when comparing two values
		 * of this type, for example {@code "=="} or {@code ".equals"}
		 * @param doAggregate true to include {@code sum(), avg(), min(), max()} methods for this type
		 */
		public ArrayType(boolean isGeneric, String genericSignature, String checkEquality, boolean doAggregate, String defaultValue) {
			this.isGeneric = isGeneric;
			this.genericSignature = genericSignature;
			this.checkEquality = checkEquality;
			this.isAggregatable = doAggregate;
			this.defaultValue = defaultValue;
		}


		public String createEqualityCheck(String varName1, String varName2) {
			return varName1 + checkEquality + "(" + varName2 + ")";
		}

	}

}
