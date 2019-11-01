package java�߲���;

public class MyFutureTest {
	public static void main(String[] args) {
		Client client = new Client();	//�½�������
		Data data = client.request("name");	//�����෢������
		System.out.println("������ϣ�");	//�����ʾ��Ϣ
		try {
			Thread.sleep(2000);	//��������
		}catch(InterruptedException e) {}	//��׽�ж��쳣
		System.out.println("����="+data.getResult());		//������
	}

}

class FutureData implements Data{	//������Լ��
	protected RealData realData = null;	//����������
	protected boolean isReady = false;	//��������
	public synchronized void setRealData(RealData realData) {	//��������
		if(isReady) {	//������Ƿ�������
			return;	//����
		}
		this.realData=realData;	//��������
		isReady = true;	//���ñ��
		notifyAll();	//����ȫ���ȴ����߳�
	}
	public synchronized String getResult() {	//��ȡ���
		while(!isReady) {	//�������û��׼����
			try {
				wait();	//�ڵ�ǰ�����ϵȴ�
			}catch(InterruptedException e) {}	//��׽�ж��쳣
		}
		return realData.result;	//����׼���õ�����
	}
}

class RealData implements Data {	//����������
	protected final String result;	//�����������
	RealData(String para) {	//��������
		StringBuffer sb = new StringBuffer();	//�½��ַ�������
		for(int i = 0;i<10;i++) {	//ѭ��ʮ��
			sb.append(para);	//�����������ַ���
			try {
				Thread.sleep(100);	//�������һ��
			}catch(InterruptedException e) {	//��׽�ж��쳣
			}
		}
		result = sb.toString();	//���ý������
	}
	public String getResult() {	//��ȡ����
		return result;	//���ؽ������
	}
}

class Client {	//����������
	public Data request(final String queryStr) {	//ʵ�����󷽷�
		final FutureData future = new FutureData();	//������Լ
		new Thread() {	//ʵ�����µ��߳�
			public void run() {	//��д�̷߳���
				RealData realData = new RealData(queryStr);	//��������
				future.setRealData(realData);	//��������
			}
		}.start();	//�����߳�
		return future;	//������Լ
	}
}

interface Data{	//������Լ�ӿ�
	public String getResult();	//����ӿڷ���
}