package java�߲���;
import java.util.concurrent.locks.ReentrantLock;

/**������������������*/
public class ReentrantLockTest implements Runnable{
	static ReentrantLock lock = new ReentrantLock(); //������������Ա����ѡ�����Ƿ�ƽ
	public static void main(String[] args) {
		for(int i = 0;i<100;i++) {	//ѭ��һ�ٴ�
			new Thread(new ReentrantLockTest()).start();	//�½�������һ���߳�
		}
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getId()+" start!");	//����߳�������Ϣ
		lock.lock();	//��ȡ��
		//lock.lockInterruptibly();	//��ȡ������Ӧ�жϵ���
		//lock.tryLock(3, TimeUnit.SECONDS);	//��һ��ʱ���ڳ��Ի�ȡ��
		try {
			Thread.sleep(100);	//����0.1��
		}catch(InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
		finally {	//�쳣��β����
			lock.unlock();	//�ͷ���
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end!");	//����̵߳Ľ�����Ϣ
	}
}
