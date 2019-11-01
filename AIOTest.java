package java高并发;

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
		new AIOEchoServer().start();	//新建一个服务进程并启动
		new Thread(new AIOClient()).start();;
		Thread.sleep(1000);	//主线程休眠
	}

}

/**
 * AIO服务器
 * @author nanometer
 */
class AIOEchoServer{
	public final static int PORT = 8000;	//设置默认端口为8000
	private AsynchronousServerSocketChannel server;	//声明异步服务器连接
	public AIOEchoServer(){	//构造服务器
		try {
			server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));	//开启在指定端口服务器连接
		}catch (IOException e) {	//如果出现IO异常
			System.out.println("服务器进程创建失败，请检查端口是否被占用");	//输出错误信息
		}
		
	}
	
	/**
	 * 启动服务器
	 * @author nanometer
	 */
	public void start(){
		System.out.println("Server listen on "+PORT);	//输出服务器监听的端口号
		server.accept(null,new CompletionHandler<AsynchronousSocketChannel, Object>() {	//创建服务器连接 非阻塞
			final ByteBuffer buffer = ByteBuffer.allocate(1024);	//实例化字节缓冲
			@Override
			public void completed(AsynchronousSocketChannel result, Object attachment) {	//实现连接成功的回调方法
				System.out.println(Thread.currentThread().getName());	//输出线程名称
				Future<Integer> writeResult=null;	//声明契约
				try {
					buffer.clear();	//清空缓冲
					result.read(buffer).get(100, TimeUnit.SECONDS);	//等待连接
					buffer.flip();	//移动缓冲位置到首位
					writeResult=result.write(buffer);	//输出缓冲数据
				}catch (InterruptedException|ExecutionException e) {	//如果被中断或者提交失败
					e.printStackTrace();	//输出失败信息
				}catch (TimeoutException e) {	//如果超时未连接
					System.out.println("连接超时");	//输出连接超时
				}finally {
					try {
						server.accept(null,this);	//连接下一次
						writeResult.get();	//等待输出完毕
						result.close();	//关闭连接
					}catch (Exception e) {	//捕捉异常
						e.printStackTrace();	//输出异常信息
					}
				}
			}

			@Override
			public void failed(Throwable exc, Object attachment) {	//如果链接失败
				System.out.println("连接失败"+exc);	//输出失败信息
			}
		});
	}
}

/**
 * AIO客户端
 * @author nanometer
 */
class AIOClient implements Runnable{
	AsynchronousSocketChannel client = null;	//声明连接
	@Override
	public void run() {
		try {
			client = AsynchronousSocketChannel.open();	//打开连接
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		client.connect(new InetSocketAddress("localhost", 8000),null,new CompletionHandler<Void, Object>() {	//添加连接到本地的8000端口

			@Override
			public void completed(Void result, Object attachment) {	//链接成功回调
				// TODO Auto-generated method stub
				client.write(ByteBuffer.wrap("Hello!".getBytes()),null,new CompletionHandler<Integer, Object>() {	//写入数据

					@Override
					public void completed(Integer result, Object attachment) {	//写入成功回调
						try {
							ByteBuffer buffer = ByteBuffer.allocate(1024);	//实例化缓冲
							client.read(buffer,buffer,new CompletionHandler<Integer,ByteBuffer>() {	//读取缓冲

								@Override
								public void completed(Integer result, ByteBuffer attachment) {	//读取成功回调
									buffer.flip();	//移动缓冲位置到首部
									System.out.println(new String(buffer.array()));	//输出读取到的信息
									try {
										client.close();	//关闭连接
									}catch (IOException e) {	//如果链接关闭异常
										System.out.println("关闭连接失败");	//输出提示信息
									}
								}

								@Override
								public void failed(Throwable exc, ByteBuffer attachment) {	//读取失败回调
									
								}
							});
						}catch (Exception e) {	//捕捉异常
							e.printStackTrace();	//输出异常信息
						}
					}

					@Override
					public void failed(Throwable exc, Object attachment) {	//写入失败回调
						// TODO Auto-generated method stub
						
					}
				});
			}

			@Override
			public void failed(Throwable exc, Object attachment) {	//连接失败回调
				// TODO Auto-generated method stub
				
			}
		});
		try {
			Thread.sleep(1000);	//线程休眠一秒
		} catch (InterruptedException e) {	//如果被中断
			System.out.println("检测到中断，客户端线程退出");	//输出提示信息
		}
	}
	
}
