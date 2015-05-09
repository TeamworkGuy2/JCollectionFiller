package primitiveCollections;

/** The handle for an {@link FloatArrayView} which is used to modify the FloatArrayView's
 * underlying array using {@link #setFloatArrayView(float[], int, int)}.
 * Use {@link #getFloatArrayView()} to retrieve the array view.
 * An instance of this class manages exactly one {@link FloatArrayView}.
 * @author TeamworkGuy2
 * @since 2015-1-18
 */
public class FloatArrayViewHandle {
	private final FloatArrayView arrayView;


	public FloatArrayViewHandle() {
		this.arrayView = new FloatArrayView();
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 */
	public FloatArrayViewHandle(float[] objs) {
		this.arrayView = new FloatArrayView(objs, 0, objs.length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 */
	public FloatArrayViewHandle(float[] objs, int offset, int length) {
		this(objs, offset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow this handle's {@link FloatArrayView#set(int, float)} method
	 * to be called, false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public FloatArrayViewHandle(float[] objs, int offset, int length, boolean allowSet) {
		this.arrayView = new FloatArrayView(objs, offset, length, allowSet);
	}


	public FloatArrayView getFloatArrayView() {
		return arrayView;
	}


	public void setFloatArrayView(float[] objs, int offset, int length) {
		arrayView.mod++;
		arrayView.objs = objs;
		arrayView.off = offset;
		arrayView.len = length;
	}

}
