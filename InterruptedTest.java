package java�߲���;
/**
 * �߳��ж�����
*/
public class InterruptedTest implements Runnable{
	public static void main(String[] args) {
		Thread thread = new Thread(new InterruptedTest());	//�½��߳�
		thread.start();	//�����߳�
		try {
			Thread.sleep(1000);	//���߳�����һ��
		}catch(InterruptedException e){}	//��׽�쳣
		thread.interrupt();	//�����ж��ź�
	}
	public void run() {
		System.out.println("thread start��");	//�����ʾ��Ϣ
		while(true) {
			if(Thread.currentThread().isInterrupted()) {	//����߳��յ��ж��ź�
				System.out.println("interrupted!");	//����ж���Ϣ
				try {
					Thread.sleep(1000L);	//����һ��
				} catch (InterruptedException e) {	//��׽�쳣
					System.out.println("sleep 1s");	//����һ��
				}
				System.out.println(Thread.currentThread().isInterrupted());	//����̵߳��ж��ź�
				break;	//����ѭ��
			}
			if(Thread.interrupted()) {	//����߳��յ��ж��ź�
				System.out.println(Thread.currentThread().isInterrupted());	//����ж��ź�
				System.out.println("continue");	//�����ʾ��Ϣ
				break;	//����ѭ��
			}
		}
	}
}
/*���ֲ�ѯ�жϵķ�ʽ��Ӧ��ͬ��ʵ�ʼ������ͬ�ģ����鱾�ϵ�������һ��*/