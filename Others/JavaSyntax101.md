1. Set: Interface. (Java's Collections Framework. ) - being an interface, cannot be instantiated directly. need a class that implements it. - Common methods: add(E e), remove(Object o), contains(Object o), size(), iterator()

Impls (Concreate classes):

- HashSet

  1. use hashtables, add, remove, contains all O(1)
  2. no duplicates, UNORDERED, allows null values
  3. syntex: Set<String> = new HashSet<>()

- TreeSet

  1. use Red-Black Tree
  2. maintains SORTED order
  3. add/remove/contains - O(logn)

- LinkedHashSet
  1. hashTable w linked list -> maintain Insertion order
     1.1 add/remove/contains O(1), iteration O(n)
  2. Slighly slower than HashSet but maintains order

======== String.format() ========
String.format("formatString", arguments);

- "formatString" 包含占位符 %d, %s 等的模版字母串，占位符会被后续的参数替代
- arguments 时将要插入到模版中的数据，顺序和占位符的位置要一一对应
