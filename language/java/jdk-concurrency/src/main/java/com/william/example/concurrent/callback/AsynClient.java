package com.william.example.concurrent.callback;

import java.util.concurrent.TimeUnit;

/**
 * 异步回调函数
 * @author zdpwilliam
 *
 */
public class AsynClient implements AsynCallback {

	public void doCallback(String question, String answer) {
		System.out.println("作业本");
        if (answer != null) {
            System.out.println("作业：" + question + " 答案：" + answer);
        } else {
            System.out.println("作业：" + question + " 答案：" + "(空白)");
        }
	}
	
	/* 同步回调关键区别点   （依赖关系回调）*/
	public void ask(final String homework, final Server service) {
        new Thread(new Runnable() {
            public void run() {
                service.getAnswer(homework, AsynClient.this);
            }
        }).start();
  
        goHome();
    }
  
    public void goHome() {
        System.out.println("我回家了……好室友，帮我写下作业。");
    }
    
    
    public static void main(String[] args) {  
    	AsynClient student = new AsynClient();
        String homework = "当x趋向于0，sin(x)/x =?";
        student.ask(homework, new Server());
    }  

	static class Server {
		
		public void getAnswer(String homework, AsynCallback someone) {
	        if ("1+1=?".equals(homework)) {
	            someone.doCallback(homework, "2");
	        } else if("当x趋向于0，sin(x)/x =?".equals(homework)) {
	            System.out.print("思考：");
	            for(int i=1; i<=3; i++) {
	                System.out.print(i+"秒 ");
	                try {
	                    TimeUnit.SECONDS.sleep(1);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	            System.out.println();
	            someone.doCallback(homework, "1");
	        } else {
	            someone.doCallback(homework, "(空白)");
	        }
	    }  
	}
}
