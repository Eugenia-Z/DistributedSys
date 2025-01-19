17 Fri 6pm & 18 Sat 6pm

# thread.start()

- A new thread is created and scheduled by JVM
- The start() method internally calls the run() method of the thread object. However, it does so in a new thread created by the JVM, not in the calling thread.

- When start() is called, the thread transitions from the NEW state to the RUNNABLE state:
  NEW: A thread is created but not started.
  RUNNABLE: The thread is ready to run and waiting for CPU time to execute.

- The actual execution of the thread depends on the thread scheduler, which is platform-dependent. The scheduler determines when the thread is allocated CPU time.
- The run() method executes the thread’s code. Once the run() method completes, the thread transitions to the TERMINATED state.

# thread.join()

- thread.join() is used to pause the exectuion of the current thread until the thread on which join() is called has finished executing.
- the order of thread execution is determined by the JVM's thread scheduler. (the order is independend of the order in which .join() is called)

# Thread Scheduler:

The JVM's thread scheduler determines the order and duration of thread execution, which can vary across platforms.
Threads may not execute in the order they are started, as scheduling depends on factors like thread priority and the underlying operating system.

# Lambda expression to create a new thread

```java
for (int i = 0; i < NUMTHREADS; i++) {
    Runnable thread = () -> { counter.inc(); };
    new Thread(thread).start();
}
```

- implements the Runnable interface, which has a single abstract method, run(). Lambda provides an implementation for it.
- when run() of the thread is executed, counter.inc() will be called
- new Thread(thread) creates a new thread with the given Runnable instance.
- start() begins the execution of the thread, invoking the run() method defined by the lambda.

equivalent to :

```java
Runnable thread = new Runnable(){
    @Override
    public void run(){
        counter.inc()
    }
}
```

# Thread states:

1. New: created but not started (ready-to-run state)
2. Runnable: started and either running or waiting to run
3. Blocked
4. Terminate

Not runnable when:

- sleep() -> the sleep period must slapse or interrupt() method called
- suspend() -> resume() must be called
- wait() -> the obect owning that variable must relinquish bit by calling notify() or notifyAll()
- waiting for I/O -> I/O must complete

# notify() and notifyAll()

- from Object class, used for interthread-communication in a synchronized context.
- used to wake up one or more threads on an object's monitor(lock).

- notify()
  Wakes up one single thread that is waiting on the object's monitor.
  The thread that is awakened is chosen arbitrarily by the JVM.
  If no threads are waiting, notify() has no effect.

- notifyAll()
  Wakes up all threads that are waiting on the object's monitor.
  All awakened threads will compete for the lock. Only one will acquire it and continue execution; others will keep waiting for the lock to be released.

# Locks in Java:

1. Synchronized (Instrinsic Locks)

- every object in Java has an instrinsic lock (aka a monitor lock)
- a thread must acquire the lock before executing a synchronized block oe method
- key Characteristics:
  - Reentrant: A thread can acquire the same lock multiple times without blocking itself.
  - Blocking: Threads attempting to acquire the lock will block until the lock is released.

2. Explicit Locks(java.util.concurrent.locks.Lock)

- ReentrantLock
- ReentrantReadWriteLock

advantages over synchronized:

- Non-blocking lock attemps (tryLock)
- Supports interruptible lock acquisition
- Fairness policy for lock acuiqistion

# Reentrant Lock 可重入锁

- 可以被同一线程多次获取的锁，解决多线程并发场景中的同步问题。

- 两种方式均支持可重入：

  - Synchronized(implicit)： 隐式可释放；在方法或者 code block 结束时自动释放
  - ReentrantLock(explicit) ： 锁的释放需要在 finally 块中显式调用 unlock，以确保锁总是被释放

- 如果一个线程已经获取了锁，可以在不释放锁的情况下，再次获取; if a thread tries to acquire a lock it already holds, it succeeds
- 避免线程在调用递归方法或者跨层次调用时的死锁问题
- 每次获取锁后，锁的计数器会增加；释放锁时，计数器或减少。直到 counter == 0， 锁才真正被释放; Each lock has an acquisition count and owning thread. Count can only bei ncremented above 1 by same owning thread.

