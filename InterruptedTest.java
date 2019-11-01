package java高并发;
/**
 * 线程中断用例
*/
public class InterruptedTest implements Runnable{
	public static void main(String[] args) {
		Thread thread = new Thread(new InterruptedTest());	//新建线程
		thread.start();	//启动线程
		try {
			Thread.sleep(1000);	//主线程休眠一秒
		}catch(InterruptedException e){}	//捕捉异常
		thread.interrupt();	//发送中断信号
	}
	public void run() {
		System.out.println("thread start！");	//输出提示信息
		while(true) {
			if(Thread.currentThread().isInterrupted()) {	//如果线程收到中断信号
				System.out.println("interrupted!");	//输出中断信息
				try {
					Thread.sleep(1000L);	//休眠一秒
				} catch (InterruptedException e) {	//捕捉异常
					System.out.println("sleep 1s");	//休眠一秒
				}
				System.out.println(Thread.currentThread().isInterrupted());	//输出线程的中断信号
				break;	//跳出循环
			}
			if(Thread.interrupted()) {	//如果线程收到中断信号
				System.out.println(Thread.currentThread().isInterrupted());	//输出中断信号
				System.out.println("continue");	//输出提示信息
				break;	//跳出循环
			}
		}
	}
}
/*两种查询中断的方式理应不同但实际检测是相同的，与书本上的描述不一致*/