package java�߲���;
/**�����̵߳���ͣ���������*/
public class SuspendTest implements Runnable{
	static private SuspendTest main = new SuspendTest();	//ʵ��������
	public static void main(String[] args) {
		Thread a = new Thread(new SuspendTest());	//�½��߳�
		a.setName("will suspend");
		a.start();	//�����߳�
		try {
			Thread.sleep(1000);	//����һ��
		} catch (InterruptedException e) {	//��׽�ж��쳣
			System.out.println(e.getMessage());	//�����ʾ��Ϣ
		}
		new Thread(new SuspendTest()).start();	//�½���������һ���߳�
		a.suspend();	//��ͣ�߳�
		try {
			Thread.sleep(10000);	//����һ��
		} catch (InterruptedException e) {	//��׽�ж��쳣
			System.out.println(e.getMessage());	//�����ʾ��Ϣ
		}
		a.resume();	//����ִ��
	}
	public void run() {
		System.out.println("thread "+Thread.currentThread().getName()+" start");	//����߳�������Ϣ
		synchronized (main) {	//��ȡʵ������
			try {
				Thread.sleep(1000);	//����һ��
			}catch(InterruptedException e) {	//��׽�쳣
				System.out.println(e.getMessage());	//����쳣��Ϣ
			}
		}
		System.out.println("thread "+Thread.currentThread().getName()+" end");	//����߳̽�����Ϣ
	}
}
