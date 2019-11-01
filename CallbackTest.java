package java高并发;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class CallbackTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));	//新建带监听的线程池
		ListenableFuture<String> task = service.submit(new RealData2("x"));	//提交任务获取契约
		service.shutdown();	//停止提交
		/*task.addListener(new Runnable() {	//添加监听事件
			public void run() {	//设置监听事件业务
				System.out.println("异步处理成功");	//输出提示信息
				try {
					System.out.println(task.get());	//输出结果
				}catch(Exception e) {	//捕捉异常
					e.printStackTrace();	//输出异常
				}
			}
		}, MoreExecutors.directExecutor());*/
		Futures.addCallback(task, new FutureCallback<String>() {	//添加回调事件
			public void onSuccess(String result) {	//成功事件
				System.out.println("异步处理成功,result = " + result);	//输出数据
			}
			public void onFailure(Throwable t) {	//失败事件
				System.out.println("异步处理失败，e = " + t);	//输出异常
			}
		});
		System.out.println("main task done....");	//输出提示信息
		Thread.sleep(3000);	//休眠
	}
}

class RealData2 implements Callable<String> {	//实现数据类
	private String para;	//声明成员变量
	RealData2(String para) {	//定义构造方法
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
