package java高并发;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**测试读写锁用例*/
public class ReentrantReadWriteLockTest implements Runnable{
	static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);	//实例化重入锁
	static ReadLock read = lock.readLock();	//获取读锁
	static WriteLock write = lock.writeLock();	//获取写锁
	static LinkedList<Integer> list = new LinkedList<Integer>();	//声明list成员
	public static void main(String[] args) {
		list.add(1);	//将1写入list
		list.add(2);	//将2写入list
		for(int i = 0;i<10;i++) {	//循环十次
			new Thread(new ReentrantReadWriteLockTest()).start();	//实例化线程并启动
		}
		try {
			Thread.sleep(2000);	//休眠两秒
		} catch (InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
		for(Object i:list.toArray()) {	//遍历list
			System.out.printf((Integer)i+" ");	//输出所有元素
		}
	}
	public void run() {
		read.lock();	//获取读锁
		int i = 0;	//声明计次变量i
		for(Object c:list.toArray()) {	//遍历list
			i+=(Integer)c;	//统计其值
		}
		read.unlock();	//释放读锁
		write.lock();	//获取写锁
		list.add(i);	//写入统计量
		write.unlock();	//释放写锁
		System.out.println(i);	//输出统计量
	}
}
