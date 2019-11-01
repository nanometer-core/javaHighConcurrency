package java�߲���;
import java.util.concurrent.Semaphore;
/**�����ź�������*/
public class SemaphoreTest implements Runnable{
	static Semaphore flag = new Semaphore(3, true);	//�½��ź����࣬����׼�������Ƿ�ƽ
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			new Thread(new SemaphoreTest()).start();	//ʵ�����̲߳�����
		}
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getId()+" start!");	//����߳̿�ʼ��Ϣ
		//flag.acquireUninterruptibly();	//����Ӧ�жϵĻ�ȡ���
		try {
			flag.acquire();	//��ȡ���
			//flag.tryAcquire(3, TimeUnit.SECONDS);	//��һ��ʱ���ڻ�ȡ���
			Thread.sleep(1000);	//����һ��
		} catch (InterruptedException e) {	//��׽�쳣
			e.printStackTrace();	//����쳣
		}finally {	//���
			flag.release();	//�ͷ����
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end!");	//����߳̽�����Ϣ
	}
}
