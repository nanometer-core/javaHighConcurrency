package java高并发;
/**测试等待和唤醒的实例*/
public class WaitTest implements Runnable{
	static Object main = new Object();	//新建本类
	public static void main(String[] args) {
		for(int i = 0;i<3;i++) {	//循环三次
			new Thread(new WaitTest()).start();	//新建并启动线程
		}
		for(int i = 0;i<3;i++) {	//循环三次
			try {
				Thread.sleep((long) (Math.random()*2000));	//随即在两秒内休眠
			} catch (InterruptedException e) {	//捕捉异常
				e.printStackTrace();	//处理异常
			}
			synchronized(main) {	//获取实例的的锁
				main.notify();	//在唤醒一个在实例上等待的对象
			}
		}
	}
	public void run() {
		System.out.println("thread start!");	//输出开始信息
		System.out.println("set wait!");	//输出等待信息
		synchronized(main) {	//获取实例的锁
			try{
				main.wait();	//在实例上等待
			}
			catch (InterruptedException e) {	//捕捉异常
				System.out.println(e.getMessage());	//输出异常信息
			}
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end!");	//输出线程结束信息
	}
}
