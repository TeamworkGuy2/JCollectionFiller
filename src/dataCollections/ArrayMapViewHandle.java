package dataCollections;

/** The handle for an {@link ArrayMapView} which is used to modify the ArrayMapView's
 * underlying array using {@link #setArrayView(Object[], int, Object[], int, int)}.
 * Use {@link #getArrayView()} to retrieve the array view.
 * An instance of this class manages exactly one {@link ArrayMapView}.
 * @param <K> type of keys in this array map view
 * @param <V> type of values in this array map view
 * @author TeamworkGuy2
 * @since 2015-4-5
 */
public class ArrayMapViewHandle<K, V> {
	private final ArrayMapView<K, V> arrayMapView;


	public ArrayMapViewHandle() {
		this.arrayMapView = new ArrayMapView<>();
	}


	/** Create an array view of an entire key and value array
	 * @param keys the keys array to create a view of
	 * @param values the values array to create a view of
	 */
	public ArrayMapViewHandle(K[] keys, V[] values) {
		this.arrayMapView = new ArrayMapView<>(keys, 0, values, 0, keys.length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param keys the keys array to create a view of
	 * @param keysOffset the offset into {@code keys} of the array view's {@code 0th} index
	 * @param values the values array to create a view of
	 * @param valuesOffset the offset into {@code values} of the array view's {@code 0th} index
	 * @param length the number of keys and values starting at {@code offset} to include in this view
	 */
	public ArrayMapViewHandle(K[] keys, int keysOffset, V[] values, int valuesOffset, int length) {
		this(keys, keysOffset, values, valuesOffset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param keys the keys array to create a view of
	 * @param keysOff the offset into {@code keys} of the array view's {@code 0th} index
	 * @param vals the values array to create a view of
	 * @param valsOff the offset into {@code values} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow this handle's {@link ArrayMapView#set(int, java.util.Map.Entry)} method
	 * to be called, false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public ArrayMapViewHandle(K[] keys, int keysOff, V[] vals, int valsOff, int length, boolean allowSet) {
		this.arrayMapView = new ArrayMapView<>(keys, keysOff, vals, valsOff, length, allowSet);
	}


	public ArrayMapView<K, V> getArrayView() {
		return arrayMapView;
	}


	public void setArrayView(K[] keys, int keysOff, V[] vals, int valsOff, int length) {
		arrayMapView.setArrayView(keys, keysOff, vals, valsOff, length);
	}

}
