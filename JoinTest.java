package java�߲���;
/**�����̵߳Ľ�����ǫ��*/
public class JoinTest implements Runnable{
	
	public static void main(String[] args) {
		Thread a = new Thread(new JoinTest());	//�½��߳�
		a.start();	//�����߳�
		try {
			a.join();	//���̵߳ȴ��߳̽���
		} catch (InterruptedException e) {	//��׽�ж��쳣
			System.out.println(e.getMessage());	//����쳣��Ϣ
		}
		System.out.println("exit!");	//����˳���ʾ
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getId()+" start");	//����߳�������Ϣ
		try {
			Thread.sleep(1000);	//����һ��
		} catch (InterruptedException e) {	//��׽�ж�
			System.out.println(e.getMessage());	//����쳣��Ϣ
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end");	//����߳̽�����Ϣ
		Thread.yield();	//�����߳���Դ
	}
}
