package java�߲���;
/**
 *�����̺߳ͽ����̲߳�������
*/
public class ThreadTest implements Runnable{
	public static void main(String[] args) {
		Thread thread = new Thread(new ThreadTest());	//�½��߳�
		thread.start();	//�����߳�
		try {
			Thread.sleep(1000);	//���߳�����һ��
		} catch (InterruptedException e) {}	//��׽�쳣
		thread.stop();	//ǿ��ֹͣ�߳�
	}
	public void run() {	//�̷߳���
		System.out.println("success!");	//�����ʾ��Ϣ
		while(true);	//��������ѭ��
	}
}
