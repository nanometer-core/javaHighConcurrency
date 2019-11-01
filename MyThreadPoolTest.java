package java�߲���;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.*;

import java.util.concurrent.TimeUnit;

public class MyThreadPoolTest implements Runnable {
	static ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());	//����һ������̵߳����Ϊʮ���̵߳Ŀ����߳�����ʱ��Ϊ��ʮ���ֱ���ύ���е��̳߳�
	//static ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));	//�н����
	//static ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());	//�޽����
	//static ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());	//���ȶ���
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			threadPool.submit(new MyThreadPoolTest());	//�ύ����
		}
		threadPool.shutdown();	//ֹͣ�̳߳�
	}
	public void run() {
		System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());	//����߳����뵱ǰʱ��
		try {
			Thread.sleep(500);	//���߰���
		}catch(InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
	}
	private void info() {
		RejectedExecutionHandler a = new AbortPolicy();	//�׳��쳣
		RejectedExecutionHandler b = new CallerRunsPolicy();	//�ڵ�������ִ������
		RejectedExecutionHandler c = new DiscardOldestPolicy();	//�������ϵ�����
		RejectedExecutionHandler d = new DiscardPolicy();	//�����ύ������
	}
}
class Temp1 extends ThreadPoolExecutor implements ThreadFactory {
	public Temp1(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	public Thread newThread(Runnable r) {	//ʵ��threadFactory�ӿڵ��½��̷߳���
		return null;
	}
	protected void beforeExecute(Thread t, Runnable r) {	//��дThreadPoolExecutor������ִ��ǰ����
		super.beforeExecute(t, r);
	}
	protected void afterExecute(Runnable r, Throwable t) {	//��дThreadPoolExecutor������ִ�к����
		super.afterExecute(r, t);
	}
	protected void terminated() {	//��дThreadPoolExecutor�Ĺرղ���
		super.terminated();
	}
}
