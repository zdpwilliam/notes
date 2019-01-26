package com.william.example.concurrent.callback;

/**
 * 一、C中的回调
 * 	回调用于层间协作，简单的说就是：下层反过来调用上层的函数。其实回调和API非常接近，他们的共性都是跨层调用的函数。
 * 但区别是：
 * （1）API是低层提供给高层的调用，一般这个函数对高层都是已知的；
 * （2）回调就是该函数写在高层，低层通过一个函数指针保存这个函数，而低层通过该函数指针调用高层那个函数。
 * 
 * 二、Java中的回调
 * 	在java编程中，要从面对对象的角度，来理解回调，而不应再从层级的角度理解回调，关于这方面的解析很少，本文旨在从理论和编程两个方面进行解释java中的回调。
 * 	上说，java中的回调就是将规定一个接口，然后设计一个类（该类与接口存在关联或者依赖关系），最后，当使用这个类的时候，实现之前规定的接口，
 * 这就是java中的回调。简单的说，在Java中，通常就是编写下层的人规定一个接口，由上层来实现这个接口；然后上层就可以把这个接口的一个对象作为参数传给下层，
 * 下层就会通过那个接口来调用由上层编写的函数。
 * 
 * 1、同步回调：一种双向调用模式，也就是说，被调用方在接口被调用时也会调用对方的接口，结果的返回必须等到被调用方执行完毕才返回。
 * 2、异步回调：伪结果立即返回，但真实的结果等到被调用方执行完毕才返回。
 * 
 * 三、调用的概念
 * 	同步调用：一种阻塞式调用，调用方要等待对方执行完毕才返回，它是一种单向调用。
 * 	回调：一种双向调用模式，也就是说，被调用方在接口被调用时也会调用对方的接口。
 *
 *	*****《还有一种复杂的双方回调函数》******
 *
 * 四、钩子函数
 * 	模版方法模式例子可以很好解释。
 *
 * @author zdpwilliam
 * 
 */
public class CallBackExample {

	//关联关系回调
    public static class RelatedClient implements IHello {
    	
        public void sayHello() {
            System.out.println("hello");
        }
        
        public static void main(String[] args) {
        	//实现接口式的关联关系回调
            IHello mTest = new RelatedClient();
            RelatedMan english = new RelatedMan();
            english.setCallback(mTest);
            english.say();
            
            //匿名类式的关联关系回调
            RelatedMan chinese = new RelatedMan();
            chinese.setCallback(new IHello() {
                public void sayHello() {
                    System.out.println("你好");
                }
            });
            chinese.say();
        }
    }
    
    static class RelatedMan {
    	/**
    	 * 关联关系回调
    	 */
        private IHello hello;
      
        void setCallback(IHello cb){
            hello = cb;  
        }
        
        public void say() {
            hello.sayHello();
        }
    }
    
    //依赖关系回调
    static class DependencyClient implements IHello {
    	
        public void sayHello() {
            System.out.println("hello");
        }
        
        public static void test() {
        	DependencyMan chinese = new DependencyMan();
        	
            chinese.say(new IHello() {
            	
                public void sayHello() {
                    System.out.println("你好");
                }
            });
        }  
      
        public static void main(String[] args) {  
            test();
            DependencyMan english = new DependencyMan();
            //依赖关系回调
            english.say(new DependencyClient());
        }
    }  

    static class DependencyMan {
    	
    	/**
    	 * 依赖关系回调
    	 */
        public void say(IHello hello) {
            hello.sayHello();
        }
    }
}
