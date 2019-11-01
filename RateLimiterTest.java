package java高并发;
import com.google.common.util.concurrent.RateLimiter;
public class RateLimiterTest implements Runnable {
	static RateLimiter limiter = RateLimiter.create(2);	//实例化令牌桶
	public static void main(String[] args) {
		for(int i = 0;i<20;i++) {	//循环二十次
			new Thread(new RateLimiterTest()).start();	//实例化线程并启动
		}
	}
	public void run() {
		limiter.acquire();	//获取令牌
		limiter.tryAcquire(); //立即返回的获取令牌
		System.out.println(System.currentTimeMillis());	//输出系统时间
	}
}