package java�߲���;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/**����ѭ��դ������*/
public class CyclicBarrierTest implements Runnable{
	static CyclicBarrier barrier = new CyclicBarrier(10,new Runnable() {	//ʵ����ѭ��դ��
		public void run() {
			System.out.println("thread over!");	//���������Ϣ
		}
	});
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			try {
				Thread.sleep(500);	//���߰�����
			}catch(InterruptedException e) {	//��׽�ж��쳣
				e.printStackTrace();	//����쳣��Ϣ
			}
			new Thread(new CyclicBarrierTest()).start();	//ʵ�����̲߳�����
		}
	}
	public void run() {
		try {
			Thread.sleep((long) (Math.random()*1999+1));	//���������������
			System.out.println("thread "+Thread.currentThread().getName()+" end!");	//����߳̽���
			barrier.await();	//ѭ��դ������ ��������ǰ�߳�ֱ����ʼ��һ��ѭ��
		}catch(InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}catch(BrokenBarrierException e) {	//��׽�����쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
	}
}
