package java高并发;
import java.util.concurrent.locks.LockSupport;
/**线程阻塞工具类实例*/
public class LockSupportTest implements Runnable{
	public static void main(String[] args) {
		Thread temp = new Thread(new LockSupportTest());	//新建线程
		temp.start();	//启动线程
		try {
			Thread.sleep(1000);	//主线程休眠一秒
		} catch (InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出中断信息
		}
		LockSupport.unpark(temp);	//唤醒线程
	}
	public void run() {
		LockSupport.park();	//阻塞自身
		System.out.println(Thread.currentThread().getName()+" end!");	//输出线程结束信息
	}
}