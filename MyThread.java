package java¸ß²¢·¢;

import java.util.concurrent.CountDownLatch;

public class MyThread extends Thread {
	private Runnable job;
	private CountDownLatch count = null;
	public MyThread(Runnable job){
		this.job=job;
	}
	public MyThread(Runnable job,CountDownLatch count) {
		this(job);
		this.count=count;
	}
	public void run() {
		if(job==null) {
			System.out.println(Thread.currentThread().getName()+" : not have job!");
			return;
		}
		long startTime = System.currentTimeMillis();
		job.run();
		long timeout = System.currentTimeMillis()-startTime;
		System.out.println(Thread.currentThread().getName()+" exit timeout "+timeout+"ms");
		if(count!=null) {
			count.countDown();
		}
	}
}
