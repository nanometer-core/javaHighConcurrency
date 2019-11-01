package java�߲���;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class CallbackTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));	//�½����������̳߳�
		ListenableFuture<String> task = service.submit(new RealData2("x"));	//�ύ�����ȡ��Լ
		service.shutdown();	//ֹͣ�ύ
		/*task.addListener(new Runnable() {	//��Ӽ����¼�
			public void run() {	//���ü����¼�ҵ��
				System.out.println("�첽����ɹ�");	//�����ʾ��Ϣ
				try {
					System.out.println(task.get());	//������
				}catch(Exception e) {	//��׽�쳣
					e.printStackTrace();	//����쳣
				}
			}
		}, MoreExecutors.directExecutor());*/
		Futures.addCallback(task, new FutureCallback<String>() {	//��ӻص��¼�
			public void onSuccess(String result) {	//�ɹ��¼�
				System.out.println("�첽����ɹ�,result = " + result);	//�������
			}
			public void onFailure(Throwable t) {	//ʧ���¼�
				System.out.println("�첽����ʧ�ܣ�e = " + t);	//����쳣
			}
		});
		System.out.println("main task done....");	//�����ʾ��Ϣ
		Thread.sleep(3000);	//����
	}
}

class RealData2 implements Callable<String> {	//ʵ��������
	private String para;	//������Ա����
	RealData2(String para) {	//���幹�췽��
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
