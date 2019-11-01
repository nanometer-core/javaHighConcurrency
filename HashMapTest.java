package java高并发;
import java.util.HashMap;
/**HashMap的高并发不安全测试用例*/
public class HashMapTest implements Runnable{
	static private HashMap<Integer, String>list = new HashMap<Integer, String>();	//声明list成员
	private int start,end;	//声明开始和结束点
	public HashMapTest(int start,int end) {	//定义构造方法
		super();	//调用父类的构造方法
		this.start=start;	//保存启动量
		this.end=end;	//保存结束量
	}
	public static void main(String[] args) {
		Thread temp1 = new Thread(new HashMapTest(0,100000));	//新建从零开始到十万结束的线程
		Thread temp2 = new Thread(new HashMapTest(100000,200000));	//新建从十万开始到二十万结束的线程
		temp1.start();	//启动第一个线程
		temp2.start();	//启动第二个线程
		try {
			temp1.join();	//等待第一个线程结束
			temp2.join();	//等待第二个线程结束
		}catch(InterruptedException e) {	//捕捉异常
			e.printStackTrace();	//输出异常信息
		}
		System.out.println(list.size());	//输出list的大小
	}
	public void run() {
		for(int i = start;i<end;i++) {	//从开始量到结束量的循环
			list.put(i, String.valueOf(i));	//添加元素
		}
	}
}
