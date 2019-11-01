package java�߲���;
/**���Եȴ��ͻ��ѵ�ʵ��*/
public class WaitTest implements Runnable{
	static Object main = new Object();	//�½�����
	public static void main(String[] args) {
		for(int i = 0;i<3;i++) {	//ѭ������
			new Thread(new WaitTest()).start();	//�½��������߳�
		}
		for(int i = 0;i<3;i++) {	//ѭ������
			try {
				Thread.sleep((long) (Math.random()*2000));	//�漴������������
			} catch (InterruptedException e) {	//��׽�쳣
				e.printStackTrace();	//�����쳣
			}
			synchronized(main) {	//��ȡʵ���ĵ���
				main.notify();	//�ڻ���һ����ʵ���ϵȴ��Ķ���
			}
		}
	}
	public void run() {
		System.out.println("thread start!");	//�����ʼ��Ϣ
		System.out.println("set wait!");	//����ȴ���Ϣ
		synchronized(main) {	//��ȡʵ������
			try{
				main.wait();	//��ʵ���ϵȴ�
			}
			catch (InterruptedException e) {	//��׽�쳣
				System.out.println(e.getMessage());	//����쳣��Ϣ
			}
		}
		System.out.println("thread "+Thread.currentThread().getId()+" end!");	//����߳̽�����Ϣ
	}
}
