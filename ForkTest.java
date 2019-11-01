package java�߲���;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkTest extends RecursiveTask<Integer>{	//�̳д�����ֵ��������
	private static final int MaxCount=100;	//�����������
	private int start;	//������ʼ��
	private int end;	//������ֹ��
	public ForkTest(int start,int end) {	//���幹�췽��
		this.start=start;	//������ʼ��
		this.end=end;	//������ֹ��
	}
	public static void main(String[] args) {	//�������ṩ���̿���
		ForkJoinPool threadPool = new ForkJoinPool();	//�½��ֶ���֮�̳߳�
		ForkTest job = new ForkTest(1, 1000);	//ʵ��������
		ForkJoinTask<Integer> result = threadPool.submit(job);	//�ύ����
		try {
			int value = result.get();	//��ȡ���
			System.out.println("sum="+value);	//������
		}catch(InterruptedException e) {	//��׽�ж��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}catch(ExecutionException e) {	//��׽�߳��쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
	}
	public Integer compute() {	//��дִ�з���
		int sum=0;	//����ͳ�Ʊ���
		if(end-start<=100) {	//�����������Χ��
			for(int i = start;i<=end;i++) {	//ͳ�Ʒ�Χ��
				sum+=i;	//����ͳ����
			}
		}
		else {	//�������������Χ
			int jobCount = (end-start)/100+1;	//����ֶ�������
			ArrayList<ForkTest> jobs = new ArrayList<ForkTest>(jobCount);	//�½��ֽ�������
			int endValue = this.start-1;	//�����ʼ����ʼ��
			for(int i = 0;i<jobCount;i++) {	//ѭ��������
				ForkTest temp = new ForkTest(endValue+1,(endValue+100)>end?end:(endValue+100));	//����ÿһ���������ʼ������ֹ��
				endValue+=100;	//��ʼ������
				jobs.add(temp);	//��ӵ�������
				temp.fork();	//�ֽ�����
			}
			for(ForkTest t:jobs) {	//����������
				sum += t.join();	//��ȡÿһ������ķ���ֵ
			}
		}
		return sum;	//�������ս��
	}
}
class info extends RecursiveAction{	//�̳в�������ֵ��������
	protected void compute() {	//��дҵ������
		
	}
}