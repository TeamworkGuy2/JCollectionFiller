# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
### [0.7.5](N/A) - 2018-09-23
#### Added
* `BaseList` implementation of `List` and `ListReadOnly`
* `ArrayView` and `PairBag` changed to implement sub-interface `ListReadOnly` instead of `RandomAccessCollection`


--------
### [0.7.4](https://github.com/TeamworkGuy2/JCollectionUtil/commit/775255b652bba3fd36e09a8fc03fd3bafa3edf5e) - 2018-09-22
#### Changed
* Upgrade to Java 10
* Upgrade to JUnit 5
* Updated dependency jcollection-interfaces@0.3.0
  * PairCollectionImmutable -> PairCollectionReadOnly


--------
### [0.7.3](https://github.com/TeamworkGuy2/JCollectionUtil/commit/04d3337137979b0e0d616616b49fda5ebe06e74c) - 2017-09-16
#### Changed
* Updated dependency jcollection-interfaces@0.2.0
  * addAll(Collection) -> addAll(Iterable)
  * removeAll(Collection) -> removeAll(Iterable)
  * putAll(PairCollection) -> putAll(PairCollectionImmutable)


--------
### [0.7.2](https://github.com/TeamworkGuy2/JCollectionUtil/commit/ed2f581256c072d4a3c7c452150c8bea7c80a366) - 2016-10-06
#### Changed
* Updated jarrays dependency and test-checks test library to latest version

#### Fixed
* A null reference bug in the PairList(Map.Entry...) constructor


--------
### [0.7.1](https://github.com/TeamworkGuy2/JCollectionUtil/commit/33d28c05058d968bc59a10ce4a548e1d7d1b8f20) - 2016-08-28
#### Changed
* Renamed remote git repository


--------
### [0.7.0](https://github.com/TeamworkGuy2/JCollectionUtil/commit/0c9f43f0a4722ca6f8776f047e91034a8c54c394) - 2016-08-21
#### Removed
* twg2.collections.builder (moved to new JCollectionBuilders project)
* twg2.collections.tuple (moved to new JTuples project)


--------
### [0.6.3](https://github.com/TeamworkGuy2/JCollectionUtil/commit/2306ca2ae09c3e841874b8ba0085f774d3865549) - 2016-08-18
#### Changed
* Extracted AddedRemoved from ListDiff into its own class
* Updated dependency jsimple-types@0.5.0 (package name change)


--------
### [0.6.2](https://github.com/TeamworkGuy2/JCollectionUtil/commit/9e397f40fbb2934f87a8fd8074c19ec2694daa44) - 2016-08-15
#### Changed
* Forgot to add compiled jar files
* Changed compiled jar file path to /bin/


--------
### [0.6.1](https://github.com/TeamworkGuy2/JCollectionUtil/commit/a37959147a47d3c99069c33d9eec4737843a11bf) - 2016-08-15
#### Added
* ListUtil filter() and map() overloads that accept BaseStream
* ListUtil.filter() Collection overload changed to accept Iterable
* Additional ListUtil test cases

#### Fixed
* one of the filterMap() overloads was incorrect


--------
### [0.6.0](https://github.com/TeamworkGuy2/JCollectionUtil/commit/b0efedf02f142ac40daaa441af0579a1dcbed3c2) - 2016-07-01
#### Changed
Moved byte array/buffer helper classes to new JBuffers library

#### Removed
* ByteBufferArray
* CircularByteArray
* SimpleByteArray


--------
### [0.5.1](https://github.com/TeamworkGuy2/JCollectionUtil/commit/f51a505f2bba6a17e66f4f81d5a94debd3cfb786) - 2016-06-26
#### Added
* Additional SortedList addItem() and calcInsertIndex() methods which require Comparable parameters instead of an explicit Comparator


--------
### [0.5.0](https://github.com/TeamworkGuy2/JCollectionUtil/commit/69d8d3fe6daba71b46828517c335d9a64471b3bd) - 2016-04-12
#### Changed
* Switched versions.md to CHANGELOG.md format, see http://keepachangelog.com/
* Updated to use JCollectionInterfaces library instead of twg2.collections.interfaces package (which now resides in that library)

#### Removed
* Removed/refactored twg2.collections.interfaces into new JCollectionInterfaces library


--------
### [0.4.0](https://github.com/TeamworkGuy2/JCollectionUtil/commit/b152efab85cc3e0f9f4c18e05cd3bcb2a42c4e33) - 2016-04-12
#### Added
* Added ListBuilder, ListUtil, MapBuilder, and MapUtil constructor size hints where possible

#### Removed
* Removed MapBuilder.ofEnumNames() in favor of MapBuilder.immutableEnumNames()


--------
### [0.3.1](https://github.com/TeamworkGuy2/JCollectionUtil/commit/7be55912b02426933da9ae30730ad20b637044c8) - 2016-03-02
#### Added
* Added SplitList
* Added ListUtil.combine() and some additional unit tests

#### Changed
* Moved test classes to separate test directory


--------
### [0.3.0](https://github.com/TeamworkGuy2/JCollectionUtil/commit/f3bee5f147741597afd34841e97c1e8e2e50dc97) - 2016-02-05
#### Added
* Added Tuple* hashCode() and equals() methods

#### Changed
* Renamed ListBuilder and MapBuilder newMutable() and newImmutable() to mutable() and immutable()
* Moved/renamed ListUtil.diff() -> ListDiff.looseDiff(), added ListDiff.diff()


--------
### [0.2.0](https://github.com/TeamworkGuy2/JCollectionUtil/commit/e7c6bc519a003a6916c151c5af9958ab4e65422e) - 2016-01-18
#### Changed
* Moved package twg2.arrays to separate JArrays library


--------
### [0.1.0](https://github.com/TeamworkGuy2/JCollectionUtil/commit/c2016a123882473e37fb71a5adaac080bce140a2) - 2016-01-17
#### Added
* Initial commit, all existing code, including array utils, collection builders, data structures (array views, bags, pair lists/bags, etc.)
* Refactored some package names, twg2.collections.utils -> twg2.collections.builder, twg2.collections.utils.dataStructures -> twg2.collections.dataStructures
