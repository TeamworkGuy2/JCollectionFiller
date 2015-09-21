package twg2.collections.tuple;

/** Tuple (N-way {@link java.util.Map.Entry} like) data structure.
 * @author TeamworkGuy2
 * @since 2014-8-6
 */
@javax.annotation.Generated("StringTemplate")
public final class Tuple2<A0, A1> implements java.util.Map.Entry<A0, A1> {
	// package-private
	final A0 value0;
	final A1 value1;


	public Tuple2(A0 arg0, A1 arg1) {
		this.value0 = arg0;
		this.value1 = arg1;
	}


	public A0 getValue0() {
		return this.value0;
	}


	public A1 getValue1() {
		return this.value1;
	}


	public Object getValue(int index) {
		switch(index) {
			case 0: return this.value0;
			case 1: return this.value1;
			default: throw new IndexOutOfBoundsException(index + " of 2 value tuple");
		}
	}


	public int size() {
		return 2;
	}


	@Override
	public A0 getKey() {
		return this.value0;
	}


	@Override
	public A1 getValue() {
		return this.value1;
	}


	@Override
	public A1 setValue(A1 value) {
		throw new IllegalStateException("cannot modify immutable tuple");
	}

	@Override
	public String toString() {
		return this.value0 + "=" + this.value1;
	}

	/** Convert a set of values to a tuple
	 * @return the newly created tuple
	 */
	public static final <A0, A1> Tuple2<A0, A1> of(A0 arg0, A1 arg1) {
		return new Tuple2<>(arg0, arg1);
	}

}
