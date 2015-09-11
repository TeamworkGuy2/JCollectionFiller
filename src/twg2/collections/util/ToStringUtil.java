package twg2.collections.util;

import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-9-10
 */
public final class ToStringUtil {


	/** Convert a pair of arrays to key-value strings in the format {@code [key0=value0, key1=value1, ...]}
	 * @param dstOptional the {@link StringBuilder} to store the key-value string representation in, can be null in which case a default StringBuilder is created
	 */
	public static StringBuilder toStringKeyValuePairs(Object[] keys, Object[] values, int size, StringBuilder dstOptional) {
		StringBuilder sb = dstOptional != null ? dstOptional : new StringBuilder(size > 50 ? 512 : size * 8);
		sb.append('[');
		if(size > 0) {
			int sizeTemp = size - 1;
			for(int i = 0; i < sizeTemp; i++) {
				sb.append(keys[i]);
				sb.append('=');
				sb.append(values[i]);
				sb.append(", ");
			}
			sb.append(keys[sizeTemp]);
			sb.append('=');
			sb.append(values[sizeTemp]);
		}
		sb.append(']');

		return sb;
	}


	public static StringBuilder toStringKeyValuePairs(Object[] keys, int keysOff, Object[] values, int valuesOff, int len, StringBuilder dstOptional) {
		StringBuilder sb = dstOptional != null ? dstOptional : new StringBuilder();
		sb.append('[');
		if(len > 0) {
			int count = len - 1;
			int keyOff = keysOff;
			int valOff = valuesOff;
			for(int i = 0; i < count; i++) {
				sb.append(keys[keyOff + i]);
				sb.append('=');
				sb.append(values[valOff + i]);
				sb.append(", ");
			}
			sb.append(keys[keyOff + count]);
			sb.append('=');
			sb.append(values[valOff + count]);
		}
		sb.append(']');

		return sb;
	}


	/** Convert a pair of lists to key-value strings in the format {@code [key0=value0, key1=value1, ...]}
	 * @param dstOptional the {@link StringBuilder} to store the key-value string representation in, can be null in which case a default StringBuilder is created
	 */
	public static <K, V> StringBuilder toStringKeyValuePairs(List<? extends K> keys, List<? extends V> values, int size, StringBuilder dstOptional) {
		StringBuilder sb = dstOptional != null ? dstOptional : new StringBuilder();
		sb.append('[');
		if(size > 0) {
			int sizeTemp = size - 1;
			for(int i = 0; i < sizeTemp; i++) {
				sb.append(keys.get(i));
				sb.append('=');
				sb.append(values.get(i));
				sb.append(", ");
			}
			sb.append(keys.get(sizeTemp));
			sb.append('=');
			sb.append(values.get(sizeTemp));
		}
		sb.append(']');

		return sb;
	}

}
