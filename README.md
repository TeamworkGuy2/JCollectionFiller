JCollectionUtil
==============

Data structures missing from the Java APIs.
Includes:
* Array views - wrappers which expose an array (or pair of arrays in the case of `ArrayMapView`) as a `List` implementation, see:
  * `ArrayMapView` and constructor class `ArrayMapViewHandle`
  * `ArrayView` and constructor class `ArrayViewHandle`
* Bag - unordered list with O(1) index removal performance, see:
  * `Bag`
  * `MultiBag` - treat a single internal array as multiple sub-lists
* `BiTypeList` - a list containing two data element types with strongly typed access to elements in the list
* Pair collections - non-unique maps, with list, sorted list, and bag implementations, see:
  * `PairBag`
  * `PairList`
  * `SortedPairList`
* Circular buffers - see:
  * `CircularArray`
  * `CircularByteArray`
* `ByteBufferArray` - Combines the ability to resize a `ByteArrayOutputStream` with the indexed position access of a `ByteBuffer` and the read/write methods of a `DataOutput` stream
* `SimpleByteBuffer` - A container for a data array, offset, and length, with methods for adjusting the offset and length. The call must manually handle the data.
* `FrequencyMap` - for tracking and incrementing/decrementing occurrences of distinct key values
* `PropertyMap` - for easily loading and saving Java '.properties' files and automatically parsing boolean, int, float, hexadecimal int, String, Color, File, and Path properties.

Take a look at the unit tests in `twg2.collections.util.tests` package for examples of how the APIs can be used.
