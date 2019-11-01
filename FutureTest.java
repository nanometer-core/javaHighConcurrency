package java�߲���;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<String> task = new FutureTask<String>(new RealData1("a"));	//ʵ������Լ��
		ExecutorService executor = Executors.newFixedThreadPool(1);	//�����̳߳�
		executor.submit(task);	//�ύ��Լ
		System.out.println("������ϣ�");	//�����ʾ��Ϣ
		try {
			Thread.sleep(2000);	//��������
		}catch(InterruptedException e) {}	//��׽�쳣
		System.out.println("���� = " + task.get());	//�������
	}
}

class RealData1 implements Callable<String> {	//ʵ��������
	private String para;	//������Ա����
	RealData1(String para) {	//���幹�췽��
		this.para=para;	//�������
	}
	public String call() throws Exception {	//ʵ�ֽӿڷ���
		StringBuffer sb = new StringBuffer();	//�½�������
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			sb.append(para);	//����ַ������ַ�������
			try {
				Thread.sleep(100);	//�������һ��
			}catch(InterruptedException e) {}	//��׽�쳣
		}
		return sb.toString();	//�����ַ���
	}
}