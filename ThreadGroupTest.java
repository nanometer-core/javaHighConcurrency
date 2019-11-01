package java�߲���;
/**�߳���Ĳ�������*/
public class ThreadGroupTest implements Runnable{
	public static void main(String[] args) {
		ThreadGroup group = new ThreadGroup("group");	//�½��߳���
		for(int i = 0;i<3;i++) {	//ѭ������
			new Thread(group, new ThreadGroupTest()).start();	//�½���ָ����������̲߳�����
		}
		System.out.println("count:"+group.activeCount());	//����߳������̵߳�����
		group.interrupt();	//�߳��鷢���ж��ź�
	}
	public void run() {
		while(true) {
			System.out.println("thread "+Thread.currentThread().getId()+" start");	//����߳�������Ϣ
			if(Thread.currentThread().isInterrupted()) {	//�ж��Ƿ����ж��ź�
				System.out.println("thread "+Thread.currentThread().getId()+" exit");	//����߳̽�����Ϣ
				break;	//��������ѭ��
			}
		}
	}
}
