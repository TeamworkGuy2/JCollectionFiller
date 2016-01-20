JCollectionUtil
==============
version: 0.2.0

Data structures which the Java APIs do not have equivalents for, including:
* Array views and two-arrays-as-a-map views
* Bags - unordered lists with O(1) index removal performance
* BiTypeList - a list containing two data element types with strongly typed access to elements in the list
* Pair collections - non-unique maps, with list, sorted list, and bag implementations
* Circular buffers
* FrequencyMap - for tracking and incrementing/decrementing occurences of keys
* PropertyMap - for easily loading/saving Java '.properties' files and automatically parsing boolean, int, float, hexadecimal int, String, Color, File, and Path properties.
* List and Map builders and conditional add()/put() methods
* Tuples - 2, 3, and 4 valued Map.Entry style objects with convenient constructor methods
* Arrays - for everything! Most functions include offset and length arguments to apply the operation to a sub-array:
   * Equality: equals(), indexOf(), lastIndexOf(), contains()
   * Add & Remove: add(), addRange(), remove(), concat()
   * Conversion: asArray(...), reverse(), expandArray(), toString()
   * Stats: avg(), max(), min(), sum()
   * and primitive-wrapper-array-to-primitive-array and vice-versa, i.e. Integer[] -> int[].

Take a look at the twg2.collections.util.tests package for examples of how the APIs can be used.
