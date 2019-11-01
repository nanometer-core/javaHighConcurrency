package java高并发;
/**线程组的测试用例*/
public class ThreadGroupTest implements Runnable{
	public static void main(String[] args) {
		ThreadGroup group = new ThreadGroup("group");	//新建线程组
		for(int i = 0;i<3;i++) {	//循环三次
			new Thread(group, new ThreadGroupTest()).start();	//新建并指定所属组的线程并启动
		}
		System.out.println("count:"+group.activeCount());	//输出线程组中线程的数量
		group.interrupt();	//线程组发送中断信号
	}
	public void run() {
		while(true) {
			System.out.println("thread "+Thread.currentThread().getId()+" start");	//输出线程启动信息
			if(Thread.currentThread().isInterrupted()) {	//判断是否有中断信号
				System.out.println("thread "+Thread.currentThread().getId()+" exit");	//输出线程结束信息
				break;	//跳出无限循环
			}
		}
	}
}
