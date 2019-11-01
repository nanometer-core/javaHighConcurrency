package java�߲���;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest implements Runnable {
	static ExecutorService threadPool = Executors.newFixedThreadPool(5);	//�½�һ���̶��������̳߳�
	//static ExecutorService threadPool = Executors.newSingleThreadExecutor();	//�½�һ��ֻ��һ���̵߳��̳߳�
	//static ExecutorService threadPool = Executors.newCachedThreadPool();	//�½�һ����̬�̳߳�
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			threadPool.submit(new ThreadPoolTest());	//�ύ����
		}
	}
	public void run() {
		System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());	//����߳������Լ���ǰʱ��
		try {
			Thread.sleep(500);	//���߰���
		}catch(InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
	}
}