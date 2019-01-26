/**
 * Created by william on 17-10-14.
 */
package com.william.example.classloader;
/*
类加载过程：
    类从被加载到虚拟机内存中开始，到卸载出内存为止，它的整个生命周期包括：加载（Loading）、
 验证（Verification）、准备(Preparation)、解析(Resolution)、初始化(Initialization)、
 使用(Using)和卸载(Unloading)7个阶段。
    其中准备、验证、解析3个部分统称为连接（Linking）。

    Java 中的类加载器大致可以分成两类，一类是系统提供的，另外一类则是由 Java 应用开发人员编写的。
 系统提供的类加载器主要有下面三个：
    1、引导类加载器（bootstrap class loader）：它用来加载 Java 的核心库，是用原生代码来实现的，
 并不继承自java.lang.ClassLoader。
    2、扩展类加载器（extensions class loader）：它用来加载 Java 的扩展库。Java 虚拟机的实现会
 提供一个扩展库目录。该类加载器在此目录里面查找并加载 Java 类。
    3、应用程序类加载器（applications class loader）：它根据 Java 应用的类路径（CLASSPATH）
 来加载Java类。一般来说，Java 应用的类都是由它来完成加载的。
    可以通过 ClassLoader.getSystemClassLoader()来获取它。

 JVM类加载机制：
    •全盘负责，当一个类加载器负责加载某个Class时，该Class所依赖的和引用的其他Class也将由该类加载器
 负责载入，除非显示使用另外一个类加载器来载入
    •父类委托，先让父类加载器试图加载该类，只有在父类加载器无法加载该类时才尝试从自己的类路径中加载该类
    •缓存机制，缓存机制将会保证所有加载过的Class都会被缓存，当程序中需要使用某个Class时，类加载器先从
 缓存区寻找该Class，只有缓存区不存在，系统才会读取该类对应的二进制数据，并将其转换成Class对象，存入缓
 存区。这就是为什么修改了Class后，必须重启JVM，程序的修改才会生效

 类加载有三种方式：
    1、命令行启动应用时候由JVM初始化加载
    2、通过Class.forName()方法动态加载
    3、通过ClassLoader.loadClass()方法动态加载

 Class.forName()和ClassLoader.loadClass()区别：
    Class.forName() 将类的.class文件加载到jvm中之外，还会对类进行解释，执行类中的static块；
    ClassLoader.loadClass() 只干一件事情，就是将.class文件加载到jvm中，不会执行static中的内容,
 只有在newInstance才会去执行static块。
    注：Class.forName(name, initialize, loader)带参函数也可控制是否加载static块。并且只有调用了
 newInstance()方法采用调用构造函数，创建类的对象。

 双亲委派模型：
    双亲委派模型的工作流程是：如果一个类加载器收到了类加载的请求，它首先不会自己去尝试加载这个类，而是
 把请求委托给父加载器去完成，依次向上，因此，所有的类加载请求最终都应该被传递到顶层的启动类加载器中，只有
 当父加载器在它的搜索范围中没有找到所需的类时，即无法完成该加载，子加载器才会尝试自己去加载该类。
 双亲委派机制：
    1、当AppClassLoader加载一个class时，它首先不会自己去尝试加载这个类，而是把类加载请求委派给父类
 加载器ExtClassLoader去完成。
    2、当ExtClassLoader加载一个class时，它首先也不会自己去尝试加载这个类，而是把类加载请求委派给
 BootStrapClassLoader去完成。
    3、如果BootStrapClassLoader加载失败（例如在$JAVA_HOME/jre/lib里未查找到该class），会使用
 ExtClassLoader来尝试加载；
    4、若ExtClassLoader也加载失败，则会使用AppClassLoader来加载，如果AppClassLoader也加载失败，
 则会报出异常ClassNotFoundException。

 自定义类加载器：
    通常情况下，我们都是直接使用系统类加载器。但是，有的时候，我们也需要自定义类加载器。比如应用是通过网络
 来传输 Java 类的字节码，为保证安全性，这些字节码经过了加密处理，这时系统类加载器就无法对其进行加载，
 这样则需要自定义类加载器来实现。自定义类加载器一般都是继承自 ClassLoader 类，从上面对 loadClass
 方法来分析来看，我们只需要重写 findClass 方法即可。下面我们通过一个示例来演示自定义类加载器的流程：

 自定义类加载器的核心在于对字节码文件的获取，如果是加密的字节码则需要在该类中对文件进行解密。由于这里只是演示，
 我并未对class文件进行加密，因此没有解密的过程。这里有几点需要注意：
    1、这里传递的文件名需要是类的全限定性名称，即com.paddx.test.classloading.Test格式的，因为defineClass
 方法是按这种格式进行处理的。
    2、最好不要重写loadClass方法，因为这样容易破坏双亲委托模式。
    3、这类Test类本身可以被AppClassLoader类加载，因此我们不能把com/paddx/test/classloading/JNDIThreadClassLoaderTest.class
 放在类路径下。否则，由于双亲委托机制的存在，会直接导致该类由AppClassLoader加载，而不会通过我们自定义类加载器来加载。
 */