package java高并发;
/**
 *启动线程和结束线程测试用例
*/
public class ThreadTest implements Runnable{
	public static void main(String[] args) {
		Thread thread = new Thread(new ThreadTest());	//新建线程
		thread.start();	//启动线程
		try {
			Thread.sleep(1000);	//主线程休眠一秒
		} catch (InterruptedException e) {}	//捕捉异常
		thread.stop();	//强行停止线程
	}
	public void run() {	//线程方法
		System.out.println("success!");	//输出提示信息
		while(true);	//进入无限循环
	}
}
