package java�߲���;
import java.util.concurrent.locks.LockSupport;
/**�߳�����������ʵ��*/
public class LockSupportTest implements Runnable{
	public static void main(String[] args) {
		Thread temp = new Thread(new LockSupportTest());	//�½��߳�
		temp.start();	//�����߳�
		try {
			Thread.sleep(1000);	//���߳�����һ��
		} catch (InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����ж���Ϣ
		}
		LockSupport.unpark(temp);	//�����߳�
	}
	public void run() {
		LockSupport.park();	//��������
		System.out.println(Thread.currentThread().getName()+" end!");	//����߳̽�����Ϣ
	}
}