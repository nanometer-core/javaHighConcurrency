package java�߲���;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ThreadLocalTest implements Runnable {
	static final int ThreadCount = 10;	//�����߳�����
	static ThreadLocal<Random> local = new ThreadLocal<Random>();	//�����ֲ߳̾�����
	static CountDownLatch jobs = new CountDownLatch(ThreadCount);	//������������
	static Random rand = new Random();	//�����������
	private boolean useThreadLocal;	//����ʹ�þֲ�������־
	public ThreadLocalTest(boolean useThreadLoacl) {	//���幹�췽��
		this.useThreadLocal=useThreadLoacl;	//�����Ƿ�ʹ�þֲ���������Ϣ
	}
	public static void main(String[] args) {	//�������ṩ���̿���
		long startTime = System.currentTimeMillis();	//���濪ʼʱ��
		for(int i = 0;i<ThreadCount;i++) {	//ѭ������ָ���������߳�
			new Thread(new ThreadLocalTest(false)).start();	//������ʹ�þֲ��������߳�
		}
		try {
			jobs.await();	//�ȴ��߳̽���
		} catch (InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
		System.out.println("use time:"+(System.currentTimeMillis()-startTime)+"ms");	//����ܺ�ʱ
		startTime = System.currentTimeMillis();	//��¼��ʼʱ��
		jobs=new CountDownLatch(ThreadCount);	//���ü�����
		for(int i = 0;i<ThreadCount;i++) {	//����ָ���������߳�
			new Thread(new ThreadLocalTest(true)).start();	//�����߳�
		}
		try {
			jobs.await();	//�ȴ��߳̽���
		} catch (InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
		System.out.println("use time:"+(System.currentTimeMillis()-startTime)+"ms");	//������ܺ�ʱ
	}
	public void run() {	//ʵ��runnable�ӿ�
		Random rand;	//�������������
		if(useThreadLocal) {	//���ʹ�þֲ�����
			if(local.get()==null) {	//����ֲ�����û�б�����
				local.set(new Random());	//�����ֲ�����
			}
			rand = local.get();	//��ȡ�ֲ�����
		}
		else {	//�����ʹ�þֲ�����
			rand = ThreadLocalTest.rand;	//����ȫ�ֱ���
		}
		for(int i = 0;i<10000000;i++) {	//ѭ������
			rand.nextInt();	//ȡ��һ�������
		}
		jobs.countDown();	//��������һ
	}
}
