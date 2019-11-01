package java高并发;
import java.util.ArrayList;
/**测试ArrayList的线程不安全*/
public class ArrayListTest implements Runnable{
	static ArrayList<Integer> list = new ArrayList<Integer>();	//声明list成员
	public static void main(String[] args) {
		Thread thread1 = new Thread(new ArrayListTest());	//新建线程1
		Thread thread2 = new Thread(new ArrayListTest());	//新建线程2
		thread1.start();	//启动线程1
		thread2.start();	//启动线程2
		try {
			thread1.join();	//等待线程1结束
			thread2.join();	//等待线程2结束
		}catch(InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
		System.out.println(list.size());	//输出list的大小
	}
	public void run() {
		for(int i = 0;i<10000;i++) {	//循环一万次
			list.add(i);	//向list添加元素
		}
	}
}
