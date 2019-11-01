package java高并发;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ThreadLocalTest implements Runnable {
	static final int ThreadCount = 10;	//设置线程数量
	static ThreadLocal<Random> local = new ThreadLocal<Random>();	//声明线程局部变量
	static CountDownLatch jobs = new CountDownLatch(ThreadCount);	//声明倒计数器
	static Random rand = new Random();	//声明随机数类
	private boolean useThreadLocal;	//声明使用局部变量标志
	public ThreadLocalTest(boolean useThreadLoacl) {	//定义构造方法
		this.useThreadLocal=useThreadLoacl;	//保存是否使用局部变量的信息
	}
	public static void main(String[] args) {	//主方法提供流程控制
		long startTime = System.currentTimeMillis();	//保存开始时间
		for(int i = 0;i<ThreadCount;i++) {	//循环启动指定数量的线程
			new Thread(new ThreadLocalTest(false)).start();	//启动不使用局部变量的线程
		}
		try {
			jobs.await();	//等待线程结束
		} catch (InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
		System.out.println("use time:"+(System.currentTimeMillis()-startTime)+"ms");	//输出总耗时
		startTime = System.currentTimeMillis();	//记录开始时间
		jobs=new CountDownLatch(ThreadCount);	//重置计数器
		for(int i = 0;i<ThreadCount;i++) {	//启动指定数量的线程
			new Thread(new ThreadLocalTest(true)).start();	//启动线程
		}
		try {
			jobs.await();	//等待线程结束
		} catch (InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}
		System.out.println("use time:"+(System.currentTimeMillis()-startTime)+"ms");	//输出用总耗时
	}
	public void run() {	//实现runnable接口
		Random rand;	//声明随机数引用
		if(useThreadLocal) {	//如果使用局部变量
			if(local.get()==null) {	//如果局部变量没有被创建
				local.set(new Random());	//创建局部变量
			}
			rand = local.get();	//获取局部变量
		}
		else {	//如果不使用局部变量
			rand = ThreadLocalTest.rand;	//保存全局变量
		}
		for(int i = 0;i<10000000;i++) {	//循环计算
			rand.nextInt();	//取下一个随机数
		}
		jobs.countDown();	//计数器减一
	}
}
