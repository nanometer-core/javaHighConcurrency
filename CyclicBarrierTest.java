package java高并发;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/**测试循环栅栏用例*/
public class CyclicBarrierTest implements Runnable{
	static CyclicBarrier barrier = new CyclicBarrier(10,new Runnable() {	//实例化循环栅栏
		public void run() {
			System.out.println("thread over!");	//输出结束信息
		}
	});
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//循环十次
			try {
				Thread.sleep(500);	//休眠半秒钟
			}catch(InterruptedException e) {	//捕捉中断异常
				e.printStackTrace();	//输出异常信息
			}
			new Thread(new CyclicBarrierTest()).start();	//实例化线程并启动
		}
	}
	public void run() {
		try {
			Thread.sleep((long) (Math.random()*1999+1));	//随机在两秒内休眠
			System.out.println("thread "+Thread.currentThread().getName()+" end!");	//输出线程结束
			barrier.await();	//循环栅栏计数 会阻塞当前线程直到开始下一次循环
		}catch(InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}catch(BrokenBarrierException e) {	//捕捉破损异常
			e.printStackTrace();	//输出异常信息
		}
	}
}
