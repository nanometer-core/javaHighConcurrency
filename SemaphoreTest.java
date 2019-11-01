package java高并发;
import java.util.concurrent.Semaphore;
/**测试信号量用例*/
public class SemaphoreTest implements Runnable{
	static Semaphore flag = new Semaphore(3, true);	//新建信号量类，设置准入数和是否公平
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//循环十次
			new Thread(new SemaphoreTest()).start();	//实例化线程并启动
		}
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getId()+" start!");	//输出线程开始信息
		//flag.acquireUninterruptibly();	//可响应中断的获取许可
		try {
			flag.acquire();	//获取许可
			//flag.tryAcquire(3, TimeUnit.SECONDS);	//在一定时间内获取许可
			Thread.sleep(1000);	//休眠一秒
		} catch (InterruptedException e) {	//捕捉异常
			e.printStackTrace();	//输出异常
		}finally {	//最后
			flag.release();	//释放许可
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end!");	//输出线程结束信息
	}
}
