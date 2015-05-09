package primitiveCollections;

/** The handle for an {@link IntArrayView} which is used to modify the IntArrayView's
 * underlying array using {@link #setIntArrayView(int[], int, int)}.
 * Use {@link #getIntArrayView()} to retrieve the array view.
 * An instance of this class manages exactly one {@link IntArrayView}.
 * @author TeamworkGuy2
 * @since 2015-1-18
 */
public class IntArrayViewHandle {
	private final IntArrayView arrayView;


	public IntArrayViewHandle() {
		this.arrayView = new IntArrayView();
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 */
	public IntArrayViewHandle(int[] objs) {
		this.arrayView = new IntArrayView(objs, 0, objs.length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 */
	public IntArrayViewHandle(int[] objs, int offset, int length) {
		this(objs, offset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow this handle's {@link IntArrayView#set(int, int)} method
	 * to be called, false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public IntArrayViewHandle(int[] objs, int offset, int length, boolean allowSet) {
		this.arrayView = new IntArrayView(objs, offset, length, allowSet);
	}


	public IntArrayView getIntArrayView() {
		return arrayView;
	}


	public void setIntArrayView(int[] objs, int offset, int length) {
		arrayView.mod++;
		arrayView.objs = objs;
		arrayView.off = offset;
		arrayView.len = length;
	}

}
