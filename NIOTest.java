package java�߲���;

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
	private Selector selector;	//����������
	private static ExecutorService tp = Executors.newCachedThreadPool();	//�����̳߳�
	public static Map<Socket, Long> time_start = new HashMap<Socket, Long>(10240);	//����map�ӿ�
	class EchoClient {	//�����ڲ���
		private LinkedList<ByteBuffer> outq;	//����������������
		EchoClient() {	//���幹�췽��
			outq = new LinkedList<ByteBuffer>();
		}
		public LinkedList<ByteBuffer> getOutputQueue() {	//��ȡ�������
			return outq;	//���ض���
		}
		public void enqueue(ByteBuffer bb) {	//ѹջ
			outq.addFirst(bb);	//ѹ�����
		}
	}
	class HandleMsg implements Runnable{	//�ڲ��ͻ��߳���
		SelectionKey sk;	//����������Լ
		ByteBuffer bb;	//����������
		public HandleMsg(SelectionKey sk,ByteBuffer bb) {	//���幹�췽��
			this.sk=sk;	//����������Լ
			this.bb=bb;	//���滺����
		}
		public void run() {	//���幤������
			EchoClient echoClient = (EchoClient)sk.attachment();	//��ȡ����
			echoClient.enqueue(bb);	//�򸽼�ѹջ
			sk.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);	//ע�����д�¼�
			selector.wakeup();	//֪ͨ����
		}
	}
	public static void main(String[] args) {	//�������ṩ���̿���
		NIOTest server = new NIOTest();	//�½���������
		tp.execute(server);	//����������
		Test36 client = new Test36("localhost", 8000);	//�½��ͻ�����
		tp.execute(client);	//�����ͻ���
	}
	public void run() {	//�����������ҵ������
		try {
			startServer();	//��������
		} catch (Exception e) {	//��׽�쳣
			e.printStackTrace();	//����쳣
		}
	}
	private void startServer() throws Exception {	//���������������
		selector = SelectorProvider.provider().openSelector();	//��ȡ����
		ServerSocketChannel ssc = ServerSocketChannel.open();	//�򿪼���
		ssc.configureBlocking(false);	//��������ģʽΪ������
		InetSocketAddress isa = new InetSocketAddress(8000);	//�󶨶˿�
		ssc.socket().bind(isa);	//�󶨼����˿�
		SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);	//ע���¼�Ϊ����
		while(true) {
			selector.select();	//�ȴ�ע����¼�
			Set readyKeys = selector.selectedKeys();	//��ȡ���д����¼�����
			Iterator i = readyKeys.iterator();	//��ȡ������
			long e = 0;	//��ʼ���¼�Ϊ��
			while(i.hasNext()) {	//�ڵ�������Ϊ��ʱѭ��
				SelectionKey sk = (SelectionKey) i.next();	//��ȡ��һ����
				i.remove();	//�������Ƴ�
				if(sk.isAcceptable()) {	//���������
					doAccept(sk);	//��������
				}
				else if(sk.isValid() && sk.isReadable()) {	//���������
					if(!time_start.containsKey(((SocketChannel)sk.channel()).socket())) {	//�����ǰû������
						time_start .put(((SocketChannel)sk.channel()).socket(), System.currentTimeMillis());	//�����һ�������ʱ��
						doRead(sk);	//�������봦��
					}
				}
				else if(sk.isValid() && sk.isWritable()) {	//��������
					doWrite(sk);	//�����������
					e = System.currentTimeMillis();	//��ȡ��ǰ��ʱ��
					long b = time_start.remove(((SocketChannel)sk.channel()).socket());	//�Ƴ���ʼʱ��
					System.out.println("spend:" + (e-b)+ "ms");	//�����ӳ�ʱ��
				}
			}
		}
	}
	private void doAccept(SelectionKey sk) {	//������ν�������
		ServerSocketChannel server = (ServerSocketChannel)sk.channel();	//��ȡ����
		SocketChannel clientchChannel;	//�����µ��׽���
		try {
			clientchChannel = server.accept();	//ͬ�����󲢻�ȡ�µ��׽���
			clientchChannel.configureBlocking(false);	//��������ģʽΪ������ģʽ
			SelectionKey clienKey = clientchChannel.register(selector, SelectionKey.OP_READ);	//ע����¼�
			EchoClient echoClient = new EchoClient();	//�½�һ������
			clienKey.attach(echoClient);	//���������ӵ���Լ��
			InetAddress clientAddress = clientchChannel.socket().getInetAddress();	//��ȡ�ͻ���ַ
			System.out.println("Accepted connection from "+clientAddress.getHostAddress()+".");	//���������Ϣ
		}catch(Exception e) {	//��׽�쳣
			System.out.println("failed to accept new clilent.");	//�����ʾ��Ϣ
			e.printStackTrace();	//����쳣��Ϣ
		}
	}
	private void doRead(SelectionKey sk) {	//������ζ�ȡ����
		SocketChannel channel = (SocketChannel)sk.channel();	//��ȡ������
		ByteBuffer bb = ByteBuffer.allocate(8192);	//�½�����ռ�
		int len;	//��������
		try {
			len = channel.read(bb);	//���Զ�ȡ����
			if(len<0) {	//�����ȡ�����ݳ��Ȳ�����
				disconnect(sk);	//�ر�����
				return;	//����
			}
		}catch(Exception e) {	//��׽�쳣
			System.out.println("failed to read form client.");	//�����ʾ��Ϣ
			e.printStackTrace();	//����쳣��Ϣ
			disconnect(sk);	//�ر�����
			return;	//����
		}
		bb.flip();	//���ö�ȡλ��
		tp.execute(new HandleMsg(sk,bb));	//�����ͻ��߳�
	}
	private void doWrite(SelectionKey sk) {	//�������д������
		SocketChannel channnel = (SocketChannel)sk.channel();	//��ȡ������
		EchoClient echoClient = (EchoClient)sk.attachment();	//��ȡ����
		LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();	//��ȡ�������
		ByteBuffer bb = outq.getLast();	//��ȡ�������һ��Ԫ��
		try {
			int len = channnel.write(bb);	//�������������ĳ���
			if(len==-1) {	//������Ȳ�����
				disconnect(sk);	//�ر�����
				return;	//����
			}
			if(bb.remaining()==0) {	//��������Ѿ�����
				outq.removeLast();	//�Ƴ��û���
			}
		}catch(Exception e) {	//��׽�쳣
			System.out.println("Failed to write to client.");	//�����ʾ��Ϣ
			e.printStackTrace();	//����쳣��Ϣ
			disconnect(sk);	//�ر�����
		}
		if(outq.size()==0) {	//����������Ϊ��
			sk.interestOps(SelectionKey.OP_READ);	//ע����¼�
		}
	}
	private void disconnect(SelectionKey sk) {	//������ιر�����
		try {
			sk.channel().close();	//�ر�����
		} catch (IOException e) {}	//��׽�쳣
	}
}

