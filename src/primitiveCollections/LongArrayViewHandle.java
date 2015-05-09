package primitiveCollections;

/** The handle for an {@link LongArrayView} which is used to modify the LongArrayView's
 * underlying array using {@link #setLongArrayView(long[], int, int)}.
 * Use {@link #getLongArrayView()} to retrieve the array view.
 * An instance of this class manages exactly one {@link LongArrayView}.
 * @author TeamworkGuy2
 * @since 2015-1-18
 */
public class LongArrayViewHandle {
	private final LongArrayView arrayView;


	public LongArrayViewHandle() {
		this.arrayView = new LongArrayView();
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 */
	public LongArrayViewHandle(long[] objs) {
		this.arrayView = new LongArrayView(objs, 0, objs.length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 */
	public LongArrayViewHandle(long[] objs, int offset, int length) {
		this(objs, offset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow this handle's {@link LongArrayView#set(int, long)} method
	 * to be called, false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public LongArrayViewHandle(long[] objs, int offset, int length, boolean allowSet) {
		this.arrayView = new LongArrayView(objs, offset, length, allowSet);
	}


	public LongArrayView getLongArrayView() {
		return arrayView;
	}


	public void setLongArrayView(long[] objs, int offset, int length) {
		arrayView.mod++;
		arrayView.objs = objs;
		arrayView.off = offset;
		arrayView.len = length;
	}

}
