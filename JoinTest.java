package java高并发;
/**测试线程的结束和谦让*/
public class JoinTest implements Runnable{
	
	public static void main(String[] args) {
		Thread a = new Thread(new JoinTest());	//新建线程
		a.start();	//启动线程
		try {
			a.join();	//主线程等待线程结束
		} catch (InterruptedException e) {	//捕捉中断异常
			System.out.println(e.getMessage());	//输出异常信息
		}
		System.out.println("exit!");	//输出退出提示
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getId()+" start");	//输出线程启动信息
		try {
			Thread.sleep(1000);	//休眠一秒
		} catch (InterruptedException e) {	//捕捉中断
			System.out.println(e.getMessage());	//输出异常信息
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end");	//输出线程结束信息
		Thread.yield();	//礼让线程资源
	}
}
