package java�߲���;
import java.util.ArrayList;
/**����ArrayList���̲߳���ȫ*/
public class ArrayListTest implements Runnable{
	static ArrayList<Integer> list = new ArrayList<Integer>();	//����list��Ա
	public static void main(String[] args) {
		Thread thread1 = new Thread(new ArrayListTest());	//�½��߳�1
		Thread thread2 = new Thread(new ArrayListTest());	//�½��߳�2
		thread1.start();	//�����߳�1
		thread2.start();	//�����߳�2
		try {
			thread1.join();	//�ȴ��߳�1����
			thread2.join();	//�ȴ��߳�2����
		}catch(InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
		System.out.println(list.size());	//���list�Ĵ�С
	}
	public void run() {
		for(int i = 0;i<10000;i++) {	//ѭ��һ���
			list.add(i);	//��list���Ԫ��
		}
	}
}
