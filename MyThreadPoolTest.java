package java高并发;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.*;

import java.util.concurrent.TimeUnit;

public class MyThreadPoolTest implements Runnable {
	static ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());	//设置一个五个线程的最大为十个线程的空闲线程生存时间为三十秒的直接提交队列的线程池
	//static ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));	//有界队列
	//static ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());	//无界队列
	//static ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());	//优先队列
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//循环十次
			threadPool.submit(new MyThreadPoolTest());	//提交任务
		}
		threadPool.shutdown();	//停止线程池
	}
	public void run() {
		System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());	//输出线程名与当前时间
		try {
			Thread.sleep(500);	//休眠半秒
		}catch(InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
	}
	private void info() {
		RejectedExecutionHandler a = new AbortPolicy();	//抛出异常
		RejectedExecutionHandler b = new CallerRunsPolicy();	//在调用者中执行任务
		RejectedExecutionHandler c = new DiscardOldestPolicy();	//丢弃最老的任务
		RejectedExecutionHandler d = new DiscardPolicy();	//丢弃提交的任务
	}
}
class Temp1 extends ThreadPoolExecutor implements ThreadFactory {
	public Temp1(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	public Thread newThread(Runnable r) {	//实现threadFactory接口的新建线程方法
		return null;
	}
	protected void beforeExecute(Thread t, Runnable r) {	//重写ThreadPoolExecutor的任务执行前操作
		super.beforeExecute(t, r);
	}
	protected void afterExecute(Runnable r, Throwable t) {	//重写ThreadPoolExecutor的任务执行后操作
		super.afterExecute(r, t);
	}
	protected void terminated() {	//重写ThreadPoolExecutor的关闭操作
		super.terminated();
	}
}
