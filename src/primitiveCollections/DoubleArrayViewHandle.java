package primitiveCollections;

/** The handle for an {@link DoubleArrayView} which is used to modify the DoubleArrayView's
 * underlying array using {@link #setDoubleArrayView(double[], int, int)}.
 * Use {@link #getDoubleArrayView()} to retrieve the array view.
 * An instance of this class manages exactly one {@link DoubleArrayView}.
 * @author TeamworkGuy2
 * @since 2015-1-18
 */
public class DoubleArrayViewHandle {
	private final DoubleArrayView arrayView;


	public DoubleArrayViewHandle() {
		this.arrayView = new DoubleArrayView();
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 */
	public DoubleArrayViewHandle(double[] objs) {
		this.arrayView = new DoubleArrayView(objs, 0, objs.length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 */
	public DoubleArrayViewHandle(double[] objs, int offset, int length) {
		this(objs, offset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow this handle's {@link DoubleArrayView#set(int, double)} method
	 * to be called, false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public DoubleArrayViewHandle(double[] objs, int offset, int length, boolean allowSet) {
		this.arrayView = new DoubleArrayView(objs, offset, length, allowSet);
	}


	public DoubleArrayView getDoubleArrayView() {
		return arrayView;
	}


	public void setDoubleArrayView(double[] objs, int offset, int length) {
		arrayView.mod++;
		arrayView.objs = objs;
		arrayView.off = offset;
		arrayView.len = length;
	}

}
