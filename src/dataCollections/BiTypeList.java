package dataCollections;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author TeamworkGuy2
 * @since 2015-6-27
 * @param <E1> the first of two data types that can be stored in this list
 * @param <E2> the second of two data types that can be stored in this list
 */
public class BiTypeList<E1, E2> {
	private Class<E1> class1;
	private Class<E2> class2;
	private List<Object> objs = new ArrayList<>();


	public BiTypeList(Class<E1> class1, Class<E2> class2) {
		this.class1 = class1;
		this.class2 = class2;
	}


	public BiTypeList<E1, E2> add1(E1 obj) {
		this.objs.add(obj);
		return this;
	}


	public final BiTypeList<E1, E2> addAll1(Iterable<? extends E1> objs) {
		for(E1 e1 : objs) {
			this.objs.add(e1);
		}
		return this;
	}


	@SafeVarargs
	public final BiTypeList<E1, E2> addAll1(E1... objs) {
		for(E1 e1 : objs) {
			this.objs.add(e1);
		}
		return this;
	}


	public boolean isType1(int index) {
		return typeOfIndex(index) == 0;
	}


	public E1 getAs1(int index) {
		Object obj = this.objs.get(index);
		if(typeOfObj(obj, false) == 0) {
			@SuppressWarnings("unchecked")
			E1 res = (E1)obj;
			return res;
		}
		return null;
	}


	public BiTypeList<E1, E2> add2(E2 obj) {
		this.objs.add(obj);
		return this;
	}


	public final BiTypeList<E1, E2> addAll2(Iterable<? extends E2> objs) {
		for(E2 obj : objs) {
			this.objs.add(obj);
		}
		return this;
	}


	@SafeVarargs
	public final BiTypeList<E1, E2> addAll2(E2... objs) {
		for(E2 obj : objs) {
			this.objs.add(obj);
		}
		return this;
	}


	public boolean isType2(int index) {
		return typeOfIndex(index) == 1;
	}


	public E2 getAs2(int index) {
		Object obj = this.objs.get(index);
		if(typeOfObj(obj, false) == 1) {
			@SuppressWarnings("unchecked")
			E2 res = (E2)obj;
			return res;
		}
		return null;
	}


	public Object get(int index) {
		return this.objs.get(index);
	}


	public Object getLast() {
		return this.objs.get(this.objs.size() - 1);
	}


	public int size() {
		return this.objs.size();
	}


	// package-private
	@SafeVarargs
	final BiTypeList<E1, E2> and(Object... objs) {
		for(Object obj : objs) {
			this.objs.add(obj);
		}
		return this;
	}


	// package-private
	List<Object> getRawObjects() {
		return objs;
	}


	// package-private
	/**
	 * @param index the index of the object to check in this list
	 * @return 0 if the object is of type 1, 0 if the object is of type 2, etc...
	 */
	int typeOfIndex(int index) {
		Object obj = this.objs.get(index);
		return typeOfObj(obj, false);
	}


	// package-private
	int typeOfObj(Object obj, boolean throwIfNoMatch) {
		if(class1.isInstance(obj)) {
			return 0;
		}
		else if(class2.isInstance(obj)) {
			return 1;
		}
		else {
			
			throw new IllegalStateException("cannot process two-type list object " + (obj != null ? ("of type " + obj.getClass()) : obj));
		}
	}


	public void forEach(Consumer<E1> elem1Consumer, Consumer<E2> elem2Consumer) {
		for(int i = 0, size = objs.size(); i < size; i++) {
			Object obj = this.objs.get(i);
			int type = typeOfObj(obj, false);

			switch(type) {
			case 0:
				@SuppressWarnings("unchecked")
				E1 elem1 = (E1)obj;
				elem1Consumer.accept(elem1);
				break;
			case 1:
				@SuppressWarnings("unchecked")
				E2 elem2 = (E2)obj;
				elem2Consumer.accept(elem2);
				break;
			}
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((class1 == null) ? 0 : class1.hashCode());
		result = prime * result + ((class2 == null) ? 0 : class2.hashCode());
		result = prime * result + ((objs == null) ? 0 : objs.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof BiTypeList)) { return false; }

		@SuppressWarnings("rawtypes")
		BiTypeList other = (BiTypeList) obj;
		if (class1 == null) {
			if (other.class1 != null) { return false; }
		}
		else if (!class1.equals(other.class1)) {
			return false;
		}
		if (class2 == null) {
			if (other.class2 != null) { return false; }
		}
		else if (!class2.equals(other.class2)) {
			return false;
		}
		if (objs == null) {
			if (other.objs != null) { return false; }
		}
		else if (!objs.equals(other.objs)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return objs.toString();
	}

}
