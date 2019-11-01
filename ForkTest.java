package java高并发;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkTest extends RecursiveTask<Integer>{	//继承带返回值的任务类
	private static final int MaxCount=100;	//定义最大处理数
	private int start;	//声明起始量
	private int end;	//声明终止量
	public ForkTest(int start,int end) {	//定义构造方法
		this.start=start;	//保存起始量
		this.end=end;	//保存终止量
	}
	public static void main(String[] args) {	//主方法提供流程控制
		ForkJoinPool threadPool = new ForkJoinPool();	//新建分而治之线程池
		ForkTest job = new ForkTest(1, 1000);	//实例化本类
		ForkJoinTask<Integer> result = threadPool.submit(job);	//提交任务
		try {
			int value = result.get();	//获取结果
			System.out.println("sum="+value);	//输出结果
		}catch(InterruptedException e) {	//捕捉中断异常
			e.printStackTrace();	//输出异常信息
		}catch(ExecutionException e) {	//捕捉线程异常
			e.printStackTrace();	//输出异常信息
		}
	}
	public Integer compute() {	//重写执行方法
		int sum=0;	//声明统计变量
		if(end-start<=100) {	//如果在能力范围内
			for(int i = start;i<=end;i++) {	//统计范围量
				sum+=i;	//加入统计量
			}
		}
		else {	//如果超出能力范围
			int jobCount = (end-start)/100+1;	//计算分多少批次
			ArrayList<ForkTest> jobs = new ArrayList<ForkTest>(jobCount);	//新建分解任务组
			int endValue = this.start-1;	//如果初始化开始量
			for(int i = 0;i<jobCount;i++) {	//循环批次数
				ForkTest temp = new ForkTest(endValue+1,(endValue+100)>end?end:(endValue+100));	//计算每一个任务的起始量与终止量
				endValue+=100;	//开始量后移
				jobs.add(temp);	//添加到任务组
				temp.fork();	//分解任务
			}
			for(ForkTest t:jobs) {	//遍历任务组
				sum += t.join();	//获取每一个任务的返回值
			}
		}
		return sum;	//返回最终结果
	}
}
class info extends RecursiveAction{	//继承不带返回值的任务类
	protected void compute() {	//重写业务流程
		
	}
}