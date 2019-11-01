package java高并发;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerTest {
	static final int ConsumerCount = 3;	//设置消费者数量
	static final int ProducerCount = 3;	//设置生产者数量
	static BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();	//声明缓冲队列
	public static void main(String[] args) throws InterruptedException {	//主方法提供流程控制
		ExecutorService threadPool = Executors.newFixedThreadPool(ProducerCount+ConsumerCount);	//实例化线程池
		for(int i = 0;i<ProducerCount;i++) {	//建立生产者
			threadPool.execute(new Producer(queue));	//实例化生产者
		}
		for(int i = 0;i<ConsumerCount;i++) {	//建立消费者
			threadPool.execute(new Consumer(queue));	//实例化消费者
		}
		threadPool.shutdown();	//停止线程增加
		Thread.sleep(50);	//休眠
		Producer.stopAll();	//停止所有生产者
		Thread.sleep(10);	//休眠
	}
}

class Producer implements Runnable {
	private BlockingQueue<Integer> queue;	//保存缓冲队列
	static boolean stop = false;	//声明结束标记
	static AtomicInteger count = new AtomicInteger(0);	//声明原子整数
	Producer(BlockingQueue<Integer> queue){	//定义构造方法
		this.queue=queue;	//保存缓冲队列
	}
	public void run() {
		while(!stop) {	//检查标记
			int tesk = count.incrementAndGet();	//获取下一个整数
			queue.offer(tesk);	//提交任务
			System.out.println("tesk-"+tesk+" submit success!");	//输出提示信息
		}
	}
	public static void stopAll() {	//停止所有生产者
		Consumer.stopAll();	//通知消费者停止
		stop=true;	//设置停止标记
	}
}

class Consumer implements Runnable {
	private BlockingQueue<Integer> queue;	//保存缓冲队列
	static boolean stop = false;	//声明停止标记
	Consumer(BlockingQueue<Integer> queue){	//定义构造方法
		this.queue=queue;	//保存缓存队列
	}
	public void run() {
		while((stop==false)||(queue.size()>0)) {	//检查队列是否为空且停止标记是否有效
			int value;	//保存任务值
			try {
				value = queue.take();	//获取任务
			} catch (InterruptedException e) {	//捕捉中断异常
				return;	//线程退出
			}
			System.out.println("tesk-"+value+" dispose over!");	//输出提示信息
		}
	}
	public static void stopAll() {	//设置停止标记
		stop=true;	//设置停止标记
	}
}