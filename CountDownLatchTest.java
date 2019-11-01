package java高并发;
import java.util.concurrent.CountDownLatch;
/**测试倒计数器用例*/
public class CountDownLatchTest implements Runnable{
	static CountDownLatch count = new CountDownLatch(10);	//实例化倒计数器
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//循环十次
			new Thread(new CountDownLatchTest()).start();	//实例化线程并启动
		}
		try {
			count.await();	//主线程在计数器上等待
		} catch (InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
		System.out.println("thread over");	//输出主线程结束信息
	}
	public void run() {
		try {
			Thread.sleep((long) (Math.random()*999+1));	//随机休眠一秒到一毫秒
		}catch(InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出中断异常
		}finally {	//最后
			count.countDown();	//计数器减一
		}
		System.out.println("thread "+Thread.currentThread().getName()+" end");	//输出线程结束信息
	}
}
