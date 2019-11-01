package java高并发;
/**测试方法加锁*/
public class SynchronizedTest implements Runnable{
	private static int value = 1;	//声明value成员
	private static Object a = new Object();	//实例化对象
	synchronized private void setValue(int value){	//设置value
		SynchronizedTest.value=value;	//设置value
	}
	synchronized public int getValue() {	//获取value
		return SynchronizedTest.value;	//返回value的值
	}
	synchronized public void printValue() {	//输出value 的值
		System.out.println(value);	//输出value的值
	}
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//循环十次
			new Thread(new SynchronizedTest()).start();	//启动线程
		}
	}
	public void run() {
		for(int i = 0;i<100;i++) {	//循环一百次
			synchronized (a) {	//获取a的锁
				printValue();	//输出value的值
				setValue(getValue()+1);	//设置value的值
			}
		}
	}
}
