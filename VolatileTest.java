package java高并发;
/**volatile关键字修正指令重排*/
public class VolatileTest implements Runnable{
	volatile long a;	//保护型长整型变量a
	public static void main(String[] args) {
		new Thread(new VolatileTest()).start();	//新建并启动线程
	}
	public void run() {
		a=0xffff;	//改变其值
	}
}
