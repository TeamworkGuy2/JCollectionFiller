package twg2.collections.tuple;

/** Tuple (N-way {@link java.util.Map.Entry} like) data structure.
 * @author TeamworkGuy2
 * @since 2014-8-6
 */
@javax.annotation.Generated("StringTemplate")
public final class Tuple4<A0, A1, A2, A3> {
	// package-private
	final A0 value0;
	final A1 value1;
	final A2 value2;
	final A3 value3;


	public Tuple4(A0 arg0, A1 arg1, A2 arg2, A3 arg3) {
		this.value0 = arg0;
		this.value1 = arg1;
		this.value2 = arg2;
		this.value3 = arg3;
	}


	public A0 getValue0() {
		return this.value0;
	}


	public A1 getValue1() {
		return this.value1;
	}


	public A2 getValue2() {
		return this.value2;
	}


	public A3 getValue3() {
		return this.value3;
	}


	public Object getValue(int index) {
		switch(index) {
			case 0: return this.value0;
			case 1: return this.value1;
			case 2: return this.value2;
			case 3: return this.value3;
			default: throw new IndexOutOfBoundsException(index + " of 4 value tuple");
		}
	}


	public int size() {
		return 4;
	}

	/** Convert a set of values to a tuple
	 * @return the newly created tuple
	 */
	public static final <A0, A1, A2, A3> Tuple4<A0, A1, A2, A3> of(A0 arg0, A1 arg1, A2 arg2, A3 arg3) {
		return new Tuple4<>(arg0, arg1, arg2, arg3);
	}

}
