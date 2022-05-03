package com.william.example.classloader;


/**
 *   双亲委派模型的的"破坏"：
 *   OSGI (Open Service Gateway Initiative)技术是Java动态化模块化系统的一系列规范
 *
 *   OSGi每个模块都有自己独立的classpath。如何实现这一点呢？
 *   是因为OSGi采取了不是原来"双亲委派"类加载机制而是"网状结构"的加载机制：
 *      OSGi为每个bundle（模块）提供一个类加载器，该加载器能够看到bundle Jar文件内部的类和资源；
 *   为了让bundle能互相协作，可以基于依赖关系，从一个bundle类加载器委托到另一个bundle类加载器。
 */
public class OSGIClassLoaderTest {

}
