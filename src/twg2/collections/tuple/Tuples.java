package twg2.collections.tuple;

/** Tuples utility methods.
 * @author TeamworkGuy2
 * @since 2014-8-6
 */
@javax.annotation.Generated("StringTemplate")
public final class Tuples {

	private Tuples() { throw new AssertionError("cannot instantiate static class Tuples"); }


	/** Convert a set of values to a tuple
	 * @return the newly created tuple
	 */
	public static final <A0, A1> Tuple2<A0, A1> of(A0 arg0, A1 arg1) {
		return new Tuple2<>(arg0, arg1);
	}


	/** Convert a set of values to a tuple
	 * @return the newly created tuple
	 */
	public static final <A0, A1, A2> Tuple3<A0, A1, A2> of(A0 arg0, A1 arg1, A2 arg2) {
		return new Tuple3<>(arg0, arg1, arg2);
	}


	/** Convert a set of values to a tuple
	 * @return the newly created tuple
	 */
	public static final <A0, A1, A2, A3> Tuple4<A0, A1, A2, A3> of(A0 arg0, A1 arg1, A2 arg2, A3 arg3) {
		return new Tuple4<>(arg0, arg1, arg2, arg3);
	}


}
