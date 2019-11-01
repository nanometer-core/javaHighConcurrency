package java�߲���;
import java.util.concurrent.CountDownLatch;
/**���Ե�����������*/
public class CountDownLatchTest implements Runnable{
	static CountDownLatch count = new CountDownLatch(10);	//ʵ������������
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			new Thread(new CountDownLatchTest()).start();	//ʵ�����̲߳�����
		}
		try {
			count.await();	//���߳��ڼ������ϵȴ�
		} catch (InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
		System.out.println("thread over");	//������߳̽�����Ϣ
	}
	public void run() {
		try {
			Thread.sleep((long) (Math.random()*999+1));	//�������һ�뵽һ����
		}catch(InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����ж��쳣
		}finally {	//���
			count.countDown();	//��������һ
		}
		System.out.println("thread "+Thread.currentThread().getName()+" end");	//����߳̽�����Ϣ
	}
}
