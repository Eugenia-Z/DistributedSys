17 Fri 6pm

# 1. .join() on threads

- thread.join() is used to pause the exectuion of the current thread until the thread on which join() is called has finished executing.
- the order of thread execution is determined by the JVM's thread scheduler. (the order is independend of the order in which .join() is called)

# 2. Lambda expression to create a new thread

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

# 3. The Dining Philosophers Problems

goal:

- no deadlock: every philosopher waits indefinately
  - 如果每个哲学家同时拿起自己左边的筷子，然后等待右边的筷子，所有哲学家都会陷入死锁。例如，哲学家 0 拿起筷子 0，哲学家 1 拿起筷子 1……哲学家 4 拿起筷子 4，最后没有人能拿到第二根筷子。
- no starvation: every philosopher has chance to eat

Solution:

1. 资源分配策略：根据哲学家 id 的奇偶性，决定谁拿筷子
2. use lock，non-blocking。
3. use semaphore

# 4. Reentrant Lock 可重入锁

- 可以被同一线程多次获取的锁，解决多线程并发场景中的同步问题。

- 两种方式均支持可重入：

  - Synchronized(implicit)： 隐式可释放；在方法或者 code block 结束时自动释放
  - ReentrantLock(explicit) ： 锁的释放需要在 finally 块中显式调用 unlock，以确保锁总是被释放

- 如果一个线程已经获取了锁，可以在不释放锁的情况下，再次获取
- 避免线程在调用递归方法或者跨层次调用时的死锁问题
- 每次获取锁后，锁的计数器会增加；释放锁时，计数器或减少。直到 counter == 0， 锁才真正被释放
- lock.lock() -> counter ++
- lock.unlock -> counter--
