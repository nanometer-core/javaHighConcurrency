package java�߲���;
import com.google.common.util.concurrent.RateLimiter;
public class RateLimiterTest implements Runnable {
	static RateLimiter limiter = RateLimiter.create(2);	//ʵ��������Ͱ
	public static void main(String[] args) {
		for(int i = 0;i<20;i++) {	//ѭ����ʮ��
			new Thread(new RateLimiterTest()).start();	//ʵ�����̲߳�����
		}
	}
	public void run() {
		limiter.acquire();	//��ȡ����
		limiter.tryAcquire(); //�������صĻ�ȡ����
		System.out.println(System.currentTimeMillis());	//���ϵͳʱ��
	}
}