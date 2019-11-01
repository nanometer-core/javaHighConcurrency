package java高并发;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest implements Runnable {
	static final int THREAD_COUNT = 10;	//设置启动的线程数
	static AtomicStampedReference<Object> object = new AtomicStampedReference<Object>(new Object(), 0);	//带时间戳的cas对象
	static AtomicInteger value = new AtomicInteger(0);	//cas整形变量
	static CountDownLatch count = new CountDownLatch(THREAD_COUNT);	//声明倒计数器
	volatile int a;
	AtomicIntegerFieldUpdater<AtomicStampedReferenceTest> upDater = AtomicIntegerFieldUpdater.newUpdater(AtomicStampedReferenceTest.class, "count");	//启用普通变量的cas操作
	public static void main(String[] args) {	//主方法提供流程控制
		for(int i = 0;i<THREAD_COUNT;i++) {	//循环启动所有线程
			new MyThread(new AtomicStampedReferenceTest(),count).start();
		}
		try {
			count.await();	//在计数器上等待
		} catch (InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
		System.out.println(value.get());	//获取最终值
	}
	public void run() {	//重写run方法
		for(int i = 0;i<100000;i++) {	//足够大的循环
			value.incrementAndGet();	//加一并获取新的值
		}
	}
}
