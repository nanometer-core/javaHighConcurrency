package java高并发;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest implements Runnable {
	static ExecutorService threadPool = Executors.newFixedThreadPool(5);	//新建一个固定数量的线程池
	//static ExecutorService threadPool = Executors.newSingleThreadExecutor();	//新建一个只有一个线程的线程池
	//static ExecutorService threadPool = Executors.newCachedThreadPool();	//新建一个动态线程池
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//循环十次
			threadPool.submit(new ThreadPoolTest());	//提交任务
		}
	}
	public void run() {
		System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());	//输出线程名称以及当前时间
		try {
			Thread.sleep(500);	//休眠半秒
		}catch(InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
	}
}