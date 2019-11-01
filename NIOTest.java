package java高并发;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOTest implements Runnable{
	private Selector selector;	//声明管理类
	private static ExecutorService tp = Executors.newCachedThreadPool();	//声明线程池
	public static Map<Socket, Long> time_start = new HashMap<Socket, Long>(10240);	//声明map接口
	class EchoClient {	//声明内部类
		private LinkedList<ByteBuffer> outq;	//声明二进制数据类
		EchoClient() {	//定义构造方法
			outq = new LinkedList<ByteBuffer>();
		}
		public LinkedList<ByteBuffer> getOutputQueue() {	//获取输出队列
			return outq;	//返回队列
		}
		public void enqueue(ByteBuffer bb) {	//压栈
			outq.addFirst(bb);	//压入队列
		}
	}
	class HandleMsg implements Runnable{	//内部客户线程类
		SelectionKey sk;	//声明连接契约
		ByteBuffer bb;	//声明缓冲区
		public HandleMsg(SelectionKey sk,ByteBuffer bb) {	//定义构造方法
			this.sk=sk;	//保存连接契约
			this.bb=bb;	//保存缓冲区
		}
		public void run() {	//定义工作流程
			EchoClient echoClient = (EchoClient)sk.attachment();	//获取附件
			echoClient.enqueue(bb);	//向附件压栈
			sk.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);	//注册读与写事件
			selector.wakeup();	//通知管理
		}
	}
	public static void main(String[] args) {	//主函数提供流程控制
		NIOTest server = new NIOTest();	//新建服务器类
		tp.execute(server);	//启动服务器
		Test36 client = new Test36("localhost", 8000);	//新建客户端类
		tp.execute(client);	//启动客户端
	}
	public void run() {	//定义服务器的业务流程
		try {
			startServer();	//启动服务
		} catch (Exception e) {	//捕捉异常
			e.printStackTrace();	//输出异常
		}
	}
	private void startServer() throws Exception {	//定义如何启动服务
		selector = SelectorProvider.provider().openSelector();	//获取管理
		ServerSocketChannel ssc = ServerSocketChannel.open();	//打开监听
		ssc.configureBlocking(false);	//设置运行模式为非阻塞
		InetSocketAddress isa = new InetSocketAddress(8000);	//绑定端口
		ssc.socket().bind(isa);	//绑定监听端口
		SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);	//注册事件为请求
		while(true) {
			selector.select();	//等待注册的事件
			Set readyKeys = selector.selectedKeys();	//获取所有触发事件的类
			Iterator i = readyKeys.iterator();	//获取迭代器
			long e = 0;	//初始化事件为零
			while(i.hasNext()) {	//在迭代器不为零时循环
				SelectionKey sk = (SelectionKey) i.next();	//获取下一个类
				i.remove();	//将该类移出
				if(sk.isAcceptable()) {	//如果是请求
					doAccept(sk);	//处理请求
				}
				else if(sk.isValid() && sk.isReadable()) {	//如果是输入
					if(!time_start.containsKey(((SocketChannel)sk.channel()).socket())) {	//如果此前没有输入
						time_start .put(((SocketChannel)sk.channel()).socket(), System.currentTimeMillis());	//保存第一次输入的时间
						doRead(sk);	//进行输入处理
					}
				}
				else if(sk.isValid() && sk.isWritable()) {	//如果是输出
					doWrite(sk);	//进行输出处理
					e = System.currentTimeMillis();	//获取当前的时间
					long b = time_start.remove(((SocketChannel)sk.channel()).socket());	//移除开始时间
					System.out.println("spend:" + (e-b)+ "ms");	//计算延迟时间
				}
			}
		}
	}
	private void doAccept(SelectionKey sk) {	//定义如何建立连接
		ServerSocketChannel server = (ServerSocketChannel)sk.channel();	//获取监听
		SocketChannel clientchChannel;	//声明新的套接字
		try {
			clientchChannel = server.accept();	//同意请求并获取新的套接字
			clientchChannel.configureBlocking(false);	//设置运行模式为非阻塞模式
			SelectionKey clienKey = clientchChannel.register(selector, SelectionKey.OP_READ);	//注册读事件
			EchoClient echoClient = new EchoClient();	//新建一个附件
			clienKey.attach(echoClient);	//将附件附加到契约上
			InetAddress clientAddress = clientchChannel.socket().getInetAddress();	//获取客户地址
			System.out.println("Accepted connection from "+clientAddress.getHostAddress()+".");	//输出连接信息
		}catch(Exception e) {	//捕捉异常
			System.out.println("failed to accept new clilent.");	//输出提示信息
			e.printStackTrace();	//输出异常信息
		}
	}
	private void doRead(SelectionKey sk) {	//定义如何读取数据
		SocketChannel channel = (SocketChannel)sk.channel();	//获取连接类
		ByteBuffer bb = ByteBuffer.allocate(8192);	//新建缓冲空间
		int len;	//声明长度
		try {
			len = channel.read(bb);	//尝试读取数据
			if(len<0) {	//如果读取的数据长度不合理
				disconnect(sk);	//关闭连接
				return;	//返回
			}
		}catch(Exception e) {	//捕捉异常
			System.out.println("failed to read form client.");	//输出提示信息
			e.printStackTrace();	//输出异常信息
			disconnect(sk);	//关闭连接
			return;	//返回
		}
		bb.flip();	//重置读取位置
		tp.execute(new HandleMsg(sk,bb));	//启动客户线程
	}
	private void doWrite(SelectionKey sk) {	//定义如何写入数据
		SocketChannel channnel = (SocketChannel)sk.channel();	//获取连接类
		EchoClient echoClient = (EchoClient)sk.attachment();	//获取附件
		LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();	//获取输出队列
		ByteBuffer bb = outq.getLast();	//获取队列最后一个元素
		try {
			int len = channnel.write(bb);	//输出并保存输出的长度
			if(len==-1) {	//如果长度不合理
				disconnect(sk);	//关闭连接
				return;	//返回
			}
			if(bb.remaining()==0) {	//如果缓冲已经读完
				outq.removeLast();	//移除该缓冲
			}
		}catch(Exception e) {	//捕捉异常
			System.out.println("Failed to write to client.");	//输出提示信息
			e.printStackTrace();	//输出异常信息
			disconnect(sk);	//关闭连接
		}
		if(outq.size()==0) {	//如果输出队列为空
			sk.interestOps(SelectionKey.OP_READ);	//注册读事件
		}
	}
	private void disconnect(SelectionKey sk) {	//定义如何关闭连接
		try {
			sk.channel().close();	//关闭连接
		} catch (IOException e) {}	//捕捉异常
	}
}

