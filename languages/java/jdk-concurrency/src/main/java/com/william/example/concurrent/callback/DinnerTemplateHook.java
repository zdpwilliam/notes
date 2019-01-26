package com.william.example.concurrent.callback;

/**
 * 
 * 	模版方法其实就是父类定义了一整套的骨架流程，而其中的某些方法，需要子类来完成，
 * 这样子的好处是，流程已经定义好，子类要做的事情就是按着流程和模版自己写实现。
 * 
 * （方法回调，钩子函数） 
 * 
 *	使用钩子函数有个千万要注意 的地方，如果你在钩子内写了死循环，会发生永远不会执行结束的情况，
 * 那系统会永远无法按照正常情况关闭。只能杀java进程了！
 * @author zdpwilliam
 *
 */
public class DinnerTemplateHook {

    public static void main(String[] args) {
        DinnerTemplate  westernStyleFood = new WesternStyleFood();
        westernStyleFood.process();
        
        final DinnerTemplate  theLastDinner = new ChineseStyleFood();

        //addShutdownHook钩子函数
        //即：当jvm结束时，会调用执行注册在shutdownHooK内的所有方法或线程
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				theLastDinner.process();
			}
		}));
    }
}

abstract class DinnerTemplate {

	private void tuangou() {
        System.out.println("团购");
    }

    abstract void haveDinner();

    private void pingjia() {
        System.out.println("评价");
    }

    public void process(){
        tuangou();
        
        haveDinner();
        
        pingjia();
    }
}

class WesternStyleFood extends DinnerTemplate {

    @Override
    protected void haveDinner() {
        System.out.println("吃西餐");
    }

}

class ChineseStyleFood extends DinnerTemplate {

    @Override
    protected void haveDinner() {
        System.out.println("吃中餐");
    }

}