JCollectionUtility
==============

Data structures which the Java APIs do not have equivalents for, including:
* array views and 2-arrays-as-map views
* bags - unordered lists with O(1) index removal performance
* bi-type list - a list containing two data element types with strongly typed access to elements in the list
* pair collections - non-unique maps, with list, sorted list, and bag implementations
* circular buffers
* List and Map builders and conditional add()/put() methods
* Tuples - 2, 3, and 4 valued Map.Entry style objects with convenient constructor methods
* array utils - for everything! Most functions include offset and length arguments to apply the operation to a sub-array:
   * Equality: equals(), indexOf(), lastIndexOf(), contains()
   * Add & Remove: add(), addRange(), remove(), concat()
   * Conversion: asArray(...), reverse(), expandArray(), toString()
   * Stats: avg(), max(), min(), sum()
   * and primitive-wrapper-array-to-primitive-array and vice-versa, i.e. Integer[] -> int[].

Take a look at the twg2.collections.util.tests package for examples of how the APIs can be used.
