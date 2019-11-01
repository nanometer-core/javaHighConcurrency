package java�߲���;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerTest {
	static final int ConsumerCount = 3;	//��������������
	static final int ProducerCount = 3;	//��������������
	static BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();	//�����������
	public static void main(String[] args) throws InterruptedException {	//�������ṩ���̿���
		ExecutorService threadPool = Executors.newFixedThreadPool(ProducerCount+ConsumerCount);	//ʵ�����̳߳�
		for(int i = 0;i<ProducerCount;i++) {	//����������
			threadPool.execute(new Producer(queue));	//ʵ����������
		}
		for(int i = 0;i<ConsumerCount;i++) {	//����������
			threadPool.execute(new Consumer(queue));	//ʵ����������
		}
		threadPool.shutdown();	//ֹͣ�߳�����
		Thread.sleep(50);	//����
		Producer.stopAll();	//ֹͣ����������
		Thread.sleep(10);	//����
	}
}

class Producer implements Runnable {
	private BlockingQueue<Integer> queue;	//���滺�����
	static boolean stop = false;	//�����������
	static AtomicInteger count = new AtomicInteger(0);	//����ԭ������
	Producer(BlockingQueue<Integer> queue){	//���幹�췽��
		this.queue=queue;	//���滺�����
	}
	public void run() {
		while(!stop) {	//�����
			int tesk = count.incrementAndGet();	//��ȡ��һ������
			queue.offer(tesk);	//�ύ����
			System.out.println("tesk-"+tesk+" submit success!");	//�����ʾ��Ϣ
		}
	}
	public static void stopAll() {	//ֹͣ����������
		Consumer.stopAll();	//֪ͨ������ֹͣ
		stop=true;	//����ֹͣ���
	}
}

class Consumer implements Runnable {
	private BlockingQueue<Integer> queue;	//���滺�����
	static boolean stop = false;	//����ֹͣ���
	Consumer(BlockingQueue<Integer> queue){	//���幹�췽��
		this.queue=queue;	//���滺�����
	}
	public void run() {
		while((stop==false)||(queue.size()>0)) {	//�������Ƿ�Ϊ����ֹͣ����Ƿ���Ч
			int value;	//��������ֵ
			try {
				value = queue.take();	//��ȡ����
			} catch (InterruptedException e) {	//��׽�ж��쳣
				return;	//�߳��˳�
			}
			System.out.println("tesk-"+value+" dispose over!");	//�����ʾ��Ϣ
		}
	}
	public static void stopAll() {	//����ֹͣ���
		stop=true;	//����ֹͣ���
	}
}