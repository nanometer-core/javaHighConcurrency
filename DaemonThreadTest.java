package java�߲���;
/**�����ػ��̵߳�ʹ������*/
public class DaemonThreadTest implements Runnable{
	public static void main(String[] args) {
		Thread thread = new Thread(new DaemonThreadTest());	//�½��߳�
		thread.setDaemon(true);	//����Ϊ�ػ��߳�
		thread.start();	//�����߳�����
		try {
			Thread.sleep(1000);	//���߳�����һ��
		}catch(InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
	}
	public void run() {
		while(true) {
			System.out.println("DaemonThread start!");	//����߳�������Ϣ
		}
	}
}
