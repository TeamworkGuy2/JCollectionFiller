package twg2.collections.dataStructures;

import java.util.HashMap;
import java.util.Map;

import twg2.primitiveIoTypes.JNumericType;

/**
 * @author TeamworkGuy2
 * @since 2015-10-3
 */
public class FrequencyMap<K, V extends Number> {
	private Map<K, V> frequencies;
	private JNumericType frequencyType;


	public FrequencyMap(JNumericType frequencyType) {
		this.frequencyType = frequencyType;
		this.frequencies = new HashMap<>();
	}


	public boolean contains(K key) {
		return frequencies.containsKey(key);
	}


	public int size() {
		return frequencies.size();
	}


	public boolean isEmpty() {
		return frequencies.isEmpty();
	}


	public V get(K key) {
		return frequencies.get(key);
	}


	public V remove(K key) {
		return frequencies.remove(key);
	}


	public Map<K, V> getRawFrequencyMap() {
		return frequencies;
	}


	public void increment(K key) {
		getAndAdd(key, 1);
	}


	public void decrement(K key) {
		getAndAdd(key, -1);
	}


	public V getAndIncrement(K key) {
		return getAndAdd(key, 1);
	}


	public V getAndDecrement(K key) {
		return getAndAdd(key, -1);
	}


	public void add(K key, double value) {
		getAndAdd(key, value);
	}


	public void subtract(K key, double value) {
		getAndAdd(key, value);
	}


	public V getAndAdd(K key, double add) {
		V value = frequencies.get(key);
		@SuppressWarnings("unchecked")
		Class<V> primitiveType = (Class<V>)frequencyType.getType();
		frequencies.put(key, of(primitiveType, frequencyType, (value != null ? value.doubleValue() + add : add)));
		return value;
	}


	@SuppressWarnings("unchecked")
	public static final <V> V of(Class<V> primitiveType, JNumericType type, double value) {
		switch(type) {
		case BYTE:
			return (V) Byte.valueOf((byte)value);
		case DOUBLE:
			return (V) Double.valueOf(value);
		case FLOAT:
			return (V) Float.valueOf((float)value);
		case INT:
			return (V) Integer.valueOf((int)value);
		case LONG:
			return (V) Long.valueOf((long)value);
		case SHORT:
			return (V) Short.valueOf((short)value);
		default:
			throw new IllegalArgumentException("unknown numeric type '" + type + "'");
		}
	}

}
