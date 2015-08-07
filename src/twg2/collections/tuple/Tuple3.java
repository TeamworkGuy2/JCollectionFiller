package twg2.collections.tuple;

/** Tuple (N-way {@link java.util.Map.Entry} like) data structure.
 * @author TeamworkGuy2
 * @since 2014-8-6
 */
@javax.annotation.Generated("StringTemplate")
public final class Tuple3<A0, A1, A2> {
	// package-private
	final A0 value0;
	final A1 value1;
	final A2 value2;


	public Tuple3(A0 arg0, A1 arg1, A2 arg2) {
		this.value0 = arg0;
		this.value1 = arg1;
		this.value2 = arg2;
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


	public Object getValue(int index) {
		switch(index) {
			case 0: return this.value0;
			case 1: return this.value1;
			case 2: return this.value2;
			default: throw new IndexOutOfBoundsException(index + " of 3 value tuple");
		}
	}


	public int size() {
		return 3;
	}

	/** Convert a set of values to a tuple
	 * @return the newly created tuple
	 */
	public static final <A0, A1, A2> Tuple3<A0, A1, A2> of(A0 arg0, A1 arg1, A2 arg2) {
		return new Tuple3<>(arg0, arg1, arg2);
	}

}
