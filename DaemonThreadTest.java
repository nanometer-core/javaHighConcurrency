package java高并发;
/**测试守护线程的使用用例*/
public class DaemonThreadTest implements Runnable{
	public static void main(String[] args) {
		Thread thread = new Thread(new DaemonThreadTest());	//新建线程
		thread.setDaemon(true);	//设置为守护线程
		thread.start();	//设置线程启动
		try {
			Thread.sleep(1000);	//主线程休眠一秒
		}catch(InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
	}
	public void run() {
		while(true) {
			System.out.println("DaemonThread start!");	//输出线程启动信息
		}
	}
}
