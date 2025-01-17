# 10 Jan week 1

1. When design API: latency constrains -> as a type of SLA.
2. API Gateway SLA

examples of scalable system

1. log analyzer (Facebook Scribe)

network bandwidth is one factor to consider for tradeoffs.

Pro tips:
inquisitive，start everything early.
Always try to optimize for performance.

# 12 Jan notes on multithreading in Java:

1. two main ways to create a thread
   1.1 Extending `Thread` class
   1.2 Implementing `Runnable` interface

   Why prefer `Runnable` over extending `Thread`?

   - Separation of Task from Thread Control:
     - Using Runnable, the task (what the thread should do) is decoupled from thread management (creating/starting threads).
     - Promotes better design, especially when a class needs to extend another class but still wants to define a task to run in a thread.

2. Static modifier in Java
   - Define class-level member.
   - Meaning that member(field, method, block) belongs to the class itself rather than to instances of the class
   - Shared among all instances of the class
   - Can be accessed without creating an instance of the class.
   - Initialized only once, at class loading time.
