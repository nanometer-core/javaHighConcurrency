package java�߲���;
/**�̵߳����ȼ���������*/
public class ThreadPriorityTest implements Runnable{
	int value = 0;	//����value��Ա
	public static void main(String[] args) {
		for(int i = 1;i<10;i++) {	//ѭ���Ŵ�
			Thread temp = new Thread(new ThreadPriorityTest());	//�½��߳�
			temp.setPriority(i);	//�������ȼ�
			temp.start();	//�����߳�
		}
	}
	public void run() {
		System.out.println(Thread.currentThread().getId()+" start!");	//����߳�������Ϣ
		while(value<100) {	//���value��ֵС��һ��
			value++;	//value��ֵ����
		}
		System.out.println(Thread.currentThread().getId()+" end!");	//����߳�ֹͣ��Ϣ
	}
}
