package java高并发;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**测试等待工具用例*/
public class ConditionTest implements Runnable{
	static ReentrantLock lock = new ReentrantLock(true);	//声明重入锁成员
	static Condition condition = lock.newCondition();	//获取重入锁的等待工具
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//循环十次
			new Thread(new ConditionTest()).start();	//实例化线程并启动
		}
		for(int i = 0;i<10;i++) {	//循环十次
			lock.lock();	//获取锁
			condition.signal();	//唤醒一个等待的线程
			//condition.signalAll();	//唤醒全部等待的线程
			lock.unlock();	//释放锁
		}
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getId()+" start!");	//输出线程开始信息
		//condition.awaitUninterruptibly();	//可响应中断的等待
		lock.lock();	//获取锁
		try {
			condition.await();	//开始等待
			//condition.await(3, TimeUnit.SECONDS);	//在一定时间内等待
			//condition.awaitUntil(new Date(new Date().getTime()));	//等待到指定时间
		} catch (InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}finally {	//最后
			lock.unlock();	//释放锁
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end!");	//输出线程结束信息
	}

}
