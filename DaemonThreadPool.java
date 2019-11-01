package java�߲���;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import com.google.common.util.concurrent.MoreExecutors;

public class DaemonThreadPool {
	public static void main(String[] args) {	//�������ṩ���̿���
		testDeamonPool();	//�����ػ��̳߳�
	}
	static void testDirectExecutor() {	//ʵ�ֲ������߳������е��̳߳�
		Executor executor = MoreExecutors.directExecutor();	//��ȡ�̳߳�
		executor.execute(()->System.out.println(Thread.currentThread().getName()));	//�ύ����
	}
	static void testDeamonPool() {	//ʵ�ֲ����ػ��̳߳�
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);	//�½��̶��̳߳ز�תΪһ���̳߳�
		MoreExecutors.getExitingExecutorService(executor);	//����Ϊ�ػ��̳߳�
		executor.execute(()->System.out.println(Thread.currentThread().getName()));	//�ύ����
	}
}
