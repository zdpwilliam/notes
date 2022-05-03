package com.william.example.volatilemistakes;

/*
Volatile关键字规则

volatile字段被用来在线程之间communicate state（交流规则）。任意线程所read的volatile字段的值都是最新的。原因有以下有4点：
(1) 编译器和JVM会阻止将volatile字段的值放入处理器寄存器（register）；
(2) 在write volatile字段之后，其值会被flush出处理器cache，写回memory；
(3) 在read volatile字段之前，会invalidate（验证）处理器cache。
因此，上述两条便保证了每次read的值都是从memory中的，即具有“可见性”这一特性。
(4) 禁止reorder（重排序，即与原程序指定的顺序不一致）任意两个volatile变量，并且同时严格限制（尽管没有禁止）reorder
volatile变量周围的非volatile变量。这一点即volatile具有变量的“顺序性”，即指令不会重新排序，而是按照程序指定的顺序执行。
    注：在旧的内存模型下，对volatile修改的变量的访问顺序不能进行重新排序，但可以对非volatile变量进行排序，但这样又可能还是会导致
volatile变量可见性问题，所以老的旧内存模型没有从根本上解决volatile 变量的可见性问题。在新的内存模型下，仍然是不允许对volatile变
量进行reorder的，不同的是再也不轻易（虽然没有完全禁止掉）允许对它周围的非volatile变量进行排序。
 */