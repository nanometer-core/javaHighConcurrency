package java�߲���;
/**���Է�������*/
public class SynchronizedTest implements Runnable{
	private static int value = 1;	//����value��Ա
	private static Object a = new Object();	//ʵ��������
	synchronized private void setValue(int value){	//����value
		SynchronizedTest.value=value;	//����value
	}
	synchronized public int getValue() {	//��ȡvalue
		return SynchronizedTest.value;	//����value��ֵ
	}
	synchronized public void printValue() {	//���value ��ֵ
		System.out.println(value);	//���value��ֵ
	}
	public static void main(String[] args) {
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			new Thread(new SynchronizedTest()).start();	//�����߳�
		}
	}
	public void run() {
		for(int i = 0;i<100;i++) {	//ѭ��һ�ٴ�
			synchronized (a) {	//��ȡa����
				printValue();	//���value��ֵ
				setValue(getValue()+1);	//����value��ֵ
			}
		}
	}
}