class Test36 implements Runnable{
	private Selector selector;	//��������
	protected Test36(String ip,int port) {	//������ι���
		SocketChannel channel=null;	//��ʼ���׽���Ϊ��
		try {
			channel = SocketChannel.open();	//�򿪼�������ȡ�׽���
			channel.configureBlocking(false);	//���ù���ģʽΪ������ģʽ
			this.selector = SelectorProvider.provider().openSelector();	//��ȡ����
			channel.connect(new InetSocketAddress(ip,port));	//�����׽��ֵ�Ŀ���ַ��˿ں�
			channel.register(selector, SelectionKey.OP_CONNECT);	//�󶨵�����ע�������¼�
		}catch(Exception e) {	//��׽�쳣
			if(channel!=null) {	//�����ȡ���׽���
				try {
					channel.close();	//�رո��׽���
				} catch (IOException e1) {	//��׽�쳣
					e1.printStackTrace();	//����쳣��Ϣ
				}
			}
		}
	}
	public void run() {	//����ͻ�����ι���
		while(true) {	//��ѭ��
			if(!selector.isOpen()) {	//�������û�б���
				break;	//����ѭ��
			}
			try {
				selector.select();	//�ȴ�ע���¼�����
			} catch (IOException e) {	//��׽�쳣
				e.printStackTrace();	//����쳣
			}
			Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();	//��ȡ������
			while(ite.hasNext()) {	//�������һ��Ԫ��
				SelectionKey key = ite.next();	//��ȡ��һ��Ԫ��
				ite.remove();	//�Ƴ���ǰԪ��
				if(key.isConnectable()) {	//����������¼�
					connect(key);	//��������
				}
				else if(key.isReadable()) {	//����Ƕ��¼�
					read(key);	//��ȡ����
				}
			}
		}
	}
	public void connect(SelectionKey key) {	//������ν�������
		SocketChannel channel = (SocketChannel)key.channel();	//��ȡ����
		try {
			if(channel.isConnectionPending()) {	//�������û��׼����
				channel.finishConnect();	//����׼��������
			}
			channel.configureBlocking(false);	//��������ģʽΪ������
			channel.write(ByteBuffer.wrap(new String("hello server!\r\n").getBytes()));	//��������
			channel.register(selector, SelectionKey.OP_READ);	//�󶨹���ע����¼�
		} catch (ClosedChannelException e) {	//��׽�׽��ֹر��쳣
			e.printStackTrace();	//����쳣��Ϣ
		} catch (IOException e) {	//��׽io���쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
	}
	public void read(SelectionKey key) {	//������ζ�����
		SocketChannel channel = (SocketChannel)key.channel();	//��ȡ������
		ByteBuffer buffer = ByteBuffer.allocate(100);	//�½�������
		try {
			channel.read(buffer);	//��ȡ����
			byte[] data = buffer.array();	//תΪ�ֽ���
			String msg = new String(data).trim();	//תΪ�ַ�����ȥ����β�ո�
			System.out.println("�ͻ����ܵ���Ϣ��"+msg);	//����ͻ����յ�����Ϣ
			channel.close();	//��������
			key.selector().close();	//�رչ���
		} catch (IOException e) {	//��׽�쳣
			e.printStackTrace();	//����쳣��Ϣ
		}
	}
}