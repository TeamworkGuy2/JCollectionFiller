# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
###[0.5.1](N/A) - 2016-06-25
####Added
* Additional SortedList addItem() and calcInsertIndex() methods which require Comparable parameters instead of an explicit Comparator


--------
###[0.5.0](https://github.com/TeamworkGuy2/JCollectionFiller/commit/69d8d3fe6daba71b46828517c335d9a64471b3bd) - 2016-04-12
####Changed
* Switched versions.md to CHANGELOG.md format, see http://keepachangelog.com/
* Updated to use JCollectionInterfaces library instead of twg2.collections.interfaces package (which now resides in that library)

####Removed
* Removed/refactored twg2.collections.interfaces into new JCollectionInterfaces library


--------
###[0.4.0](https://github.com/TeamworkGuy2/JCollectionFiller/commit/b152efab85cc3e0f9f4c18e05cd3bcb2a42c4e33) - 2016-04-12
####Added
* Added ListBuilder, ListUtil, MapBuilder, and MapUtil constructor size hints where possible

####Removed
* Removed MapBuilder.ofEnumNames() in favor of MapBuilder.immutableEnumNames()


--------
###[0.3.1](https://github.com/TeamworkGuy2/JCollectionFiller/commit/7be55912b02426933da9ae30730ad20b637044c8) - 2016-03-02
####Added
* Added SplitList
* Added ListUtil.combine() and some additional unit tests

####Changed
* Moved test classes to separate test directory


--------
###[0.3.0](https://github.com/TeamworkGuy2/JCollectionFiller/commit/f3bee5f147741597afd34841e97c1e8e2e50dc97) - 2016-02-05
####Added
* Added Tuple* hashCode() and equals() methods

####Changed
* Renamed ListBuilder and MapBuilder newMutable() and newImmutable() to mutable() and immutable()
* Moved/renamed ListUtil.diff() -> ListDiff.looseDiff(), added ListDiff.diff()


--------
###[0.2.0](https://github.com/TeamworkGuy2/JCollectionFiller/commit/e7c6bc519a003a6916c151c5af9958ab4e65422e) - 2016-01-18
####Changed
* Moved package twg2.arrays to separate JArrays library


--------
###[0.1.0](https://github.com/TeamworkGuy2/JCollectionFiller/commit/c2016a123882473e37fb71a5adaac080bce140a2) - 2016-01-17
####Added
* Initial version tracking, all existing code, including array utils, collection builders, data structures (array views, bags, pair lists/bags, etc.)
* Refactored some package names, twg2.collections.utils -> twg2.collections.builder, twg2.collections.utils.dataStructures -> twg2.collections.dataStructures
