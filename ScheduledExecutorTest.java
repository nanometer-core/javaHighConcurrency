package java�߲���;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest implements Runnable{
	static ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);	//�½�һ���ӳ��̳߳�
	public static void main(String[] args) {
		//threadPool.schedule(new Test23(), 500, TimeUnit.MILLISECONDS);	//��ָ��ʱ���ִ��һ��
		//threadPool.scheduleAtFixedRate(new Test23(), 500, 500, TimeUnit.MILLISECONDS);	//��ָ�������ִ��һ��
		threadPool.scheduleWithFixedDelay(new ScheduledExecutorTest(), 500, 500, TimeUnit.MILLISECONDS);	//���ӳ�ָ��ʱ���ִ��һ��
	}
	public void run() {
		System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());	//����߳����͵�ǰϵͳʱ��
		try {
			Thread.sleep(100);	//�������һ��
		}catch(InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
	}
}
