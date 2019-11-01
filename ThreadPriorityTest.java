package java高并发;
/**线程的优先级测试用例*/
public class ThreadPriorityTest implements Runnable{
	int value = 0;	//声明value成员
	public static void main(String[] args) {
		for(int i = 1;i<10;i++) {	//循环九次
			Thread temp = new Thread(new ThreadPriorityTest());	//新建线程
			temp.setPriority(i);	//设置优先级
			temp.start();	//启动线程
		}
	}
	public void run() {
		System.out.println(Thread.currentThread().getId()+" start!");	//输出线程启动信息
		while(value<100) {	//如果value的值小于一百
			value++;	//value的值自增
		}
		System.out.println(Thread.currentThread().getId()+" end!");	//输出线程停止信息
	}
}
