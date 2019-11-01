package java高并发;
import java.util.concurrent.locks.ReentrantLock;

/**测试重入锁功能用例*/
public class ReentrantLockTest implements Runnable{
	static ReentrantLock lock = new ReentrantLock(); //声明重入锁成员，可选参数是否公平
	public static void main(String[] args) {
		for(int i = 0;i<100;i++) {	//循环一百次
			new Thread(new ReentrantLockTest()).start();	//新建并启动一个线程
		}
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getId()+" start!");	//输出线程启动信息
		lock.lock();	//获取锁
		//lock.lockInterruptibly();	//获取可以响应中断的锁
		//lock.tryLock(3, TimeUnit.SECONDS);	//在一定时间内尝试获取锁
		try {
			Thread.sleep(100);	//休眠0.1秒
		}catch(InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
		finally {	//异常的尾处理
			lock.unlock();	//释放锁
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end!");	//输出线程的结束信息
	}
}
