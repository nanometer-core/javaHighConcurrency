package java高并发;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<String> task = new FutureTask<String>(new RealData1("a"));	//实例化契约类
		ExecutorService executor = Executors.newFixedThreadPool(1);	//创建线程池
		executor.submit(task);	//提交契约
		System.out.println("请求完毕！");	//输出提示信息
		try {
			Thread.sleep(2000);	//休眠两秒
		}catch(InterruptedException e) {}	//捕捉异常
		System.out.println("数据 = " + task.get());	//输出数据
	}
}

class RealData1 implements Callable<String> {	//实现数据类
	private String para;	//声明成员变量
	RealData1(String para) {	//定义构造方法
		this.para=para;	//保存参数
	}
	public String call() throws Exception {	//实现接口方法
		StringBuffer sb = new StringBuffer();	//新建缓冲区
		for(int i = 0;i<10;i++) {	//循环十次
			sb.append(para);	//添加字符串到字符缓冲区
			try {
				Thread.sleep(100);	//休眠零点一秒
			}catch(InterruptedException e) {}	//捕捉异常
		}
		return sb.toString();	//返回字符串
	}
}