package java高并发;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest implements Runnable{
	static ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);	//新建一个延迟线程池
	public static void main(String[] args) {
		//threadPool.schedule(new Test23(), 500, TimeUnit.MILLISECONDS);	//在指定时间后执行一次
		//threadPool.scheduleAtFixedRate(new Test23(), 500, 500, TimeUnit.MILLISECONDS);	//在指定间隔后执行一次
		threadPool.scheduleWithFixedDelay(new ScheduledExecutorTest(), 500, 500, TimeUnit.MILLISECONDS);	//在延迟指定时间后执行一次
	}
	public void run() {
		System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());	//输出线程名和当前系统时间
		try {
			Thread.sleep(100);	//休眠零点一秒
		}catch(InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
	}
}
