package java�߲���;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**���Զ�д������*/
public class ReentrantReadWriteLockTest implements Runnable{
	static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);	//ʵ����������
	static ReadLock read = lock.readLock();	//��ȡ����
	static WriteLock write = lock.writeLock();	//��ȡд��
	static LinkedList<Integer> list = new LinkedList<Integer>();	//����list��Ա
	public static void main(String[] args) {
		list.add(1);	//��1д��list
		list.add(2);	//��2д��list
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			new Thread(new ReentrantReadWriteLockTest()).start();	//ʵ�����̲߳�����
		}
		try {
			Thread.sleep(2000);	//��������
		} catch (InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
		for(Object i:list.toArray()) {	//����list
			System.out.printf((Integer)i+" ");	//�������Ԫ��
		}
	}
	public void run() {
		read.lock();	//��ȡ����
		int i = 0;	//�����ƴα���i
		for(Object c:list.toArray()) {	//����list
			i+=(Integer)c;	//ͳ����ֵ
		}
		read.unlock();	//�ͷŶ���
		write.lock();	//��ȡд��
		list.add(i);	//д��ͳ����
		write.unlock();	//�ͷ�д��
		System.out.println(i);	//���ͳ����
	}
}
