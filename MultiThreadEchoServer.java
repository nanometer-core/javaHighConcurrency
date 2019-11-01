package java高并发;

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
	private static ExecutorService tp = Executors.newCachedThreadPool();	//实例化一个线程池
	static class HandleMsg implements Runnable {	//新建客户端
		Socket clientSocket;	//声明一个套接字
		public HandleMsg(Socket clientSocket) {	//定义构造方法
			this.clientSocket=clientSocket;	//保存客户端套接字
		}
		public void run() {
			BufferedReader is = null;	//声明一个输入流
			PrintWriter os = null;	//声明一个输出流
			try {
				is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	//实例化输入流
				os = new PrintWriter(clientSocket.getOutputStream(),true);	//实例化输出流
				String inputLine = null;	//声明一个字符串
				long b = System.currentTimeMillis();	//保存开始时间
				while((inputLine = is.readLine())!=null) {	//读取字符串
					os.println(inputLine);	//将读取的字符串输出到输出流
				}
				long e = System.currentTimeMillis();	//保存结束时间
				System.out.println("spend:"+(e-b)+"ms");	//输出时间间隔
			}catch(IOException e) {	//捕捉异常
				e.printStackTrace();	//输出异常信息
			}finally {	//最后
				try {
					if(is!=null)is.close();	//如果输入流不为空则关闭输入流
					if(os!=null)os.close();	//如果输出流不为空则关闭输出流
					clientSocket.close();	//关闭套接字
				}catch(IOException e) {	//捕捉异常
					e.printStackTrace();	//输出异常信息
				}
			}
		}
	}
	public static void main(String[] args) {
		ServerSocket echoServer = null;	//声明监听套接字
		Socket clientSocket = null;	//声明套接字
		try {
			echoServer = new ServerSocket(8000);	//绑定监听套接字
		}catch(IOException e) {	//捕捉异常
			e.printStackTrace();	//输出异常
		}
		tp.execute(new NIOTest());	//启动客户端
		while(true) {
			try {
				clientSocket = echoServer.accept();	//获取新连接的套接字
				System.out.println(clientSocket.getRemoteSocketAddress()+" connect!");	//输出连接信息
				tp.execute(new HandleMsg(clientSocket));	//启动客户连接线程
			}catch(IOException e) {	//捕捉异常
				e.printStackTrace();	//输出异常
			}
		}
	}
}
class Test34 implements Runnable{
	public void run() {
		Socket client = null;	//声明套接字
		PrintWriter writer = null;	//声明输出流
		BufferedReader reader = null;	//声明输入流
		try {
			client = new Socket();	//新建套接字
			client.connect(new InetSocketAddress("localhost", 8000));	//连接到指定地址
			writer = new PrintWriter(client.getOutputStream(),true);	//实例化输出流
			writer.println("Hello!");	//输出信息
			writer.flush();	//清空输出流
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));	//实例化输入流
			System.out.println("form server: "+reader.readLine());	//输出输入流的信息
		}catch(UnknownHostException e) {	//捕捉未知地址异常
			e.printStackTrace();	//输出异常信息
		}catch(IOException e) {	//捕捉输入输出异常
			e.printStackTrace();	//输出异常信息
		}finally {
			if(writer!=null)writer.close();	//如果输出流不为空则关闭输出流
			try {
				if(reader!=null)reader.close();	//如果输入流不为空则关闭输入流
				if(client!=null)client.close();	//如果套接字不为空则关闭套接字
			} catch (IOException e) {	//捕捉输入输出异常
				e.printStackTrace();	//输出异常信息
			}
		}
	}
}