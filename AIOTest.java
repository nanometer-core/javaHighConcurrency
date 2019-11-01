package java�߲���;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AIOTest {
	public static void main(String[] args) throws InterruptedException {
		new AIOEchoServer().start();	//�½�һ��������̲�����
		new Thread(new AIOClient()).start();;
		Thread.sleep(1000);	//���߳�����
	}

}

/**
 * AIO������
 * @author nanometer
 */
class AIOEchoServer{
	public final static int PORT = 8000;	//����Ĭ�϶˿�Ϊ8000
	private AsynchronousServerSocketChannel server;	//�����첽����������
	public AIOEchoServer(){	//���������
		try {
			server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));	//������ָ���˿ڷ���������
		}catch (IOException e) {	//�������IO�쳣
			System.out.println("���������̴���ʧ�ܣ�����˿��Ƿ�ռ��");	//���������Ϣ
		}
		
	}
	
	/**
	 * ����������
	 * @author nanometer
	 */
	public void start(){
		System.out.println("Server listen on "+PORT);	//��������������Ķ˿ں�
		server.accept(null,new CompletionHandler<AsynchronousSocketChannel, Object>() {	//�������������� ������
			final ByteBuffer buffer = ByteBuffer.allocate(1024);	//ʵ�����ֽڻ���
			@Override
			public void completed(AsynchronousSocketChannel result, Object attachment) {	//ʵ�����ӳɹ��Ļص�����
				System.out.println(Thread.currentThread().getName());	//����߳�����
				Future<Integer> writeResult=null;	//������Լ
				try {
					buffer.clear();	//��ջ���
					result.read(buffer).get(100, TimeUnit.SECONDS);	//�ȴ�����
					buffer.flip();	//�ƶ�����λ�õ���λ
					writeResult=result.write(buffer);	//�����������
				}catch (InterruptedException|ExecutionException e) {	//������жϻ����ύʧ��
					e.printStackTrace();	//���ʧ����Ϣ
				}catch (TimeoutException e) {	//�����ʱδ����
					System.out.println("���ӳ�ʱ");	//������ӳ�ʱ
				}finally {
					try {
						server.accept(null,this);	//������һ��
						writeResult.get();	//�ȴ�������
						result.close();	//�ر�����
					}catch (Exception e) {	//��׽�쳣
						e.printStackTrace();	//����쳣��Ϣ
					}
				}
			}

			@Override
			public void failed(Throwable exc, Object attachment) {	//�������ʧ��
				System.out.println("����ʧ��"+exc);	//���ʧ����Ϣ
			}
		});
	}
}

/**
 * AIO�ͻ���
 * @author nanometer
 */
class AIOClient implements Runnable{
	AsynchronousSocketChannel client = null;	//��������
	@Override
	public void run() {
		try {
			client = AsynchronousSocketChannel.open();	//������
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		client.connect(new InetSocketAddress("localhost", 8000),null,new CompletionHandler<Void, Object>() {	//������ӵ����ص�8000�˿�

			@Override
			public void completed(Void result, Object attachment) {	//���ӳɹ��ص�
				// TODO Auto-generated method stub
				client.write(ByteBuffer.wrap("Hello!".getBytes()),null,new CompletionHandler<Integer, Object>() {	//д������

					@Override
					public void completed(Integer result, Object attachment) {	//д��ɹ��ص�
						try {
							ByteBuffer buffer = ByteBuffer.allocate(1024);	//ʵ��������
							client.read(buffer,buffer,new CompletionHandler<Integer,ByteBuffer>() {	//��ȡ����

								@Override
								public void completed(Integer result, ByteBuffer attachment) {	//��ȡ�ɹ��ص�
									buffer.flip();	//�ƶ�����λ�õ��ײ�
									System.out.println(new String(buffer.array()));	//�����ȡ������Ϣ
									try {
										client.close();	//�ر�����
									}catch (IOException e) {	//������ӹر��쳣
										System.out.println("�ر�����ʧ��");	//�����ʾ��Ϣ
									}
								}

								@Override
								public void failed(Throwable exc, ByteBuffer attachment) {	//��ȡʧ�ܻص�
									
								}
							});
						}catch (Exception e) {	//��׽�쳣
							e.printStackTrace();	//����쳣��Ϣ
						}
					}

					@Override
					public void failed(Throwable exc, Object attachment) {	//д��ʧ�ܻص�
						// TODO Auto-generated method stub
						
					}
				});
			}

			@Override
			public void failed(Throwable exc, Object attachment) {	//����ʧ�ܻص�
				// TODO Auto-generated method stub
				
			}
		});
		try {
			Thread.sleep(1000);	//�߳�����һ��
		} catch (InterruptedException e) {	//������ж�
			System.out.println("��⵽�жϣ��ͻ����߳��˳�");	//�����ʾ��Ϣ
		}
	}
	
}
