package java�߲���;
import java.util.HashMap;
/**HashMap�ĸ߲�������ȫ��������*/
public class HashMapTest implements Runnable{
	static private HashMap<Integer, String>list = new HashMap<Integer, String>();	//����list��Ա
	private int start,end;	//������ʼ�ͽ�����
	public HashMapTest(int start,int end) {	//���幹�췽��
		super();	//���ø���Ĺ��췽��
		this.start=start;	//����������
		this.end=end;	//���������
	}
	public static void main(String[] args) {
		Thread temp1 = new Thread(new HashMapTest(0,100000));	//�½����㿪ʼ��ʮ��������߳�
		Thread temp2 = new Thread(new HashMapTest(100000,200000));	//�½���ʮ��ʼ����ʮ��������߳�
		temp1.start();	//������һ���߳�
		temp2.start();	//�����ڶ����߳�
		try {
			temp1.join();	//�ȴ���һ���߳̽���
			temp2.join();	//�ȴ��ڶ����߳̽���
		}catch(InterruptedException e) {	//��׽�쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
		System.out.println(list.size());	//���list�Ĵ�С
	}
	public void run() {
		for(int i = start;i<end;i++) {	//�ӿ�ʼ������������ѭ��
			list.put(i, String.valueOf(i));	//���Ԫ��
		}
	}
}
