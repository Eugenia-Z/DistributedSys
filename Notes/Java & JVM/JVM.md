# HeapDump

Java 进程所使用的内存状况的快照，用于分析对象、类信息以及立即回收等情况。以文件的形式持久化在磁盘中。

获取 heapDump 文件：

1. jmap 命令
2. jcmd mingling
3. jvm 参数设置

# Topics： JVM 内存管理

JVM 主要将内存划分为以下几个区域：

1. 方法区（Method Area）
   - 也称 永久代（Permanent Generation）（JDK 8 之前），元空间（Metaspace）（JDK 8 及以后）。
   - 存储 类的元数据（Class Metadata）、运行时常量池（Runtime Constant Pool）、静态变量等。
   - 在 JDK 8 之后，方法区不再使用堆，而是由 本地内存（Native Memory） 管理。
2. 堆（Heap）
   - Java 对象存储的主要区域，所有对象都在堆中分配。
   - GC（垃圾回收器）主要管理的区域。
   - 进一步划分为：
     - 新生代（Young Generation）
       - Eden 区：新创建的对象首先分配到 Eden 区。
       - Survivor 区（S0, S1）：Eden 满了后，存活的对象会转移到 Survivor 区。
     - 老年代（Old Generation）
       - 长期存活的对象会晋升到老年代。
       - 主要存储生命周期较长的大对象。
3. 栈（Stack）
   - 每个线程创建时都会分配一个 栈，用于存储方法的 局部变量、方法调用信息 等。
   - 主要包含：
     - 局部变量表（Local Variable Table）：存储方法中的局部变量（基本类型、对象引用）。
     - 操作数栈（Operand Stack）：存储计算过程中的中间结果。
     - 方法返回地址（Return Address）：记录方法调用的返回地址。
4. 本地方法栈（Native Method Stack）
   - 用于执行 JNI（Java Native Interface）本地方法（调用 C/C++ 代码）。
   - 作用类似于 Java 栈，但专门用于 Native 代码。
5. 程序计数器（PC Register）
   - 记录当前线程执行的 JVM 指令地址，用于线程切换时恢复执行状态。
   - 每个线程都有一个独立的 PC 计数器。

# JVM 内存分配与垃圾回收（GC）

1. 对象的分配
   - 栈上分配（逃逸分析）：如果对象不会逃出方法作用域，JVM 可以优化，将其分配在栈上，而不是堆。
   - TLAB（Thread Local Allocation Buffer）：JVM 为每个线程分配一个小的内存区域，减少多线程争用堆空间的锁开销。
2. 垃圾回收机制
   - 新生代 GC（Minor GC）：
     - 发生在 Eden 区满时，存活对象进入 Survivor 区。
     - 多次 GC 后仍存活的对象会晋升到 老年代。
   - 老年代 GC（Major GC / Full GC）：
     - 发生在 老年代空间不足时，通常会导致 STW（Stop The World）。
     - 回收整个堆，执行成本高。
   - 垃圾回收算法：
     - 引用计数（Reference Counting）：简单但不能处理循环引用，JVM 不使用。
     - 可达性分析（Reachability Analysis）：GC Root 可达的对象是存活对象，否则会被回收。
   - GC 机制：
     - 标记-清除（Mark-Sweep）：标记存活对象，清除不可达对象，容易产生内存碎片。
     - 标记-整理（Mark-Compact）：类似标记-清除，但整理存活对象，减少碎片化。
     - 复制（Copying）：新生代采用复制算法（Eden -> Survivor），减少碎片化。

# JVM 调优相关参数

参数 作用
-Xms<size> 设置堆的初始大小
-Xmx<size> 设置堆的最大大小
-XX:NewRatio=<n> 新生代与老年代的比例
-XX:SurvivorRatio=<n> 设置 Survivor 与 Eden 区的比例
-XX:+UseG1GC 启用 G1 GC
-XX:+UseZGC 启用 ZGC
