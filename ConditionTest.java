package java�߲���;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**���Եȴ���������*/
public class ConditionTest implements Runnable{
	static ReentrantLock lock = new ReentrantLock(true);	//������������Ա
	static Condition condition = lock.newCondition();	//��ȡ�������ĵȴ�����
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			new Thread(new ConditionTest()).start();	//ʵ�����̲߳�����
		}
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			lock.lock();	//��ȡ��
			condition.signal();	//����һ���ȴ����߳�
			//condition.signalAll();	//����ȫ���ȴ����߳�
			lock.unlock();	//�ͷ���
		}
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getId()+" start!");	//����߳̿�ʼ��Ϣ
		//condition.awaitUninterruptibly();	//����Ӧ�жϵĵȴ�
		lock.lock();	//��ȡ��
		try {
			condition.await();	//��ʼ�ȴ�
			//condition.await(3, TimeUnit.SECONDS);	//��һ��ʱ���ڵȴ�
			//condition.awaitUntil(new Date(new Date().getTime()));	//�ȴ���ָ��ʱ��
		} catch (InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}finally {	//���
			lock.unlock();	//�ͷ���
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end!");	//����߳̽�����Ϣ
	}

}