- lock.lock() -> counter ++
- lock.unlock -> counter --

# Read/Write Locks (ReentrantReadWriteLock ) 读写锁

- Allows multiple threads to read a resource simultaneously, but only one thread to write
- Helps improve performance when reads are more frequent than writes.

# Barrier Synchronization

In Java, barrier synchronization ensures that multiple threads wait at a common point before proceeding, achieving coordinated execution.

A CyclicBarrier is a synchronization aid that allows a group of threads to wait at a barrier point until all threads in the group reach it. Once all threads have arrived at the barrier, the barrier is broken, and all waiting threads are released to continue their execution.

Cyclic Nature:

The barrier can be reused after all threads have been released, hence the term "cyclic."

Key Methods

- await(): Causes the calling thread to wait until all threads have reached the barrier. Throws BrokenBarrierException if the barrier is broken (e.g., one thread times out).
- reset(): Resets the barrier to its initial state.
- getParties(): Returns the number of threads required to trip the barrier.
- isBroken():Checks if the barrier is in a broken state.

```java
CyclicBarrier(int parties)
CyclicBarrier(int parties, Runnable barrierAction)
```

# Countdown Latch

The CountDownLatch class is a synchronization tool in Java that allows one or more threads to wait for other threads to finish their execution before proceeding. It maintains a count that is decremented each time a thread calls countDown(). When the count reaches zero, all waiting threads are released and can continue their execution.

Key Methods:

- await():
  Causes the current thread to wait until the latch's count reaches zero.
- countDown():
  Decrements the latch's count. Once the count reaches zero, all waiting threads are released.
- getCount():
  Returns the current count of the latch.

# CyclicBarrier vs. CountDownLatch

- Reusability:
  Reusable after all threads pass. vs. Single-use only.
- Trigger Condition:
  All threads call await(). vs. Count reaches zero.
- Barrier Action:
  Can execute a barrier action. vs. No action; only threads proceed.
- Use Case:
  Thread coordination in phases. vs. One-time events or dependencies.

# Java Guards

- A guard is essentially a conditional check that ensures a thread does not proceed until a required condition is satisfied. Guards are usually used with synchronization tools such as synchronized, wait(), notify()

- Waiting for a condition to become true before proceeding
- Blokcing threads until a certian state is achieved

example:

- A producer-consumer system where the consumer must wait until the buffer has data to consume.

# Thread Pool

- In Java, a thread pool is a group of pre-instantiated threads that are ready to execute tasks.
- Thread pools manage the lifecycle of threads, improving performance and resource management in applications where multiple tasks need to run concurrently.
- Instead of creating a new thread for each task, a thread pool reuses existing threads, reducing the overhead associated with thread creation and destruction.

# Executor Framework:

Java provides the Executor framework in the java.util.concurrent package to manage thread pools. The main interface is Executor, with ExecutorService and ScheduledExecutorService providing extended functionality.

- The Executors utility class provides factory methods to create different types of thread pools.
- Decouples the submission of a request from the execution policy used.

```java
ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);

```

Execution service;

- a sub interface of Executor that accepts Runnables and Callables.
- From Runnable return: Future
- From Callable return: value

Executor Service shutdown()

- Must shutdown an executor
  - executorService.shutdown();
- Stops accepting new requests but does not shutdown immediately
  - Completes all tasks in queue.
- This method will return when all the threads have finished execution
  - executorService.awaitTermination();

# The Dining Philosophers Problems

goal:

- no deadlock: every philosopher waits indefinately
  - 如果每个哲学家同时拿起自己左边的筷子，然后等待右边的筷子，所有哲学家都会陷入死锁。例如，哲学家 0 拿起筷子 0，哲学家 1 拿起筷子 1……哲学家 4 拿起筷子 4，最后没有人能拿到第二根筷子。
- no starvation: every philosopher has chance to eat

Solution:

1. 资源分配策略：根据哲学家 id 的奇偶性，决定谁拿筷子
2. use lock，non-blocking。
3. use semaphore
