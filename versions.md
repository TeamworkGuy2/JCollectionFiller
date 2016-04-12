--------
####0.4.0
date: 2016-4-12

commit: ?

* Removed MapBuilder.ofEnumNames() in favor of MapBuilder.immutableEnumNames()
* Added ListBuilder, ListUtil, MapBuilder, and MapUtil constructor size hints where possible


--------
####0.3.1
date: 2016-3-2

commit: 7be55912b02426933da9ae30730ad20b637044c8

* Added SplitList
* Added ListUtil.combine() and some additional unit tests
* Moved test classes to separate test directory


--------
####0.3.0
date: 2016-2-5

commit: f3bee5f147741597afd34841e97c1e8e2e50dc97

* Added Tuple* hashCode() and equals() methods
* Renamed ListBuilder and MapBuilder newMutable() and newImmutable() to mutable() and immutable()
* Moved/renamed ListUtil.diff() -> ListDiff.looseDiff(), added ListDiff.diff()


--------
####0.2.0
date: 2016-1-18

commit: e7c6bc519a003a6916c151c5af9958ab4e65422e

* Moved package twg2.arrays to separate JArrays library


--------
####0.1.0
date: 2016-1-17

commit: c2016a123882473e37fb71a5adaac080bce140a2

* Initial version tracking, all existing code, including array utils, collection builders, data structures (array views, bags, pair lists/bags, etc.)
* Refactored some package names, twg2.collections.utils -> twg2.collections.builder, twg2.collections.utils.dataStructures -> twg2.collections.dataStructures
