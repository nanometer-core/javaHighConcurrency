package java高并发;
/**测试线程的暂停与继续用例*/
public class SuspendTest implements Runnable{
	static private SuspendTest main = new SuspendTest();	//实例化本类
	public static void main(String[] args) {
		Thread a = new Thread(new SuspendTest());	//新建线程
		a.setName("will suspend");
		a.start();	//启动线程
		try {
			Thread.sleep(1000);	//休眠一秒
		} catch (InterruptedException e) {	//捕捉中断异常
			System.out.println(e.getMessage());	//输出提示信息
		}
		new Thread(new SuspendTest()).start();	//新建并启动另一个线程
		a.suspend();	//暂停线程
		try {
			Thread.sleep(10000);	//休眠一秒
		} catch (InterruptedException e) {	//捕捉中断异常
			System.out.println(e.getMessage());	//输出提示信息
		}
		a.resume();	//继续执行
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getName()+" start");	//输出线程启动信息
		synchronized (main) {	//获取实例的锁
			try {
				Thread.sleep(1000);	//休眠一秒
			}catch(InterruptedException e) {	//捕捉异常
				System.out.println(e.getMessage());	//输出异常信息
			}
		}
		System.out.println("thread "+Thread.currentThread().getName()+" end");	//输出线程结束信息
	}
}
