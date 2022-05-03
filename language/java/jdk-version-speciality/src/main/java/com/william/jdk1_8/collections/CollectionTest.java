package com.william.jdk1_8.collections;

import java.util.*;
import java.util.concurrent.*;

public class CollectionTest {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();

        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();
        List<String> vector = new Vector<>();
        List<String> cponwright = new CopyOnWriteArrayList<>();


        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> hashTable = new Hashtable<>();
        Map<String, String> concurrentMap = new ConcurrentHashMap<>();

        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        Map<String, String> treeMap = new TreeMap<>();
        Map<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        ThreadLocal<String> threadLocalMap = new ThreadLocal<>();


        Set<String> hashSet = new HashSet<>();
        Set<String> linkedHashSet = new LinkedHashSet<>();
        Set<String> treeSet = new TreeSet<>();
        Set<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
        Set<String> concurrentSkipListSet = new ConcurrentSkipListSet<>();

        Queue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        Queue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
        Queue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        Queue<String> synchronousQueue = new SynchronousQueue<>();
        Queue<String> priorityBlockingQueue = new PriorityBlockingQueue<>(10);

        Queue delayQueue = new DelayQueue<>();

        Deque<String> arrayDeque = new ArrayDeque<>();
        Deque<String> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
        Deque<String> linkedBlockingDeque = new LinkedBlockingDeque<>();
    }
}
