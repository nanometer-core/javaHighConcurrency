package java高并发;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import com.google.common.util.concurrent.MoreExecutors;

public class DaemonThreadPool {
	public static void main(String[] args) {	//主方法提供流程控制
		testDeamonPool();	//测试守护线程池
	}
	static void testDirectExecutor() {	//实现测试在线程中运行的线程池
		Executor executor = MoreExecutors.directExecutor();	//获取线程池
		executor.execute(()->System.out.println(Thread.currentThread().getName()));	//提交任务
	}
	static void testDeamonPool() {	//实现测试守护线程池
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);	//新建固定线程池并转为一般线程池
		MoreExecutors.getExitingExecutorService(executor);	//设置为守护线程池
		executor.execute(()->System.out.println(Thread.currentThread().getName()));	//提交任务
	}
}
