package java�߲���;
/**volatile�ؼ�������ָ������*/
public class VolatileTest implements Runnable{
	volatile long a;	//�����ͳ����ͱ���a
	public static void main(String[] args) {
		new Thread(new VolatileTest()).start();	//�½��������߳�
	}
	public void run() {
		a=0xffff;	//�ı���ֵ
	}
}