class Test36 implements Runnable{
	private Selector selector;	//声明管理
	protected Test36(String ip,int port) {	//定义如何构造
		SocketChannel channel=null;	//初始化套接字为空
		try {
			channel = SocketChannel.open();	//打开监听并获取套接字
			channel.configureBlocking(false);	//设置工作模式为非阻塞模式
			this.selector = SelectorProvider.provider().openSelector();	//获取管理
			channel.connect(new InetSocketAddress(ip,port));	//设置套接字的目标地址与端口号
			channel.register(selector, SelectionKey.OP_CONNECT);	//绑定到管理并注册连接事件
		}catch(Exception e) {	//捕捉异常
			if(channel!=null) {	//如果获取了套接字
				try {
					channel.close();	//关闭该套接字
				} catch (IOException e1) {	//捕捉异常
					e1.printStackTrace();	//输出异常信息
				}
			}
		}
	}
	public void run() {	//定义客户端如何工作
		while(true) {	//死循环
			if(!selector.isOpen()) {	//如果管理没有被打开
				break;	//跳出循环
			}
			try {
				selector.select();	//等待注册事件发生
			} catch (IOException e) {	//捕捉异常
				e.printStackTrace();	//输出异常
			}
			Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();	//获取迭代器
			while(ite.hasNext()) {	//如果有下一个元素
				SelectionKey key = ite.next();	//获取下一个元素
				ite.remove();	//移除当前元素
				if(key.isConnectable()) {	//如果是连接事件
					connect(key);	//接受连接
				}
				else if(key.isReadable()) {	//如果是读事件
					read(key);	//读取数据
				}
			}
		}
	}
	public void connect(SelectionKey key) {	//定义如何建立连接
		SocketChannel channel = (SocketChannel)key.channel();	//获取连接
		try {
			if(channel.isConnectionPending()) {	//如果连接没有准备好
				channel.finishConnect();	//继续准备该连接
			}
			channel.configureBlocking(false);	//设置阻塞模式为非阻塞
			channel.write(ByteBuffer.wrap(new String("hello server!\r\n").getBytes()));	//发送数据
			channel.register(selector, SelectionKey.OP_READ);	//绑定管理并注册读事件
		} catch (ClosedChannelException e) {	//捕捉套接字关闭异常
			e.printStackTrace();	//输出异常信息
		} catch (IOException e) {	//捕捉io流异常
			e.printStackTrace();	//输出异常信息
		}
	}
	public void read(SelectionKey key) {	//定义如何读数据
		SocketChannel channel = (SocketChannel)key.channel();	//获取连接类
		ByteBuffer buffer = ByteBuffer.allocate(100);	//新建缓冲区
		try {
			channel.read(buffer);	//读取数据
			byte[] data = buffer.array();	//转为字节型
			String msg = new String(data).trim();	//转为字符串并去除首尾空格
			System.out.println("客户端受到信息："+msg);	//输出客户端收到的消息
			channel.close();	//管理连接
			key.selector().close();	//关闭管理
		} catch (IOException e) {	//捕捉异常
			e.printStackTrace();	//输出异常信息
		}
	}
}