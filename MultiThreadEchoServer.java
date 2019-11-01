package java�߲���;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadEchoServer {
	private static ExecutorService tp = Executors.newCachedThreadPool();	//ʵ����һ���̳߳�
	static class HandleMsg implements Runnable {	//�½��ͻ���
		Socket clientSocket;	//����һ���׽���
		public HandleMsg(Socket clientSocket) {	//���幹�췽��
			this.clientSocket=clientSocket;	//����ͻ����׽���
		}
		public void run() {
			BufferedReader is = null;	//����һ��������
			PrintWriter os = null;	//����һ�������
			try {
				is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	//ʵ����������
				os = new PrintWriter(clientSocket.getOutputStream(),true);	//ʵ���������
				String inputLine = null;	//����һ���ַ���
				long b = System.currentTimeMillis();	//���濪ʼʱ��
				while((inputLine = is.readLine())!=null) {	//��ȡ�ַ���
					os.println(inputLine);	//����ȡ���ַ�������������
				}
				long e = System.currentTimeMillis();	//�������ʱ��
				System.out.println("spend:"+(e-b)+"ms");	//���ʱ����
			}catch(IOException e) {	//��׽�쳣
				e.printStackTrace();	//����쳣��Ϣ
			}finally {	//���
				try {
					if(is!=null)is.close();	//�����������Ϊ����ر�������
					if(os!=null)os.close();	//����������Ϊ����ر������
					clientSocket.close();	//�ر��׽���
				}catch(IOException e) {	//��׽�쳣
					e.printStackTrace();	//����쳣��Ϣ
				}
			}
		}
	}
	public static void main(String[] args) {
		ServerSocket echoServer = null;	//���������׽���
		Socket clientSocket = null;	//�����׽���
		try {
			echoServer = new ServerSocket(8000);	//�󶨼����׽���
		}catch(IOException e) {	//��׽�쳣
			e.printStackTrace();	//����쳣
		}
		tp.execute(new NIOTest());	//�����ͻ���
		while(true) {
			try {
				clientSocket = echoServer.accept();	//��ȡ�����ӵ��׽���
				System.out.println(clientSocket.getRemoteSocketAddress()+" connect!");	//���������Ϣ
				tp.execute(new HandleMsg(clientSocket));	//�����ͻ������߳�
			}catch(IOException e) {	//��׽�쳣
				e.printStackTrace();	//����쳣
			}
		}
	}
}
class Test34 implements Runnable{
	public void run() {
		Socket client = null;	//�����׽���
		PrintWriter writer = null;	//���������
		BufferedReader reader = null;	//����������
		try {
			client = new Socket();	//�½��׽���
			client.connect(new InetSocketAddress("localhost", 8000));	//���ӵ�ָ����ַ
			writer = new PrintWriter(client.getOutputStream(),true);	//ʵ���������
			writer.println("Hello!");	//�����Ϣ
			writer.flush();	//��������
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));	//ʵ����������
			System.out.println("form server: "+reader.readLine());	//�������������Ϣ
		}catch(UnknownHostException e) {	//��׽δ֪��ַ�쳣
			e.printStackTrace();	//����쳣��Ϣ
		}catch(IOException e) {	//��׽��������쳣
			e.printStackTrace();	//����쳣��Ϣ
		}finally {
			if(writer!=null)writer.close();	//����������Ϊ����ر������
			try {
				if(reader!=null)reader.close();	//�����������Ϊ����ر�������
				if(client!=null)client.close();	//����׽��ֲ�Ϊ����ر��׽���
			} catch (IOException e) {	//��׽��������쳣
				e.printStackTrace();	//����쳣��Ϣ
			}
		}
	}
}