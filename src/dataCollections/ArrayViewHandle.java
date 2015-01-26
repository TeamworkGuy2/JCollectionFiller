package dataCollections;

/** The handle for an {@link ArrayView} which is used to modify the ArrayView's
 * underlying array using {@link #setArrayView(Object[], int, int)}.
 * Use {@link #getArrayView()} to retrieve the array view.
 * An instance of this class manages exactly one {@link ArrayView}.
 * @param <E> type of values in this array view
 * @author TeamworkGuy2
 * @since 2014-12-13
 */
public class ArrayViewHandle<E> {
	private final ArrayView<E> arrayView;


	public ArrayViewHandle() {
		this.arrayView = new ArrayView<>();
	}


	/** Create an array view of an entire array
	 * @param objs the array to create a view of
	 */
	public ArrayViewHandle(E[] objs) {
		this.arrayView = new ArrayView<>(objs, 0, objs.length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 */
	public ArrayViewHandle(E[] objs, int offset, int length) {
		this(objs, offset, length, false);
	}


	/** Create an array view of a sub-portion of an array
	 * @param objs the array to create a view of
	 * @param offset the offset into {@code objs} of the array view's {@code 0th} index
	 * @param length the number of values starting at {@code offset} to include in this view
	 * @param allowSet true to allow this handle's {@link ArrayView#set(int, Object)} method
	 * to be called, false to throw an {@link UnsupportedOperationException} when {@code set} is called
	 */
	public ArrayViewHandle(E[] objs, int offset, int length, boolean allowSet) {
		this.arrayView = new ArrayView<>(objs, offset, length, allowSet);
	}


	public ArrayView<E> getArrayView() {
		return arrayView;
	}


	public void setArrayView(E[] objs, int offset, int length) {
		arrayView.mod++;
		arrayView.objs = objs;
		arrayView.off = offset;
		arrayView.len = length;
	}

}
