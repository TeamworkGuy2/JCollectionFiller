JCollectionUtil
==============
version: 0.7.1

Data structures which the Java APIs do not have equivalents for, including:
* Array views and two-arrays-as-a-map views, see: ArrayMapView, ArrayMapViewHandle, ArrayView, ArrayViewHandle
* Bags - unordered lists with O(1) index removal performance, see: Bag, MultiBag
* BiTypeList - a list containing two data element types with strongly typed access to elements in the list
* Pair collections - non-unique maps, with list, sorted list, and bag implementations, see: PairBag, PairList, SortedPairList
* Circular buffers, see CircularArray, CircularByteArray
* ByteBufferArray - Combines the ability to resize a ByteArrayOutputStream with the indexed position access of a {@link ByteBuffer} and the read/write methods of DataOutput
* SimpleByteBuffer - A container for a data array, offset, and length, with helpers for adjusting the offset and length, you have to manually handle the data.
* FrequencyMap - for tracking and incrementing/decrementing occurences of keys
* PropertyMap - for easily loading/saving Java '.properties' files and automatically parsing boolean, int, float, hexadecimal int, String, Color, File, and Path properties.

Take a look at the twg2.collections.util.tests package for examples of how the APIs can be used.
