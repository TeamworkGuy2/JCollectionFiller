JCollectionUtility
==============

Data structures which the Java APIs do not have equivalents for, including:
* array views and 2-arrays-as-map views
* bags - unordered lists with O(1) index removal performance
* pair collections - non-unique maps, with list, sorted list, and bag implementations
* circular buffers
* List and Map builders and conditional add()/put() methods
* Tuples - 2, 3, and 4 valued Map.Entry style objects with convenient constructor methods
* array utils - for everything! sub-array: equals(), indexOf(), lastIndexOf(), toString() and: asArray(...), concat(), avg(), max(), min(), sum(), reverse(), and primitive-wrapper-array-to-primitive-array and vice-versa, i.e. Integer[] -> int[].

Take a look at the twg2.collections.util.tests package for examples of how the APIs can be used.
