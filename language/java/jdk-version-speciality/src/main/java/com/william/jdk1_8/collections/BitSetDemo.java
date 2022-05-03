package com.william.jdk1_8.collections;

import java.util.BitSet;

public class BitSetDemo {

    public static void main(String[] args) {
        int[] source = new int[] {3, 5, 6, 9, 1000};
        BitSet bitSet = new BitSet();
        for (int i = 0; i < source.length; i++) {
            bitSet.set(source[i]);
        }
        System.out.println(bitSet);
        //set中不存在2 --> false
        System.out.println(bitSet.get(2));
        //set中存在9   --> true
        System.out.println(bitSet.get(9));
        System.out.println(bitSet.size());
        System.out.println(bitSet.length());

        // BitSet介绍  http://shift-alt-ctrl.iteye.com/blog/2194519
        /*
         *      JAVA中，一个long型数字占用64位空间，根据上述“位图”的概念，那么一个long型数字（4个字节）就可以保存64个数字的“存在性”
         *  状态(无碰撞冲突时，即true、false状态)。比如50个数字{0,1,10,...63}，判定“15”是否存在，那么我们通常会首先将这些数字使用
         *  数组或者hashmap保存，然后再去判定，那么保存这些这些数据需要占用64 * 64位；如果使用位图，那么一个long型数字即可。
         *
         *      1) BitSet只面向数字比较，比如set(int a,boolean value)方法，将数字a在bitSet中设定为true或者false；此后可以通过
         *  get(int a)方法检测结果。对于string类型的数据，如果像使用BitSet，那么可以将其hashcode值映射在bitset中。
         *      2) 首先我们需要知道：1，1<<64，1<<128，1<<192...等，这些数字的计算结果是相等的（位运算）；这也是一个long数字，
         *  只能表达连续的(或者无冲突的)64个数字的状态，即如果把数字1在long中用位表示，那么数字64将无法通过同一个long数字中位表示--冲突；
         *  BitSet内部，是一个long[]数组，数组的大小由bitSet接收的最大数字决定，这个数组将数字分段表示[0,63],[64,127],[128,191]...。
         *  即long[0]用来存储[0,63]这个范围的数字的“存在性”，long[1]用来存储[64,127]，依次轮推，这样就避免了位运算导致的冲突。
         *      3) bitSet内部的long[]数组是基于向量的，即随着set的最大数字而动态扩展。
         *      数组的最大长度计算：(maxValue - 1) >> 6  + 1
         *
         *      4) BitSet与Hashcode冲突
         *      因为BitSet API只能接收int型的数字，即只能判定int数字是否在bitSet中存在。所以，对于String类型，我们通常使用它的hashcode，
         *  但这有一种隐患，java中hashcode存在冲突问题，即不同的String可能得到的hashcode是一样的（即使不重写hashcode方法），如果
         *  我们不能很好的解决这个问题，那么就会出现“数据抖动”---不同的hashcode算法、运行环境、bitSet容量，会导致判断的结果有所不同。
         *
         *      比如A、B连个字符串，它们的hashcode一样，如果A在BitSet中“着色”(值为true)，那么检测B是否在BitSet存在时，也会得到true。
         *      这个问题该如何解决或者缓解呢？
         *      1）调整hashcode生成算法：我们可以对一个String使用多个hashcode算法，生成多个hashcode，然后在同一个BitSet进行多次
         *  “着色”，在判断存在性时，只有所有的着色位为true时，才判定成功。
         *      2) 多个BitSet并行保存：
         *      改良1)中的实现方式，我们仍然使用多个hashcode生成算法，但是每个算法生成的值在不同的BitSet中着色，这样可以保持每个
         *  BitSet的稀疏度(降低冲突的几率)。在实际结果上，比1)的误判率更低，但是它需要额外的占用更多的内存，毕竟每个BitSet都需要
         *  占用内存。这种方式，通常是缩小hashcode的值域，避免内存过度消耗。
         *      3) 是否有必要完全避免误判？
         *      如果做到100%的正确判断率，在原理上说BitSet是无法做的，BitSet能够保证“如果判定结果为false，那么数据一定是不存在；
         *  但是如果结果为true，可能数据存在，也可能不存在(冲突覆盖)”,即“false == YES，true == Maybe”。有人提出将冲突的数据保存
         *  在类似于BTree的额外数据结构中，事实上这种方式增加了设计的复杂度，而且最终仍然没有良好的解决内存占用较大的问题。
         *
         */
        // BloomFilter(布隆姆过滤器)
        /*
         *      BloomFilter 的设计思想和BitSet有较大的相似性，目的也一致，它的核心思想也是使用多个Hash算法在一个“位图”结构上着色，最终
         *  提高“存在性”判断的效率。
         */
    }
}
