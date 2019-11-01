package java�߲���;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest implements Runnable {
	static final int THREAD_COUNT = 10;	//�����������߳���
	static AtomicStampedReference<Object> object = new AtomicStampedReference<Object>(new Object(), 0);	//��ʱ�����cas����
	static AtomicInteger value = new AtomicInteger(0);	//cas���α���
	static CountDownLatch count = new CountDownLatch(THREAD_COUNT);	//������������
	volatile int a;
	AtomicIntegerFieldUpdater<AtomicStampedReferenceTest> upDater = AtomicIntegerFieldUpdater.newUpdater(AtomicStampedReferenceTest.class, "count");	//������ͨ������cas����
	public static void main(String[] args) {	//�������ṩ���̿���
		for(int i = 0;i<THREAD_COUNT;i++) {	//ѭ�����������߳�
			new MyThread(new AtomicStampedReferenceTest(),count).start();
		}
		try {
			count.await();	//�ڼ������ϵȴ�
		} catch (InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
		System.out.println(value.get());	//��ȡ����ֵ
	}
	public void run() {	//��дrun����
		for(int i = 0;i<100000;i++) {	//�㹻���ѭ��
			value.incrementAndGet();	//��һ����ȡ�µ�ֵ
		}
	}
}
